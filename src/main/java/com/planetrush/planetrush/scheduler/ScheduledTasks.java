package com.planetrush.planetrush.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.planetrush.planetrush.core.exception.handler.PlanetExceptionHandler;
import com.planetrush.planetrush.member.domain.ChallengeHistory;
import com.planetrush.planetrush.member.domain.Member;
import com.planetrush.planetrush.member.domain.ProgressAvg;
import com.planetrush.planetrush.member.repository.ChallengeHistoryRepository;
import com.planetrush.planetrush.member.repository.MemberRepository;
import com.planetrush.planetrush.member.repository.ProgressAvgRepository;
import com.planetrush.planetrush.member.repository.custom.ChallengeHistoryRepositoryCustom;
import com.planetrush.planetrush.member.repository.custom.ProgressAvgRepositoryCustom;
import com.planetrush.planetrush.planet.domain.Category;
import com.planetrush.planetrush.planet.domain.Planet;
import com.planetrush.planetrush.planet.domain.Resident;
import com.planetrush.planetrush.planet.repository.ResidentRepository;
import com.planetrush.planetrush.planet.repository.custom.PlanetRepositoryCustom;
import com.planetrush.planetrush.planet.repository.custom.ResidentRepositoryCustom;
import com.planetrush.planetrush.scheduler.log.JobLog;
import com.planetrush.planetrush.scheduler.log.JobLogRepository;
import com.planetrush.planetrush.verification.domain.VerificationRecord;
import com.planetrush.planetrush.verification.repository.custom.VerificationRecordRepositoryCustom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class ScheduledTasks {

	private final ResidentRepository residentRepository;
	private final ChallengeHistoryRepository challengeHistoryRepository;
	private final PlanetRepositoryCustom planetRepositoryCustom;
	private final ResidentRepositoryCustom residentRepositoryCustom;
	private final VerificationRecordRepositoryCustom verificationRecordRepositoryCustom;
	private final ChallengeHistoryRepositoryCustom challengeHistoryRepositoryCustom;
	private final ProgressAvgRepository progressAvgRepository;
	private final MemberRepository memberRepository;
	private final PlanetExceptionHandler planetExceptionHandler;
	private final ProgressAvgRepositoryCustom progressAvgRepositoryCustom;

	private final JobLogRepository jobLogRepository;

	/**
	 * <p매일 자정마다 챌린지가 시작되어야 하는 행성의 상태를 READY에서 IN_PROGRESS로 변경합니다.></p>
	 * <p매일 자정마다 챌린지가 종료되어야 하는 행성의 상태를 IN_PROFRESS에서 UNDER_REVIEW로 변경합니다.></p>
	 */
	@Scheduled(cron = "0 0/10 * * * ?")
	@Transactional
	void changePlanetReadyToInProgress() {
		log.info("[SCHEDULE] changePlanetReadyToInProgress start");
		JobLog joblog = new JobLog("changePlanetReadyToInProgress");
		planetRepositoryCustom.updateStatusReadyToInProgress();
		planetRepositoryCustom.updateStatusInProgressToUnderReview();
		joblog.finish();
		jobLogRepository.save(joblog);
	}

	/**
	 * <p>하루에 한 번, 진행중, 심사중인 행성을 조회합니다.</p>
	 * <p>행성 입주자들을 대상으로 마지막 인증 기록을 조회합니다.</p>
	 * <p>마지막 인증 기록 날짜와 현재 날짜가 4일 차이가 나면 퇴출됩니다. 또한 챌린지는 실패 상태로 기록됩니다.</p>
	 */
	@Scheduled(cron = "0 2/10 * * * ?")
	@Transactional
	void removeIfLastVerificationOlderThanThreeDays() {
		log.info("[SCHEDULE] removeIfLastVerificationOlderThanThreeDays start");
		JobLog joblog = new JobLog("removeIfLastVerificationOlderThanThreeDays");
		List<Planet> planets = planetRepositoryCustom.findAllStatusIsInProgressAndUnderReview();
		planets.forEach(planet -> {
			List<Resident> residents = residentRepository.findByPlanetId(planet.getId());
			residents.forEach(resident -> {
				Member member = resident.getMember();
				VerificationRecord latestRecord = verificationRecordRepositoryCustom.findLatestRecord(
					member, planet);
				/* 인증 기록 없이 3일이 지난 경우 */
				if (latestRecord == null && planet.calcElapsedPeriod() >= 4) {
					expulsionFromPlanet(member, planet);
				}
				/* 마지막 인증을 한 후 3일이 지난 경우 */
				if (latestRecord != null && latestRecord.isDifferenceGreaterThanFourDays()) {
					expulsionFromPlanet(member, planet);
				}
			});
		});
		joblog.finish();
		jobLogRepository.save(joblog);
	}

	private void expulsionFromPlanet(Member member, Planet planet) {
		log.info("[EXPULSION] the last verificationRecordId = {}", member.getId());
		residentRepositoryCustom.banMemberFromPlanet(member, planet);
		planet.participantExpulsion();
	}

	/**
	 * <p>하루에 한 번, 심사 대기중인 행성을 조회합니다.</p>
	 * <p>개인 기록을 저장하고 행성 전체 진행률을 계산합니다.</p>
	 * <p>전체 진행률이 70퍼센트 이상이면 성공, 아니면 실패 상태로 저장됩니다.</p>
	 */
	@Scheduled(cron = "0 4/10 * * * ?")
	@Transactional
	void completeOrDestroyPlanet() {
		log.info("[SCHEDULE] completeOrDestroyPlanet start");
		JobLog joblog = new JobLog("completeOrDestroyPlanet");
		List<Planet> planets = planetRepositoryCustom.findAllStatusIsUnderReview();
		List<ChallengeHistory> challengeHistories = new ArrayList<>();
		planets.forEach(planet -> {
			long totalVerificationCnt = planet.calcTotalVerificationCnt();
			int actualVerificationCnt = 0;
			List<Resident> residents = residentRepository.findByPlanetId(planet.getId());
			for (Resident resident : residents) {
				List<VerificationRecord> records = verificationRecordRepositoryCustom.findAllByMemberAndPlanet(
					resident.getMember(), resident.getPlanet());
				double memberProgress = records.size() / (double)totalVerificationCnt * 100;
				actualVerificationCnt += records.size();
				challengeHistories.add(ChallengeHistory.builder()
					.member(resident.getMember())
					.planetImgUrl(planet.getPlanetImg())
					.planetName(planet.getName())
					.challengeContent(planet.getContent())
					.category(planet.getCategory())
					.progress(memberProgress)
					.build());
			}
			double totalProgressRatio = (double)actualVerificationCnt / (totalVerificationCnt * residents.size()) * 100;
			if (totalProgressRatio >= 70.0) {
				planet.complete();
				challengeHistories.forEach(ChallengeHistory::successResult);
			} else {
				planet.destroy();
				challengeHistories.forEach(ChallengeHistory::failResult);
			}
			challengeHistoryRepository.saveAll(challengeHistories);
		});
		joblog.finish();
		jobLogRepository.save(joblog);
	}

	/**
	 * <p>개인의 카테고리별 완주율 평균을 저장합니다.</p>
	 * <p>완주 기록이 없는 카테고리일 경우 null이 저장됩니다.</p>
	 */
	@Scheduled(cron = "0 6/10 * * * ?")
	@Transactional
	void progressCalculation() {
		log.info("[SCHEDULE] progressCalculation start");
		JobLog joblog = new JobLog("progressCalculation");
		Map<Long, Map<Category, Double>> averageScoreByMember = challengeHistoryRepositoryCustom.getAverageScoreByMember();
		averageScoreByMember.forEach((memberId, scoreByCategoryMap) -> {
			double beautyAvg = scoreByCategoryMap.getOrDefault(Category.BEAUTY, -1.0);
			double exerciseAvg = scoreByCategoryMap.getOrDefault(Category.EXERCISE, -1.0);
			double lifeAvg = scoreByCategoryMap.getOrDefault(Category.LIFE, -1.0);
			double studyAvg = scoreByCategoryMap.getOrDefault(Category.STUDY, -1.0);
			double etcAvg = scoreByCategoryMap.getOrDefault(Category.ETC, -1.0);
			int totalCnt = 0;
			double sum = 0.0;
			for (Category category : Category.values()) {
				double avg = scoreByCategoryMap.getOrDefault(category, -1.0);
				if (avg != -1.0) {
					sum += avg;
					totalCnt++;
				}
			}
			double totalAvg = sum / totalCnt;
			progressAvgRepositoryCustom.updateProgressAvg(memberId, ProgressAvg.builder()
				.beautyAvg(beautyAvg)
				.exerciseAvg(exerciseAvg)
				.lifeAvg(lifeAvg)
				.studyAvg(studyAvg)
				.etcAvg(etcAvg)
				.totalAvg(totalAvg)
				.build());
		});
		joblog.finish();
		jobLogRepository.save(joblog);
	}

}

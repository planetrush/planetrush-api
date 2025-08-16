package com.planetrush.planetrush.planet;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.planetrush.planetrush.IntegrationTest;
import com.planetrush.planetrush.fixture.MemberFixture;
import com.planetrush.planetrush.fixture.PlanetFixture;
import com.planetrush.planetrush.fixture.ResidentFixture;
import com.planetrush.planetrush.member.domain.Member;
import com.planetrush.planetrush.member.repository.MemberRepository;
import com.planetrush.planetrush.planet.domain.Planet;
import com.planetrush.planetrush.planet.domain.PlanetStatus;
import com.planetrush.planetrush.planet.domain.Resident;
import com.planetrush.planetrush.planet.exception.PlanetDestroyedException;
import com.planetrush.planetrush.planet.repository.DefaultPlanetImgRepository;
import com.planetrush.planetrush.planet.repository.PlanetRepository;
import com.planetrush.planetrush.planet.repository.ResidentRepository;
import com.planetrush.planetrush.planet.repository.custom.PlanetRepositoryCustom;
import com.planetrush.planetrush.planet.repository.custom.ResidentRepositoryCustom;
import com.planetrush.planetrush.planet.service.PlanetServiceImpl;
import com.planetrush.planetrush.planet.service.dto.PlanetSubscriptionDto;
import com.planetrush.planetrush.verification.repository.custom.VerificationRecordRepositoryCustom;

public class PlanetIntegrationTest extends IntegrationTest {

	@Autowired
	private PlanetServiceImpl planetService;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private PlanetRepository planetRepository;
	@Autowired
	private ResidentRepository residentRepository;
	@Autowired
	private DefaultPlanetImgRepository defaultPlanetImgRepository;
	@Autowired
	private PlanetRepositoryCustom planetRepositoryCustom;
	@Autowired
	private ResidentRepositoryCustom residentRepositoryCustom;
	@Autowired
	private VerificationRecordRepositoryCustom verificationRecordRepositoryCustom;

	@BeforeEach
	void setUp() {
		List<Member> members = MemberFixture.activeMembers(2);
		memberRepository.saveAll(members);

		Planet planet = PlanetFixture.readyPlanet();
		planetRepository.save(planet);

		Resident resident = ResidentFixture.creator(members.get(0), planet);
		residentRepository.save(resident);
	}

	@AfterEach
	void clear() {
		residentRepository.deleteAll();
		planetRepository.deleteAll();
		memberRepository.deleteAll();
	}

	@DisplayName("가입이 탈퇴보다 먼저 요청될 경우 한 명의 거주자만 남는다.")
	@RepeatedTest(100)
	void should_() throws InterruptedException {
		// GIVEN
		List<Member> members = memberRepository.findAll();
		Member member1 = members.get(0);
		Member member2 = members.get(1);

		List<Planet> planets = planetRepository.findAll();
		Planet planet = planets.get(0);

		PlanetSubscriptionDto registerDto = PlanetSubscriptionDto.builder()
			.planetId(planet.getId())
			.memberId(member2.getId())
			.build();

		PlanetSubscriptionDto deleteDto = PlanetSubscriptionDto.builder()
			.planetId(planet.getId())
			.memberId(member1.getId())
			.build();

		AtomicBoolean isDestroyed = new AtomicBoolean(false);
		CountDownLatch startLatch = new CountDownLatch(1);
		CountDownLatch doneLatch = new CountDownLatch(2);

		Runnable registerResidentTask = () -> {
			try {
				startLatch.await();
				planetService.registerResident(registerDto);
			} catch (Exception e) {
				e.printStackTrace();
				if (e instanceof PlanetDestroyedException) {
					isDestroyed.set(true);
				}
			} finally {
				doneLatch.countDown();
			}
		};

		Runnable deleteResidentTask = () -> {
			try {
				startLatch.await();
				planetService.deleteResident(deleteDto);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				doneLatch.countDown();
			}
		};

		// WHEN
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.submit(registerResidentTask);
		executor.submit(deleteResidentTask);

		startLatch.countDown();
		doneLatch.await();

		// THEN
		planet = planetRepository.findById(planet.getId()).get();
		if (isDestroyed.get()) {
			assertThat(planet.getStatus()).isEqualTo(PlanetStatus.DESTROYED);
			assertThat(planet.getCurrentParticipants()).isEqualTo(0);
		} else {
			assertThat(planet.getStatus()).isEqualTo(PlanetStatus.READY);
			assertThat(planet.getCurrentParticipants()).isEqualTo(1);
		}
	}

	@DisplayName("행성 가입이 탈퇴보다 먼저 요청될 경우 한 명의 거주자만 남는다.")
	@Test
	void should_leave_one_resident_when_register_before_delete() {
		// GIVEN
		List<Member> members = memberRepository.findAll();
		Member member1 = members.get(0);
		Member member2 = members.get(1);

		List<Planet> planets = planetRepository.findAll();
		Planet planet = planets.get(0);

		PlanetSubscriptionDto registerDto = PlanetSubscriptionDto.builder()
			.planetId(planet.getId())
			.memberId(member2.getId())
			.build();

		PlanetSubscriptionDto deleteDto = PlanetSubscriptionDto.builder()
			.planetId(planet.getId())
			.memberId(member1.getId())
			.build();

		// WHEN
		planetService.registerResident(registerDto);
		planetService.deleteResident(deleteDto);

		// THEN
		planet = planetRepository.findById(planet.getId()).get();

		assertThat(planet.getCurrentParticipants()).isEqualTo(1);
		assertThat(planet.getStatus()).isEqualTo(PlanetStatus.READY);
	}

	@DisplayName("행성 탈퇴가 가입보다 먼저 요청될 경우 한 명의 거주자만 남는다.")
	@Test
	void register_and_delete() throws InterruptedException {
		// GIVEN
		List<Member> members = memberRepository.findAll();
		Member member1 = members.get(0);
		Member member2 = members.get(1);

		List<Planet> planets = planetRepository.findAll();
		Planet planet = planets.get(0);

		PlanetSubscriptionDto registerDto = PlanetSubscriptionDto.builder()
			.planetId(planet.getId())
			.memberId(member2.getId())
			.build();

		PlanetSubscriptionDto deleteDto = PlanetSubscriptionDto.builder()
			.planetId(planet.getId())
			.memberId(member1.getId())
			.build();

		// WHEN
		planetService.deleteResident(deleteDto);

		// THEN
		planet = planetRepository.findById(planet.getId()).get();

		assertThatThrownBy(() -> planetService.registerResident(registerDto))
			.isInstanceOf(PlanetDestroyedException.class);
		assertThat(planet.getCurrentParticipants()).isEqualTo(0);
		assertThat(planet.getStatus()).isEqualTo(PlanetStatus.DESTROYED);
	}
}
package com.planetrush.planetrush.member.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.planetrush.planetrush.core.aop.annotation.RequireJwtToken;
import com.planetrush.planetrush.core.aop.member.MemberContext;
import com.planetrush.planetrush.core.template.response.BaseResponse;
import com.planetrush.planetrush.member.controller.response.GetMyCollectionRes;
import com.planetrush.planetrush.member.service.GetMyCollectionService;
import com.planetrush.planetrush.member.service.dto.CollectionSearchCond;
import com.planetrush.planetrush.member.service.dto.PlanetCollectionDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class GetMyCollectionController extends MemberController {

	private final GetMyCollectionService getPlanetCollectionService;

	/**
	 * 사용자의 완료한 행성 컬렉션을 가져옵니다.
	 * @return ResponseEntity
	 */
	@RequireJwtToken
	@GetMapping("/collections")
	public ResponseEntity<BaseResponse<GetMyCollectionRes>> getPlanetCollections(
		@RequestParam(value = "lh-id", required = false) Long lastHistoryId,
		@RequestParam("size") int size) {
		Long memberId = MemberContext.getMemberId();
		List<PlanetCollectionDto> planetCollection = getPlanetCollectionService.getPlanetCollections(
			CollectionSearchCond.builder().memberId(memberId).lastHistoryId(lastHistoryId).size(size).build());

		boolean hasNext = false;
		if(planetCollection.size() > size) {
			planetCollection.remove(size);
			hasNext = true;
		}

		return ResponseEntity.ok(
			BaseResponse.ofSuccess(GetMyCollectionRes.builder()
				.planetCollection(planetCollection)
				.hasNext(hasNext)
				.build()));
	}
}

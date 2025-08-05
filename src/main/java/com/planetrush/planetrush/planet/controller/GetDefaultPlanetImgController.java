package com.planetrush.planetrush.planet.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planetrush.planetrush.core.template.response.BaseResponse;
import com.planetrush.planetrush.planet.service.GetDefaultPlanetImgService;
import com.planetrush.planetrush.planet.service.dto.GetDefaultPlanetImgDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class GetDefaultPlanetImgController extends PlanetController {

	private final GetDefaultPlanetImgService getDefaultPlanetImgService;

	/**
	 * 모든 기본 행성 이미지 URL을 불러옵니다.
	 * @return 모든 기본 행성 이미지의 URL을 담은 List를 포함한 ResponseEntity
	 */
	@GetMapping("/images")
	public ResponseEntity<BaseResponse<List<GetDefaultPlanetImgDto>>> getAllPlanetImgs() {
		return ResponseEntity.ok(BaseResponse.ofSuccess(getDefaultPlanetImgService.getAllImgUrls()));
	}

}

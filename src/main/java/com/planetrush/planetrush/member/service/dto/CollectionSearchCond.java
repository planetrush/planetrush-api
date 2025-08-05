package com.planetrush.planetrush.member.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CollectionSearchCond {

	Long memberId;
	Long lastHistoryId;
	int size;
}

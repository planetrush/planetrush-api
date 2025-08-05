package com.planetrush.planetrush.member.domain;

import java.util.Random;

public enum Nickname {

	STRONG_TIGER("강한 호랑이"),
	SHINING_STAR("빛나는 별"),
	FAST_CHEETAH("빠른 치타"),
	CALM_RIVER("고요한 강"),
	LITTLE_BIRD("작은 새"),
	BRAVE_LION("용감한 사자"),
	HAPPY_TREE("행복한 나무"),
	SOUND_OF_WIND("바람의 소리"),
	BLUE_SEA("푸른 바다"),
	WINGED_ANGEL("날개 달린 천사"),
	MILKY_WAY("은하수"),
	MYSTERIOUS_FOREST("신비한 숲"),
	QUIET_MOON("조용한 달"),
	RED_SUN("붉은 태양"),
	FRESH_SPRING("산뜻한 봄"),
	COOL_BREEZE("시원한 바람"),
	BLUE_SKY("파란 하늘"),
	WHITE_CLOUD("흰 구름"),
	SMILING_FACE("웃는 얼굴"),
	FREE_SPIRIT("자유로운 영혼"),
	HAPPY_FOX("행복한 여우"),
	GREEN_TREE("초록 나무"),
	SPARKLING_WAVE("반짝이는 물결"),
	WARM_SUNSHINE("따뜻한 햇살"),
	COLD_ICE("차가운 얼음"),
	AZURE_SKY("푸른 하늘"),
	FLASHING_LIGHTNING("번쩍이는 번개"),
	SLOW_TURTLE("느린 거북이"),
	FRAGRANT_FLOWER("향기로운 꽃"),
	STRONG_WIND("힘찬 바람");

	private final String koreanName;
	private static final Random RANDOM = new Random();

	Nickname(String koreanName) {
		this.koreanName = koreanName;
	}

	private String getKoreanName() {
		return koreanName;
	}

	public static Nickname getRandomNickname() {
		Nickname[] nicknames = values();
		return nicknames[RANDOM.nextInt(nicknames.length)];
	}

	public static String getRandomKoreanNickname() {
		return getRandomNickname().getKoreanName();
	}
}

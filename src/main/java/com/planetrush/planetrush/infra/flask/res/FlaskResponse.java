package com.planetrush.planetrush.infra.flask.res;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FlaskResponse<T> {

	private String code;
	private String message;
	private boolean success;
	private T data;

}

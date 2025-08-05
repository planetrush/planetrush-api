package com.planetrush.planetrush.core.exception.handler;

import java.util.Enumeration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.planetrush.planetrush.core.mattermost.NotificationManager;
import com.planetrush.planetrush.core.template.response.BaseResponse;
import com.planetrush.planetrush.core.template.response.ResponseCode;
import com.planetrush.planetrush.planet.exception.InvalidStartDateException;
import com.planetrush.planetrush.planet.exception.NegativeParticipantCountException;
import com.planetrush.planetrush.planet.exception.PlanetDestroyedException;
import com.planetrush.planetrush.planet.exception.PlanetNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class PlanetExceptionHandler {

	private final NotificationManager nm;

	@ExceptionHandler(PlanetNotFoundException.class)
	public ResponseEntity<BaseResponse<Object>> handlePlanetNotFoundException(PlanetNotFoundException e,
		HttpServletRequest req) {
		log.info(e.getMessage());
		nm.sendNotification(e, req.getRequestURI(), getParams(req));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.ofFail(ResponseCode.PLANET_NOT_FOUND));
	}

	@ExceptionHandler(NegativeParticipantCountException.class)
	public ResponseEntity<BaseResponse<Object>> handleResidentLimitExceededException(
		NegativeParticipantCountException e, HttpServletRequest req) {
		log.info(e.getMessage());
		nm.sendNotification(e, req.getRequestURI(), getParams(req));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(BaseResponse.ofFail(ResponseCode.PARTICIPANTS_OVERFLOW));
	}

	private String getParams(HttpServletRequest req) {
		StringBuilder params = new StringBuilder();
		Enumeration<String> keys = req.getParameterNames();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			params.append("- ").append(key).append(" : ").append(req.getParameter(key)).append("/n");
		}
		return params.toString();
	}

	@ExceptionHandler(PlanetDestroyedException.class)
	public ResponseEntity<BaseResponse<Object>> handlePlanetDestroyedException(
		PlanetDestroyedException e, HttpServletRequest req) {
		log.info(e.getMessage());
		nm.sendNotification(e, req.getRequestURI(), getParams(req));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(BaseResponse.ofFail(ResponseCode.PLANET_IS_DESTROYED));
	}

	@ExceptionHandler(InvalidStartDateException.class)
	public ResponseEntity<BaseResponse<Object>> handleInvalidStartDateException(
		InvalidStartDateException e, HttpServletRequest req) {
		log.info(e.getMessage());
		nm.sendNotification(e, req.getRequestURI(), getParams(req));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(BaseResponse.ofFail(ResponseCode.INVALID_START_DATE));
	}
}

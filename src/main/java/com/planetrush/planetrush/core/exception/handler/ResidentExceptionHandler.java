package com.planetrush.planetrush.core.exception.handler;

import java.util.Enumeration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.planetrush.planetrush.core.mattermost.NotificationManager;
import com.planetrush.planetrush.core.template.response.BaseResponse;
import com.planetrush.planetrush.core.template.response.ResponseCode;
import com.planetrush.planetrush.planet.exception.RegisterResidentTimeoutException;
import com.planetrush.planetrush.planet.exception.ResidentAlreadyExistsException;
import com.planetrush.planetrush.planet.exception.ResidentExitTimeoutException;
import com.planetrush.planetrush.planet.exception.ResidentNotFoundException;
import com.planetrush.planetrush.planet.exception.ResidentOverflowException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ResidentExceptionHandler {

	private final NotificationManager nm;

	@ExceptionHandler(ResidentAlreadyExistsException.class)
	public ResponseEntity<BaseResponse<Object>> handleResidentAlreadyExistsException(
		ResidentAlreadyExistsException e, HttpServletRequest req) {
		log.info(e.getMessage());
		nm.sendNotification(e, req.getRequestURI(), getParams(req));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(BaseResponse.ofFail(ResponseCode.ALREADY_EXIST_RESIDENT));
	}

	@ExceptionHandler(ResidentNotFoundException.class)
	public ResponseEntity<BaseResponse<Object>> handleResidentNotFoundException(ResidentNotFoundException e,
		HttpServletRequest req) {
		log.info(e.getMessage());
		nm.sendNotification(e, req.getRequestURI(), getParams(req));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(BaseResponse.ofFail(ResponseCode.RESIDENT_NOT_FOUND));
	}

	@ExceptionHandler(ResidentExitTimeoutException.class)
	public ResponseEntity<BaseResponse<Object>> handleResidentExitTimeoutException(ResidentExitTimeoutException e,
		HttpServletRequest req) {
		log.info(e.getMessage());
		nm.sendNotification(e, req.getRequestURI(), getParams(req));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(BaseResponse.ofFail(ResponseCode.RESIDENT_EXIT_TIMEOUT));
	}

	@ExceptionHandler(RegisterResidentTimeoutException.class)
	public ResponseEntity<BaseResponse<Object>> handleResidentTimeoutException(RegisterResidentTimeoutException e,
		HttpServletRequest req) {
		log.info(e.getMessage());
		nm.sendNotification(e, req.getRequestURI(), getParams(req));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(BaseResponse.ofFail(ResponseCode.REGISTER_RESIDENT_TIMEOUT));
	}

	@ExceptionHandler(ResidentOverflowException.class)
	public ResponseEntity<BaseResponse<Object>> handleResidentOverflowException(ResidentOverflowException e,
		HttpServletRequest req) {
		log.info(e.getMessage());
		nm.sendNotification(e, req.getRequestURI(), getParams(req));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(BaseResponse.ofFail(ResponseCode.RESIDENT_OVERFLOW));
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

}

package com.planetrush.planetrush.core.exception.handler;

import static com.planetrush.planetrush.core.template.response.ResponseCode.*;

import java.util.Enumeration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.planetrush.planetrush.core.jwt.exception.ExpiredJwtException;
import com.planetrush.planetrush.core.jwt.exception.UnAuthorizedException;
import com.planetrush.planetrush.core.jwt.exception.UnSupportedJwtException;
import com.planetrush.planetrush.core.mattermost.NotificationManager;
import com.planetrush.planetrush.core.template.response.BaseResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class AuthExceptionHandler {

	private final NotificationManager nm;

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<BaseResponse<Object>> expiredJwtException(ExpiredJwtException e, HttpServletRequest req) {
		log.info(e.getMessage());
		nm.sendNotification(e, req.getRequestURI(), getParams(req));
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			.body(BaseResponse.ofFail(EXPIRED_JWT_EXCEPTION));
	}

	@ExceptionHandler(UnAuthorizedException.class)
	public ResponseEntity<BaseResponse<Object>> unAuthorizedException(UnAuthorizedException e, HttpServletRequest req) {
		log.info(e.getMessage());
		nm.sendNotification(e, req.getRequestURI(), getParams(req));
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			.body(BaseResponse.ofFail(UNAUTHORIZED_EXCEPTION));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<BaseResponse<Object>> illegalArgumentException(IllegalArgumentException e, HttpServletRequest req) {
		log.info(e.getMessage());
		nm.sendNotification(e, req.getRequestURI(), getParams(req));
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			.body(BaseResponse.ofFail(UNAUTHORIZED_EXCEPTION));
	}

	@ExceptionHandler(UnSupportedJwtException.class)
	public ResponseEntity<BaseResponse<Object>> unSupportedJwtException(UnSupportedJwtException e, HttpServletRequest req) {
		log.info(e.getMessage());
		nm.sendNotification(e, req.getRequestURI(), getParams(req));
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			.body(BaseResponse.ofFail(UNSUPPORTED_JWT_EXCEPTION));
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

package com.planetrush.planetrush.core.exception.handler;

import java.util.Enumeration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.planetrush.planetrush.core.mattermost.NotificationManager;
import com.planetrush.planetrush.core.template.response.BaseResponse;
import com.planetrush.planetrush.core.template.response.ResponseCode;
import com.planetrush.planetrush.infra.flask.exception.FlaskServerNotConnectedException;
import com.planetrush.planetrush.infra.flask.exception.ImageSimilarityCheckErrorException;
import com.planetrush.planetrush.infra.flask.exception.InvalidImageUrlCountException;
import com.planetrush.planetrush.infra.flask.exception.ProgressAvgNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class FlaskExceptionHandler {

	private final NotificationManager nm;

	@ExceptionHandler(FlaskServerNotConnectedException.class)
	public ResponseEntity<BaseResponse<Object>> handleFlaskServerNotConnectedException(
		FlaskServerNotConnectedException e,
		HttpServletRequest req) {
		log.info(e.getMessage());
		nm.sendNotification(e, req.getRequestURI(), getParams(req));
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(BaseResponse.ofFail(ResponseCode.FLASK_SERVER_NOT_CONNECTED));
	}

	@ExceptionHandler(InvalidImageUrlCountException.class)
	public ResponseEntity<BaseResponse<Object>> handleInvalidImageUrlCountException(InvalidImageUrlCountException e,
		HttpServletRequest req) {
		log.info(e.getMessage());
		nm.sendNotification(e, req.getRequestURI(), getParams(req));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(BaseResponse.ofFail(ResponseCode.INVALID_IMAGE_URL_COUNT));
	}

	@ExceptionHandler(ImageSimilarityCheckErrorException.class)
	public ResponseEntity<BaseResponse<Object>> handleImageSimilarityCheckErrorException(
		ImageSimilarityCheckErrorException e,
		HttpServletRequest req) {
		log.info(e.getMessage());
		nm.sendNotification(e, req.getRequestURI(), getParams(req));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(BaseResponse.ofFail(ResponseCode.IMAGE_SIMILARITY_CHECK_ERROR));
	}

	@ExceptionHandler(ProgressAvgNotFoundException.class)
	public ResponseEntity<BaseResponse<Object>> handleProgressAvgNotFoundException(ProgressAvgNotFoundException e,
		HttpServletRequest req) {
		log.info(e.getMessage());
		nm.sendNotification(e, req.getRequestURI(), getParams(req));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(BaseResponse.ofFail(ResponseCode.PROGRESS_AVG_NOT_FOUND));
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

package com.planetrush.planetrush.core.exception.handler;

import java.util.Enumeration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.planetrush.planetrush.core.mattermost.NotificationManager;
import com.planetrush.planetrush.core.template.response.BaseResponse;
import com.planetrush.planetrush.core.template.response.ResponseCode;
import com.planetrush.planetrush.member.exception.MemberNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class MemberExceptionHandler {

	private final NotificationManager nm;

	@ExceptionHandler(MemberNotFoundException.class)
	public ResponseEntity<BaseResponse<Object>> handleMemberNotFoundException(MemberNotFoundException e,
		HttpServletRequest req) {
		log.info(e.getMessage());
		nm.sendNotification(e, req.getRequestURI(), getParams(req));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.ofFail(ResponseCode.MEMBER_NOT_FOUND));
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

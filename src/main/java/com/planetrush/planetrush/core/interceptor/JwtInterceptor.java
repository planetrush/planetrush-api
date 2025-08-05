package com.planetrush.planetrush.core.interceptor;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.planetrush.planetrush.core.jwt.JwtTokenProvider;
import com.planetrush.planetrush.core.jwt.exception.UnAuthorizedException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtInterceptor implements HandlerInterceptor {

	private final String HEADER_AUTHORIZATION = "Authorization";

	private final JwtTokenProvider jwtTokenProvider;

	/**
	 * 요청이 컨트롤러에 도달하기 전에 호출되는 메서드입니다.
	 *
	 * <p>PreFlight 요청과 /reissue URI에 대한 POST 요청은 토큰 검사를 생략하고 통과시킵니다.</p>
	 * <p>그 외의 요청에 대해서는 Authorization 헤더에 포함된 JWT 토큰을 검증합니다.</p>
	 * <p>유효한 토큰일 경우 요청을 통과시키고, 그렇지 않을 경우 UnAuthorizedException을 발생시킵니다.</p>
	 *
	 * @param request  HttpServletRequest 객체
	 * @param response HttpServletResponse 객체
	 * @param handler  현재 요청을 처리할 핸들러
	 * @return 토큰이 유효한 경우 true, 그렇지 않은 경우 예외 발생
	 * @throws UnAuthorizedException 유효하지 않은 토큰인 경우
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
		Object handler) {
		if (HttpMethod.OPTIONS.matches(request.getMethod())) {
			log.info("PreFlight 요청");
			return true;
		}

		final String token = request.getHeader(HEADER_AUTHORIZATION);
		if (token != null && jwtTokenProvider.validateToken(token)) {
			log.info("토큰 사용 가능 : {}", token);
			return true;
		} else {
			log.info("토큰 사용 불가능 : {}", token);
			throw new UnAuthorizedException();
		}
	}
}

package com.bnote.global.response;

import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 컨트롤러가 {@link RsData}를 그대로 반환하면(ResponseEntity로 감싸지 않아도)
 * RsData.statusCode()에 맞는 실제 HTTP 응답 코드가 나가도록 해준다.
 * 예) RsData.created(...)를 반환하면 실제 응답도 201이 된다.
 */
@Aspect
@Component
public class ResponseAspect {

	private final HttpServletResponse response;

	public ResponseAspect(HttpServletResponse response) {
		this.response = response;
	}

	@Around("""
		execution(public com.bnote.global.response.RsData *(..)) &&
		(
		    within(@org.springframework.stereotype.Controller *) ||
		    within(@org.springframework.web.bind.annotation.RestController *)
		) &&
		(
		    @annotation(org.springframework.web.bind.annotation.GetMapping) ||
		    @annotation(org.springframework.web.bind.annotation.PostMapping) ||
		    @annotation(org.springframework.web.bind.annotation.PutMapping) ||
		    @annotation(org.springframework.web.bind.annotation.PatchMapping) ||
		    @annotation(org.springframework.web.bind.annotation.DeleteMapping) ||
		    @annotation(org.springframework.web.bind.annotation.RequestMapping)
		)
		""")
	public Object handleResponse(ProceedingJoinPoint joinPoint) throws Throwable {
		Object proceed = joinPoint.proceed();

		RsData<?> rsData = (RsData<?>) proceed;
		response.setStatus(rsData.statusCode());

		return proceed;
	}
}
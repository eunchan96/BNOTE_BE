package com.bnote.domain.member.controller;

import com.bnote.domain.member.dto.request.ReissueRequest;
import com.bnote.domain.member.dto.request.SocialLoginRequest;
import com.bnote.domain.member.dto.response.TokenResponse;
import com.bnote.domain.member.entity.SocialType;
import com.bnote.domain.member.service.AuthService;
import com.bnote.global.rq.Rq;
import com.bnote.global.rsData.RsData;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;
	private final Rq rq;

	public AuthController(AuthService authService, Rq rq) {
		this.authService = authService;
		this.rq = rq;
	}

	@PostMapping("/login/kakao")
	public RsData<TokenResponse> loginKakao(@Valid @RequestBody SocialLoginRequest request) {
		TokenResponse token = authService.login(SocialType.KAKAO, request.authCode());
		return new RsData<>("200-1", "카카오 로그인 되었습니다.", token);
	}

	@PostMapping("/login/google")
	public RsData<TokenResponse> loginGoogle(@Valid @RequestBody SocialLoginRequest request) {
		TokenResponse token = authService.login(SocialType.GOOGLE, request.authCode());
		return new RsData<>("200-1", "구글 로그인 되었습니다.", token);
	}

	@PostMapping("/reissue")
	public RsData<TokenResponse> reissue(@Valid @RequestBody ReissueRequest request) {
		TokenResponse token = authService.reissue(request.refreshToken());
		return new RsData<>("200-1", "토큰이 재발급되었습니다.", token);
	}

	@PostMapping("/logout")
	public RsData<Void> logout() {
		authService.logout(rq.getActorIdOrThrow());
		return new RsData<>("200-1", "로그아웃 되었습니다.");
	}
}
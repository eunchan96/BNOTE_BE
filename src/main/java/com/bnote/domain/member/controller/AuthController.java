package com.bnote.domain.member.controller;

import com.bnote.domain.member.dto.request.ReissueRequest;
import com.bnote.domain.member.dto.request.SocialLoginRequest;
import com.bnote.domain.member.dto.response.TokenResponse;
import com.bnote.domain.member.facade.AuthFacade;
import com.bnote.global.response.RsData;
import com.bnote.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthControllerDocs {

	private final AuthFacade authFacade;
	private final Rq rq;

	@PostMapping("/login/kakao")
	public RsData<TokenResponse> loginKakao(@Valid @RequestBody SocialLoginRequest request) {
		TokenResponse token = authFacade.loginKakao(request.authCode());
		return toLoginResponse(token);
	}

	@PostMapping("/login/google")
	public RsData<TokenResponse> loginGoogle(@Valid @RequestBody SocialLoginRequest request) {
		TokenResponse token = authFacade.loginGoogle(request.authCode());
		return toLoginResponse(token);
	}

	@PostMapping("/reissue")
	public RsData<TokenResponse> reissue(@Valid @RequestBody ReissueRequest request) {
		TokenResponse token = authFacade.reissue(request.refreshToken());
		return RsData.ok("토큰이 재발급되었습니다.", token);
	}

	@PostMapping("/logout")
	public RsData<Void> logout() {
		authFacade.logout(rq.getActorIdOrThrow());
		return RsData.ok("로그아웃 되었습니다.");
	}

	private RsData<TokenResponse> toLoginResponse(TokenResponse token) {
		if (token.isNewMember()) {
			return RsData.created("회원가입 후 로그인 되었습니다.", token);
		}
		return RsData.ok("로그인 되었습니다.", token);
	}
}
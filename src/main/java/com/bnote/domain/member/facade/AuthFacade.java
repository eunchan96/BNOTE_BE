package com.bnote.domain.member.facade;

import com.bnote.domain.member.dto.response.TokenResponse;
import com.bnote.domain.member.entity.SocialType;
import com.bnote.domain.member.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthFacade {

	private final AuthService authService;

	public TokenResponse loginKakao(String authCode) {
		return authService.login(SocialType.KAKAO, authCode);
	}

	public TokenResponse loginGoogle(String authCode) {
		return authService.login(SocialType.GOOGLE, authCode);
	}

	public TokenResponse reissue(String refreshToken) {
		return authService.reissue(refreshToken);
	}

	public void logout(Long memberId) {
		authService.logout(memberId);
	}
}
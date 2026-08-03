package com.bnote.domain.member.service;

import com.bnote.domain.member.dto.response.TokenResponse;
import com.bnote.domain.member.entity.Member;
import com.bnote.domain.member.entity.SocialType;
import com.bnote.domain.member.repository.MemberRepository;
import com.bnote.global.auth.jwt.JwtProvider;
import com.bnote.global.auth.oauth.OAuthClient;
import com.bnote.global.auth.oauth.SocialUserInfo;
import com.bnote.global.exception.ServiceException;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthService {

	private final MemberRepository memberRepository;
	private final JwtProvider jwtProvider;
	private final List<OAuthClient> oAuthClients;

	public AuthService(MemberRepository memberRepository, JwtProvider jwtProvider, List<OAuthClient> oAuthClients) {
		this.memberRepository = memberRepository;
		this.jwtProvider = jwtProvider;
		this.oAuthClients = oAuthClients;
	}

	@Transactional
	public TokenResponse login(SocialType socialType, String authCode) {
		OAuthClient oAuthClient = findClient(socialType);
		SocialUserInfo userInfo = oAuthClient.getUserInfo(authCode);

		var existingMember = memberRepository.findBySocialTypeAndSocialId(socialType, userInfo.socialId());
		boolean isNewMember = existingMember.isEmpty();

		Member member = existingMember
			.map(existing -> {
				existing.updateProfile(userInfo.nickname(), userInfo.profileImageUrl());
				return existing;
			})
			.orElseGet(() -> memberRepository.save(
				Member.builder()
					.socialType(socialType)
					.socialId(userInfo.socialId())
					.nickname(userInfo.nickname())
					.profileImageUrl(userInfo.profileImageUrl())
					.build()
			));

		return issueTokens(member, isNewMember);
	}

	@Transactional
	public TokenResponse reissue(String refreshToken) {
		if (!jwtProvider.isValid(refreshToken)) {
			throw new ServiceException("401-1", "유효하지 않은 refresh token 입니다.");
		}

		Long memberId = jwtProvider.getMemberId(refreshToken);
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new ServiceException("404-1", "회원을 찾을 수 없습니다."));

		if (!Objects.equals(refreshToken, member.getRefreshToken())) {
			throw new ServiceException("401-2", "일치하지 않는 refresh token 입니다.");
		}

		return issueTokens(member, false);
	}

	@Transactional
	public void logout(Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new ServiceException("404-1", "회원을 찾을 수 없습니다."));
		member.updateRefreshToken(null);
	}

	private TokenResponse issueTokens(Member member, boolean isNewMember) {
		String accessToken = jwtProvider.generateAccessToken(member.getId());
		String refreshToken = jwtProvider.generateRefreshToken(member.getId());
		member.updateRefreshToken(refreshToken);
		return new TokenResponse(accessToken, refreshToken, isNewMember);
	}

	private OAuthClient findClient(SocialType socialType) {
		return oAuthClients.stream()
			.filter(client -> client.supportType() == socialType)
			.findFirst()
			.orElseThrow(() -> new ServiceException("400-1", "지원하지 않는 소셜 로그인입니다: " + socialType));
	}
}
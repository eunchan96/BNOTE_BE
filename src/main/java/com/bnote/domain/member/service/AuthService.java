package com.bnote.domain.member.service;

import com.bnote.domain.member.dto.response.TokenResponse;
import com.bnote.domain.member.entity.Member;
import com.bnote.domain.member.entity.SocialType;
import com.bnote.domain.member.exception.MemberException;
import com.bnote.domain.member.repository.MemberRepository;
import com.bnote.global.auth.jwt.JwtProvider;
import com.bnote.global.auth.oauth.OAuthClient;
import com.bnote.global.auth.oauth.SocialUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

	private final MemberRepository memberRepository;
	private final JwtProvider jwtProvider;
	private final List<OAuthClient> oAuthClients;

	@Transactional
	public TokenResponse login(SocialType socialType, String authCode) {
		OAuthClient oAuthClient = findClient(socialType);
		SocialUserInfo userInfo = oAuthClient.getUserInfo(authCode);

		var existingMember = memberRepository.findBySocialTypeAndSocialId(socialType, userInfo.socialId());
		boolean isNewMember = existingMember.isEmpty();

		Member member = existingMember
				.map(existing -> {
					existing.updateSocialProfile(userInfo.nickname(), userInfo.profileImageUrl());
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
			throw MemberException.invalidRefreshToken();
		}

		Long memberId = jwtProvider.getMemberId(refreshToken);
		Member member = memberRepository.findById(memberId)
				.orElseThrow(MemberException::notFound);

		if (!Objects.equals(refreshToken, member.getRefreshToken())) {
			throw MemberException.refreshTokenMismatch();
		}

		return issueTokens(member, false);
	}

	@Transactional
	public void logout(Long memberId) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(MemberException::notFound);
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
				.orElseThrow(() -> MemberException.unsupportedSocialType(socialType.name()));
	}
}
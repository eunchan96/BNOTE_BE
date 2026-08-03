package com.bnote.domain.member.service;

import com.bnote.domain.member.dto.response.TokenResponse;
import com.bnote.domain.member.entity.Member;
import com.bnote.domain.member.entity.SocialType;
import com.bnote.domain.member.repository.MemberRepository;
import com.bnote.global.auth.jwt.JwtProvider;
import com.bnote.global.auth.oauth.OAuthClient;
import com.bnote.global.auth.oauth.SocialUserInfo;
import com.bnote.global.exception.ServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthServiceTest {

	private final MemberRepository memberRepository = mock(MemberRepository.class);
	private final JwtProvider jwtProvider = mock(JwtProvider.class);
	private final OAuthClient kakaoOAuthClient = mock(OAuthClient.class);
	private final AuthService authService = new AuthService(
		memberRepository, jwtProvider, List.of(kakaoOAuthClient)
	);

	@Test
	@DisplayName("최초 카카오 로그인 시 회원이 새로 생성되고 isNewMember가 true다")
	void t1() {
		when(kakaoOAuthClient.supportType()).thenReturn(SocialType.KAKAO);
		when(kakaoOAuthClient.getUserInfo("test-auth-code"))
			.thenReturn(new SocialUserInfo("12345", "은찬", "https://example.com/profile.png"));
		when(memberRepository.findBySocialTypeAndSocialId(SocialType.KAKAO, "12345"))
			.thenReturn(Optional.empty());

		Member savedMember = Member.builder()
			.socialType(SocialType.KAKAO)
			.socialId("12345")
			.nickname("은찬")
			.profileImageUrl("https://example.com/profile.png")
			.build();
		ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
		when(memberRepository.save(memberCaptor.capture())).thenReturn(savedMember);
		when(jwtProvider.generateAccessToken(null)).thenReturn("access-token");
		when(jwtProvider.generateRefreshToken(null)).thenReturn("refresh-token");

		TokenResponse result = authService.login(SocialType.KAKAO, "test-auth-code");

		assertThat(result.isNewMember()).isTrue();
		assertThat(result.accessToken()).isEqualTo("access-token");
		assertThat(result.refreshToken()).isEqualTo("refresh-token");
		assertThat(memberCaptor.getValue().getSocialId()).isEqualTo("12345");
	}

	@Test
	@DisplayName("이미 가입된 회원이 다시 로그인하면 isNewMember는 false이고 프로필이 갱신된다")
	void t2() {
		when(kakaoOAuthClient.supportType()).thenReturn(SocialType.KAKAO);
		when(kakaoOAuthClient.getUserInfo("test-auth-code"))
			.thenReturn(new SocialUserInfo("12345", "새 닉네임", "https://example.com/new.png"));

		Member existing = Member.builder()
			.socialType(SocialType.KAKAO)
			.socialId("12345")
			.nickname("기존 닉네임")
			.profileImageUrl("https://example.com/old.png")
			.build();
		when(memberRepository.findBySocialTypeAndSocialId(SocialType.KAKAO, "12345"))
			.thenReturn(Optional.of(existing));
		when(jwtProvider.generateAccessToken(null)).thenReturn("access-token");
		when(jwtProvider.generateRefreshToken(null)).thenReturn("refresh-token");

		TokenResponse result = authService.login(SocialType.KAKAO, "test-auth-code");

		assertThat(result.isNewMember()).isFalse();
		assertThat(existing.getNickname()).isEqualTo("새 닉네임");
	}

	@Test
	@DisplayName("유효하지 않은 refresh token으로 재발급을 시도하면 예외가 발생한다")
	void t3() {
		when(jwtProvider.isValid("invalid-token")).thenReturn(false);

		assertThatThrownBy(() -> authService.reissue("invalid-token"))
			.isInstanceOf(ServiceException.class)
			.hasMessageContaining("유효하지 않은 refresh token");
	}

	@Test
	@DisplayName("회원이 들고 있는 refresh token과 다른 토큰으로 재발급을 시도하면 예외가 발생한다")
	void t4() {
		Member member = Member.builder()
			.socialType(SocialType.KAKAO)
			.socialId("12345")
			.nickname("은찬")
			.profileImageUrl(null)
			.build();
		member.updateRefreshToken("stored-refresh-token");

		when(jwtProvider.isValid("other-refresh-token")).thenReturn(true);
		when(jwtProvider.getMemberId("other-refresh-token")).thenReturn(1L);
		when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

		assertThatThrownBy(() -> authService.reissue("other-refresh-token"))
			.isInstanceOf(ServiceException.class)
			.hasMessageContaining("일치하지 않는 refresh token");
	}

	@Test
	@DisplayName("로그아웃하면 회원의 refresh token이 제거된다")
	void t5() {
		Member member = Member.builder()
			.socialType(SocialType.KAKAO)
			.socialId("12345")
			.nickname("은찬")
			.profileImageUrl(null)
			.build();
		member.updateRefreshToken("refresh-token");
		when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

		authService.logout(1L);

		assertThat(member.getRefreshToken()).isNull();
	}
}
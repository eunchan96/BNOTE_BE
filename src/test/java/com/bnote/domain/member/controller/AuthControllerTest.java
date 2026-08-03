package com.bnote.domain.member.controller;

import com.bnote.domain.member.entity.Member;
import com.bnote.domain.member.entity.SocialType;
import com.bnote.domain.member.repository.MemberRepository;
import com.bnote.global.auth.jwt.JwtProvider;
import com.bnote.global.auth.oauth.KakaoOAuthClient;
import com.bnote.global.auth.oauth.SocialUserInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private JwtProvider jwtProvider;

	@MockitoBean
	private KakaoOAuthClient kakaoOAuthClient;

	@Test
	@DisplayName("카카오 로그인 - 최초 로그인이면 회원가입 처리되고 201로 토큰이 발급된다")
	void t1() throws Exception {
		when(kakaoOAuthClient.supportType()).thenReturn(SocialType.KAKAO);
		when(kakaoOAuthClient.getUserInfo(anyString()))
				.thenReturn(new SocialUserInfo("kakao-social-id", "은찬", "https://example.com/p.png"));

		mvc.perform(
						post("/api/v1/auth/login/kakao")
								.contentType(MediaType.APPLICATION_JSON)
								.content("""
						{ "authCode": "dummy-auth-code" }
						""")
				)
				.andDo(print())
				.andExpect(handler().handlerType(AuthController.class))
				.andExpect(handler().methodName("loginKakao"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.resultCode").value("201"))
				.andExpect(jsonPath("$.data.accessToken").isNotEmpty())
				.andExpect(jsonPath("$.data.refreshToken").isNotEmpty())
				.andExpect(jsonPath("$.data.isNewMember").value(true));
	}

	@Test
	@DisplayName("카카오 로그인 - 이미 가입된 회원이면 200으로 로그인된다")
	void t1b() throws Exception {
		memberRepository.save(
				Member.builder()
						.socialType(SocialType.KAKAO)
						.socialId("existing-kakao-id")
						.nickname("기존회원")
						.profileImageUrl(null)
						.build()
		);
		when(kakaoOAuthClient.supportType()).thenReturn(SocialType.KAKAO);
		when(kakaoOAuthClient.getUserInfo(anyString()))
				.thenReturn(new SocialUserInfo("existing-kakao-id", "기존회원", null));

		mvc.perform(
						post("/api/v1/auth/login/kakao")
								.contentType(MediaType.APPLICATION_JSON)
								.content("""
						{ "authCode": "dummy-auth-code" }
						""")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.resultCode").value("200"))
				.andExpect(jsonPath("$.data.isNewMember").value(false));
	}

	@Test
	@DisplayName("토큰 재발급 - 저장된 refresh token과 일치하면 새 토큰이 발급된다")
	void t2() throws Exception {
		Member member = memberRepository.save(
				Member.builder()
						.socialType(SocialType.KAKAO)
						.socialId("reissue-test-id")
						.nickname("은찬")
						.profileImageUrl(null)
						.build()
		);
		String refreshToken = jwtProvider.generateRefreshToken(member.getId());
		member.updateRefreshToken(refreshToken);

		mvc.perform(
						post("/api/v1/auth/reissue")
								.contentType(MediaType.APPLICATION_JSON)
								.content("""
						{ "refreshToken": "%s" }
						""".formatted(refreshToken))
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.resultCode").value("200"))
				.andExpect(jsonPath("$.data.accessToken").isNotEmpty());
	}

	@Test
	@DisplayName("로그아웃 - 로그인 상태에서 요청하면 refresh token이 제거된다")
	void t3() throws Exception {
		Member member = memberRepository.save(
				Member.builder()
						.socialType(SocialType.KAKAO)
						.socialId("logout-test-id")
						.nickname("은찬")
						.profileImageUrl(null)
						.build()
		);
		member.updateRefreshToken("some-refresh-token");
		String accessToken = jwtProvider.generateAccessToken(member.getId());

		mvc.perform(
						post("/api/v1/auth/logout")
								.header("Authorization", "Bearer " + accessToken)
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.resultCode").value("200"));

		org.assertj.core.api.Assertions.assertThat(
				memberRepository.findById(member.getId()).orElseThrow().getRefreshToken()
		).isNull();
	}
}
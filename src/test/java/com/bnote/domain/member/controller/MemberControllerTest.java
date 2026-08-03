package com.bnote.domain.member.controller;

import com.bnote.domain.member.entity.Member;
import com.bnote.domain.member.entity.SocialType;
import com.bnote.domain.member.repository.MemberRepository;
import com.bnote.global.auth.jwt.JwtProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private JwtProvider jwtProvider;

	private Member member;
	private String accessToken;

	@BeforeEach
	void setUp() {
		member = memberRepository.save(
			Member.builder()
				.socialType(SocialType.KAKAO)
				.socialId("test-social-id")
				.nickname("은찬")
				.profileImageUrl("https://example.com/profile.png")
				.build()
		);
		accessToken = jwtProvider.generateAccessToken(member.getId());
	}

	@Test
	@DisplayName("내 정보 조회 - 로그인 상태")
	void t1() throws Exception {
		mvc.perform(
				get("/members/me")
					.header("Authorization", "Bearer " + accessToken)
			)
			.andDo(print())
			.andExpect(handler().handlerType(MemberController.class))
			.andExpect(handler().methodName("getMe"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.resultCode").value("200"))
			.andExpect(jsonPath("$.data.id").value(member.getId()))
			.andExpect(jsonPath("$.data.nickname").value("은찬"))
			.andExpect(jsonPath("$.data.socialType").value("KAKAO"));
	}

	@Test
	@DisplayName("내 정보 조회 - 토큰 없이 요청하면 401")
	void t2() throws Exception {
		mvc.perform(get("/members/me"))
			.andDo(print())
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("$.resultCode").value("401-1"));
	}

	@Test
	@DisplayName("회원 탈퇴")
	void t3() throws Exception {
		mvc.perform(
				delete("/members/me")
					.header("Authorization", "Bearer " + accessToken)
			)
			.andDo(print())
			.andExpect(handler().handlerType(MemberController.class))
			.andExpect(handler().methodName("withdraw"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.resultCode").value("200"));

		Assertions.assertThat(memberRepository.findById(member.getId())).isEmpty();
	}
}
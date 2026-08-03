package com.bnote.domain.bookmark.controller;

import com.bnote.domain.member.entity.Member;
import com.bnote.domain.member.entity.SocialType;
import com.bnote.domain.member.repository.MemberRepository;
import com.bnote.global.auth.jwt.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BookmarkControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtProvider jwtProvider;

    private String accessToken;

    @BeforeEach
    void setUp() {
        Member member = memberRepository.save(
                Member.builder().socialType(SocialType.KAKAO).socialId("bookmark-test-id").nickname("은찬").build()
        );
        accessToken = jwtProvider.generateAccessToken(member.getId());
    }

    @Test
    @DisplayName("토글 후 목록 조회에서 확인된다")
    void t1() throws Exception {
        mvc.perform(
                        put("/api/v1/bookmarks/1/1/1")
                                .header("Authorization", "Bearer " + accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
						{ "isBookmarked": true }
						""")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.isBookmarked").value(true));

        mvc.perform(
                        get("/api/v1/bookmarks").param("bookId", "1").param("chapter", "1")
                                .header("Authorization", "Bearer " + accessToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].verse").value(1));
    }

    @Test
    @DisplayName("인증 없이 접근하면 401")
    void t2() throws Exception {
        mvc.perform(get("/api/v1/bookmarks").param("bookId", "1").param("chapter", "1"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
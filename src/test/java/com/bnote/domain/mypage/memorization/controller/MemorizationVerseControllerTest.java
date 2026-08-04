package com.bnote.domain.mypage.memorization.controller;

import com.bnote.domain.member.entity.Member;
import com.bnote.domain.member.entity.SocialType;
import com.bnote.domain.member.repository.MemberRepository;
import com.bnote.domain.mypage.memorization.entity.MemorizationGroup;
import com.bnote.domain.mypage.memorization.repository.MemorizationGroupRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemorizationVerseControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemorizationGroupRepository memorizationGroupRepository;

    @Autowired
    private JwtProvider jwtProvider;

    private String accessToken;
    private Long groupId;

    @BeforeEach
    void setUp() {
        Member member = memberRepository.save(
                Member.builder().socialType(SocialType.KAKAO).socialId("memorization-verse-test").nickname("은찬").build()
        );
        accessToken = jwtProvider.generateAccessToken(member.getId());

        MemorizationGroup group = memorizationGroupRepository.save(
                MemorizationGroup.builder().memberId(member.getId()).name("그룹").sortOrder(0).build()
        );
        groupId = group.getId();
    }

    @Test
    @DisplayName("등록 후 연습 기록까지 정상 동작한다")
    void t1() throws Exception {
        String response = mvc.perform(
                        post("/api/v1/memorization-verses")
                                .header("Authorization", "Bearer " + accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
						{ "groupId": %d, "startBookId": 43, "startChapter": 3, "startVerse": 16, "endBookId": 43, "endChapter": 3, "endVerse": 16, "translation": "NKRV" }
						""".formatted(groupId))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long id = ((Number) com.jayway.jsonpath.JsonPath.read(response, "$.data.id")).longValue();

        mvc.perform(
                        put("/api/v1/memorization-verses/" + id + "/review")
                                .header("Authorization", "Bearer " + accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
						{ "mastered": true }
						""")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.reviewCount").value(1))
                .andExpect(jsonPath("$.data.isMastered").value(true));
    }
}
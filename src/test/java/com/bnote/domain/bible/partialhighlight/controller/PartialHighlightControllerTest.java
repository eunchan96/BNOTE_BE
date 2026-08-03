package com.bnote.domain.bible.partialhighlight.controller;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PartialHighlightControllerTest {

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
                Member.builder().socialType(SocialType.KAKAO).socialId("partial-test-id").nickname("은찬").build()
        );
        accessToken = jwtProvider.generateAccessToken(member.getId());
    }

    @Test
    @DisplayName("등록 후 삭제까지 정상 동작한다")
    void t1() throws Exception {
        String responseJson = mvc.perform(
                        post("/api/v1/partial-highlights")
                                .header("Authorization", "Bearer " + accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
						{ "translation": "NKRV", "bookId": 1, "chapter": 1, "verse": 1, "startOffset": 0, "endOffset": 3 }
						""")
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.colorHex").value("#FFF9C4"))
                .andReturn().getResponse().getContentAsString();

        Number idNumber = com.jayway.jsonpath.JsonPath.read(responseJson, "$.data.id");
        Long id = idNumber.longValue();

        mvc.perform(
                        delete("/api/v1/partial-highlights/" + id)
                                .header("Authorization", "Bearer " + accessToken)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
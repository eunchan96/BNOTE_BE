package com.bnote.domain.sermon.controller;

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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SermonControllerTest {

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
                Member.builder().socialType(SocialType.KAKAO).socialId("sermon-test").nickname("은찬").build()
        );
        accessToken = jwtProvider.generateAccessToken(member.getId());
    }

    @Test
    @DisplayName("설교노트를 등록하면 201과 함께 본문이 반환된다")
    void t1() throws Exception {
        mvc.perform(
                        post("/api/v1/sermons")
                                .header("Authorization", "Bearer " + accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
						{
						  "title": "산상수훈",
						  "sermonDate": "2026-01-05",
						  "memo": "복 있는 사람은...",
						  "bibleRefs": [
						    { "startBookId": 40, "startChapter": 5, "startVerse": 1, "endBookId": 40, "endChapter": 7, "endVerse": 29 }
						  ]
						}
						""")
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.title").value("산상수훈"))
                .andExpect(jsonPath("$.data.bibleRefs[0].startChapter").value(5));
    }

    @Test
    @DisplayName("설교노트 사진을 업로드하면 접근 가능한 URL이 반환된다")
    void t2() throws Exception {
        String sermonResponse = mvc.perform(
                        post("/api/v1/sermons")
                                .header("Authorization", "Bearer " + accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
						{ "title": "제목", "sermonDate": "2026-01-05", "memo": "내용" }
						""")
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long sermonId = ((Number) com.jayway.jsonpath.JsonPath.read(sermonResponse, "$.data.id")).longValue();

        MockMultipartFile file = new MockMultipartFile("file", "note.jpg", "image/jpeg", "dummy-image-bytes".getBytes());

        mvc.perform(
                        multipart("/api/v1/sermons/" + sermonId + "/photos")
                                .file(file)
                                .header("Authorization", "Bearer " + accessToken)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.imageUrl").value(org.hamcrest.Matchers.startsWith("/uploads/sermons/" + sermonId + "/")));
    }
}
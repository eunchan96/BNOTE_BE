package com.bnote.domain.bible.scrap.controller;

import com.bnote.domain.bible.bible.entity.BibleVerse;
import com.bnote.domain.bible.bible.repository.BibleVerseRepository;
import com.bnote.domain.bible.scrap.entity.ScrapGroup;
import com.bnote.domain.bible.scrap.repository.ScrapGroupRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ScrapControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ScrapGroupRepository scrapGroupRepository;

    @Autowired
    private BibleVerseRepository bibleVerseRepository;

    @Autowired
    private JwtProvider jwtProvider;

    private String accessToken;
    private Long groupId;

    @BeforeEach
    void setUp() {
        Member member = memberRepository.save(
                Member.builder().socialType(SocialType.KAKAO).socialId("scrap-test").nickname("은찬").build()
        );
        accessToken = jwtProvider.generateAccessToken(member.getId());

        ScrapGroup group = scrapGroupRepository.save(
                ScrapGroup.builder().memberId(member.getId()).name("은혜의 말씀").sortOrder(0).build()
        );
        groupId = group.getId();

        bibleVerseRepository.save(
                BibleVerse.builder().translation("NKRV").bookId(1).chapter(1).verse(1).text("태초에 하나님이").build()
        );
    }

    @Test
    @DisplayName("스크랩 등록 후 그룹별 목록 조회에서 확인된다")
    void t1() throws Exception {
        mvc.perform(
                        post("/api/v1/scraps")
                                .header("Authorization", "Bearer " + accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
						{ "groupId": %d, "bookId": 1, "chapter": 1, "startVerse": 1, "endVerse": 1, "translation": "NKRV" }
						""".formatted(groupId))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.verseText").value("태초에 하나님이"));

        mvc.perform(
                        get("/api/v1/scraps").param("groupId", groupId.toString())
                                .header("Authorization", "Bearer " + accessToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].verseText").value("태초에 하나님이"));
    }
}
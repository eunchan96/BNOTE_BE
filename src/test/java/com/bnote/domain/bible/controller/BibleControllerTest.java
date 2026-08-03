package com.bnote.domain.bible.controller;

import com.bnote.domain.bible.entity.BibleVerse;
import com.bnote.domain.bible.repository.BibleVerseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BibleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BibleVerseRepository bibleVerseRepository;

    @BeforeEach
    void setUp() {
        bibleVerseRepository.save(
                BibleVerse.builder().translation("NKRV").bookId(1).chapter(1).verse(1)
                        .text("태초에 하나님이 천지를 창조하시니라").build()
        );
    }

    @Test
    @DisplayName("성경 장 조회 - 인증 없이도 접근 가능")
    void t1() throws Exception {
        mvc.perform(get("/bibles/1/1").param("translation", "NKRV"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("200"))
                .andExpect(jsonPath("$.data.bookName").value("창세기"))
                .andExpect(jsonPath("$.data.verses[0].text").value("태초에 하나님이 천지를 창조하시니라"));
    }

    @Test
    @DisplayName("성경 검색 - 인증 없이도 접근 가능")
    void t2() throws Exception {
        mvc.perform(get("/bibles/search").param("keyword", "태초에"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("200"))
                .andExpect(jsonPath("$.data.results[0].bookName").value("창세기"));
    }

    @Test
    @DisplayName("대역본 목록 조회")
    void t3() throws Exception {
        mvc.perform(get("/translations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.translations[0].code").value("NKRV"))
                .andExpect(jsonPath("$.data.translations", org.hamcrest.Matchers.hasSize(8)));
    }
}
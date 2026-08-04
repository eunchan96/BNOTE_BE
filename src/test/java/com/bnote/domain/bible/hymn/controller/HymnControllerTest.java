package com.bnote.domain.bible.hymn.controller;

import com.bnote.domain.bible.hymn.entity.Hymn;
import com.bnote.domain.bible.hymn.entity.HymnCategory;
import com.bnote.domain.bible.hymn.repository.HymnCategoryRepository;
import com.bnote.domain.bible.hymn.repository.HymnRepository;
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
class HymnControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private HymnRepository hymnRepository;

    @Autowired
    private HymnCategoryRepository hymnCategoryRepository;

    @BeforeEach
    void setUp() {
        HymnCategory major = hymnCategoryRepository.save(HymnCategory.builder().name("예배").parentId(null).sortOrder(0).build());
        HymnCategory minor = hymnCategoryRepository.save(
                HymnCategory.builder().name("찬양과 경배").parentId(major.getId()).sortOrder(0).build()
        );
        hymnRepository.save(
                Hymn.builder().number(1).title("만복의 근원 하나님").categoryId(minor.getId())
                        .imageFileName("001.jpg").youtubeSongUrl("url1").youtubeMrUrl("mr1").build()
        );
    }

    @Test
    @DisplayName("인증 없이 찬송가 목록을 조회할 수 있다")
    void t1() throws Exception {
        mvc.perform(get("/api/v1/hymns"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value("만복의 근원 하나님"));
    }

    @Test
    @DisplayName("장번호로 상세 조회할 수 있다")
    void t2() throws Exception {
        mvc.perform(get("/api/v1/hymns/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.imageFileNames[0]").value("001.jpg"));
    }

    @Test
    @DisplayName("대분류 카테고리 목록을 조회할 수 있다")
    void t3() throws Exception {
        mvc.perform(get("/api/v1/hymn-categories/major"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").value("예배"));
    }
}
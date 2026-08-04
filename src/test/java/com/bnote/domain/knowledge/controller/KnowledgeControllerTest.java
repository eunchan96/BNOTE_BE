package com.bnote.domain.knowledge.controller;

import com.bnote.domain.knowledge.entity.BibleFigure;
import com.bnote.domain.knowledge.repository.BibleFigureRepository;
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
class KnowledgeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BibleFigureRepository bibleFigureRepository;

    @BeforeEach
    void setUp() {
        bibleFigureRepository.save(
                BibleFigure.builder().id("abraham").name("아브라함").summary("믿음의 조상").build()
        );
    }

    @Test
    @DisplayName("인증 없이 인물사전을 조회할 수 있다")
    void t1() throws Exception {
        mvc.perform(get("/api/v1/knowledge/figures"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").value("아브라함"));
    }
}
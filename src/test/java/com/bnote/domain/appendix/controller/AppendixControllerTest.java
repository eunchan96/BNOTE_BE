package com.bnote.domain.appendix.controller;

import com.bnote.domain.appendix.seed.AppendixSeeder;
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
class AppendixControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AppendixSeeder appendixSeeder;

    @BeforeEach
    void setUp() {
        appendixSeeder.seedIfEmpty();
    }

    @Test
    @DisplayName("인증 없이 주기도문을 조회할 수 있다")
    void t1() throws Exception {
        mvc.perform(get("/api/v1/appendix/lords-prayer"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("주기도문"));
    }

    @Test
    @DisplayName("십계명을 조회할 수 있다")
    void t2() throws Exception {
        mvc.perform(get("/api/v1/appendix/ten-commandments"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.commandments.length()").value(2));
    }

    @Test
    @DisplayName("교독문 번호로 상세 조회할 수 있다")
    void t3() throws Exception {
        mvc.perform(get("/api/v1/appendix/responsive-readings/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("창조"));
    }

    @Test
    @DisplayName("존재하지 않는 교독문 번호는 404를 반환한다")
    void t4() throws Exception {
        mvc.perform(get("/api/v1/appendix/responsive-readings/999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
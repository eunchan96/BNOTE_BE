package com.bnote.domain.knowledge.service;

import com.bnote.domain.knowledge.entity.BibleFigure;
import com.bnote.domain.knowledge.repository.BibleFigureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class KnowledgeServiceTest {

    @Autowired
    private KnowledgeService knowledgeService;

    @Autowired
    private BibleFigureRepository bibleFigureRepository;

    @BeforeEach
    void setUp() {
        bibleFigureRepository.save(
                BibleFigure.builder().id("abraham").name("아브라함").otherNames("아브람")
                        .category("족장").era("족장 시대").summary("믿음의 조상").build()
        );
        bibleFigureRepository.save(
                BibleFigure.builder().id("moses").name("모세").summary("출애굽의 지도자").build()
        );
    }

    @Test
    @DisplayName("키워드 없이 조회하면 전체를 반환한다")
    void t1() {
        assertThat(knowledgeService.getFigures(null)).hasSize(2);
    }

    @Test
    @DisplayName("이름 또는 다른 이름으로 검색할 수 있다")
    void t2() {
        assertThat(knowledgeService.getFigures("아브람")).hasSize(1);
        assertThat(knowledgeService.getFigures("모세")).hasSize(1);
    }
}
package com.bnote.domain.knowledge.seed;

import com.bnote.domain.knowledge.repository.*;
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
class KnowledgeSeederTest {

    @Autowired
    private KnowledgeSeeder knowledgeSeeder;

    @Autowired
    private BibleFigureRepository bibleFigureRepository;

    @Autowired
    private BiblePlaceRepository biblePlaceRepository;

    @Autowired
    private GenealogyChartRepository genealogyChartRepository;

    @Autowired
    private GenealogyEntryRepository genealogyEntryRepository;

    @Autowired
    private TopicalVerseGroupRepository topicalVerseGroupRepository;

    @Autowired
    private TopicalVerseRefRepository topicalVerseRefRepository;

    @Test
    @DisplayName("8개 영역이 fixture로부터 모두 채워진다")
    void t1() {
        knowledgeSeeder.seedIfEmpty();

        assertThat(bibleFigureRepository.count()).isEqualTo(1);
        assertThat(biblePlaceRepository.count()).isEqualTo(1);
        assertThat(genealogyChartRepository.count()).isEqualTo(1);
        assertThat(genealogyEntryRepository.findByChartIdOrderBySortOrderAsc("abraham-family")).hasSize(2);
        assertThat(topicalVerseGroupRepository.count()).isEqualTo(1);
        assertThat(topicalVerseRefRepository.findByGroupId("comfort")).hasSize(1);
    }

    @Test
    @DisplayName("이미 데이터가 있으면 다시 시딩하지 않는다")
    void t2() {
        knowledgeSeeder.seedIfEmpty();
        knowledgeSeeder.seedIfEmpty();

        assertThat(bibleFigureRepository.count()).isEqualTo(1);
    }
}
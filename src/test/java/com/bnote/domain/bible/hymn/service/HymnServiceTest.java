package com.bnote.domain.bible.hymn.service;

import com.bnote.domain.bible.hymn.entity.Hymn;
import com.bnote.domain.bible.hymn.entity.HymnCategory;
import com.bnote.domain.bible.hymn.repository.HymnCategoryRepository;
import com.bnote.domain.bible.hymn.repository.HymnRepository;
import com.bnote.global.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class HymnServiceTest {

    @Autowired
    private HymnService hymnService;

    @Autowired
    private HymnRepository hymnRepository;

    @Autowired
    private HymnCategoryRepository hymnCategoryRepository;

    private Long minorCategoryId;

    @BeforeEach
    void setUp() {
        HymnCategory major = hymnCategoryRepository.save(HymnCategory.builder().name("예배").parentId(null).sortOrder(0).build());
        HymnCategory minor = hymnCategoryRepository.save(
                HymnCategory.builder().name("찬양과 경배").parentId(major.getId()).sortOrder(0).build()
        );
        minorCategoryId = minor.getId();

        hymnRepository.save(
                Hymn.builder().number(1).title("만복의 근원 하나님").categoryId(minorCategoryId)
                        .imageFileName("001.jpg").youtubeSongUrl("url1").youtubeMrUrl("mr1").build()
        );
        hymnRepository.save(
                Hymn.builder().number(2).title("찬양 성부 성자 성령").categoryId(minorCategoryId)
                        .imageFileName("002.jpg").youtubeSongUrl("url2").youtubeMrUrl("mr2").build()
        );
    }

    @Test
    @DisplayName("카테고리로 필터링해서 조회할 수 있다")
    void t1() {
        assertThat(hymnService.getAll(minorCategoryId, null)).hasSize(2);
    }

    @Test
    @DisplayName("제목으로 검색할 수 있다")
    void t2() {
        assertThat(hymnService.getAll(null, "찬양")).hasSize(1);
    }

    @Test
    @DisplayName("장번호로 상세 조회할 수 있다")
    void t3() {
        assertThat(hymnService.getByNumber(1).title()).isEqualTo("만복의 근원 하나님");
    }

    @Test
    @DisplayName("존재하지 않는 장번호를 조회하면 예외가 발생한다")
    void t4() {
        assertThatThrownBy(() -> hymnService.getByNumber(999))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("존재하지 않는 찬송가");
    }
}
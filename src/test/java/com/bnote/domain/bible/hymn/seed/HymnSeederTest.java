package com.bnote.domain.bible.hymn.seed;

import com.bnote.domain.bible.hymn.repository.HymnCategoryRepository;
import com.bnote.domain.bible.hymn.repository.HymnRepository;
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
class HymnSeederTest {

    @Autowired
    private HymnSeeder hymnSeeder;

    @Autowired
    private HymnRepository hymnRepository;

    @Autowired
    private HymnCategoryRepository hymnCategoryRepository;

    @Test
    @DisplayName("대분류/소분류/찬송가가 JSON 논리 id를 실제 DB id로 매핑하며 채워진다")
    void t1() {
        hymnSeeder.seedIfEmpty();

        assertThat(hymnCategoryRepository.findByParentIdIsNullOrderBySortOrderAsc()).hasSize(2);
        assertThat(hymnRepository.count()).isEqualTo(3);

        var majorCategories = hymnCategoryRepository.findByParentIdIsNullOrderBySortOrderAsc();
        var worshipCategory = majorCategories.get(0);
        var minorCategories = hymnCategoryRepository.findByParentIdOrderBySortOrderAsc(worshipCategory.getId());
        assertThat(minorCategories).hasSize(1);

        var hymnsInFirstMinor = hymnRepository.findByCategoryIdOrderByNumberAsc(minorCategories.get(0).getId());
        assertThat(hymnsInFirstMinor).extracting("number").containsExactly(1, 2);
    }

    @Test
    @DisplayName("여러 악보 이미지는 파이프로 저장되고, 조회 시 리스트로 나뉜다")
    void t2() {
        hymnSeeder.seedIfEmpty();

        var hymn = hymnRepository.findById(2).orElseThrow();
        assertThat(hymn.getImageFileName()).isEqualTo("002_1.jpg|002_2.jpg");
    }

    @Test
    @DisplayName("이미 데이터가 있으면 다시 시딩하지 않는다")
    void t3() {
        hymnSeeder.seedIfEmpty();
        hymnSeeder.seedIfEmpty();

        assertThat(hymnRepository.count()).isEqualTo(3);
    }
}
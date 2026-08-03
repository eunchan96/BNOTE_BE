package com.bnote.domain.bible.service;

import com.bnote.domain.bible.dto.response.BibleChapterResponse;
import com.bnote.domain.bible.entity.BibleVerse;
import com.bnote.domain.bible.repository.BibleVerseRepository;
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
class BibleServiceTest {

    @Autowired
    private BibleService bibleService;

    @Autowired
    private BibleVerseRepository bibleVerseRepository;

    @BeforeEach
    void setUp() {
        bibleVerseRepository.save(
                BibleVerse.builder().translation("NKRV").bookId(1).chapter(1).verse(1)
                        .text("태초에 하나님이 천지를 창조하시니라").build()
        );
        bibleVerseRepository.save(
                BibleVerse.builder().translation("NKRV").bookId(1).chapter(1).verse(2)
                        .text("땅이 혼돈하고 공허하며 흑암이 깊음 위에 있고 하나님의 영은 수면 위에 운행하시니라").build()
        );
    }

    @Test
    @DisplayName("성경 장을 조회하면 절 순서대로 반환된다")
    void t1() {
        BibleChapterResponse response = bibleService.getChapter(1, 1, "NKRV");

        assertThat(response.bookName()).isEqualTo("창세기");
        assertThat(response.verses()).hasSize(2);
        assertThat(response.verses().get(0).verse()).isEqualTo(1);
        assertThat(response.verses().get(0).text()).contains("태초에");
    }

    @Test
    @DisplayName("translation을 지정하지 않으면 기본값(NKRV)으로 조회된다")
    void t2() {
        BibleChapterResponse response = bibleService.getChapter(1, 1, null);

        assertThat(response.verses()).hasSize(2);
    }

    @Test
    @DisplayName("존재하지 않는 장을 조회하면 예외가 발생한다")
    void t3() {
        assertThatThrownBy(() -> bibleService.getChapter(1, 99, "NKRV"))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("찾을 수 없습니다");
    }

    @Test
    @DisplayName("범위를 벗어난 bookId를 조회하면 예외가 발생한다")
    void t4() {
        assertThatThrownBy(() -> bibleService.getChapter(999, 1, "NKRV"))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("올바르지 않은 성경 권");
    }

    @Test
    @DisplayName("지원하지 않는 번역본으로 조회하면 예외가 발생한다")
    void t5() {
        assertThatThrownBy(() -> bibleService.getChapter(1, 1, "XXX"))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("지원하지 않는 번역본");
    }

    @Test
    @DisplayName("2글자 미만 검색어는 예외가 발생한다")
    void t6() {
        assertThatThrownBy(() -> bibleService.search("태", "NKRV"))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("2글자 이상");
    }

    @Test
    @DisplayName("검색어가 포함된 절을 찾아 반환한다")
    void t7() {
        var response = bibleService.search("태초에", "NKRV");

        assertThat(response.results()).hasSize(1);
        assertThat(response.results().get(0).bookName()).isEqualTo("창세기");
    }
}
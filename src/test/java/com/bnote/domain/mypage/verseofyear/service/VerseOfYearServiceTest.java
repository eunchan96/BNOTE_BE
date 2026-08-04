package com.bnote.domain.mypage.verseofyear.service;

import com.bnote.domain.bible.bible.entity.BibleVerse;
import com.bnote.domain.bible.bible.repository.BibleVerseRepository;
import com.bnote.domain.mypage.verseofyear.dto.request.VerseOfYearRefRequest;
import com.bnote.domain.mypage.verseofyear.dto.request.VerseOfYearRequest;
import com.bnote.domain.mypage.verseofyear.dto.response.VerseOfYearResponse;
import com.bnote.global.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class VerseOfYearServiceTest {

    @Autowired
    private VerseOfYearService verseOfYearService;

    @Autowired
    private BibleVerseRepository bibleVerseRepository;

    private static final Long MEMBER_ID = 1L;

    @BeforeEach
    void setUp() {
        bibleVerseRepository.save(
                BibleVerse.builder().translation("NKRV").bookId(24).chapter(29).verse(11)
                        .text("여호와의 말씀이니라 내가 너희를 향한 나의 생각을 아나니").build()
        );
    }

    @Test
    @DisplayName("등록 시 본문 스냅샷이 함께 저장된다")
    void t1() {
        VerseOfYearResponse response = verseOfYearService.save(
                MEMBER_ID,
                new VerseOfYearRequest(2026, "평안의 계획", List.of(
                        new VerseOfYearRefRequest(24, 29, 11, 24, 29, 11, "NKRV")
                ))
        );

        assertThat(response.verseRefs()).hasSize(1);
        assertThat(response.verseRefs().get(0).verseText()).contains("여호와의 말씀이니라");
    }

    @Test
    @DisplayName("같은 연도로 다시 등록하면 내용이 교체된다(upsert)")
    void t2() {
        verseOfYearService.save(MEMBER_ID, new VerseOfYearRequest(2026, "첫 메모", null));
        VerseOfYearResponse updated = verseOfYearService.save(MEMBER_ID, new VerseOfYearRequest(2026, "수정된 메모", null));

        assertThat(updated.note()).isEqualTo("수정된 메모");
        assertThat(verseOfYearService.getByYear(MEMBER_ID, 2026).note()).isEqualTo("수정된 메모");
    }

    @Test
    @DisplayName("등록되지 않은 연도를 조회하면 예외가 발생한다")
    void t3() {
        assertThatThrownBy(() -> verseOfYearService.getByYear(MEMBER_ID, 1999))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("등록된 올해의 말씀이 없습니다");
    }
}
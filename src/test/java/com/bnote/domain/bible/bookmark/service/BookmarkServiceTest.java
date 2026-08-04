package com.bnote.domain.bible.bookmark.service;

import com.bnote.domain.bible.bookmark.dto.request.BookmarkToggleRequest;
import com.bnote.domain.bible.bookmark.dto.response.BookmarkResponse;
import com.bnote.domain.bible.bookmark.repository.BibleBookmarkRepository;
import com.bnote.domain.bible.bookmark.service.BookmarkService;
import com.bnote.global.exception.ServiceException;
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
class BookmarkServiceTest {

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private BibleBookmarkRepository bibleBookmarkRepository;

    private static final Long MEMBER_ID = 1L;

    @Test
    @DisplayName("북마크만 켜면 하이라이트는 false로 생성된다")
    void t1() {
        BookmarkResponse response = bookmarkService.toggle(
                MEMBER_ID, 1, 1, 1, new BookmarkToggleRequest(true, null)
        );

        assertThat(response.isBookmarked()).isTrue();
        assertThat(response.isHighlighted()).isFalse();
        assertThat(bibleBookmarkRepository.findByMemberIdAndBookIdAndChapterAndVerse(MEMBER_ID, 1, 1, 1)).isPresent();
    }

    @Test
    @DisplayName("이미 있는 위치를 다시 토글하면 해당 필드만 갱신된다")
    void t2() {
        bookmarkService.toggle(MEMBER_ID, 1, 1, 1, new BookmarkToggleRequest(true, null));

        BookmarkResponse response = bookmarkService.toggle(
                MEMBER_ID, 1, 1, 1, new BookmarkToggleRequest(null, true)
        );

        assertThat(response.isBookmarked()).isTrue();
        assertThat(response.isHighlighted()).isTrue();
    }

    @Test
    @DisplayName("북마크·하이라이트가 모두 false가 되면 레코드가 삭제된다")
    void t3() {
        bookmarkService.toggle(MEMBER_ID, 1, 1, 1, new BookmarkToggleRequest(true, true));

        bookmarkService.toggle(MEMBER_ID, 1, 1, 1, new BookmarkToggleRequest(false, false));

        assertThat(bibleBookmarkRepository.findByMemberIdAndBookIdAndChapterAndVerse(MEMBER_ID, 1, 1, 1)).isEmpty();
    }

    @Test
    @DisplayName("장별 목록 조회는 북마크/하이라이트 중 하나라도 true인 절만 반환한다")
    void t4() {
        bookmarkService.toggle(MEMBER_ID, 1, 1, 1, new BookmarkToggleRequest(true, null));
        bookmarkService.toggle(MEMBER_ID, 1, 1, 2, new BookmarkToggleRequest(null, true));
        bookmarkService.toggle(MEMBER_ID, 1, 1, 3, new BookmarkToggleRequest(false, false));

        List<BookmarkResponse> responses = bookmarkService.getByChapter(MEMBER_ID, 1, 1);

        assertThat(responses).hasSize(2);
        assertThat(responses).extracting(BookmarkResponse::verse).containsExactly(1, 2);
    }

    @Test
    @DisplayName("범위를 벗어난 bookId면 예외가 발생한다")
    void t5() {
        assertThatThrownBy(() -> bookmarkService.toggle(MEMBER_ID, 999, 1, 1, new BookmarkToggleRequest(true, null)))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("올바르지 않은 성경 권");
    }
}
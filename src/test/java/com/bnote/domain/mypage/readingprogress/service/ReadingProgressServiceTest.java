package com.bnote.domain.mypage.readingprogress.service;

import com.bnote.domain.mypage.readingprogress.dto.request.ReadingProgressRequest;
import com.bnote.domain.mypage.readingprogress.dto.response.ReadingProgressResponse;
import com.bnote.global.exception.ServiceException;
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
class ReadingProgressServiceTest {

    @Autowired
    private ReadingProgressService readingProgressService;

    private static final Long MEMBER_ID = 1L;
    private static final Long OTHER_MEMBER_ID = 2L;

    @Test
    @DisplayName("체크하면 목록에 반영된다")
    void t1() {
        readingProgressService.check(MEMBER_ID, new ReadingProgressRequest(1, 1));

        assertThat(readingProgressService.getAll(MEMBER_ID)).hasSize(1);
    }

    @Test
    @DisplayName("같은 장을 두 번 체크해도 하나만 남는다(멱등)")
    void t2() {
        readingProgressService.check(MEMBER_ID, new ReadingProgressRequest(1, 1));
        readingProgressService.check(MEMBER_ID, new ReadingProgressRequest(1, 1));

        assertThat(readingProgressService.getAll(MEMBER_ID)).hasSize(1);
    }

    @Test
    @DisplayName("본인 기록만 취소할 수 있다")
    void t3() {
        ReadingProgressResponse checked = readingProgressService.check(MEMBER_ID, new ReadingProgressRequest(1, 1));

        assertThatThrownBy(() -> readingProgressService.cancel(OTHER_MEMBER_ID, checked.id()))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("본인의 기록만");
    }

    @Test
    @DisplayName("체크 취소가 정상 동작한다")
    void t4() {
        ReadingProgressResponse checked = readingProgressService.check(MEMBER_ID, new ReadingProgressRequest(1, 1));

        readingProgressService.cancel(MEMBER_ID, checked.id());

        assertThat(readingProgressService.getAll(MEMBER_ID)).isEmpty();
    }
}
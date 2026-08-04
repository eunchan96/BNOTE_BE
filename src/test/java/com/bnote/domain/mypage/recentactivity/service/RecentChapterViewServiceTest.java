package com.bnote.domain.mypage.recentactivity.service;

import com.bnote.domain.mypage.recentactivity.dto.request.RecentChapterViewRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class RecentChapterViewServiceTest {

    @Autowired
    private RecentChapterViewService recentChapterViewService;

    private static final Long MEMBER_ID = 1L;

    @Test
    @DisplayName("같은 장을 다시 기록해도 하나만 남는다(upsert)")
    void t1() {
        recentChapterViewService.record(MEMBER_ID, new RecentChapterViewRequest(1, 1));
        recentChapterViewService.record(MEMBER_ID, new RecentChapterViewRequest(1, 1));

        assertThat(recentChapterViewService.getRecent(MEMBER_ID, null)).hasSize(1);
    }

    @Test
    @DisplayName("limit을 지정하면 그만큼만 최신순으로 반환한다")
    void t2() {
        for (int chapter = 1; chapter <= 5; chapter++) {
            recentChapterViewService.record(MEMBER_ID, new RecentChapterViewRequest(1, chapter));
        }

        List<?> result = recentChapterViewService.getRecent(MEMBER_ID, 3);

        assertThat(result).hasSize(3);
    }
}
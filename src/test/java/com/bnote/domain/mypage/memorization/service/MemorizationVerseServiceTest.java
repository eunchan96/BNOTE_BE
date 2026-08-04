package com.bnote.domain.mypage.memorization.service;

import com.bnote.domain.bible.bible.entity.BibleVerse;
import com.bnote.domain.bible.bible.repository.BibleVerseRepository;
import com.bnote.domain.mypage.memorization.dto.request.MemorizationGroupRequest;
import com.bnote.domain.mypage.memorization.dto.request.MemorizationReviewRequest;
import com.bnote.domain.mypage.memorization.dto.request.MemorizationVerseRequest;
import com.bnote.domain.mypage.memorization.dto.response.MemorizationGroupResponse;
import com.bnote.domain.mypage.memorization.dto.response.MemorizationVerseResponse;
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
class MemorizationVerseServiceTest {

    @Autowired
    private MemorizationVerseService memorizationVerseService;

    @Autowired
    private MemorizationGroupService memorizationGroupService;

    @Autowired
    private BibleVerseRepository bibleVerseRepository;

    private static final Long MEMBER_ID = 1L;
    private Long groupId;

    @BeforeEach
    void setUp() {
        bibleVerseRepository.save(
                BibleVerse.builder().translation("NKRV").bookId(43).chapter(3).verse(16)
                        .text("하나님이 세상을 이처럼 사랑하사").build()
        );
        MemorizationGroupResponse group = memorizationGroupService.create(MEMBER_ID, new MemorizationGroupRequest("그룹", 0));
        groupId = group.id();
    }

    @Test
    @DisplayName("등록 시 본문 스냅샷이 저장된다")
    void t1() {
        MemorizationVerseResponse response = memorizationVerseService.create(
                MEMBER_ID, new MemorizationVerseRequest(groupId, 43, 3, 16, 43, 3, 16, "NKRV", "요한복음")
        );

        assertThat(response.verseText()).contains("하나님이 세상을");
        assertThat(response.reviewCount()).isZero();
        assertThat(response.isMastered()).isFalse();
    }

    @Test
    @DisplayName("연습 기록을 남기면 reviewCount가 증가하고 mastered가 갱신된다")
    void t2() {
        MemorizationVerseResponse created = memorizationVerseService.create(
                MEMBER_ID, new MemorizationVerseRequest(groupId, 43, 3, 16, 43, 3, 16, "NKRV", null)
        );

        MemorizationVerseResponse reviewed = memorizationVerseService.review(
                MEMBER_ID, created.id(), new MemorizationReviewRequest(true)
        );

        assertThat(reviewed.reviewCount()).isEqualTo(1);
        assertThat(reviewed.isMastered()).isTrue();
        assertThat(reviewed.lastReviewedAt()).isNotNull();
    }
}
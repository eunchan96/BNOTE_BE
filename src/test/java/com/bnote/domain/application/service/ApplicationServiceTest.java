package com.bnote.domain.application.service;

import com.bnote.domain.application.dto.request.ApplicationBibleRefRequest;
import com.bnote.domain.application.dto.request.ApplicationRequest;
import com.bnote.domain.application.dto.response.ApplicationResponse;
import com.bnote.domain.member.repository.MemberRepository;
import com.bnote.domain.sermon.entity.Sermon;
import com.bnote.domain.sermon.repository.SermonRepository;
import com.bnote.global.exception.ServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class ApplicationServiceTest {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private SermonRepository sermonRepository;

    @Autowired
    private MemberRepository memberRepository;

    private static final Long MEMBER_ID = 1L;
    private static final Long OTHER_MEMBER_ID = 2L;

    @Test
    @DisplayName("적용을 본문 범위와 함께 등록할 수 있다")
    void t1() {
        ApplicationResponse response = applicationService.create(
                MEMBER_ID,
                new ApplicationRequest(
                        "오늘의 적용", null, LocalDate.of(2026, 1, 5),
                        "묵상 내용", "기도 내용", "순종 내용",
                        List.of(new ApplicationBibleRefRequest(43, 3, 16, 43, 3, 16, false)),
                        null
                )
        );

        assertThat(response.title()).isEqualTo("오늘의 적용");
        assertThat(response.bibleRefs()).hasSize(1);
        assertThat(response.meditationMemo()).isEqualTo("묵상 내용");
    }

    @Test
    @DisplayName("설교노트와 연결해서 등록할 수 있고 sermonIds로 조회된다")
    void t2() {
        Sermon sermon = sermonRepository.save(
                Sermon.builder().memberId(MEMBER_ID).title("설교제목").sermonDate(LocalDate.now()).memo("내용").build()
        );

        ApplicationResponse response = applicationService.create(
                MEMBER_ID,
                new ApplicationRequest("적용", null, LocalDate.now(), "묵상", "기도", "순종", null, List.of(sermon.getId()))
        );

        assertThat(response.sermonIds()).containsExactly(sermon.getId());
    }

    @Test
    @DisplayName("본인 소유가 아닌 설교노트를 연결하려 하면 예외가 발생한다")
    void t3() {
        Sermon otherSermon = sermonRepository.save(
                Sermon.builder().memberId(OTHER_MEMBER_ID).title("남의 설교").sermonDate(LocalDate.now()).memo("내용").build()
        );

        assertThatThrownBy(() ->
                applicationService.create(
                        MEMBER_ID,
                        new ApplicationRequest("적용", null, LocalDate.now(), "묵상", "기도", "순종", null, List.of(otherSermon.getId()))
                )
        )
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("본인의 적용만");
    }

    @Test
    @DisplayName("본인 소유가 아닌 적용은 조회할 수 없다")
    void t4() {
        ApplicationResponse created = applicationService.create(
                MEMBER_ID, new ApplicationRequest("적용", null, LocalDate.now(), "묵상", "기도", "순종", null, null)
        );

        assertThatThrownBy(() -> applicationService.getById(OTHER_MEMBER_ID, created.id()))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("본인의 적용만");
    }

    @Test
    @DisplayName("수정 시 본문 범위가 새 값으로 교체된다")
    void t5() {
        ApplicationResponse created = applicationService.create(
                MEMBER_ID,
                new ApplicationRequest(
                        "적용", null, LocalDate.now(), "묵상", "기도", "순종",
                        List.of(new ApplicationBibleRefRequest(1, 1, 1, 1, 1, 1, false)), null
                )
        );

        ApplicationResponse updated = applicationService.update(
                MEMBER_ID, created.id(),
                new ApplicationRequest(
                        "수정된 적용", null, LocalDate.now(), "묵상2", "기도2", "순종2",
                        List.of(new ApplicationBibleRefRequest(43, 3, 16, 43, 3, 16, false)), null
                )
        );

        assertThat(updated.title()).isEqualTo("수정된 적용");
        assertThat(updated.bibleRefs()).hasSize(1);
        assertThat(updated.bibleRefs().get(0).startBookId()).isEqualTo(43);
    }
}
package com.bnote.domain.mypage.gratitude.service;

import com.bnote.domain.mypage.gratitude.dto.request.GratitudeNoteRequest;
import com.bnote.domain.mypage.gratitude.dto.response.GratitudeNoteResponse;
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
class GratitudeServiceTest {

    @Autowired
    private GratitudeService gratitudeService;

    private static final Long MEMBER_ID = 1L;
    private static final Long OTHER_MEMBER_ID = 2L;
    private static final LocalDate TODAY = LocalDate.of(2026, 1, 5);

    @Test
    @DisplayName("한 노트에 여러 줄을 등록할 수 있다")
    void t1() {
        GratitudeNoteResponse response = gratitudeService.save(
                MEMBER_ID, new GratitudeNoteRequest(TODAY, List.of("건강해서 감사", "가족이 있어서 감사"))
        );

        assertThat(response.entries()).hasSize(2);
    }

    @Test
    @DisplayName("같은 날짜로 다시 등록하면 내용이 교체된다(upsert)")
    void t2() {
        gratitudeService.save(MEMBER_ID, new GratitudeNoteRequest(TODAY, List.of("첫 감사")));
        GratitudeNoteResponse updated = gratitudeService.save(MEMBER_ID, new GratitudeNoteRequest(TODAY, List.of("수정된 감사1", "수정된 감사2")));

        assertThat(updated.entries()).hasSize(2);
        assertThat(gratitudeService.getAll(MEMBER_ID)).hasSize(1);
    }

    @Test
    @DisplayName("본인 노트만 삭제할 수 있다")
    void t3() {
        GratitudeNoteResponse created = gratitudeService.save(MEMBER_ID, new GratitudeNoteRequest(TODAY, List.of("감사")));

        assertThatThrownBy(() -> gratitudeService.delete(OTHER_MEMBER_ID, created.id()))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("본인의 감사노트만");
    }
}
package com.bnote.domain.sermon.service;

import com.bnote.domain.sermon.dto.request.BibleRefRequest;
import com.bnote.domain.sermon.dto.request.PreacherRequest;
import com.bnote.domain.sermon.dto.request.SermonRequest;
import com.bnote.domain.sermon.dto.response.PreacherResponse;
import com.bnote.domain.sermon.dto.response.SermonResponse;
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
class SermonServiceTest {

    @Autowired
    private SermonService sermonService;

    @Autowired
    private PreacherService preacherService;

    private static final Long MEMBER_ID = 1L;
    private static final Long OTHER_MEMBER_ID = 2L;

    @Test
    @DisplayName("설교노트를 본문 범위와 함께 등록할 수 있다")
    void t1() {
        SermonResponse response = sermonService.create(
                MEMBER_ID,
                new SermonRequest(
                        "산상수훈", null, LocalDate.of(2026, 1, 5), null, "복 있는 사람은...", null,
                        List.of(new BibleRefRequest(40, 5, 1, 40, 7, 29))
                )
        );

        assertThat(response.title()).isEqualTo("산상수훈");
        assertThat(response.bibleRefs()).hasSize(1);
        assertThat(response.bibleRefs().get(0).startChapter()).isEqualTo(5);
    }

    @Test
    @DisplayName("본인 소유가 아닌 설교자를 지정하면 예외가 발생한다")
    void t2() {
        PreacherResponse otherPreacher = preacherService.create(OTHER_MEMBER_ID, new PreacherRequest("남의 목사님", 0));

        assertThatThrownBy(() ->
                sermonService.create(
                        MEMBER_ID,
                        new SermonRequest("제목", otherPreacher.id(), LocalDate.now(), null, "내용", null, null)
                )
        )
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("본인의 설교노트만");
    }

    @Test
    @DisplayName("설교노트 수정 시 본문 범위가 새 값으로 교체된다")
    void t3() {
        SermonResponse created = sermonService.create(
                MEMBER_ID,
                new SermonRequest("제목", null, LocalDate.now(), null, "내용", null, List.of(new BibleRefRequest(1, 1, 1, 1, 1, 1)))
        );

        SermonResponse updated = sermonService.update(
                MEMBER_ID, created.id(),
                new SermonRequest("수정된 제목", null, LocalDate.now(), null, "내용", null, List.of(new BibleRefRequest(43, 3, 16, 43, 3, 16)))
        );

        assertThat(updated.title()).isEqualTo("수정된 제목");
        assertThat(updated.bibleRefs()).hasSize(1);
        assertThat(updated.bibleRefs().get(0).startBookId()).isEqualTo(43);
    }

    @Test
    @DisplayName("본인 소유가 아닌 설교노트는 조회할 수 없다")
    void t4() {
        SermonResponse created = sermonService.create(
                MEMBER_ID, new SermonRequest("제목", null, LocalDate.now(), null, "내용", null, null)
        );

        assertThatThrownBy(() -> sermonService.getById(OTHER_MEMBER_ID, created.id()))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("본인의 설교노트만");
    }

    @Test
    @DisplayName("설교노트 삭제 시 목록에서도 사라진다")
    void t5() {
        SermonResponse created = sermonService.create(
                MEMBER_ID, new SermonRequest("제목", null, LocalDate.now(), null, "내용", null, null)
        );

        sermonService.delete(MEMBER_ID, created.id());

        assertThat(sermonService.getAll(MEMBER_ID, null)).isEmpty();
    }
}
package com.bnote.domain.bible.memo.service;

import com.bnote.domain.bible.memo.dto.request.VerseMemoRequest;
import com.bnote.domain.bible.memo.dto.request.VerseMemoUpdateRequest;
import com.bnote.domain.bible.memo.dto.response.VerseMemoResponse;
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
class VerseMemoServiceTest {

    @Autowired
    private VerseMemoService verseMemoService;

    private static final Long MEMBER_ID = 1L;
    private static final Long OTHER_MEMBER_ID = 2L;

    @Test
    @DisplayName("같은 위치에 메모를 여러 개 작성할 수 있다")
    void t1() {
        verseMemoService.create(MEMBER_ID, new VerseMemoRequest(1, 1, 1, "첫 번째 메모"));
        verseMemoService.create(MEMBER_ID, new VerseMemoRequest(1, 1, 1, "두 번째 메모"));

        List<VerseMemoResponse> responses = verseMemoService.getByChapter(MEMBER_ID, 1, 1);

        assertThat(responses).hasSize(2);
    }

    @Test
    @DisplayName("본인 메모만 수정할 수 있다")
    void t2() {
        VerseMemoResponse created = verseMemoService.create(MEMBER_ID, new VerseMemoRequest(1, 1, 1, "원본"));

        assertThatThrownBy(() ->
                verseMemoService.update(OTHER_MEMBER_ID, created.id(), new VerseMemoUpdateRequest("수정 시도"))
        )
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("본인의 메모만");
    }

    @Test
    @DisplayName("메모 수정/삭제가 정상 동작한다")
    void t3() {
        VerseMemoResponse created = verseMemoService.create(MEMBER_ID, new VerseMemoRequest(1, 1, 1, "원본"));

        VerseMemoResponse updated = verseMemoService.update(MEMBER_ID, created.id(), new VerseMemoUpdateRequest("수정본"));
        assertThat(updated.text()).isEqualTo("수정본");

        verseMemoService.delete(MEMBER_ID, created.id());
        assertThat(verseMemoService.getByChapter(MEMBER_ID, 1, 1)).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 메모를 수정하려 하면 예외가 발생한다")
    void t4() {
        assertThatThrownBy(() -> verseMemoService.update(MEMBER_ID, 999L, new VerseMemoUpdateRequest("x")))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("존재하지 않는 구절 메모");
    }
}
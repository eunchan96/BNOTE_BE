package com.bnote.domain.sermon.service;

import com.bnote.domain.sermon.dto.request.PreacherRequest;
import com.bnote.domain.sermon.dto.response.PreacherResponse;
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
class PreacherServiceTest {

    @Autowired
    private PreacherService preacherService;

    private static final Long MEMBER_ID = 1L;
    private static final Long OTHER_MEMBER_ID = 2L;

    @Test
    @DisplayName("설교자를 등록하고 목록에서 확인할 수 있다")
    void t1() {
        preacherService.create(MEMBER_ID, new PreacherRequest("김OO 목사", 0));

        assertThat(preacherService.getAll(MEMBER_ID)).hasSize(1);
    }

    @Test
    @DisplayName("본인 설교자만 수정할 수 있다")
    void t2() {
        PreacherResponse preacher = preacherService.create(MEMBER_ID, new PreacherRequest("김OO 목사", 0));

        assertThatThrownBy(() -> preacherService.update(OTHER_MEMBER_ID, preacher.id(), new PreacherRequest("변경", 0)))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("본인의 설교노트만");
    }
}
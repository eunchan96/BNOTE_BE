package com.bnote.domain.mypage.copyformat.service;

import com.bnote.domain.mypage.copyformat.dto.request.CopyFormatPresetRequest;
import com.bnote.domain.mypage.copyformat.dto.response.CopyFormatPresetResponse;
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
class CopyFormatPresetServiceTest {

    @Autowired
    private CopyFormatPresetService copyFormatPresetService;

    private static final Long MEMBER_ID = 1L;
    private static final Long OTHER_MEMBER_ID = 2L;

    @Test
    @DisplayName("프리셋을 등록하고 목록에서 확인할 수 있다")
    void t1() {
        copyFormatPresetService.create(MEMBER_ID, new CopyFormatPresetRequest("기본형", "{\"showReference\":true}"));

        assertThat(copyFormatPresetService.getAll(MEMBER_ID)).hasSize(1);
    }

    @Test
    @DisplayName("본인 프리셋만 삭제할 수 있다")
    void t2() {
        CopyFormatPresetResponse preset = copyFormatPresetService.create(
                MEMBER_ID, new CopyFormatPresetRequest("기본형", "{\"showReference\":true}")
        );

        assertThatThrownBy(() -> copyFormatPresetService.delete(OTHER_MEMBER_ID, preset.id()))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("본인의 프리셋만");
    }
}
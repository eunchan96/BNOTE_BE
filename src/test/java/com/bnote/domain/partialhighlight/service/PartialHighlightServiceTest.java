package com.bnote.domain.partialhighlight.service;

import com.bnote.domain.partialhighlight.dto.request.PartialHighlightRequest;
import com.bnote.domain.partialhighlight.dto.response.PartialHighlightResponse;
import com.bnote.domain.partialhighlight.entity.PartialHighlight;
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
class PartialHighlightServiceTest {

    @Autowired
    private PartialHighlightService partialHighlightService;

    private static final Long MEMBER_ID = 1L;
    private static final Long OTHER_MEMBER_ID = 2L;

    @Test
    @DisplayName("색상을 지정하지 않으면 기본 색상으로 생성된다")
    void t1() {
        PartialHighlightResponse response = partialHighlightService.create(
                MEMBER_ID, new PartialHighlightRequest("NKRV", 1, 1, 1, 0, 3, null, null)
        );

        assertThat(response.colorHex()).isEqualTo(PartialHighlight.DEFAULT_COLOR_HEX);
    }

    @Test
    @DisplayName("본인 소유가 아니면 삭제할 수 없다")
    void t2() {
        PartialHighlightResponse created = partialHighlightService.create(
                MEMBER_ID, new PartialHighlightRequest("NKRV", 1, 1, 1, 0, 3, null, "#FF0000")
        );

        assertThatThrownBy(() -> partialHighlightService.delete(OTHER_MEMBER_ID, created.id()))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("본인의 하이라이트만");
    }

    @Test
    @DisplayName("본인 소유면 정상적으로 삭제된다")
    void t3() {
        PartialHighlightResponse created = partialHighlightService.create(
                MEMBER_ID, new PartialHighlightRequest("NKRV", 1, 1, 1, 0, 3, null, "#FF0000")
        );

        partialHighlightService.delete(MEMBER_ID, created.id());

        assertThat(partialHighlightService.getByLocation(MEMBER_ID, 1, 1, 1)).isEmpty();
    }

    @Test
    @DisplayName("verse를 생략하면 해당 장 전체의 하이라이트를 반환한다")
    void t4() {
        partialHighlightService.create(MEMBER_ID, new PartialHighlightRequest("NKRV", 1, 1, 1, 0, 3, null, null));
        partialHighlightService.create(MEMBER_ID, new PartialHighlightRequest("NKRV", 1, 1, 2, 0, 3, null, null));

        assertThat(partialHighlightService.getByLocation(MEMBER_ID, 1, 1, null)).hasSize(2);
    }
}
package com.bnote.domain.mypage.memorization.service;

import com.bnote.domain.mypage.memorization.dto.request.MemorizationGroupRequest;
import com.bnote.domain.mypage.memorization.dto.response.MemorizationGroupResponse;
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
class MemorizationGroupServiceTest {

    @Autowired
    private MemorizationGroupService memorizationGroupService;

    private static final Long MEMBER_ID = 1L;
    private static final Long OTHER_MEMBER_ID = 2L;

    @Test
    @DisplayName("그룹을 등록하고 목록에서 확인할 수 있다")
    void t1() {
        memorizationGroupService.create(MEMBER_ID, new MemorizationGroupRequest("올해의 말씀", 0));

        assertThat(memorizationGroupService.getAll(MEMBER_ID)).hasSize(1);
    }

    @Test
    @DisplayName("본인 그룹만 삭제할 수 있다")
    void t2() {
        MemorizationGroupResponse group = memorizationGroupService.create(MEMBER_ID, new MemorizationGroupRequest("그룹", 0));

        assertThatThrownBy(() -> memorizationGroupService.delete(OTHER_MEMBER_ID, group.id()))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("본인의 암송 자료만");
    }
}
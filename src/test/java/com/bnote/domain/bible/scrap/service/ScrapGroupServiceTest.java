package com.bnote.domain.bible.scrap.service;

import com.bnote.domain.bible.scrap.dto.request.ScrapGroupRequest;
import com.bnote.domain.bible.scrap.dto.response.ScrapGroupResponse;
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
class ScrapGroupServiceTest {

    @Autowired
    private ScrapGroupService scrapGroupService;

    private static final Long MEMBER_ID = 1L;
    private static final Long OTHER_MEMBER_ID = 2L;

    @Test
    @DisplayName("스크랩 그룹을 등록하고 목록에서 확인할 수 있다")
    void t1() {
        scrapGroupService.create(MEMBER_ID, new ScrapGroupRequest("은혜의 말씀", 0));

        assertThat(scrapGroupService.getGroups(MEMBER_ID)).hasSize(1);
    }

    @Test
    @DisplayName("본인 그룹만 이름 변경할 수 있다")
    void t2() {
        ScrapGroupResponse group = scrapGroupService.create(MEMBER_ID, new ScrapGroupRequest("원래 이름", 0));

        assertThatThrownBy(() ->
                scrapGroupService.rename(OTHER_MEMBER_ID, group.id(), new ScrapGroupRequest("바꾼 이름", 0))
        )
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("본인의 스크랩만");
    }

    @Test
    @DisplayName("그룹 이름 변경이 정상 동작한다")
    void t3() {
        ScrapGroupResponse group = scrapGroupService.create(MEMBER_ID, new ScrapGroupRequest("원래 이름", 0));

        ScrapGroupResponse renamed = scrapGroupService.rename(MEMBER_ID, group.id(), new ScrapGroupRequest("바꾼 이름", 0));

        assertThat(renamed.name()).isEqualTo("바꾼 이름");
    }
}
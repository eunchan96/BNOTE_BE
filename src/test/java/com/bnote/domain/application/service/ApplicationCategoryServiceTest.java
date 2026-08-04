package com.bnote.domain.application.service;

import com.bnote.domain.application.dto.request.ApplicationCategoryRequest;
import com.bnote.domain.application.dto.response.ApplicationCategoryResponse;
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
class ApplicationCategoryServiceTest {

    @Autowired
    private ApplicationCategoryService applicationCategoryService;

    private static final Long MEMBER_ID = 1L;

    @Test
    @DisplayName("최초 조회 시 기본 카테고리 3종(통독/설교/교제)이 자동 생성된다")
    void t1() {
        List<ApplicationCategoryResponse> categories = applicationCategoryService.getAll(MEMBER_ID);

        assertThat(categories).hasSize(3);
        assertThat(categories).extracting(ApplicationCategoryResponse::name)
                .containsExactly("통독", "설교", "교제");
    }

    @Test
    @DisplayName("기본 카테고리는 삭제할 수 없다")
    void t2() {
        Long defaultCategoryId = applicationCategoryService.getAll(MEMBER_ID).get(0).id();

        assertThatThrownBy(() -> applicationCategoryService.delete(MEMBER_ID, defaultCategoryId))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("기본 제공되는 카테고리");
    }

    @Test
    @DisplayName("직접 등록한 카테고리는 삭제할 수 있다")
    void t3() {
        applicationCategoryService.getAll(MEMBER_ID);
        var custom = applicationCategoryService.create(MEMBER_ID, new ApplicationCategoryRequest("가정예배", "#123456", 10));

        applicationCategoryService.delete(MEMBER_ID, custom.id());

        assertThat(applicationCategoryService.getAll(MEMBER_ID)).hasSize(3);
    }
}
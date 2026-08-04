package com.bnote.domain.sermon.service;

import com.bnote.domain.sermon.dto.request.SermonCategoryRequest;
import com.bnote.domain.sermon.dto.response.SermonCategoryResponse;
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
class SermonCategoryServiceTest {

    @Autowired
    private SermonCategoryService sermonCategoryService;

    private static final Long MEMBER_ID = 1L;

    @Test
    @DisplayName("최초 조회 시 기본 카테고리 6종이 자동 생성된다")
    void t1() {
        List<SermonCategoryResponse> categories = sermonCategoryService.getAll(MEMBER_ID);

        assertThat(categories).hasSize(6);
        assertThat(categories).allMatch(SermonCategoryResponse::isDefault);
    }

    @Test
    @DisplayName("기본 카테고리는 삭제할 수 없다")
    void t2() {
        List<SermonCategoryResponse> categories = sermonCategoryService.getAll(MEMBER_ID);
        Long defaultCategoryId = categories.get(0).id();

        assertThatThrownBy(() -> sermonCategoryService.delete(MEMBER_ID, defaultCategoryId))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("기본 제공되는 카테고리");
    }

    @Test
    @DisplayName("직접 등록한 카테고리는 삭제할 수 있다")
    void t3() {
        sermonCategoryService.getAll(MEMBER_ID); // 기본 카테고리 먼저 생성
        SermonCategoryResponse custom = sermonCategoryService.create(MEMBER_ID, new SermonCategoryRequest("특별새벽기도", "#000000", 10));

        sermonCategoryService.delete(MEMBER_ID, custom.id());

        assertThat(sermonCategoryService.getAll(MEMBER_ID)).hasSize(6);
    }
}
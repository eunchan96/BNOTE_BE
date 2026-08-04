package com.bnote.domain.bible.memo.service;

import com.bnote.domain.bible.memo.dto.request.WordMemoRequest;
import com.bnote.domain.bible.memo.dto.request.WordMemoUpdateRequest;
import com.bnote.domain.bible.memo.dto.response.WordMemoResponse;
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
class WordMemoServiceTest {

    @Autowired
    private WordMemoService wordMemoService;

    private static final Long MEMBER_ID = 1L;
    private static final Long OTHER_MEMBER_ID = 2L;

    @Test
    @DisplayName("단어 메모를 등록하고 조회할 수 있다")
    void t1() {
        wordMemoService.create(MEMBER_ID, new WordMemoRequest("NKRV", 1, 1, 1, 0, 2, "태초"));

        assertThat(wordMemoService.getByChapter(MEMBER_ID, 1, 1)).hasSize(1);
    }

    @Test
    @DisplayName("본인 메모만 삭제할 수 있다")
    void t2() {
        WordMemoResponse created = wordMemoService.create(MEMBER_ID, new WordMemoRequest("NKRV", 1, 1, 1, 0, 2, "태초"));

        assertThatThrownBy(() -> wordMemoService.delete(OTHER_MEMBER_ID, created.id()))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("본인의 메모만");
    }

    @Test
    @DisplayName("메모 수정이 정상 동작한다")
    void t3() {
        WordMemoResponse created = wordMemoService.create(MEMBER_ID, new WordMemoRequest("NKRV", 1, 1, 1, 0, 2, "태초"));

        WordMemoResponse updated = wordMemoService.update(MEMBER_ID, created.id(), new WordMemoUpdateRequest("태초에"));

        assertThat(updated.text()).isEqualTo("태초에");
    }
}
package com.bnote.domain.bible.scrap.service;

import com.bnote.domain.bible.bible.entity.BibleVerse;
import com.bnote.domain.bible.bible.repository.BibleVerseRepository;
import com.bnote.domain.bible.scrap.dto.request.ScrapGroupRequest;
import com.bnote.domain.bible.scrap.dto.request.ScrapRequest;
import com.bnote.domain.bible.scrap.dto.response.ScrapGroupResponse;
import com.bnote.domain.bible.scrap.dto.response.ScrapResponse;
import com.bnote.global.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
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
class ScrapServiceTest {

    @Autowired
    private ScrapService scrapService;

    @Autowired
    private ScrapGroupService scrapGroupService;

    @Autowired
    private BibleVerseRepository bibleVerseRepository;

    private static final Long MEMBER_ID = 1L;
    private static final Long OTHER_MEMBER_ID = 2L;
    private Long groupId;

    @BeforeEach
    void setUp() {
        bibleVerseRepository.save(
                BibleVerse.builder().translation("NKRV").bookId(1).chapter(1).verse(1).text("태초에 하나님이").build()
        );
        bibleVerseRepository.save(
                BibleVerse.builder().translation("NKRV").bookId(1).chapter(1).verse(2).text("천지를 창조하시니라").build()
        );
        ScrapGroupResponse group = scrapGroupService.create(MEMBER_ID, new ScrapGroupRequest("은혜의 말씀", 0));
        groupId = group.id();
    }

    @Test
    @DisplayName("스크랩 등록 시 절 범위의 본문이 스냅샷으로 저장된다")
    void t1() {
        ScrapResponse response = scrapService.create(
                MEMBER_ID, new ScrapRequest(groupId, 1, 1, 1, 2, "NKRV")
        );

        assertThat(response.verseText()).isEqualTo("태초에 하나님이 천지를 창조하시니라");
    }

    @Test
    @DisplayName("본인 소유가 아닌 그룹에는 스크랩할 수 없다")
    void t2() {
        assertThatThrownBy(() ->
                scrapService.create(OTHER_MEMBER_ID, new ScrapRequest(groupId, 1, 1, 1, 2, "NKRV"))
        )
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("본인의 스크랩만");
    }

    @Test
    @DisplayName("그룹을 삭제하면 그룹에 속한 스크랩도 함께 삭제된다")
    void t3() {
        scrapService.create(MEMBER_ID, new ScrapRequest(groupId, 1, 1, 1, 2, "NKRV"));

        scrapGroupService.delete(MEMBER_ID, groupId);

        assertThatThrownBy(() -> scrapService.getByGroup(MEMBER_ID, groupId))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("존재하지 않는 스크랩 그룹");
    }
}
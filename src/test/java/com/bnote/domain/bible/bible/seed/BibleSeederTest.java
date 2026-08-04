package com.bnote.domain.bible.bible.seed;

import com.bnote.domain.bible.bible.entity.Translation;
import com.bnote.domain.bible.bible.repository.BibleVerseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class BibleSeederTest {

    @Autowired
    private BibleSeeder bibleSeeder;

    @Autowired
    private BibleVerseRepository bibleVerseRepository;

    @Test
    @DisplayName("flat 포맷(NKRV) 시드 파일을 읽어 절을 채운다")
    void t1() {
        bibleSeeder.seedIfEmpty(Translation.NKRV);

        var verses = bibleVerseRepository.findByTranslationAndBookIdAndChapterOrderByVerseAsc("NKRV", 1, 1);
        assertThat(verses).hasSize(2);
        assertThat(verses.get(0).getText()).isEqualTo("태초에 하나님이 천지를 창조하시니라");
    }

    @Test
    @DisplayName("이미 데이터가 있으면 다시 시딩하지 않는다")
    void t2() {
        bibleSeeder.seedIfEmpty(Translation.NKRV);
        bibleSeeder.seedIfEmpty(Translation.NKRV);

        assertThat(bibleVerseRepository.countByTranslation("NKRV")).isEqualTo(2);
    }

    @Test
    @DisplayName("nested 포맷(NIV)에서 같은 절 번호가 연속되면 하나로 합쳐진다")
    void t3() {
        bibleSeeder.seedIfEmpty(Translation.NIV);

        var verses = bibleVerseRepository.findByTranslationAndBookIdAndChapterOrderByVerseAsc("NIV", 1, 1);
        assertThat(verses).hasSize(2);
        assertThat(verses.get(0).getVerse()).isEqualTo(1);
        assertThat(verses.get(0).getText()).isEqualTo("In the beginning God created the heavens and the earth.");
        assertThat(verses.get(1).getText()).isEqualTo("Now the earth was formless and empty.");
    }

    @Test
    @DisplayName("시드 파일이 없는 번역본은 조용히 건너뛴다")
    void t4() {
        bibleSeeder.seedIfEmpty(Translation.KRV);

        assertThat(bibleVerseRepository.countByTranslation("KRV")).isZero();
    }
}
package com.bnote.domain.appendix.seed;

import com.bnote.domain.appendix.repository.AppendixTextRepository;
import com.bnote.domain.appendix.repository.ResponsiveReadingRepository;
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
class AppendixSeederTest {

    @Autowired
    private AppendixSeeder appendixSeeder;

    @Autowired
    private AppendixTextRepository appendixTextRepository;

    @Autowired
    private ResponsiveReadingRepository responsiveReadingRepository;

    @Test
    @DisplayName("fixture에 있는 부록만 DB에 채워진다")
    void t1() {
        appendixSeeder.seedIfEmpty();

        assertThat(appendixTextRepository.existsById("lords-prayer")).isTrue();
        assertThat(appendixTextRepository.existsById("ten-commandments")).isTrue();
        assertThat(appendixTextRepository.existsById("apostles-creed")).isFalse(); // fixture에 없음
    }

    @Test
    @DisplayName("이미 데이터가 있으면 다시 시딩하지 않는다")
    void t2() {
        appendixSeeder.seedIfEmpty();
        appendixSeeder.seedIfEmpty();

        assertThat(appendixTextRepository.count()).isEqualTo(2);
    }
}
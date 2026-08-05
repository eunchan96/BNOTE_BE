package com.bnote.domain.appendix.service;

import com.bnote.domain.appendix.seed.AppendixSeeder;
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
class AppendixServiceTest {

    @Autowired
    private AppendixService appendixService;

    @Autowired
    private AppendixSeeder appendixSeeder;

    @BeforeEach
    void setUp() {
        appendixSeeder.seedIfEmpty();
    }

    @Test
    @DisplayName("fixture에 있는 타입은 정상 조회된다")
    void t1() {
        var response = appendixService.getLordsPrayer();

        assertThat(response.title()).isEqualTo("주기도문");
        assertThat(response.versions()).hasSize(1);
        assertThat(response.versions().get(0).lines()).hasSize(2);
    }

    @Test
    @DisplayName("십계명은 계명 목록과 요약을 함께 반환한다")
    void t2() {
        var response = appendixService.getTenCommandments();

        assertThat(response.commandments()).hasSize(2);
        assertThat(response.summary().reference()).isEqualTo("마 22:37-40");
    }

    @Test
    @DisplayName("교독문은 번호로 상세 조회할 수 있다")
    void t3() {
        var response = appendixService.getResponsiveReading(1);

        assertThat(response.title()).isEqualTo("창조");
        assertThat(response.lines()).hasSize(2);
    }

    @Test
    @DisplayName("교독문 전체 목록을 조회할 수 있다")
    void t4() {
        assertThat(appendixService.getResponsiveReadings()).hasSize(2);
    }

    @Test
    @DisplayName("존재하지 않는 교독문 번호를 조회하면 예외가 발생한다")
    void t5() {
        assertThatThrownBy(() -> appendixService.getResponsiveReading(999))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("존재하지 않는 교독문");
    }

    @Test
    @DisplayName("fixture에 없는 타입을 조회하면 예외가 발생한다")
    void t6() {
        assertThatThrownBy(() -> appendixService.getApostlesCreed())
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("사도신경 데이터가 없습니다");
    }
}
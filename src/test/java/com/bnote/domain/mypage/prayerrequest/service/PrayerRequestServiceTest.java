package com.bnote.domain.mypage.prayerrequest.service;

import com.bnote.domain.mypage.prayerrequest.dto.request.PrayerRequestAnswerRequest;
import com.bnote.domain.mypage.prayerrequest.dto.request.PrayerRequestCreateRequest;
import com.bnote.domain.mypage.prayerrequest.dto.response.PrayerRequestResponse;
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
class PrayerRequestServiceTest {

    @Autowired
    private PrayerRequestService prayerRequestService;

    private static final Long MEMBER_ID = 1L;
    private static final Long OTHER_MEMBER_ID = 2L;

    @Test
    @DisplayName("기도제목을 등록하고 목록에서 확인할 수 있다")
    void t1() {
        prayerRequestService.create(MEMBER_ID, new PrayerRequestCreateRequest("가족의 건강을 위해"));

        assertThat(prayerRequestService.getAll(MEMBER_ID)).hasSize(1);
    }

    @Test
    @DisplayName("응답 체크하면 응답 날짜가 함께 기록된다")
    void t2() {
        PrayerRequestResponse created = prayerRequestService.create(MEMBER_ID, new PrayerRequestCreateRequest("가족의 건강을 위해"));

        PrayerRequestResponse answered = prayerRequestService.markAnswered(
                MEMBER_ID, created.id(), new PrayerRequestAnswerRequest(true)
        );

        assertThat(answered.isAnswered()).isTrue();
        assertThat(answered.answeredDate()).isNotNull();
    }

    @Test
    @DisplayName("본인 기도제목만 삭제할 수 있다")
    void t3() {
        PrayerRequestResponse created = prayerRequestService.create(MEMBER_ID, new PrayerRequestCreateRequest("가족의 건강을 위해"));

        assertThatThrownBy(() -> prayerRequestService.delete(OTHER_MEMBER_ID, created.id()))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("본인의 기도제목만");
    }
}
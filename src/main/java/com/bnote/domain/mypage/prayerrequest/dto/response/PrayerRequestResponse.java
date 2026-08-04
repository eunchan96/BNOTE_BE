package com.bnote.domain.mypage.prayerrequest.dto.response;

import com.bnote.domain.mypage.prayerrequest.entity.PrayerRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record PrayerRequestResponse(
	Long id,
	String content,
	boolean isAnswered,
	LocalDate answeredDate,
	LocalDateTime createdAt
) {
	public static PrayerRequestResponse from(PrayerRequest prayerRequest) {
		return new PrayerRequestResponse(
			prayerRequest.getId(), prayerRequest.getContent(), prayerRequest.isAnswered(),
			prayerRequest.getAnsweredDate(), prayerRequest.getCreateDate()
		);
	}
}
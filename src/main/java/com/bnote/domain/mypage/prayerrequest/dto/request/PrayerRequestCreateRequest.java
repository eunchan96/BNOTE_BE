package com.bnote.domain.mypage.prayerrequest.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PrayerRequestCreateRequest(
	@NotBlank String content
) {
}
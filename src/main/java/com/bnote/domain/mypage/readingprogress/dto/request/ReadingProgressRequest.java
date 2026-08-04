package com.bnote.domain.mypage.readingprogress.dto.request;

import jakarta.validation.constraints.NotNull;

public record ReadingProgressRequest(
	@NotNull Integer bookId,
	@NotNull Integer chapter
) {
}
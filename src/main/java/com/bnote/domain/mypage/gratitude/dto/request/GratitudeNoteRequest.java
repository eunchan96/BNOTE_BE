package com.bnote.domain.mypage.gratitude.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record GratitudeNoteRequest(
	@NotNull LocalDate date,
	@NotEmpty List<String> entries
) {
}
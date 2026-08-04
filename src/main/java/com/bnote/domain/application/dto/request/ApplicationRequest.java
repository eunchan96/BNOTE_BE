package com.bnote.domain.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record ApplicationRequest(
	@NotBlank String title,
	Long categoryId,
	@NotNull LocalDate applicationDate,
	String meditationMemo,
	String prayerMemo,
	String obedienceMemo,
	List<ApplicationBibleRefRequest> bibleRefs,
	List<Long> sermonIds
) {
}
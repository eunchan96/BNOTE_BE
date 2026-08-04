package com.bnote.domain.sermon.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record SermonRequest(
	@NotBlank String title,
	Long preacherId,
	@NotNull LocalDate sermonDate,
	Long categoryId,
	String memo,
	String link,
	List<BibleRefRequest> bibleRefs
) {
}
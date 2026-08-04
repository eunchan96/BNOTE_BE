package com.bnote.domain.mypage.verseofyear.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record VerseOfYearRequest(
	@NotNull Integer year,
	String note,
	List<VerseOfYearRefRequest> verseRefs
) {
}
package com.bnote.domain.mypage.verseofyear.dto.response;

import com.bnote.domain.mypage.verseofyear.entity.VerseOfYear;
import java.util.List;

public record VerseOfYearResponse(
	Long id,
	Integer year,
	String note,
	List<VerseOfYearRefResponse> verseRefs
) {
	public static VerseOfYearResponse of(VerseOfYear verseOfYear, List<VerseOfYearRefResponse> verseRefs) {
		return new VerseOfYearResponse(verseOfYear.getId(), verseOfYear.getYear(), verseOfYear.getNote(), verseRefs);
	}
}
package com.bnote.domain.application.dto.response;

import com.bnote.domain.application.entity.Application;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ApplicationResponse(
	Long id,
	String title,
	ApplicationCategoryResponse category,
	LocalDate applicationDate,
	String meditationMemo,
	String prayerMemo,
	String obedienceMemo,
	List<ApplicationBibleRefResponse> bibleRefs,
	List<Long> sermonIds,
	LocalDateTime createdAt
) {
	public static ApplicationResponse of(
		Application application, ApplicationCategoryResponse category,
		List<ApplicationBibleRefResponse> bibleRefs, List<Long> sermonIds
	) {
		return new ApplicationResponse(
			application.getId(), application.getTitle(), category, application.getApplicationDate(),
			application.getMeditationMemo(), application.getPrayerMemo(), application.getObedienceMemo(),
			bibleRefs, sermonIds, application.getCreateDate()
		);
	}
}
package com.bnote.domain.application.dto.response;

import com.bnote.domain.application.entity.ApplicationBibleRef;

public record ApplicationBibleRefResponse(
	Long id,
	Integer startBookId,
	Integer startChapter,
	Integer startVerse,
	Integer endBookId,
	Integer endChapter,
	Integer endVerse,
	boolean isChapterOnly
) {
	public static ApplicationBibleRefResponse from(ApplicationBibleRef ref) {
		return new ApplicationBibleRefResponse(
			ref.getId(), ref.getStartBookId(), ref.getStartChapter(), ref.getStartVerse(),
			ref.getEndBookId(), ref.getEndChapter(), ref.getEndVerse(), ref.isChapterOnly()
		);
	}
}
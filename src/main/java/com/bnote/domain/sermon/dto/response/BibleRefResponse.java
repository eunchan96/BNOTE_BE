package com.bnote.domain.sermon.dto.response;

import com.bnote.domain.sermon.entity.SermonBibleRef;

public record BibleRefResponse(
	Long id,
	Integer startBookId,
	Integer startChapter,
	Integer startVerse,
	Integer endBookId,
	Integer endChapter,
	Integer endVerse
) {
	public static BibleRefResponse from(SermonBibleRef ref) {
		return new BibleRefResponse(
			ref.getId(), ref.getStartBookId(), ref.getStartChapter(), ref.getStartVerse(),
			ref.getEndBookId(), ref.getEndChapter(), ref.getEndVerse()
		);
	}
}
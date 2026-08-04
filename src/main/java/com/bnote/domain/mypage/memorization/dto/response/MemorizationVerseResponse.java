package com.bnote.domain.mypage.memorization.dto.response;

import com.bnote.domain.mypage.memorization.entity.MemorizationVerse;
import java.time.LocalDateTime;

public record MemorizationVerseResponse(
	Long id,
	Long groupId,
	Integer startBookId,
	Integer startChapter,
	Integer startVerse,
	Integer endBookId,
	Integer endChapter,
	Integer endVerse,
	String verseText,
	String note,
	int reviewCount,
	LocalDateTime lastReviewedAt,
	boolean isMastered,
	LocalDateTime createdAt
) {
	public static MemorizationVerseResponse from(MemorizationVerse verse) {
		return new MemorizationVerseResponse(
			verse.getId(), verse.getGroupId(), verse.getStartBookId(), verse.getStartChapter(), verse.getStartVerse(),
			verse.getEndBookId(), verse.getEndChapter(), verse.getEndVerse(), verse.getVerseText(), verse.getNote(),
			verse.getReviewCount(), verse.getLastReviewedAt(), verse.isMastered(), verse.getCreateDate()
		);
	}
}
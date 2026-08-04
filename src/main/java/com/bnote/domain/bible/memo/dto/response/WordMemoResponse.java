package com.bnote.domain.bible.memo.dto.response;

import com.bnote.domain.bible.memo.entity.WordMemo;
import java.time.LocalDateTime;

public record WordMemoResponse(
	Long id,
	String translation,
	Integer bookId,
	Integer chapter,
	Integer verse,
	Integer startOffset,
	Integer endOffset,
	String text,
	String sourceLabel,
	LocalDateTime updatedAt
) {
	public static WordMemoResponse from(WordMemo memo) {
		return new WordMemoResponse(
			memo.getId(), memo.getTranslation(), memo.getBookId(), memo.getChapter(), memo.getVerse(),
			memo.getStartOffset(), memo.getEndOffset(), memo.getText(), memo.getSourceLabel(), memo.getModifyDate()
		);
	}
}
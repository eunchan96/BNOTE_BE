package com.bnote.domain.bible.memo.dto.response;

import com.bnote.domain.bible.memo.entity.VerseMemo;
import java.time.LocalDateTime;

public record VerseMemoResponse(
	Long id,
	Integer bookId,
	Integer chapter,
	Integer verse,
	String text,
	LocalDateTime updatedAt
) {
	public static VerseMemoResponse from(VerseMemo memo) {
		return new VerseMemoResponse(
			memo.getId(), memo.getBookId(), memo.getChapter(), memo.getVerse(), memo.getText(), memo.getModifyDate()
		);
	}
}
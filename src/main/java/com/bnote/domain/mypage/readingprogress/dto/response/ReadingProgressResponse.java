package com.bnote.domain.mypage.readingprogress.dto.response;

import com.bnote.domain.mypage.readingprogress.entity.ReadingProgress;
import java.time.LocalDate;

public record ReadingProgressResponse(
	Long id,
	Integer bookId,
	Integer chapter,
	LocalDate readDate
) {
	public static ReadingProgressResponse from(ReadingProgress progress) {
		return new ReadingProgressResponse(progress.getId(), progress.getBookId(), progress.getChapter(), progress.getReadDate());
	}
}
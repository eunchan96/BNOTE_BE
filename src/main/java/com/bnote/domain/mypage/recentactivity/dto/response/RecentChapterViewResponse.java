package com.bnote.domain.mypage.recentactivity.dto.response;

import com.bnote.domain.mypage.recentactivity.entity.RecentChapterView;
import java.time.LocalDateTime;

public record RecentChapterViewResponse(
	Long id,
	Integer bookId,
	Integer chapter,
	LocalDateTime viewedAt
) {
	public static RecentChapterViewResponse from(RecentChapterView view) {
		return new RecentChapterViewResponse(view.getId(), view.getBookId(), view.getChapter(), view.getViewedAt());
	}
}
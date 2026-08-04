package com.bnote.domain.mypage.recentactivity.dto.request;

import jakarta.validation.constraints.NotNull;

public record RecentChapterViewRequest(
	@NotNull Integer bookId,
	@NotNull Integer chapter
) {
}
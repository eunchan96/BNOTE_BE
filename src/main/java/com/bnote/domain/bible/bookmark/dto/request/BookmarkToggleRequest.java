package com.bnote.domain.bible.bookmark.dto.request;

public record BookmarkToggleRequest(
	Boolean isBookmarked,
	Boolean isHighlighted
) {
}
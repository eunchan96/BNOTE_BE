package com.bnote.domain.bookmark.dto.request;

public record BookmarkToggleRequest(
	Boolean isBookmarked,
	Boolean isHighlighted
) {
}
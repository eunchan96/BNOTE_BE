package com.bnote.domain.bible.scrap.dto.response;

import com.bnote.domain.bible.scrap.entity.ScrapGroup;

public record ScrapGroupResponse(
	Long id,
	String name,
	int sortOrder
) {
	public static ScrapGroupResponse from(ScrapGroup group) {
		return new ScrapGroupResponse(group.getId(), group.getName(), group.getSortOrder());
	}
}
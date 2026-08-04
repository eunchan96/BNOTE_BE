package com.bnote.domain.bible.hymn.dto.response;

import com.bnote.domain.bible.hymn.entity.HymnCategory;

public record HymnCategoryResponse(
	Long id,
	String name,
	Long parentId,
	int sortOrder
) {
	public static HymnCategoryResponse from(HymnCategory category) {
		return new HymnCategoryResponse(category.getId(), category.getName(), category.getParentId(), category.getSortOrder());
	}
}
package com.bnote.domain.knowledge.dto.response;

import com.bnote.domain.knowledge.entity.BiblePlace;

public record BiblePlaceResponse(
	String id, String name, String otherNames, String category, String region,
	String summary, String description, Integer keyBookId, Integer keyChapter, String keyVerseLabel
) {
	public static BiblePlaceResponse from(BiblePlace e) {
		return new BiblePlaceResponse(
			e.getId(), e.getName(), e.getOtherNames(), e.getCategory(), e.getRegion(),
			e.getSummary(), e.getDescription(), e.getKeyBookId(), e.getKeyChapter(), e.getKeyVerseLabel()
		);
	}
}
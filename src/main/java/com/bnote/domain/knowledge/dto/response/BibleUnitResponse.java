package com.bnote.domain.knowledge.dto.response;

import com.bnote.domain.knowledge.entity.BibleUnit;

public record BibleUnitResponse(
	String id, String title, String category, String summary, String description,
	Integer keyBookId, Integer keyChapter, String keyVerseLabel
) {
	public static BibleUnitResponse from(BibleUnit e) {
		return new BibleUnitResponse(
			e.getId(), e.getTitle(), e.getCategory(), e.getSummary(), e.getDescription(),
			e.getKeyBookId(), e.getKeyChapter(), e.getKeyVerseLabel()
		);
	}
}
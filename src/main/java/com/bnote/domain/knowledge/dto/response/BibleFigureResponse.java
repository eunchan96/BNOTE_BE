package com.bnote.domain.knowledge.dto.response;

import com.bnote.domain.knowledge.entity.BibleFigure;

public record BibleFigureResponse(
	String id, String name, String otherNames, String category, String era,
	String summary, String description, Integer keyBookId, Integer keyChapter, String keyVerseLabel
) {
	public static BibleFigureResponse from(BibleFigure e) {
		return new BibleFigureResponse(
			e.getId(), e.getName(), e.getOtherNames(), e.getCategory(), e.getEra(),
			e.getSummary(), e.getDescription(), e.getKeyBookId(), e.getKeyChapter(), e.getKeyVerseLabel()
		);
	}
}
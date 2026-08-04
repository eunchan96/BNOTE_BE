package com.bnote.domain.knowledge.dto.response;

import com.bnote.domain.knowledge.entity.ParableOrMiracle;

public record ParableOrMiracleResponse(
	String id, String title, String type, String summary, String description,
	Integer keyBookId, Integer keyChapter, String keyVerseLabel
) {
	public static ParableOrMiracleResponse from(ParableOrMiracle e) {
		return new ParableOrMiracleResponse(
			e.getId(), e.getTitle(), e.getType(), e.getSummary(), e.getDescription(),
			e.getKeyBookId(), e.getKeyChapter(), e.getKeyVerseLabel()
		);
	}
}
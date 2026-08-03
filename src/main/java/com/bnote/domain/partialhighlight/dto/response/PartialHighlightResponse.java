package com.bnote.domain.partialhighlight.dto.response;

import com.bnote.domain.partialhighlight.entity.PartialHighlight;

public record PartialHighlightResponse(
	Long id,
	String translation,
	Integer bookId,
	Integer chapter,
	Integer verse,
	Integer startOffset,
	Integer endOffset,
	Integer segment,
	String colorHex
) {
	public static PartialHighlightResponse from(PartialHighlight highlight) {
		return new PartialHighlightResponse(
			highlight.getId(),
			highlight.getTranslation(),
			highlight.getBookId(),
			highlight.getChapter(),
			highlight.getVerse(),
			highlight.getStartOffset(),
			highlight.getEndOffset(),
			highlight.getSegment(),
			highlight.getColorHex()
		);
	}
}
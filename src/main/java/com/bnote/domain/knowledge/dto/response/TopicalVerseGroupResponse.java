package com.bnote.domain.knowledge.dto.response;

import com.bnote.domain.knowledge.entity.TopicalVerseGroup;
import com.bnote.domain.knowledge.entity.TopicalVerseRef;
import java.util.List;

public record TopicalVerseGroupResponse(
	String id, String title, List<VerseItem> verses
) {
	public record VerseItem(Integer bookId, Integer chapter, Integer verseStart, Integer verseEnd) {
		public static VerseItem from(TopicalVerseRef ref) {
			return new VerseItem(ref.getBookId(), ref.getChapter(), ref.getVerseStart(), ref.getVerseEnd());
		}
	}

	public static TopicalVerseGroupResponse of(TopicalVerseGroup group, List<TopicalVerseRef> refs) {
		return new TopicalVerseGroupResponse(group.getId(), group.getTitle(), refs.stream().map(VerseItem::from).toList());
	}
}
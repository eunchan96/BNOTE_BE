package com.bnote.domain.knowledge.dto.response;

import com.bnote.domain.knowledge.entity.GenealogyChart;
import com.bnote.domain.knowledge.entity.GenealogyEntry;
import java.util.List;

public record GenealogyChartResponse(
	String id, String title, String description,
	Integer keyBookId, Integer keyChapter, String keyVerseLabel,
	List<EntryItem> entries
) {
	public record EntryItem(String name, String relation, String note) {
		public static EntryItem from(GenealogyEntry e) {
			return new EntryItem(e.getName(), e.getRelation(), e.getNote());
		}
	}

	public static GenealogyChartResponse of(GenealogyChart chart, List<GenealogyEntry> entries) {
		return new GenealogyChartResponse(
			chart.getId(), chart.getTitle(), chart.getDescription(),
			chart.getKeyBookId(), chart.getKeyChapter(), chart.getKeyVerseLabel(),
			entries.stream().map(EntryItem::from).toList()
		);
	}
}
package com.bnote.domain.knowledge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "genealogy_charts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenealogyChart {

	@Id
	private String id;

	@Column(nullable = false)
	private String title;

	@Lob
	private String description;

	@Column(name = "key_book_id")
	private Integer keyBookId;

	@Column(name = "key_chapter")
	private Integer keyChapter;

	@Column(name = "key_verse_label")
	private String keyVerseLabel;

	@Builder
	private GenealogyChart(
		String id, String title, String description, Integer keyBookId, Integer keyChapter, String keyVerseLabel
	) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.keyBookId = keyBookId;
		this.keyChapter = keyChapter;
		this.keyVerseLabel = keyVerseLabel;
	}
}
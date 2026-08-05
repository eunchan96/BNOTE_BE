package com.bnote.domain.knowledge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "bible_places")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BiblePlace {

	@Id
	private String id;

	@Column(nullable = false)
	private String name;

	@Column(name = "other_names")
	private String otherNames;

	private String category;

	private String region;

	@Column(nullable = false)
	private String summary;

	@Column(columnDefinition = "text")
	private String description;

	@Column(name = "key_book_id")
	private Integer keyBookId;

	@Column(name = "key_chapter")
	private Integer keyChapter;

	@Column(name = "key_verse_label")
	private String keyVerseLabel;

	@Builder
	private BiblePlace(
		String id, String name, String otherNames, String category, String region, String summary,
		String description, Integer keyBookId, Integer keyChapter, String keyVerseLabel
	) {
		this.id = id;
		this.name = name;
		this.otherNames = otherNames;
		this.category = category;
		this.region = region;
		this.summary = summary;
		this.description = description;
		this.keyBookId = keyBookId;
		this.keyChapter = keyChapter;
		this.keyVerseLabel = keyVerseLabel;
	}
}
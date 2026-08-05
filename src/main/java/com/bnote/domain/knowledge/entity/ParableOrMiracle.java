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
@Table(name = "parables_and_miracles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParableOrMiracle {

	@Id
	private String id;

	@Column(nullable = false)
	private String title;

	/** "비유" 또는 "이적". DB 컬럼명은 category_type — "type"도 예약어 후보라 안전하게 명시 */
	@Column(name = "category_type", nullable = false)
	private String type;

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
	private ParableOrMiracle(
			String id, String title, String type, String summary, String description,
			Integer keyBookId, Integer keyChapter, String keyVerseLabel
	) {
		this.id = id;
		this.title = title;
		this.type = type;
		this.summary = summary;
		this.description = description;
		this.keyBookId = keyBookId;
		this.keyChapter = keyChapter;
		this.keyVerseLabel = keyVerseLabel;
	}
}
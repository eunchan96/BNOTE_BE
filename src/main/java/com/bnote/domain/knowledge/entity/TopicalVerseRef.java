package com.bnote.domain.knowledge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "topical_verse_refs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TopicalVerseRef {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "group_id", nullable = false)
	private String groupId;

	@Column(name = "book_id", nullable = false)
	private Integer bookId;

	@Column(nullable = false)
	private Integer chapter;

	@Column(name = "verse_start", nullable = false)
	private Integer verseStart;

	@Column(name = "verse_end", nullable = false)
	private Integer verseEnd;

	@Builder
	private TopicalVerseRef(String groupId, Integer bookId, Integer chapter, Integer verseStart, Integer verseEnd) {
		this.groupId = groupId;
		this.bookId = bookId;
		this.chapter = chapter;
		this.verseStart = verseStart;
		this.verseEnd = verseEnd;
	}
}
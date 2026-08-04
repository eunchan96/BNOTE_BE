package com.bnote.domain.sermon.entity;

import com.bnote.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "sermon_bible_refs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SermonBibleRef extends BaseEntity {

	@Column(name = "sermon_id", nullable = false)
	private Long sermonId;

	@Column(name = "start_book_id", nullable = false)
	private Integer startBookId;

	@Column(name = "start_chapter", nullable = false)
	private Integer startChapter;

	@Column(name = "start_verse", nullable = false)
	private Integer startVerse;

	@Column(name = "end_book_id", nullable = false)
	private Integer endBookId;

	@Column(name = "end_chapter", nullable = false)
	private Integer endChapter;

	@Column(name = "end_verse", nullable = false)
	private Integer endVerse;

	@Builder
	private SermonBibleRef(
		Long sermonId, Integer startBookId, Integer startChapter, Integer startVerse,
		Integer endBookId, Integer endChapter, Integer endVerse
	) {
		this.sermonId = sermonId;
		this.startBookId = startBookId;
		this.startChapter = startChapter;
		this.startVerse = startVerse;
		this.endBookId = endBookId;
		this.endChapter = endChapter;
		this.endVerse = endVerse;
	}
}
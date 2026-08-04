package com.bnote.domain.application.entity;

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
@Table(name = "application_bible_refs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationBibleRef extends BaseEntity {

	@Column(name = "application_id", nullable = false)
	private Long applicationId;

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

	@Column(name = "is_chapter_only", nullable = false)
	private boolean isChapterOnly;

	@Builder
	private ApplicationBibleRef(
		Long applicationId, Integer startBookId, Integer startChapter, Integer startVerse,
		Integer endBookId, Integer endChapter, Integer endVerse, boolean isChapterOnly
	) {
		this.applicationId = applicationId;
		this.startBookId = startBookId;
		this.startChapter = startChapter;
		this.startVerse = startVerse;
		this.endBookId = endBookId;
		this.endChapter = endChapter;
		this.endVerse = endVerse;
		this.isChapterOnly = isChapterOnly;
	}
}
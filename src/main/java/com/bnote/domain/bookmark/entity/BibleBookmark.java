package com.bnote.domain.bookmark.entity;

import com.bnote.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(
	name = "bible_bookmarks",
	uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "book_id", "chapter", "verse"})
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BibleBookmark extends BaseEntity {

	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@Column(name = "book_id", nullable = false)
	private Integer bookId;

	@Column(nullable = false)
	private Integer chapter;

	@Column(nullable = false)
	private Integer verse;

	@Column(name = "is_bookmarked", nullable = false)
	private boolean isBookmarked;

	@Column(name = "is_highlighted", nullable = false)
	private boolean isHighlighted;

	@Builder
	private BibleBookmark(Long memberId, Integer bookId, Integer chapter, Integer verse) {
		this.memberId = memberId;
		this.bookId = bookId;
		this.chapter = chapter;
		this.verse = verse;
	}

	/** 전달된 값만 반영한다(null이면 기존 값 유지). 둘 다 false가 되면 이 로우는 의미가 없어진다. */
	public void toggle(Boolean isBookmarked, Boolean isHighlighted) {
		if (isBookmarked != null) {
			this.isBookmarked = isBookmarked;
		}
		if (isHighlighted != null) {
			this.isHighlighted = isHighlighted;
		}
	}

	public boolean isEmpty() {
		return !isBookmarked && !isHighlighted;
	}
}
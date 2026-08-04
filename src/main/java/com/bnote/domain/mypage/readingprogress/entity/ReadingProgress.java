package com.bnote.domain.mypage.readingprogress.entity;

import com.bnote.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Table(
	name = "reading_progress",
	uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "book_id", "chapter"})
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadingProgress extends BaseEntity {

	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@Column(name = "book_id", nullable = false)
	private Integer bookId;

	@Column(nullable = false)
	private Integer chapter;

	@Column(name = "read_date", nullable = false)
	private LocalDate readDate;

	@Builder
	private ReadingProgress(Long memberId, Integer bookId, Integer chapter, LocalDate readDate) {
		this.memberId = memberId;
		this.bookId = bookId;
		this.chapter = chapter;
		this.readDate = readDate;
	}
}
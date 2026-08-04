package com.bnote.domain.mypage.recentactivity.entity;

import com.bnote.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(
	name = "recent_chapter_views",
	uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "book_id", "chapter"})
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecentChapterView extends BaseEntity {

	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@Column(name = "book_id", nullable = false)
	private Integer bookId;

	@Column(nullable = false)
	private Integer chapter;

	@Column(name = "viewed_at", nullable = false)
	private LocalDateTime viewedAt;

	@Builder
	private RecentChapterView(Long memberId, Integer bookId, Integer chapter) {
		this.memberId = memberId;
		this.bookId = bookId;
		this.chapter = chapter;
		this.viewedAt = LocalDateTime.now();
	}

	public void touch() {
		this.viewedAt = LocalDateTime.now();
	}
}
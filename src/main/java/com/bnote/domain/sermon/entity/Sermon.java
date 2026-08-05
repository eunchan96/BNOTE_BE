package com.bnote.domain.sermon.entity;

import com.bnote.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "sermons")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sermon extends BaseEntity {

	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@Column(nullable = false)
	private String title;

	@Column(name = "preacher_id")
	private Long preacherId;

	@Column(name = "sermon_date", nullable = false)
	private LocalDate sermonDate;

	@Column(name = "category_id")
	private Long categoryId;

	@Column(columnDefinition = "text", nullable = false)
	private String memo;

	private String link;

	@Builder
	private Sermon(
		Long memberId, String title, Long preacherId, LocalDate sermonDate,
		Long categoryId, String memo, String link
	) {
		this.memberId = memberId;
		this.title = title;
		this.preacherId = preacherId;
		this.sermonDate = sermonDate;
		this.categoryId = categoryId;
		this.memo = memo;
		this.link = link;
	}

	public void update(
		String title, Long preacherId, LocalDate sermonDate, Long categoryId, String memo, String link
	) {
		this.title = title;
		this.preacherId = preacherId;
		this.sermonDate = sermonDate;
		this.categoryId = categoryId;
		this.memo = memo;
		this.link = link;
	}
}
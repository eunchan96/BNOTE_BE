package com.bnote.domain.application.entity;

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
@Table(name = "applications")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Application extends BaseEntity {

	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@Column(nullable = false)
	private String title;

	@Column(name = "category_id")
	private Long categoryId;

	@Column(name = "application_date", nullable = false)
	private LocalDate applicationDate;

	@Column(name = "meditation_memo", columnDefinition = "text")
	private String meditationMemo;

	@Column(name = "prayer_memo", columnDefinition = "text")
	private String prayerMemo;

	@Column(name = "obedience_memo", columnDefinition = "text")
	private String obedienceMemo;

	@Builder
	private Application(
		Long memberId, String title, Long categoryId, LocalDate applicationDate,
		String meditationMemo, String prayerMemo, String obedienceMemo
	) {
		this.memberId = memberId;
		this.title = title;
		this.categoryId = categoryId;
		this.applicationDate = applicationDate;
		this.meditationMemo = meditationMemo;
		this.prayerMemo = prayerMemo;
		this.obedienceMemo = obedienceMemo;
	}

	public void update(
		String title, Long categoryId, LocalDate applicationDate,
		String meditationMemo, String prayerMemo, String obedienceMemo
	) {
		this.title = title;
		this.categoryId = categoryId;
		this.applicationDate = applicationDate;
		this.meditationMemo = meditationMemo;
		this.prayerMemo = prayerMemo;
		this.obedienceMemo = obedienceMemo;
	}
}
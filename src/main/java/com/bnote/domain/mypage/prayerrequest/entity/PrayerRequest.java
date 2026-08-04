package com.bnote.domain.mypage.prayerrequest.entity;

import com.bnote.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "prayer_requests")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PrayerRequest extends BaseEntity {

	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@Lob
	@Column(nullable = false)
	private String content;

	@Column(name = "is_answered", nullable = false)
	private boolean isAnswered;

	@Column(name = "answered_date")
	private LocalDate answeredDate;

	@Builder
	private PrayerRequest(Long memberId, String content) {
		this.memberId = memberId;
		this.content = content;
	}

	public void markAnswered(boolean answered) {
		this.isAnswered = answered;
		this.answeredDate = answered ? LocalDate.now() : null;
	}

	public void updateContent(String content) {
		this.content = content;
	}
}
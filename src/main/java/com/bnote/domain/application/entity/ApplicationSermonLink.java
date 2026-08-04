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
@Table(name = "application_sermon_links")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationSermonLink extends BaseEntity {

	@Column(name = "application_id", nullable = false)
	private Long applicationId;

	@Column(name = "sermon_id", nullable = false)
	private Long sermonId;

	@Builder
	private ApplicationSermonLink(Long applicationId, Long sermonId) {
		this.applicationId = applicationId;
		this.sermonId = sermonId;
	}
}
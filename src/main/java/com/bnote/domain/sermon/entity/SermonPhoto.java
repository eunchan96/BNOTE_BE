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
@Table(name = "sermon_photos")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SermonPhoto extends BaseEntity {

	@Column(name = "sermon_id", nullable = false)
	private Long sermonId;

	@Column(name = "image_url", nullable = false)
	private String imageUrl;

	@Column(name = "sort_order", nullable = false)
	private int sortOrder;

	@Builder
	private SermonPhoto(Long sermonId, String imageUrl, int sortOrder) {
		this.sermonId = sermonId;
		this.imageUrl = imageUrl;
		this.sortOrder = sortOrder;
	}
}
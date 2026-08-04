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
@Table(name = "sermon_categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SermonCategory extends BaseEntity {

	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@Column(nullable = false)
	private String name;

	@Column(name = "color_hex", nullable = false, length = 10)
	private String colorHex;

	@Column(name = "is_default", nullable = false)
	private boolean isDefault;

	@Column(name = "sort_order", nullable = false)
	private int sortOrder;

	@Builder
	private SermonCategory(Long memberId, String name, String colorHex, boolean isDefault, int sortOrder) {
		this.memberId = memberId;
		this.name = name;
		this.colorHex = colorHex;
		this.isDefault = isDefault;
		this.sortOrder = sortOrder;
	}

	public void update(String name, String colorHex, int sortOrder) {
		this.name = name;
		this.colorHex = colorHex;
		this.sortOrder = sortOrder;
	}
}
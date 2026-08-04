package com.bnote.domain.bible.hymn.entity;

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
@Table(name = "hymn_categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HymnCategory extends BaseEntity {

	@Column(nullable = false)
	private String name;

	/** null이면 대분류, 값이 있으면 그 대분류에 속한 소분류 */
	@Column(name = "parent_id")
	private Long parentId;

	@Column(name = "sort_order", nullable = false)
	private int sortOrder;

	@Builder
	private HymnCategory(String name, Long parentId, int sortOrder) {
		this.name = name;
		this.parentId = parentId;
		this.sortOrder = sortOrder;
	}
}
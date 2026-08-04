package com.bnote.domain.mypage.memorization.entity;

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
@Table(name = "memorization_groups")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemorizationGroup extends BaseEntity {

	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@Column(nullable = false)
	private String name;

	@Column(name = "sort_order", nullable = false)
	private int sortOrder;

	@Builder
	private MemorizationGroup(Long memberId, String name, int sortOrder) {
		this.memberId = memberId;
		this.name = name;
		this.sortOrder = sortOrder;
	}

	public void rename(String name) {
		this.name = name;
	}
}
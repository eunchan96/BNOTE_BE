package com.bnote.domain.knowledge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "genealogy_entries")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenealogyEntry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "chart_id", nullable = false)
	private String chartId;

	@Column(nullable = false)
	private String name;

	private String relation;

	private String note;

	@Column(name = "sort_order", nullable = false)
	private int sortOrder;

	@Builder
	private GenealogyEntry(String chartId, String name, String relation, String note, int sortOrder) {
		this.chartId = chartId;
		this.name = name;
		this.relation = relation;
		this.note = note;
		this.sortOrder = sortOrder;
	}
}
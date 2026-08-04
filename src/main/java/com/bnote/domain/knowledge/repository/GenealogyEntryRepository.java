package com.bnote.domain.knowledge.repository;

import com.bnote.domain.knowledge.entity.GenealogyEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenealogyEntryRepository extends JpaRepository<GenealogyEntry, Long> {

	List<GenealogyEntry> findByChartIdOrderBySortOrderAsc(String chartId);
}
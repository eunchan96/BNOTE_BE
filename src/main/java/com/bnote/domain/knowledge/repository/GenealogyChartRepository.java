package com.bnote.domain.knowledge.repository;

import com.bnote.domain.knowledge.entity.GenealogyChart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenealogyChartRepository extends JpaRepository<GenealogyChart, String> {
}
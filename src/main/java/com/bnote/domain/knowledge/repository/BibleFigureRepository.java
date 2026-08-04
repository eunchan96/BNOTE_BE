package com.bnote.domain.knowledge.repository;

import com.bnote.domain.knowledge.entity.BibleFigure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BibleFigureRepository extends JpaRepository<BibleFigure, String> {

	List<BibleFigure> findByNameContainingOrOtherNamesContaining(String name, String otherNames);
}
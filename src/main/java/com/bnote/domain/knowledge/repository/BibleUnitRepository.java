package com.bnote.domain.knowledge.repository;

import com.bnote.domain.knowledge.entity.BibleUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BibleUnitRepository extends JpaRepository<BibleUnit, String> {
}
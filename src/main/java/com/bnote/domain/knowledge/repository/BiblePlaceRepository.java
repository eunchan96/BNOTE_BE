package com.bnote.domain.knowledge.repository;

import com.bnote.domain.knowledge.entity.BiblePlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BiblePlaceRepository extends JpaRepository<BiblePlace, String> {

	List<BiblePlace> findByNameContainingOrOtherNamesContaining(String name, String otherNames);
}
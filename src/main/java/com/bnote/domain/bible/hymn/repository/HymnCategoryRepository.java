package com.bnote.domain.bible.hymn.repository;

import com.bnote.domain.bible.hymn.entity.HymnCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HymnCategoryRepository extends JpaRepository<HymnCategory, Long> {

	List<HymnCategory> findByParentIdIsNullOrderBySortOrderAsc();

	List<HymnCategory> findByParentIdOrderBySortOrderAsc(Long parentId);
}
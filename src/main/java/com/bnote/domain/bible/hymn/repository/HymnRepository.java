package com.bnote.domain.bible.hymn.repository;

import com.bnote.domain.bible.hymn.entity.Hymn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HymnRepository extends JpaRepository<Hymn, Integer> {

	List<Hymn> findAllByOrderByNumberAsc();

	List<Hymn> findByCategoryIdOrderByNumberAsc(Long categoryId);

	List<Hymn> findByTitleContainingOrderByNumberAsc(String keyword);
}
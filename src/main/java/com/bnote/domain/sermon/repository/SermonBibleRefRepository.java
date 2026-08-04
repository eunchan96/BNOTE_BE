package com.bnote.domain.sermon.repository;

import com.bnote.domain.sermon.entity.SermonBibleRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SermonBibleRefRepository extends JpaRepository<SermonBibleRef, Long> {

	List<SermonBibleRef> findBySermonId(Long sermonId);

	void deleteBySermonId(Long sermonId);
}
package com.bnote.domain.sermon.repository;

import com.bnote.domain.sermon.entity.SermonPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SermonPhotoRepository extends JpaRepository<SermonPhoto, Long> {

	List<SermonPhoto> findBySermonIdOrderBySortOrderAsc(Long sermonId);

	void deleteBySermonId(Long sermonId);
}
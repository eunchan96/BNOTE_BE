package com.bnote.domain.mypage.gratitude.repository;

import com.bnote.domain.mypage.gratitude.entity.GratitudeEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GratitudeEntryRepository extends JpaRepository<GratitudeEntry, Long> {

	List<GratitudeEntry> findByNoteIdOrderBySortOrderAsc(Long noteId);

	void deleteByNoteId(Long noteId);
}
package com.bnote.domain.partialhighlight.repository;

import com.bnote.domain.partialhighlight.entity.PartialHighlight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartialHighlightRepository extends JpaRepository<PartialHighlight, Long> {

	List<PartialHighlight> findByMemberIdAndBookIdAndChapterOrderByVerseAsc(
			Long memberId, Integer bookId, Integer chapter
	);

	List<PartialHighlight> findByMemberIdAndBookIdAndChapterAndVerseOrderByStartOffsetAsc(
			Long memberId, Integer bookId, Integer chapter, Integer verse
	);
}
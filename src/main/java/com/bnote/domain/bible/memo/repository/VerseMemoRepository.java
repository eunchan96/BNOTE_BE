package com.bnote.domain.bible.memo.repository;

import com.bnote.domain.bible.memo.entity.VerseMemo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerseMemoRepository extends JpaRepository<VerseMemo, Long> {

	List<VerseMemo> findByMemberIdAndBookIdAndChapterOrderByVerseAsc(Long memberId, Integer bookId, Integer chapter);
}
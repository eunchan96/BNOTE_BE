package com.bnote.domain.bible.memo.repository;

import com.bnote.domain.bible.memo.entity.WordMemo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordMemoRepository extends JpaRepository<WordMemo, Long> {

	List<WordMemo> findByMemberIdAndBookIdAndChapterOrderByVerseAsc(Long memberId, Integer bookId, Integer chapter);
}
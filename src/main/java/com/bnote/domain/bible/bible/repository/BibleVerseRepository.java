package com.bnote.domain.bible.bible.repository;

import com.bnote.domain.bible.bible.entity.BibleVerse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BibleVerseRepository extends JpaRepository<BibleVerse, Long> {

	List<BibleVerse> findByTranslationAndBookIdAndChapterOrderByVerseAsc(
		String translation, Integer bookId, Integer chapter
	);

	List<BibleVerse> findByTranslationAndTextContainingOrderByBookIdAscChapterAscVerseAsc(
		String translation, String keyword
	);
}
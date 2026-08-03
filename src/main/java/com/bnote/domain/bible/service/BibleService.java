package com.bnote.domain.bible.service;

import com.bnote.domain.bible.dto.response.BibleChapterResponse;
import com.bnote.domain.bible.dto.response.BibleSearchResponse;
import com.bnote.domain.bible.entity.BibleBooks;
import com.bnote.domain.bible.entity.BibleVerse;
import com.bnote.domain.bible.entity.Translation;
import com.bnote.domain.bible.exception.BibleException;
import com.bnote.domain.bible.repository.BibleVerseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BibleService {

	private final BibleVerseRepository bibleVerseRepository;

	public BibleChapterResponse getChapter(Integer bookId, Integer chapter, String translationCode) {
		validateBookId(bookId);
		String translation = resolveTranslation(translationCode);

		List<BibleVerse> verses = bibleVerseRepository
				.findByTranslationAndBookIdAndChapterOrderByVerseAsc(translation, bookId, chapter);

		if (verses.isEmpty()) {
			throw BibleException.chapterNotFound();
		}

		return BibleChapterResponse.of(bookId, chapter, verses);
	}

	public BibleSearchResponse search(String keyword, String translationCode) {
		if (keyword == null || keyword.trim().length() < 2) {
			throw BibleException.keywordTooShort();
		}
		String translation = resolveTranslation(translationCode);

		List<BibleVerse> verses = bibleVerseRepository
				.findByTranslationAndTextContainingOrderByBookIdAscChapterAscVerseAsc(translation, keyword.trim());

		return BibleSearchResponse.of(verses);
	}

	private void validateBookId(Integer bookId) {
		if (bookId == null || bookId < 1 || bookId > BibleBooks.totalBookCount()) {
			throw BibleException.invalidBookId();
		}
	}

	private String resolveTranslation(String translationCode) {
		String code = (translationCode == null || translationCode.isBlank())
				? Translation.NKRV.getCode()
				: translationCode;

		for (Translation translation : Translation.values()) {
			if (translation.getCode().equalsIgnoreCase(code)) {
				return translation.getCode();
			}
		}
		throw BibleException.unsupportedTranslation(code);
	}
}
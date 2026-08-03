package com.bnote.domain.bible.service;

import com.bnote.domain.bible.dto.response.BibleChapterResponse;
import com.bnote.domain.bible.dto.response.BibleSearchResponse;
import com.bnote.domain.bible.entity.BibleBooks;
import com.bnote.domain.bible.entity.BibleVerse;
import com.bnote.domain.bible.entity.Translation;
import com.bnote.domain.bible.repository.BibleVerseRepository;
import com.bnote.global.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BibleService {

	private final BibleVerseRepository bibleVerseRepository;

	public BibleService(BibleVerseRepository bibleVerseRepository) {
		this.bibleVerseRepository = bibleVerseRepository;
	}

	public BibleChapterResponse getChapter(Integer bookId, Integer chapter, String translationCode) {
		validateBookId(bookId);
		String translation = resolveTranslation(translationCode);

		List<BibleVerse> verses = bibleVerseRepository
			.findByTranslationAndBookIdAndChapterOrderByVerseAsc(translation, bookId, chapter);

		if (verses.isEmpty()) {
			throw new ServiceException("404-1", "해당 본문을 찾을 수 없습니다.");
		}

		return BibleChapterResponse.of(bookId, chapter, verses);
	}

	public BibleSearchResponse search(String keyword, String translationCode) {
		if (keyword == null || keyword.trim().length() < 2) {
			throw new ServiceException("400-1", "검색어는 2글자 이상 입력해주세요.");
		}
		String translation = resolveTranslation(translationCode);

		List<BibleVerse> verses = bibleVerseRepository
			.findByTranslationAndTextContainingOrderByBookIdAscChapterAscVerseAsc(translation, keyword.trim());

		return BibleSearchResponse.of(verses);
	}

	private void validateBookId(Integer bookId) {
		if (bookId == null || bookId < 1 || bookId > BibleBooks.totalBookCount()) {
			throw new ServiceException("400-1", "올바르지 않은 성경 권입니다.");
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
		throw new ServiceException("400-2", "지원하지 않는 번역본입니다: " + code);
	}
}
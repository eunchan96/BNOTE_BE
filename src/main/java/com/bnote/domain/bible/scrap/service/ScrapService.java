package com.bnote.domain.bible.scrap.service;

import com.bnote.domain.bible.bible.entity.BibleVerse;
import com.bnote.domain.bible.bible.entity.Translation;
import com.bnote.domain.bible.bible.repository.BibleVerseRepository;
import com.bnote.domain.bible.scrap.dto.request.ScrapRequest;
import com.bnote.domain.bible.scrap.dto.response.ScrapResponse;
import com.bnote.domain.bible.scrap.entity.Scrap;
import com.bnote.domain.bible.scrap.entity.ScrapGroup;
import com.bnote.domain.bible.scrap.exception.ScrapException;
import com.bnote.domain.bible.scrap.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScrapService {

	private final ScrapRepository scrapRepository;
	private final ScrapGroupService scrapGroupService;
	private final BibleVerseRepository bibleVerseRepository;

	public List<ScrapResponse> getByGroup(Long memberId, Long groupId) {
		scrapGroupService.findOwnedGroup(memberId, groupId);
		return scrapRepository.findByGroupIdOrderByCreateDateDesc(groupId)
			.stream()
			.map(ScrapResponse::from)
			.toList();
	}

	@Transactional
	public ScrapResponse create(Long memberId, ScrapRequest request) {
		ScrapGroup group = scrapGroupService.findOwnedGroup(memberId, request.groupId());

		String translation = (request.translation() == null || request.translation().isBlank())
			? Translation.NKRV.getCode()
			: request.translation();

		String verseText = buildVerseText(translation, request.bookId(), request.chapter(), request.startVerse(), request.endVerse());

		Scrap scrap = Scrap.builder()
			.groupId(group.getId())
			.bookId(request.bookId())
			.chapter(request.chapter())
			.startVerse(request.startVerse())
			.endVerse(request.endVerse())
			.verseText(verseText)
			.build();

		return ScrapResponse.from(scrapRepository.save(scrap));
	}

	@Transactional
	public void delete(Long memberId, Long id) {
		Scrap scrap = scrapRepository.findById(id)
			.orElseThrow(ScrapException::scrapNotFound);

		scrapGroupService.findOwnedGroup(memberId, scrap.getGroupId());

		scrapRepository.delete(scrap);
	}

	private String buildVerseText(String translation, Integer bookId, Integer chapter, Integer startVerse, Integer endVerse) {
		List<BibleVerse> verses = bibleVerseRepository
			.findByTranslationAndBookIdAndChapterAndVerseBetweenOrderByVerseAsc(translation, bookId, chapter, startVerse, endVerse);

		return verses.stream().map(BibleVerse::getText).reduce((a, b) -> a + " " + b).orElse("");
	}
}
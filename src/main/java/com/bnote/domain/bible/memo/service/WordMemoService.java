package com.bnote.domain.bible.memo.service;

import com.bnote.domain.bible.bible.entity.BibleBooks;
import com.bnote.domain.bible.bible.exception.BibleException;
import com.bnote.domain.bible.memo.dto.request.WordMemoRequest;
import com.bnote.domain.bible.memo.dto.request.WordMemoUpdateRequest;
import com.bnote.domain.bible.memo.dto.response.WordMemoResponse;
import com.bnote.domain.bible.memo.entity.WordMemo;
import com.bnote.domain.bible.memo.exception.MemoException;
import com.bnote.domain.bible.memo.repository.WordMemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WordMemoService {

	private final WordMemoRepository wordMemoRepository;

	public List<WordMemoResponse> getByChapter(Long memberId, Integer bookId, Integer chapter) {
		validateBookId(bookId);
		return wordMemoRepository.findByMemberIdAndBookIdAndChapterOrderByVerseAsc(memberId, bookId, chapter)
			.stream()
			.map(WordMemoResponse::from)
			.toList();
	}

	@Transactional
	public WordMemoResponse create(Long memberId, WordMemoRequest request) {
		validateBookId(request.bookId());

		WordMemo memo = WordMemo.builder()
			.memberId(memberId)
			.translation(request.translation())
			.bookId(request.bookId())
			.chapter(request.chapter())
			.verse(request.verse())
			.startOffset(request.startOffset())
			.endOffset(request.endOffset())
			.text(request.text())
			.build();

		return WordMemoResponse.from(wordMemoRepository.save(memo));
	}

	@Transactional
	public WordMemoResponse update(Long memberId, Long id, WordMemoUpdateRequest request) {
		WordMemo memo = findOwnedMemo(memberId, id);
		memo.updateText(request.text());
		return WordMemoResponse.from(memo);
	}

	@Transactional
	public void delete(Long memberId, Long id) {
		WordMemo memo = findOwnedMemo(memberId, id);
		wordMemoRepository.delete(memo);
	}

	private WordMemo findOwnedMemo(Long memberId, Long id) {
		WordMemo memo = wordMemoRepository.findById(id)
			.orElseThrow(MemoException::wordMemoNotFound);

		if (!memo.getMemberId().equals(memberId)) {
			throw MemoException.accessDenied();
		}
		return memo;
	}

	private void validateBookId(Integer bookId) {
		if (bookId == null || bookId < 1 || bookId > BibleBooks.totalBookCount()) {
			throw BibleException.invalidBookId();
		}
	}
}
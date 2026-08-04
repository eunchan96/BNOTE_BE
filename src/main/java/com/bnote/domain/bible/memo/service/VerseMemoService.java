package com.bnote.domain.bible.memo.service;

import com.bnote.domain.bible.bible.entity.BibleBooks;
import com.bnote.domain.bible.bible.exception.BibleException;
import com.bnote.domain.bible.memo.dto.request.VerseMemoRequest;
import com.bnote.domain.bible.memo.dto.request.VerseMemoUpdateRequest;
import com.bnote.domain.bible.memo.dto.response.VerseMemoResponse;
import com.bnote.domain.bible.memo.entity.VerseMemo;
import com.bnote.domain.bible.memo.exception.MemoException;
import com.bnote.domain.bible.memo.repository.VerseMemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VerseMemoService {

	private final VerseMemoRepository verseMemoRepository;

	public List<VerseMemoResponse> getByChapter(Long memberId, Integer bookId, Integer chapter) {
		validateBookId(bookId);
		return verseMemoRepository.findByMemberIdAndBookIdAndChapterOrderByVerseAsc(memberId, bookId, chapter)
			.stream()
			.map(VerseMemoResponse::from)
			.toList();
	}

	@Transactional
	public VerseMemoResponse create(Long memberId, VerseMemoRequest request) {
		validateBookId(request.bookId());

		VerseMemo memo = VerseMemo.builder()
			.memberId(memberId)
			.bookId(request.bookId())
			.chapter(request.chapter())
			.verse(request.verse())
			.text(request.text())
			.build();

		return VerseMemoResponse.from(verseMemoRepository.save(memo));
	}

	@Transactional
	public VerseMemoResponse update(Long memberId, Long id, VerseMemoUpdateRequest request) {
		VerseMemo memo = findOwnedMemo(memberId, id);
		memo.updateText(request.text());
		return VerseMemoResponse.from(memo);
	}

	@Transactional
	public void delete(Long memberId, Long id) {
		VerseMemo memo = findOwnedMemo(memberId, id);
		verseMemoRepository.delete(memo);
	}

	private VerseMemo findOwnedMemo(Long memberId, Long id) {
		VerseMemo memo = verseMemoRepository.findById(id)
			.orElseThrow(MemoException::verseMemoNotFound);

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
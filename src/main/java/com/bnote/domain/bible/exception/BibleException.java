package com.bnote.domain.bible.exception;

import com.bnote.global.exception.ServiceException;
import com.bnote.global.response.RsStatus;

public class BibleException extends ServiceException {

	public BibleException(String resultCode, String msg) {
		super(resultCode, msg);
	}

	// ======================================= not found ======================================= //
	private static BibleException notFound(int detailCode, String msg) {
		return new BibleException(RsStatus.NOT_FOUND.getResultCode() + "-" + detailCode, msg);
	}

	// 404-1
	public static BibleException chapterNotFound() {
		return notFound(1, "해당 본문을 찾을 수 없습니다.");
	}

	// ======================================= bad request ======================================= //
	private static BibleException badRequest(int detailCode, String msg) {
		return new BibleException(RsStatus.BAD_REQUEST.getResultCode() + "-" + detailCode, msg);
	}

	// 400-1
	public static BibleException invalidBookId() {
		return badRequest(1, "올바르지 않은 성경 권입니다.");
	}

	// 400-2
	public static BibleException unsupportedTranslation(String code) {
		return badRequest(2, "지원하지 않는 번역본입니다: " + code);
	}

	// 400-3
	public static BibleException keywordTooShort() {
		return badRequest(3, "검색어는 2글자 이상 입력해주세요.");
	}
}
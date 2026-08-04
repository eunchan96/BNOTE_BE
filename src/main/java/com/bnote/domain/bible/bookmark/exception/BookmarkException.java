package com.bnote.domain.bible.bookmark.exception;

import com.bnote.global.exception.ServiceException;
import com.bnote.global.response.RsStatus;

public class BookmarkException extends ServiceException {

	public BookmarkException(String resultCode, String msg) {
		super(resultCode, msg);
	}

	private static BookmarkException notFound(int detailCode, String msg) {
		return new BookmarkException(RsStatus.NOT_FOUND.getResultCode() + "-" + detailCode, msg);
	}

	// 404-1
	public static BookmarkException notFound() {
		return notFound(1, "존재하지 않는 북마크입니다.");
	}
}
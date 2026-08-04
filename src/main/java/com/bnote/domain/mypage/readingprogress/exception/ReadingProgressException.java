package com.bnote.domain.mypage.readingprogress.exception;

import com.bnote.global.exception.ServiceException;
import com.bnote.global.response.RsStatus;

public class ReadingProgressException extends ServiceException {

	public ReadingProgressException(String resultCode, String msg) {
		super(resultCode, msg);
	}

	// 404-1
	public static ReadingProgressException notFound() {
		return new ReadingProgressException(RsStatus.NOT_FOUND.getResultCode() + "-1", "체크된 기록이 없습니다.");
	}

	// 403-1
	public static ReadingProgressException accessDenied() {
		return new ReadingProgressException(RsStatus.FORBIDDEN.getResultCode() + "-1", "본인의 기록만 취소할 수 있습니다.");
	}
}
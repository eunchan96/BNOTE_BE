package com.bnote.domain.mypage.verseofyear.exception;

import com.bnote.global.exception.ServiceException;
import com.bnote.global.response.RsStatus;

public class VerseOfYearException extends ServiceException {

	public VerseOfYearException(String resultCode, String msg) {
		super(resultCode, msg);
	}

	// 404-1
	public static VerseOfYearException notFound() {
		return new VerseOfYearException(RsStatus.NOT_FOUND.getResultCode() + "-1", "등록된 올해의 말씀이 없습니다.");
	}
}
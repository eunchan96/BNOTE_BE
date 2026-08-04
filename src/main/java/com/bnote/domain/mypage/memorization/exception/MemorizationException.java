package com.bnote.domain.mypage.memorization.exception;

import com.bnote.global.exception.ServiceException;
import com.bnote.global.response.RsStatus;

public class MemorizationException extends ServiceException {

	public MemorizationException(String resultCode, String msg) {
		super(resultCode, msg);
	}

	private static MemorizationException notFound(int detailCode, String msg) {
		return new MemorizationException(RsStatus.NOT_FOUND.getResultCode() + "-" + detailCode, msg);
	}

	// 404-1
	public static MemorizationException groupNotFound() {
		return notFound(1, "존재하지 않는 암송 그룹입니다.");
	}

	// 404-2
	public static MemorizationException verseNotFound() {
		return notFound(2, "존재하지 않는 암송 구절입니다.");
	}

	// 403-1
	public static MemorizationException accessDenied() {
		return new MemorizationException(RsStatus.FORBIDDEN.getResultCode() + "-1", "본인의 암송 자료만 접근할 수 있습니다.");
	}
}
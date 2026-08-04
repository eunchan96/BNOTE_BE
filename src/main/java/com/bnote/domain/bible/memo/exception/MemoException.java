package com.bnote.domain.bible.memo.exception;

import com.bnote.global.exception.ServiceException;
import com.bnote.global.response.RsStatus;

public class MemoException extends ServiceException {

	public MemoException(String resultCode, String msg) {
		super(resultCode, msg);
	}

	private static MemoException notFound(int detailCode, String msg) {
		return new MemoException(RsStatus.NOT_FOUND.getResultCode() + "-" + detailCode, msg);
	}

	// 404-1
	public static MemoException verseMemoNotFound() {
		return notFound(1, "존재하지 않는 구절 메모입니다.");
	}

	// 404-2
	public static MemoException wordMemoNotFound() {
		return notFound(2, "존재하지 않는 단어 메모입니다.");
	}

	private static MemoException forbidden(int detailCode, String msg) {
		return new MemoException(RsStatus.FORBIDDEN.getResultCode() + "-" + detailCode, msg);
	}

	// 403-1
	public static MemoException accessDenied() {
		return forbidden(1, "본인의 메모만 수정/삭제할 수 있습니다.");
	}
}
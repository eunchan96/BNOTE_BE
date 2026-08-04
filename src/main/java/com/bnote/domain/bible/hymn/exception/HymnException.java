package com.bnote.domain.bible.hymn.exception;

import com.bnote.global.exception.ServiceException;
import com.bnote.global.response.RsStatus;

public class HymnException extends ServiceException {

	public HymnException(String resultCode, String msg) {
		super(resultCode, msg);
	}

	private static HymnException notFound(int detailCode, String msg) {
		return new HymnException(RsStatus.NOT_FOUND.getResultCode() + "-" + detailCode, msg);
	}

	// 404-1
	public static HymnException notFound() {
		return notFound(1, "존재하지 않는 찬송가입니다.");
	}
}
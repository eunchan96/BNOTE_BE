package com.bnote.domain.bible.scrap.exception;

import com.bnote.global.exception.ServiceException;
import com.bnote.global.response.RsStatus;

public class ScrapException extends ServiceException {

	public ScrapException(String resultCode, String msg) {
		super(resultCode, msg);
	}

	private static ScrapException notFound(int detailCode, String msg) {
		return new ScrapException(RsStatus.NOT_FOUND.getResultCode() + "-" + detailCode, msg);
	}

	// 404-1
	public static ScrapException groupNotFound() {
		return notFound(1, "존재하지 않는 스크랩 그룹입니다.");
	}

	// 404-2
	public static ScrapException scrapNotFound() {
		return notFound(2, "존재하지 않는 스크랩입니다.");
	}

	private static ScrapException forbidden(int detailCode, String msg) {
		return new ScrapException(RsStatus.FORBIDDEN.getResultCode() + "-" + detailCode, msg);
	}

	// 403-1
	public static ScrapException accessDenied() {
		return forbidden(1, "본인의 스크랩만 접근할 수 있습니다.");
	}
}
package com.bnote.domain.partialhighlight.exception;

import com.bnote.global.exception.ServiceException;
import com.bnote.global.response.RsStatus;

public class PartialHighlightException extends ServiceException {

	public PartialHighlightException(String resultCode, String msg) {
		super(resultCode, msg);
	}

	private static PartialHighlightException notFound(int detailCode, String msg) {
		return new PartialHighlightException(RsStatus.NOT_FOUND.getResultCode() + "-" + detailCode, msg);
	}

	// 404-1
	public static PartialHighlightException notFound() {
		return notFound(1, "존재하지 않는 부분 하이라이트입니다.");
	}

	private static PartialHighlightException forbidden(int detailCode, String msg) {
		return new PartialHighlightException(RsStatus.FORBIDDEN.getResultCode() + "-" + detailCode, msg);
	}

	// 403-1
	public static PartialHighlightException accessDenied() {
		return forbidden(1, "본인의 하이라이트만 삭제할 수 있습니다.");
	}
}
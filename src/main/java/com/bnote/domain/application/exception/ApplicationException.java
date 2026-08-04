package com.bnote.domain.application.exception;

import com.bnote.global.exception.ServiceException;
import com.bnote.global.response.RsStatus;

public class ApplicationException extends ServiceException {

	public ApplicationException(String resultCode, String msg) {
		super(resultCode, msg);
	}

	private static ApplicationException notFound(int detailCode, String msg) {
		return new ApplicationException(RsStatus.NOT_FOUND.getResultCode() + "-" + detailCode, msg);
	}

	// 404-1
	public static ApplicationException applicationNotFound() {
		return notFound(1, "존재하지 않는 적용입니다.");
	}

	// 404-2
	public static ApplicationException categoryNotFound() {
		return notFound(2, "존재하지 않는 적용 카테고리입니다.");
	}

	// 404-3
	public static ApplicationException sermonNotFound() {
		return notFound(3, "존재하지 않는 설교노트입니다.");
	}

	private static ApplicationException forbidden(int detailCode, String msg) {
		return new ApplicationException(RsStatus.FORBIDDEN.getResultCode() + "-" + detailCode, msg);
	}

	// 403-1
	public static ApplicationException accessDenied() {
		return forbidden(1, "본인의 적용만 접근할 수 있습니다.");
	}

	// 403-2
	public static ApplicationException defaultCategoryNotDeletable() {
		return forbidden(2, "기본 제공되는 카테고리는 삭제할 수 없습니다.");
	}
}
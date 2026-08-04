package com.bnote.domain.sermon.exception;

import com.bnote.global.exception.ServiceException;
import com.bnote.global.response.RsStatus;

public class SermonException extends ServiceException {

	public SermonException(String resultCode, String msg) {
		super(resultCode, msg);
	}

	private static SermonException notFound(int detailCode, String msg) {
		return new SermonException(RsStatus.NOT_FOUND.getResultCode() + "-" + detailCode, msg);
	}

	// 404-1
	public static SermonException sermonNotFound() {
		return notFound(1, "존재하지 않는 설교노트입니다.");
	}

	// 404-2
	public static SermonException preacherNotFound() {
		return notFound(2, "존재하지 않는 설교자입니다.");
	}

	// 404-3
	public static SermonException categoryNotFound() {
		return notFound(3, "존재하지 않는 설교 카테고리입니다.");
	}

	private static SermonException forbidden(int detailCode, String msg) {
		return new SermonException(RsStatus.FORBIDDEN.getResultCode() + "-" + detailCode, msg);
	}

	// 403-1
	public static SermonException accessDenied() {
		return forbidden(1, "본인의 설교노트만 접근할 수 있습니다.");
	}

	// 403-2
	public static SermonException defaultCategoryNotDeletable() {
		return forbidden(2, "기본 제공되는 카테고리는 삭제할 수 없습니다.");
	}
}
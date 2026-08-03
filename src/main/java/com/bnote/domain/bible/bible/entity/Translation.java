package com.bnote.domain.bible.bible.entity;

/**
 * 성경 번역본 목록. Android 앱과 동일하게 DB 테이블이 아닌 고정된 목록으로 관리한다.
 */
public enum Translation {
	NKRV("NKRV", "개역개정"),
	KRV("KRV", "개역한글"),
	KSB("KSB", "표준새번역"),
	KLB("KLB", "현대인의성경"),
	EASY("EASY", "쉬운성경"),
	NIV("NIV", "NIV (New International Version)"),
	KJV("KJV", "KJV (King James Version)"),
	ESV("ESV", "ESV (English Standard Version)");

	private final String code;
	private final String displayName;

	Translation(String code, String displayName) {
		this.code = code;
		this.displayName = displayName;
	}

	public String getCode() {
		return code;
	}

	public String getDisplayName() {
		return displayName;
	}
}
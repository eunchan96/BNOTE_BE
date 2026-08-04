package com.bnote.domain.bible.bible.entity;

/**
 * 성경 번역본 목록. Android 앱과 동일하게 DB 테이블이 아닌 고정된 목록으로 관리한다.
 * assetFileName은 src/main/resources/bible-data/ 아래 JSON 파일명과 일치해야 한다.
 */
public enum Translation {
	NKRV("NKRV", "개역개정", "nkrv.json", false),
	KRV("KRV", "개역한글", "krv.json", false),
	KSB("KSB", "표준새번역", "ksb.json", false),
	KLB("KLB", "현대인의성경", "klb.json", false),
	EASY("EASY", "쉬운성경", "easy.json", false),
	NIV("NIV", "NIV (New International Version)", "niv.json", true),
	KJV("KJV", "KJV (King James Version)", "kjv.json", false),
	ESV("ESV", "ESV (English Standard Version)", "esv.json", true);

	private final String code;
	private final String displayName;
	private final String assetFileName;

	/**
	 * true면 JSON이 "책 목록 -> 장 목록 -> 절 목록"으로 중첩된 구조(NIV, ESV).
	 * false면 절 하나하나가 book(정수 ID)·chapter·verse를 직접 갖는 평평한 배열(나머지 전부).
	 */
	private final boolean nestedBookFormat;

	Translation(String code, String displayName, String assetFileName, boolean nestedBookFormat) {
		this.code = code;
		this.displayName = displayName;
		this.assetFileName = assetFileName;
		this.nestedBookFormat = nestedBookFormat;
	}

	public String getCode() {
		return code;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getAssetFileName() {
		return assetFileName;
	}

	public boolean isNestedBookFormat() {
		return nestedBookFormat;
	}
}
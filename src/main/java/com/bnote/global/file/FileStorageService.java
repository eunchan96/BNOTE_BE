package com.bnote.global.file;

import org.springframework.web.multipart.MultipartFile;

/**
 * 파일 저장 방식은 환경에 따라 다르다.
 * - 로컬/개발: LocalFileStorageService (서버 디스크에 저장)
 * - 운영(Render 등 디스크가 휘발성인 PaaS): SupabaseFileStorageService (Supabase Storage에 저장)
 * file.storage.type 설정값으로 어떤 구현체를 쓸지 고른다.
 */
public interface FileStorageService {

	/**
	 * @param subDirectory "sermons/123" 같은 하위 경로 (앞뒤 슬래시 없이)
	 * @return 접근 가능한 URL
	 */
	String store(MultipartFile file, String subDirectory);
}
package com.bnote.global.file;

import com.bnote.global.exception.ServiceException;
import com.bnote.global.response.RsStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

/**
 * Supabase Storage에 업로드한다. Render처럼 배포/재시작마다 디스크가 초기화되는
 * 환경에서는 로컬 저장 대신 이걸 쓴다. file.storage.type=supabase로 활성화.
 *
 * 버킷은 public으로 만들어두는 걸 전제로 한다(공개 URL을 그대로 응답에 내려주는 구조라서).
 */
@Component
@ConditionalOnProperty(name = "file.storage.type", havingValue = "supabase")
public class SupabaseFileStorageService implements FileStorageService {

	private final RestClient restClient = RestClient.create();
	private final String storageUrl;
	private final String bucket;
	private final String serviceRoleKey;

	public SupabaseFileStorageService(
			@Value("${supabase.storage.url}") String storageUrl,
			@Value("${supabase.storage.bucket}") String bucket,
			@Value("${supabase.storage.service-role-key}") String serviceRoleKey
	) {
		this.storageUrl = storageUrl;
		this.bucket = bucket;
		this.serviceRoleKey = serviceRoleKey;
	}

	@Override
	public String store(MultipartFile file, String subDirectory) {
		if (file == null || file.isEmpty()) {
			throw new ServiceException(RsStatus.BAD_REQUEST.getResultCode() + "-1", "빈 파일은 업로드할 수 없습니다.");
		}

		String extension = extractExtension(file.getOriginalFilename());
		String objectPath = subDirectory + "/" + UUID.randomUUID() + extension;

		String targetUrl = storageUrl + "/object/" + bucket + "/" + objectPath;

		try {
			restClient.post()
					.uri(URI.create(targetUrl))
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + serviceRoleKey)
					.contentType(resolveMediaType(file))
					.body(file.getBytes())
					.retrieve()
					.toBodilessEntity();
		} catch (IOException e) {
			throw new ServiceException(RsStatus.INTERNAL_SERVER_ERROR.getResultCode(), "파일 업로드에 실패했습니다.");
		}

		return storageUrl + "/object/public/" + bucket + "/" + objectPath;
	}

	private MediaType resolveMediaType(MultipartFile file) {
		String contentType = file.getContentType();
		return contentType == null ? MediaType.APPLICATION_OCTET_STREAM : MediaType.parseMediaType(contentType);
	}

	private String extractExtension(String originalFilename) {
		if (originalFilename == null || !originalFilename.contains(".")) {
			return "";
		}
		return originalFilename.substring(originalFilename.lastIndexOf('.'));
	}
}
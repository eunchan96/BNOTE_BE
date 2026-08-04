package com.bnote.global.file;

import com.bnote.global.exception.ServiceException;
import com.bnote.global.response.RsStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

/**
 * 서버 로컬 디스크에 저장한다. 개발 환경 기본값.
 * Render 같은 디스크가 휘발성인 PaaS에서는 재배포/재시작 시 파일이 사라지므로 운영에는 쓰지 않는다.
 */
@Component
@ConditionalOnProperty(name = "file.storage.type", havingValue = "local", matchIfMissing = true)
public class LocalFileStorageService implements FileStorageService {

	private final Path uploadRoot;

	public LocalFileStorageService(@Value("${file.upload.path}") String uploadPath) {
		this.uploadRoot = Path.of(uploadPath);
	}

	@Override
	public String store(MultipartFile file, String subDirectory) {
		if (file == null || file.isEmpty()) {
			throw new ServiceException(RsStatus.BAD_REQUEST.getResultCode() + "-1", "빈 파일은 업로드할 수 없습니다.");
		}

		try {
			Path targetDir = uploadRoot.resolve(subDirectory);
			Files.createDirectories(targetDir);

			String extension = extractExtension(file.getOriginalFilename());
			String fileName = UUID.randomUUID() + extension;

			Path targetPath = targetDir.resolve(fileName);
			file.transferTo(targetPath);

			return "/uploads/" + subDirectory + "/" + fileName;
		} catch (IOException e) {
			throw new ServiceException(
				RsStatus.INTERNAL_SERVER_ERROR.getResultCode(), "파일 업로드에 실패했습니다."
			);
		}
	}

	private String extractExtension(String originalFilename) {
		if (originalFilename == null || !originalFilename.contains(".")) {
			return "";
		}
		return originalFilename.substring(originalFilename.lastIndexOf('.'));
	}
}
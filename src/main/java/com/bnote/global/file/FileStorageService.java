package com.bnote.global.file;

import com.bnote.global.exception.ServiceException;
import com.bnote.global.response.RsStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Component
public class FileStorageService {

	private final Path uploadRoot;

	public FileStorageService(@Value("${file.upload.path}") String uploadPath) {
		this.uploadRoot = Path.of(uploadPath);
	}

	/**
	 * @param subDirectory "sermons/123" 같은 하위 경로 (앞뒤 슬래시 없이)
	 * @return "/uploads/sermons/123/{생성된 파일명}" 형태의 접근 가능한 URL
	 */
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
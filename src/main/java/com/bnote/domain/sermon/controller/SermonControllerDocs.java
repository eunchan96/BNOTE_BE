package com.bnote.domain.sermon.controller;

import com.bnote.domain.sermon.dto.request.SermonRequest;
import com.bnote.domain.sermon.dto.response.SermonPhotoResponse;
import com.bnote.domain.sermon.dto.response.SermonResponse;
import com.bnote.global.response.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Sermon", description = "설교노트 API")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/v1/sermons")
public interface SermonControllerDocs {

	@Operation(summary = "설교노트 목록 조회", description = "keyword로 제목을 검색할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<List<SermonResponse>> getAll(@Parameter(description = "제목 검색어") String keyword);

	@Operation(summary = "설교노트 상세 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아님",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 설교노트",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<SermonResponse> getById(@Parameter(description = "설교노트 id") Long id);

	@Operation(summary = "설교노트 등록", description = "본문(bibleRefs)은 여러 개 등록할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "등록 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<SermonResponse> create(@Valid @RequestBody SermonRequest request);

	@Operation(summary = "설교노트 수정")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "수정 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아님",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 설교노트",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<SermonResponse> update(@Parameter(description = "설교노트 id") Long id, @Valid @RequestBody SermonRequest request);

	@Operation(summary = "설교노트 삭제", description = "본문/사진도 함께 삭제됩니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "삭제 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아님",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 설교노트",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<Void> delete(@Parameter(description = "설교노트 id") Long id);

	@Operation(summary = "설교노트 사진 업로드", description = "multipart/form-data로 이미지 파일을 업로드합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "업로드 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아님",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 설교노트",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<SermonPhotoResponse> addPhoto(@Parameter(description = "설교노트 id") Long id, MultipartFile file);
}
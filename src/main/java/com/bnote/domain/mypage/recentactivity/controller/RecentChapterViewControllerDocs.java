package com.bnote.domain.mypage.recentactivity.controller;

import com.bnote.domain.mypage.recentactivity.dto.request.RecentChapterViewRequest;
import com.bnote.domain.mypage.recentactivity.dto.response.RecentChapterViewResponse;
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

import java.util.List;

@Tag(name = "RecentChapterView", description = "최근 열람 장 API")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/v1/recent-chapter-views")
public interface RecentChapterViewControllerDocs {

	@Operation(summary = "최근 열람 장 목록 조회", description = "최신순으로 limit개(기본 10개)를 반환합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<List<RecentChapterViewResponse>> getRecent(@Parameter(description = "가져올 개수 (기본 10)") Integer limit);

	@Operation(summary = "열람 기록", description = "성경 장을 열 때마다 호출합니다. 이미 기록된 장이면 시각만 갱신됩니다(upsert).")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "기록 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<RecentChapterViewResponse> record(@Valid @RequestBody RecentChapterViewRequest request);
}
package com.bnote.domain.knowledge.controller;

import com.bnote.domain.knowledge.dto.response.BibleFigureResponse;
import com.bnote.domain.knowledge.dto.response.BiblePlaceResponse;
import com.bnote.domain.knowledge.dto.response.BibleUnitResponse;
import com.bnote.domain.knowledge.dto.response.CultureTopicResponse;
import com.bnote.domain.knowledge.dto.response.GenealogyChartResponse;
import com.bnote.domain.knowledge.dto.response.ParableOrMiracleResponse;
import com.bnote.domain.knowledge.dto.response.TimelineEventResponse;
import com.bnote.domain.knowledge.dto.response.TopicalVerseGroupResponse;
import com.bnote.global.response.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "Knowledge", description = "성경 배경지식 허브 API (인증 불필요, 공용 참고 데이터)")
public interface KnowledgeControllerDocs {

	@Operation(summary = "인물사전 목록/검색")
	@ApiResponses(@ApiResponse(responseCode = "200", description = "조회 성공",
		content = @Content(schema = @Schema(implementation = RsData.class))))
	RsData<List<BibleFigureResponse>> getFigures(@Parameter(description = "이름 검색어") String keyword);

	@Operation(summary = "지명사전 목록/검색")
	@ApiResponses(@ApiResponse(responseCode = "200", description = "조회 성공",
		content = @Content(schema = @Schema(implementation = RsData.class))))
	RsData<List<BiblePlaceResponse>> getPlaces(@Parameter(description = "이름 검색어") String keyword);

	@Operation(summary = "족보 목록 조회")
	@ApiResponses(@ApiResponse(responseCode = "200", description = "조회 성공",
		content = @Content(schema = @Schema(implementation = RsData.class))))
	RsData<List<GenealogyChartResponse>> getGenealogies();

	@Operation(summary = "연대표 조회")
	@ApiResponses(@ApiResponse(responseCode = "200", description = "조회 성공",
		content = @Content(schema = @Schema(implementation = RsData.class))))
	RsData<List<TimelineEventResponse>> getTimeline();

	@Operation(summary = "당시 문화 조회")
	@ApiResponses(@ApiResponse(responseCode = "200", description = "조회 성공",
		content = @Content(schema = @Schema(implementation = RsData.class))))
	RsData<List<CultureTopicResponse>> getCultureTopics();

	@Operation(summary = "비유와 이적 조회")
	@ApiResponses(@ApiResponse(responseCode = "200", description = "조회 성공",
		content = @Content(schema = @Schema(implementation = RsData.class))))
	RsData<List<ParableOrMiracleResponse>> getParables();

	@Operation(summary = "상황별 말씀 조회")
	@ApiResponses(@ApiResponse(responseCode = "200", description = "조회 성공",
		content = @Content(schema = @Schema(implementation = RsData.class))))
	RsData<List<TopicalVerseGroupResponse>> getTopicalVerses();

	@Operation(summary = "성경 단위 조회")
	@ApiResponses(@ApiResponse(responseCode = "200", description = "조회 성공",
		content = @Content(schema = @Schema(implementation = RsData.class))))
	RsData<List<BibleUnitResponse>> getUnits();
}
package com.bnote.domain.sermon.dto.response;

import com.bnote.domain.sermon.entity.Sermon;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record SermonResponse(
	Long id,
	String title,
	PreacherResponse preacher,
	LocalDate sermonDate,
	SermonCategoryResponse category,
	String memo,
	String link,
	List<BibleRefResponse> bibleRefs,
	List<SermonPhotoResponse> photos,
	LocalDateTime createdAt
) {
	public static SermonResponse of(
		Sermon sermon, PreacherResponse preacher, SermonCategoryResponse category,
		List<BibleRefResponse> bibleRefs, List<SermonPhotoResponse> photos
	) {
		return new SermonResponse(
			sermon.getId(), sermon.getTitle(), preacher, sermon.getSermonDate(), category,
			sermon.getMemo(), sermon.getLink(), bibleRefs, photos, sermon.getCreateDate()
		);
	}
}
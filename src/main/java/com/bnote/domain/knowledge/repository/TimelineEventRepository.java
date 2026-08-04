package com.bnote.domain.knowledge.repository;

import com.bnote.domain.knowledge.entity.TimelineEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimelineEventRepository extends JpaRepository<TimelineEvent, String> {
}
package com.bnote.domain.knowledge.repository;

import com.bnote.domain.knowledge.entity.CultureTopic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CultureTopicRepository extends JpaRepository<CultureTopic, String> {
}
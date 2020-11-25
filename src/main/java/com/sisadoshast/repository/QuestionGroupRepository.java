package com.sisadoshast.repository;

import com.sisadoshast.domain.QuestionGroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the QuestionGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionGroupRepository extends JpaRepository<QuestionGroup, Long> {

}

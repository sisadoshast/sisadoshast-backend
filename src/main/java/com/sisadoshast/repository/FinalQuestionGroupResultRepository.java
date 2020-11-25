package com.sisadoshast.repository;

import com.sisadoshast.domain.FinalQuestionGroupResult;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FinalQuestionGroupResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FinalQuestionGroupResultRepository extends JpaRepository<FinalQuestionGroupResult, Long> {

}

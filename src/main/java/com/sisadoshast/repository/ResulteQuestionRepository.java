package com.sisadoshast.repository;

import com.sisadoshast.domain.ResulteQuestion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ResulteQuestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResulteQuestionRepository extends JpaRepository<ResulteQuestion, Long> {

}

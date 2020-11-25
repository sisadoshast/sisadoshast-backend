package com.sisadoshast.repository;

import com.sisadoshast.domain.FinalResult;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FinalResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FinalResultRepository extends JpaRepository<FinalResult, Long> {

}

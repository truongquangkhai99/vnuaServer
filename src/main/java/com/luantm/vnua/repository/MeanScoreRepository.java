package com.luantm.vnua.repository;

import com.luantm.vnua.domain.MeanScore;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MeanScore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeanScoreRepository extends JpaRepository<MeanScore, Long> {

}

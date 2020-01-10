package com.luantm.vnua.repository.search;

import com.luantm.vnua.domain.MeanScore;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MeanScore} entity.
 */
public interface MeanScoreSearchRepository extends ElasticsearchRepository<MeanScore, Long> {
}

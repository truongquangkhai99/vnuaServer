package com.luantm.vnua.web.rest;

import com.luantm.vnua.domain.MeanScore;
import com.luantm.vnua.repository.MeanScoreRepository;
import com.luantm.vnua.repository.search.MeanScoreSearchRepository;
import com.luantm.vnua.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.luantm.vnua.domain.MeanScore}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MeanScoreResource {

    private final Logger log = LoggerFactory.getLogger(MeanScoreResource.class);

    private static final String ENTITY_NAME = "meanScore";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MeanScoreRepository meanScoreRepository;

    private final MeanScoreSearchRepository meanScoreSearchRepository;

    public MeanScoreResource(MeanScoreRepository meanScoreRepository, MeanScoreSearchRepository meanScoreSearchRepository) {
        this.meanScoreRepository = meanScoreRepository;
        this.meanScoreSearchRepository = meanScoreSearchRepository;
    }

    /**
     * {@code POST  /mean-scores} : Create a new meanScore.
     *
     * @param meanScore the meanScore to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new meanScore, or with status {@code 400 (Bad Request)} if the meanScore has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mean-scores")
    public ResponseEntity<MeanScore> createMeanScore(@RequestBody MeanScore meanScore) throws URISyntaxException {
        log.debug("REST request to save MeanScore : {}", meanScore);
        if (meanScore.getId() != null) {
            throw new BadRequestAlertException("A new meanScore cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MeanScore result = meanScoreRepository.save(meanScore);
        meanScoreSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/mean-scores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mean-scores} : Updates an existing meanScore.
     *
     * @param meanScore the meanScore to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated meanScore,
     * or with status {@code 400 (Bad Request)} if the meanScore is not valid,
     * or with status {@code 500 (Internal Server Error)} if the meanScore couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mean-scores")
    public ResponseEntity<MeanScore> updateMeanScore(@RequestBody MeanScore meanScore) throws URISyntaxException {
        log.debug("REST request to update MeanScore : {}", meanScore);
        if (meanScore.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MeanScore result = meanScoreRepository.save(meanScore);
        meanScoreSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, meanScore.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mean-scores} : get all the meanScores.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of meanScores in body.
     */
    @GetMapping("/mean-scores")
    public ResponseEntity<List<MeanScore>> getAllMeanScores(Pageable pageable) {
        log.debug("REST request to get a page of MeanScores");
        Page<MeanScore> page = meanScoreRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mean-scores/:id} : get the "id" meanScore.
     *
     * @param id the id of the meanScore to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the meanScore, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mean-scores/{id}")
    public ResponseEntity<MeanScore> getMeanScore(@PathVariable Long id) {
        log.debug("REST request to get MeanScore : {}", id);
        Optional<MeanScore> meanScore = meanScoreRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(meanScore);
    }

    /**
     * {@code DELETE  /mean-scores/:id} : delete the "id" meanScore.
     *
     * @param id the id of the meanScore to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mean-scores/{id}")
    public ResponseEntity<Void> deleteMeanScore(@PathVariable Long id) {
        log.debug("REST request to delete MeanScore : {}", id);
        meanScoreRepository.deleteById(id);
        meanScoreSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/mean-scores?query=:query} : search for the meanScore corresponding
     * to the query.
     *
     * @param query the query of the meanScore search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/mean-scores")
    public ResponseEntity<List<MeanScore>> searchMeanScores(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MeanScores for query {}", query);
        Page<MeanScore> page = meanScoreSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}

package com.sisadoshast.web.rest;

import com.sisadoshast.domain.FinalQuestionGroupResult;
import com.sisadoshast.repository.FinalQuestionGroupResultRepository;
import com.sisadoshast.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sisadoshast.domain.FinalQuestionGroupResult}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FinalQuestionGroupResultResource {

    private final Logger log = LoggerFactory.getLogger(FinalQuestionGroupResultResource.class);

    private static final String ENTITY_NAME = "finalQuestionGroupResult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FinalQuestionGroupResultRepository finalQuestionGroupResultRepository;

    public FinalQuestionGroupResultResource(FinalQuestionGroupResultRepository finalQuestionGroupResultRepository) {
        this.finalQuestionGroupResultRepository = finalQuestionGroupResultRepository;
    }

    /**
     * {@code POST  /final-question-group-results} : Create a new finalQuestionGroupResult.
     *
     * @param finalQuestionGroupResult the finalQuestionGroupResult to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new finalQuestionGroupResult, or with status {@code 400 (Bad Request)} if the finalQuestionGroupResult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/final-question-group-results")
    public ResponseEntity<FinalQuestionGroupResult> createFinalQuestionGroupResult(@RequestBody FinalQuestionGroupResult finalQuestionGroupResult) throws URISyntaxException {
        log.debug("REST request to save FinalQuestionGroupResult : {}", finalQuestionGroupResult);
        if (finalQuestionGroupResult.getId() != null) {
            throw new BadRequestAlertException("A new finalQuestionGroupResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FinalQuestionGroupResult result = finalQuestionGroupResultRepository.save(finalQuestionGroupResult);
        return ResponseEntity.created(new URI("/api/final-question-group-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /final-question-group-results} : Updates an existing finalQuestionGroupResult.
     *
     * @param finalQuestionGroupResult the finalQuestionGroupResult to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated finalQuestionGroupResult,
     * or with status {@code 400 (Bad Request)} if the finalQuestionGroupResult is not valid,
     * or with status {@code 500 (Internal Server Error)} if the finalQuestionGroupResult couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/final-question-group-results")
    public ResponseEntity<FinalQuestionGroupResult> updateFinalQuestionGroupResult(@RequestBody FinalQuestionGroupResult finalQuestionGroupResult) throws URISyntaxException {
        log.debug("REST request to update FinalQuestionGroupResult : {}", finalQuestionGroupResult);
        if (finalQuestionGroupResult.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FinalQuestionGroupResult result = finalQuestionGroupResultRepository.save(finalQuestionGroupResult);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, finalQuestionGroupResult.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /final-question-group-results} : get all the finalQuestionGroupResults.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of finalQuestionGroupResults in body.
     */
    @GetMapping("/final-question-group-results")
    public List<FinalQuestionGroupResult> getAllFinalQuestionGroupResults() {
        log.debug("REST request to get all FinalQuestionGroupResults");
        return finalQuestionGroupResultRepository.findAll();
    }

    /**
     * {@code GET  /final-question-group-results/:id} : get the "id" finalQuestionGroupResult.
     *
     * @param id the id of the finalQuestionGroupResult to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the finalQuestionGroupResult, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/final-question-group-results/{id}")
    public ResponseEntity<FinalQuestionGroupResult> getFinalQuestionGroupResult(@PathVariable Long id) {
        log.debug("REST request to get FinalQuestionGroupResult : {}", id);
        Optional<FinalQuestionGroupResult> finalQuestionGroupResult = finalQuestionGroupResultRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(finalQuestionGroupResult);
    }

    /**
     * {@code DELETE  /final-question-group-results/:id} : delete the "id" finalQuestionGroupResult.
     *
     * @param id the id of the finalQuestionGroupResult to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/final-question-group-results/{id}")
    public ResponseEntity<Void> deleteFinalQuestionGroupResult(@PathVariable Long id) {
        log.debug("REST request to delete FinalQuestionGroupResult : {}", id);
        finalQuestionGroupResultRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

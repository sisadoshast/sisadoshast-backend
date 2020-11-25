package com.sisadoshast.web.rest;

import com.sisadoshast.domain.FinalResult;
import com.sisadoshast.repository.FinalResultRepository;
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
 * REST controller for managing {@link com.sisadoshast.domain.FinalResult}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FinalResultResource {

    private final Logger log = LoggerFactory.getLogger(FinalResultResource.class);

    private static final String ENTITY_NAME = "finalResult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FinalResultRepository finalResultRepository;

    public FinalResultResource(FinalResultRepository finalResultRepository) {
        this.finalResultRepository = finalResultRepository;
    }

    /**
     * {@code POST  /final-results} : Create a new finalResult.
     *
     * @param finalResult the finalResult to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new finalResult, or with status {@code 400 (Bad Request)} if the finalResult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/final-results")
    public ResponseEntity<FinalResult> createFinalResult(@RequestBody FinalResult finalResult) throws URISyntaxException {
        log.debug("REST request to save FinalResult : {}", finalResult);
        if (finalResult.getId() != null) {
            throw new BadRequestAlertException("A new finalResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FinalResult result = finalResultRepository.save(finalResult);
        return ResponseEntity.created(new URI("/api/final-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /final-results} : Updates an existing finalResult.
     *
     * @param finalResult the finalResult to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated finalResult,
     * or with status {@code 400 (Bad Request)} if the finalResult is not valid,
     * or with status {@code 500 (Internal Server Error)} if the finalResult couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/final-results")
    public ResponseEntity<FinalResult> updateFinalResult(@RequestBody FinalResult finalResult) throws URISyntaxException {
        log.debug("REST request to update FinalResult : {}", finalResult);
        if (finalResult.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FinalResult result = finalResultRepository.save(finalResult);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, finalResult.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /final-results} : get all the finalResults.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of finalResults in body.
     */
    @GetMapping("/final-results")
    public List<FinalResult> getAllFinalResults() {
        log.debug("REST request to get all FinalResults");
        return finalResultRepository.findAll();
    }

    /**
     * {@code GET  /final-results/:id} : get the "id" finalResult.
     *
     * @param id the id of the finalResult to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the finalResult, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/final-results/{id}")
    public ResponseEntity<FinalResult> getFinalResult(@PathVariable Long id) {
        log.debug("REST request to get FinalResult : {}", id);
        Optional<FinalResult> finalResult = finalResultRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(finalResult);
    }

    /**
     * {@code DELETE  /final-results/:id} : delete the "id" finalResult.
     *
     * @param id the id of the finalResult to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/final-results/{id}")
    public ResponseEntity<Void> deleteFinalResult(@PathVariable Long id) {
        log.debug("REST request to delete FinalResult : {}", id);
        finalResultRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

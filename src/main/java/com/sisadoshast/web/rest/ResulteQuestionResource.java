package com.sisadoshast.web.rest;

import com.sisadoshast.domain.ResulteQuestion;
import com.sisadoshast.repository.ResulteQuestionRepository;
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
 * REST controller for managing {@link com.sisadoshast.domain.ResulteQuestion}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ResulteQuestionResource {

    private final Logger log = LoggerFactory.getLogger(ResulteQuestionResource.class);

    private static final String ENTITY_NAME = "resulteQuestion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResulteQuestionRepository resulteQuestionRepository;

    public ResulteQuestionResource(ResulteQuestionRepository resulteQuestionRepository) {
        this.resulteQuestionRepository = resulteQuestionRepository;
    }

    /**
     * {@code POST  /resulte-questions} : Create a new resulteQuestion.
     *
     * @param resulteQuestion the resulteQuestion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resulteQuestion, or with status {@code 400 (Bad Request)} if the resulteQuestion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resulte-questions")
    public ResponseEntity<ResulteQuestion> createResulteQuestion(@RequestBody ResulteQuestion resulteQuestion) throws URISyntaxException {
        log.debug("REST request to save ResulteQuestion : {}", resulteQuestion);
        if (resulteQuestion.getId() != null) {
            throw new BadRequestAlertException("A new resulteQuestion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResulteQuestion result = resulteQuestionRepository.save(resulteQuestion);
        return ResponseEntity.created(new URI("/api/resulte-questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resulte-questions} : Updates an existing resulteQuestion.
     *
     * @param resulteQuestion the resulteQuestion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resulteQuestion,
     * or with status {@code 400 (Bad Request)} if the resulteQuestion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resulteQuestion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resulte-questions")
    public ResponseEntity<ResulteQuestion> updateResulteQuestion(@RequestBody ResulteQuestion resulteQuestion) throws URISyntaxException {
        log.debug("REST request to update ResulteQuestion : {}", resulteQuestion);
        if (resulteQuestion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResulteQuestion result = resulteQuestionRepository.save(resulteQuestion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, resulteQuestion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /resulte-questions} : get all the resulteQuestions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resulteQuestions in body.
     */
    @GetMapping("/resulte-questions")
    public List<ResulteQuestion> getAllResulteQuestions() {
        log.debug("REST request to get all ResulteQuestions");
        return resulteQuestionRepository.findAll();
    }

    /**
     * {@code GET  /resulte-questions/:id} : get the "id" resulteQuestion.
     *
     * @param id the id of the resulteQuestion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resulteQuestion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resulte-questions/{id}")
    public ResponseEntity<ResulteQuestion> getResulteQuestion(@PathVariable Long id) {
        log.debug("REST request to get ResulteQuestion : {}", id);
        Optional<ResulteQuestion> resulteQuestion = resulteQuestionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resulteQuestion);
    }

    /**
     * {@code DELETE  /resulte-questions/:id} : delete the "id" resulteQuestion.
     *
     * @param id the id of the resulteQuestion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resulte-questions/{id}")
    public ResponseEntity<Void> deleteResulteQuestion(@PathVariable Long id) {
        log.debug("REST request to delete ResulteQuestion : {}", id);
        resulteQuestionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

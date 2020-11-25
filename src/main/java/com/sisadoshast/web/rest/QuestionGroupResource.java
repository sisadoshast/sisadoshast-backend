package com.sisadoshast.web.rest;

import com.sisadoshast.domain.QuestionGroup;
import com.sisadoshast.repository.QuestionGroupRepository;
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
 * REST controller for managing {@link com.sisadoshast.domain.QuestionGroup}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class QuestionGroupResource {

    private final Logger log = LoggerFactory.getLogger(QuestionGroupResource.class);

    private static final String ENTITY_NAME = "questionGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionGroupRepository questionGroupRepository;

    public QuestionGroupResource(QuestionGroupRepository questionGroupRepository) {
        this.questionGroupRepository = questionGroupRepository;
    }

    /**
     * {@code POST  /question-groups} : Create a new questionGroup.
     *
     * @param questionGroup the questionGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questionGroup, or with status {@code 400 (Bad Request)} if the questionGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/question-groups")
    public ResponseEntity<QuestionGroup> createQuestionGroup(@RequestBody QuestionGroup questionGroup) throws URISyntaxException {
        log.debug("REST request to save QuestionGroup : {}", questionGroup);
        if (questionGroup.getId() != null) {
            throw new BadRequestAlertException("A new questionGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionGroup result = questionGroupRepository.save(questionGroup);
        return ResponseEntity.created(new URI("/api/question-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /question-groups} : Updates an existing questionGroup.
     *
     * @param questionGroup the questionGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionGroup,
     * or with status {@code 400 (Bad Request)} if the questionGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questionGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/question-groups")
    public ResponseEntity<QuestionGroup> updateQuestionGroup(@RequestBody QuestionGroup questionGroup) throws URISyntaxException {
        log.debug("REST request to update QuestionGroup : {}", questionGroup);
        if (questionGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuestionGroup result = questionGroupRepository.save(questionGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, questionGroup.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /question-groups} : get all the questionGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questionGroups in body.
     */
    @GetMapping("/question-groups")
    public List<QuestionGroup> getAllQuestionGroups() {
        log.debug("REST request to get all QuestionGroups");
        return questionGroupRepository.findAll();
    }

    /**
     * {@code GET  /question-groups/:id} : get the "id" questionGroup.
     *
     * @param id the id of the questionGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questionGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/question-groups/{id}")
    public ResponseEntity<QuestionGroup> getQuestionGroup(@PathVariable Long id) {
        log.debug("REST request to get QuestionGroup : {}", id);
        Optional<QuestionGroup> questionGroup = questionGroupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(questionGroup);
    }

    /**
     * {@code DELETE  /question-groups/:id} : delete the "id" questionGroup.
     *
     * @param id the id of the questionGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/question-groups/{id}")
    public ResponseEntity<Void> deleteQuestionGroup(@PathVariable Long id) {
        log.debug("REST request to delete QuestionGroup : {}", id);
        questionGroupRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

package com.sisadoshast.web.rest;

import com.sisadoshast.Application;
import com.sisadoshast.domain.FinalQuestionGroupResult;
import com.sisadoshast.repository.FinalQuestionGroupResultRepository;
import com.sisadoshast.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.sisadoshast.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FinalQuestionGroupResultResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class FinalQuestionGroupResultResourceIT {

    private static final Integer DEFAULT_AVRAGE_QUESTION_GROUP_RESULT = 1;
    private static final Integer UPDATED_AVRAGE_QUESTION_GROUP_RESULT = 2;

    private static final Integer DEFAULT_WEIGHT = 1;
    private static final Integer UPDATED_WEIGHT = 2;

    @Autowired
    private FinalQuestionGroupResultRepository finalQuestionGroupResultRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restFinalQuestionGroupResultMockMvc;

    private FinalQuestionGroupResult finalQuestionGroupResult;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FinalQuestionGroupResultResource finalQuestionGroupResultResource = new FinalQuestionGroupResultResource(finalQuestionGroupResultRepository);
        this.restFinalQuestionGroupResultMockMvc = MockMvcBuilders.standaloneSetup(finalQuestionGroupResultResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FinalQuestionGroupResult createEntity(EntityManager em) {
        FinalQuestionGroupResult finalQuestionGroupResult = new FinalQuestionGroupResult()
            .avrageQuestionGroupResult(DEFAULT_AVRAGE_QUESTION_GROUP_RESULT)
            .weight(DEFAULT_WEIGHT);
        return finalQuestionGroupResult;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FinalQuestionGroupResult createUpdatedEntity(EntityManager em) {
        FinalQuestionGroupResult finalQuestionGroupResult = new FinalQuestionGroupResult()
            .avrageQuestionGroupResult(UPDATED_AVRAGE_QUESTION_GROUP_RESULT)
            .weight(UPDATED_WEIGHT);
        return finalQuestionGroupResult;
    }

    @BeforeEach
    public void initTest() {
        finalQuestionGroupResult = createEntity(em);
    }

    @Test
    @Transactional
    public void createFinalQuestionGroupResult() throws Exception {
        int databaseSizeBeforeCreate = finalQuestionGroupResultRepository.findAll().size();

        // Create the FinalQuestionGroupResult
        restFinalQuestionGroupResultMockMvc.perform(post("/api/final-question-group-results")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(finalQuestionGroupResult)))
            .andExpect(status().isCreated());

        // Validate the FinalQuestionGroupResult in the database
        List<FinalQuestionGroupResult> finalQuestionGroupResultList = finalQuestionGroupResultRepository.findAll();
        assertThat(finalQuestionGroupResultList).hasSize(databaseSizeBeforeCreate + 1);
        FinalQuestionGroupResult testFinalQuestionGroupResult = finalQuestionGroupResultList.get(finalQuestionGroupResultList.size() - 1);
        assertThat(testFinalQuestionGroupResult.getAvrageQuestionGroupResult()).isEqualTo(DEFAULT_AVRAGE_QUESTION_GROUP_RESULT);
        assertThat(testFinalQuestionGroupResult.getWeight()).isEqualTo(DEFAULT_WEIGHT);
    }

    @Test
    @Transactional
    public void createFinalQuestionGroupResultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = finalQuestionGroupResultRepository.findAll().size();

        // Create the FinalQuestionGroupResult with an existing ID
        finalQuestionGroupResult.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFinalQuestionGroupResultMockMvc.perform(post("/api/final-question-group-results")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(finalQuestionGroupResult)))
            .andExpect(status().isBadRequest());

        // Validate the FinalQuestionGroupResult in the database
        List<FinalQuestionGroupResult> finalQuestionGroupResultList = finalQuestionGroupResultRepository.findAll();
        assertThat(finalQuestionGroupResultList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFinalQuestionGroupResults() throws Exception {
        // Initialize the database
        finalQuestionGroupResultRepository.saveAndFlush(finalQuestionGroupResult);

        // Get all the finalQuestionGroupResultList
        restFinalQuestionGroupResultMockMvc.perform(get("/api/final-question-group-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(finalQuestionGroupResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].avrageQuestionGroupResult").value(hasItem(DEFAULT_AVRAGE_QUESTION_GROUP_RESULT)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)));
    }
    
    @Test
    @Transactional
    public void getFinalQuestionGroupResult() throws Exception {
        // Initialize the database
        finalQuestionGroupResultRepository.saveAndFlush(finalQuestionGroupResult);

        // Get the finalQuestionGroupResult
        restFinalQuestionGroupResultMockMvc.perform(get("/api/final-question-group-results/{id}", finalQuestionGroupResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(finalQuestionGroupResult.getId().intValue()))
            .andExpect(jsonPath("$.avrageQuestionGroupResult").value(DEFAULT_AVRAGE_QUESTION_GROUP_RESULT))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT));
    }

    @Test
    @Transactional
    public void getNonExistingFinalQuestionGroupResult() throws Exception {
        // Get the finalQuestionGroupResult
        restFinalQuestionGroupResultMockMvc.perform(get("/api/final-question-group-results/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFinalQuestionGroupResult() throws Exception {
        // Initialize the database
        finalQuestionGroupResultRepository.saveAndFlush(finalQuestionGroupResult);

        int databaseSizeBeforeUpdate = finalQuestionGroupResultRepository.findAll().size();

        // Update the finalQuestionGroupResult
        FinalQuestionGroupResult updatedFinalQuestionGroupResult = finalQuestionGroupResultRepository.findById(finalQuestionGroupResult.getId()).get();
        // Disconnect from session so that the updates on updatedFinalQuestionGroupResult are not directly saved in db
        em.detach(updatedFinalQuestionGroupResult);
        updatedFinalQuestionGroupResult
            .avrageQuestionGroupResult(UPDATED_AVRAGE_QUESTION_GROUP_RESULT)
            .weight(UPDATED_WEIGHT);

        restFinalQuestionGroupResultMockMvc.perform(put("/api/final-question-group-results")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFinalQuestionGroupResult)))
            .andExpect(status().isOk());

        // Validate the FinalQuestionGroupResult in the database
        List<FinalQuestionGroupResult> finalQuestionGroupResultList = finalQuestionGroupResultRepository.findAll();
        assertThat(finalQuestionGroupResultList).hasSize(databaseSizeBeforeUpdate);
        FinalQuestionGroupResult testFinalQuestionGroupResult = finalQuestionGroupResultList.get(finalQuestionGroupResultList.size() - 1);
        assertThat(testFinalQuestionGroupResult.getAvrageQuestionGroupResult()).isEqualTo(UPDATED_AVRAGE_QUESTION_GROUP_RESULT);
        assertThat(testFinalQuestionGroupResult.getWeight()).isEqualTo(UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void updateNonExistingFinalQuestionGroupResult() throws Exception {
        int databaseSizeBeforeUpdate = finalQuestionGroupResultRepository.findAll().size();

        // Create the FinalQuestionGroupResult

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFinalQuestionGroupResultMockMvc.perform(put("/api/final-question-group-results")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(finalQuestionGroupResult)))
            .andExpect(status().isBadRequest());

        // Validate the FinalQuestionGroupResult in the database
        List<FinalQuestionGroupResult> finalQuestionGroupResultList = finalQuestionGroupResultRepository.findAll();
        assertThat(finalQuestionGroupResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFinalQuestionGroupResult() throws Exception {
        // Initialize the database
        finalQuestionGroupResultRepository.saveAndFlush(finalQuestionGroupResult);

        int databaseSizeBeforeDelete = finalQuestionGroupResultRepository.findAll().size();

        // Delete the finalQuestionGroupResult
        restFinalQuestionGroupResultMockMvc.perform(delete("/api/final-question-group-results/{id}", finalQuestionGroupResult.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FinalQuestionGroupResult> finalQuestionGroupResultList = finalQuestionGroupResultRepository.findAll();
        assertThat(finalQuestionGroupResultList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.sisadoshast.web.rest;

import com.sisadoshast.Application;
import com.sisadoshast.domain.FinalResult;
import com.sisadoshast.repository.FinalResultRepository;
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
 * Integration tests for the {@link FinalResultResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class FinalResultResourceIT {

    private static final Integer DEFAULT_AVRAGE_RESULT = 1;
    private static final Integer UPDATED_AVRAGE_RESULT = 2;

    @Autowired
    private FinalResultRepository finalResultRepository;

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

    private MockMvc restFinalResultMockMvc;

    private FinalResult finalResult;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FinalResultResource finalResultResource = new FinalResultResource(finalResultRepository);
        this.restFinalResultMockMvc = MockMvcBuilders.standaloneSetup(finalResultResource)
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
    public static FinalResult createEntity(EntityManager em) {
        FinalResult finalResult = new FinalResult()
            .avrageResult(DEFAULT_AVRAGE_RESULT);
        return finalResult;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FinalResult createUpdatedEntity(EntityManager em) {
        FinalResult finalResult = new FinalResult()
            .avrageResult(UPDATED_AVRAGE_RESULT);
        return finalResult;
    }

    @BeforeEach
    public void initTest() {
        finalResult = createEntity(em);
    }

    @Test
    @Transactional
    public void createFinalResult() throws Exception {
        int databaseSizeBeforeCreate = finalResultRepository.findAll().size();

        // Create the FinalResult
        restFinalResultMockMvc.perform(post("/api/final-results")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(finalResult)))
            .andExpect(status().isCreated());

        // Validate the FinalResult in the database
        List<FinalResult> finalResultList = finalResultRepository.findAll();
        assertThat(finalResultList).hasSize(databaseSizeBeforeCreate + 1);
        FinalResult testFinalResult = finalResultList.get(finalResultList.size() - 1);
        assertThat(testFinalResult.getAvrageResult()).isEqualTo(DEFAULT_AVRAGE_RESULT);
    }

    @Test
    @Transactional
    public void createFinalResultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = finalResultRepository.findAll().size();

        // Create the FinalResult with an existing ID
        finalResult.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFinalResultMockMvc.perform(post("/api/final-results")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(finalResult)))
            .andExpect(status().isBadRequest());

        // Validate the FinalResult in the database
        List<FinalResult> finalResultList = finalResultRepository.findAll();
        assertThat(finalResultList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFinalResults() throws Exception {
        // Initialize the database
        finalResultRepository.saveAndFlush(finalResult);

        // Get all the finalResultList
        restFinalResultMockMvc.perform(get("/api/final-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(finalResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].avrageResult").value(hasItem(DEFAULT_AVRAGE_RESULT)));
    }
    
    @Test
    @Transactional
    public void getFinalResult() throws Exception {
        // Initialize the database
        finalResultRepository.saveAndFlush(finalResult);

        // Get the finalResult
        restFinalResultMockMvc.perform(get("/api/final-results/{id}", finalResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(finalResult.getId().intValue()))
            .andExpect(jsonPath("$.avrageResult").value(DEFAULT_AVRAGE_RESULT));
    }

    @Test
    @Transactional
    public void getNonExistingFinalResult() throws Exception {
        // Get the finalResult
        restFinalResultMockMvc.perform(get("/api/final-results/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFinalResult() throws Exception {
        // Initialize the database
        finalResultRepository.saveAndFlush(finalResult);

        int databaseSizeBeforeUpdate = finalResultRepository.findAll().size();

        // Update the finalResult
        FinalResult updatedFinalResult = finalResultRepository.findById(finalResult.getId()).get();
        // Disconnect from session so that the updates on updatedFinalResult are not directly saved in db
        em.detach(updatedFinalResult);
        updatedFinalResult
            .avrageResult(UPDATED_AVRAGE_RESULT);

        restFinalResultMockMvc.perform(put("/api/final-results")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFinalResult)))
            .andExpect(status().isOk());

        // Validate the FinalResult in the database
        List<FinalResult> finalResultList = finalResultRepository.findAll();
        assertThat(finalResultList).hasSize(databaseSizeBeforeUpdate);
        FinalResult testFinalResult = finalResultList.get(finalResultList.size() - 1);
        assertThat(testFinalResult.getAvrageResult()).isEqualTo(UPDATED_AVRAGE_RESULT);
    }

    @Test
    @Transactional
    public void updateNonExistingFinalResult() throws Exception {
        int databaseSizeBeforeUpdate = finalResultRepository.findAll().size();

        // Create the FinalResult

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFinalResultMockMvc.perform(put("/api/final-results")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(finalResult)))
            .andExpect(status().isBadRequest());

        // Validate the FinalResult in the database
        List<FinalResult> finalResultList = finalResultRepository.findAll();
        assertThat(finalResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFinalResult() throws Exception {
        // Initialize the database
        finalResultRepository.saveAndFlush(finalResult);

        int databaseSizeBeforeDelete = finalResultRepository.findAll().size();

        // Delete the finalResult
        restFinalResultMockMvc.perform(delete("/api/final-results/{id}", finalResult.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FinalResult> finalResultList = finalResultRepository.findAll();
        assertThat(finalResultList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

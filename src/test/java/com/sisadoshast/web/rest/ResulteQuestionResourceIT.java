package com.sisadoshast.web.rest;

import com.sisadoshast.Application;
import com.sisadoshast.domain.ResulteQuestion;
import com.sisadoshast.repository.ResulteQuestionRepository;
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
 * Integration tests for the {@link ResulteQuestionResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class ResulteQuestionResourceIT {

    private static final Integer DEFAULT_EMPLOYEE_WEIGHT = 1;
    private static final Integer UPDATED_EMPLOYEE_WEIGHT = 2;

    private static final Integer DEFAULT_RESULT = 1;
    private static final Integer UPDATED_RESULT = 2;

    @Autowired
    private ResulteQuestionRepository resulteQuestionRepository;

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

    private MockMvc restResulteQuestionMockMvc;

    private ResulteQuestion resulteQuestion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResulteQuestionResource resulteQuestionResource = new ResulteQuestionResource(resulteQuestionRepository);
        this.restResulteQuestionMockMvc = MockMvcBuilders.standaloneSetup(resulteQuestionResource)
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
    public static ResulteQuestion createEntity(EntityManager em) {
        ResulteQuestion resulteQuestion = new ResulteQuestion()
            .employeeWeight(DEFAULT_EMPLOYEE_WEIGHT)
            .result(DEFAULT_RESULT);
        return resulteQuestion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResulteQuestion createUpdatedEntity(EntityManager em) {
        ResulteQuestion resulteQuestion = new ResulteQuestion()
            .employeeWeight(UPDATED_EMPLOYEE_WEIGHT)
            .result(UPDATED_RESULT);
        return resulteQuestion;
    }

    @BeforeEach
    public void initTest() {
        resulteQuestion = createEntity(em);
    }

    @Test
    @Transactional
    public void createResulteQuestion() throws Exception {
        int databaseSizeBeforeCreate = resulteQuestionRepository.findAll().size();

        // Create the ResulteQuestion
        restResulteQuestionMockMvc.perform(post("/api/resulte-questions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resulteQuestion)))
            .andExpect(status().isCreated());

        // Validate the ResulteQuestion in the database
        List<ResulteQuestion> resulteQuestionList = resulteQuestionRepository.findAll();
        assertThat(resulteQuestionList).hasSize(databaseSizeBeforeCreate + 1);
        ResulteQuestion testResulteQuestion = resulteQuestionList.get(resulteQuestionList.size() - 1);
        assertThat(testResulteQuestion.getEmployeeWeight()).isEqualTo(DEFAULT_EMPLOYEE_WEIGHT);
        assertThat(testResulteQuestion.getResult()).isEqualTo(DEFAULT_RESULT);
    }

    @Test
    @Transactional
    public void createResulteQuestionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resulteQuestionRepository.findAll().size();

        // Create the ResulteQuestion with an existing ID
        resulteQuestion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResulteQuestionMockMvc.perform(post("/api/resulte-questions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resulteQuestion)))
            .andExpect(status().isBadRequest());

        // Validate the ResulteQuestion in the database
        List<ResulteQuestion> resulteQuestionList = resulteQuestionRepository.findAll();
        assertThat(resulteQuestionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllResulteQuestions() throws Exception {
        // Initialize the database
        resulteQuestionRepository.saveAndFlush(resulteQuestion);

        // Get all the resulteQuestionList
        restResulteQuestionMockMvc.perform(get("/api/resulte-questions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resulteQuestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeWeight").value(hasItem(DEFAULT_EMPLOYEE_WEIGHT)))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)));
    }
    
    @Test
    @Transactional
    public void getResulteQuestion() throws Exception {
        // Initialize the database
        resulteQuestionRepository.saveAndFlush(resulteQuestion);

        // Get the resulteQuestion
        restResulteQuestionMockMvc.perform(get("/api/resulte-questions/{id}", resulteQuestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resulteQuestion.getId().intValue()))
            .andExpect(jsonPath("$.employeeWeight").value(DEFAULT_EMPLOYEE_WEIGHT))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT));
    }

    @Test
    @Transactional
    public void getNonExistingResulteQuestion() throws Exception {
        // Get the resulteQuestion
        restResulteQuestionMockMvc.perform(get("/api/resulte-questions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResulteQuestion() throws Exception {
        // Initialize the database
        resulteQuestionRepository.saveAndFlush(resulteQuestion);

        int databaseSizeBeforeUpdate = resulteQuestionRepository.findAll().size();

        // Update the resulteQuestion
        ResulteQuestion updatedResulteQuestion = resulteQuestionRepository.findById(resulteQuestion.getId()).get();
        // Disconnect from session so that the updates on updatedResulteQuestion are not directly saved in db
        em.detach(updatedResulteQuestion);
        updatedResulteQuestion
            .employeeWeight(UPDATED_EMPLOYEE_WEIGHT)
            .result(UPDATED_RESULT);

        restResulteQuestionMockMvc.perform(put("/api/resulte-questions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedResulteQuestion)))
            .andExpect(status().isOk());

        // Validate the ResulteQuestion in the database
        List<ResulteQuestion> resulteQuestionList = resulteQuestionRepository.findAll();
        assertThat(resulteQuestionList).hasSize(databaseSizeBeforeUpdate);
        ResulteQuestion testResulteQuestion = resulteQuestionList.get(resulteQuestionList.size() - 1);
        assertThat(testResulteQuestion.getEmployeeWeight()).isEqualTo(UPDATED_EMPLOYEE_WEIGHT);
        assertThat(testResulteQuestion.getResult()).isEqualTo(UPDATED_RESULT);
    }

    @Test
    @Transactional
    public void updateNonExistingResulteQuestion() throws Exception {
        int databaseSizeBeforeUpdate = resulteQuestionRepository.findAll().size();

        // Create the ResulteQuestion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResulteQuestionMockMvc.perform(put("/api/resulte-questions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resulteQuestion)))
            .andExpect(status().isBadRequest());

        // Validate the ResulteQuestion in the database
        List<ResulteQuestion> resulteQuestionList = resulteQuestionRepository.findAll();
        assertThat(resulteQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResulteQuestion() throws Exception {
        // Initialize the database
        resulteQuestionRepository.saveAndFlush(resulteQuestion);

        int databaseSizeBeforeDelete = resulteQuestionRepository.findAll().size();

        // Delete the resulteQuestion
        restResulteQuestionMockMvc.perform(delete("/api/resulte-questions/{id}", resulteQuestion.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResulteQuestion> resulteQuestionList = resulteQuestionRepository.findAll();
        assertThat(resulteQuestionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

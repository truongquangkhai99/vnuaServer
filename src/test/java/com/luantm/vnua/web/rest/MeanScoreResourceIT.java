package com.luantm.vnua.web.rest;

import com.luantm.vnua.VnuaServerApp;
import com.luantm.vnua.domain.MeanScore;
import com.luantm.vnua.repository.MeanScoreRepository;
import com.luantm.vnua.repository.search.MeanScoreSearchRepository;
import com.luantm.vnua.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.luantm.vnua.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MeanScoreResource} REST controller.
 */
@SpringBootTest(classes = VnuaServerApp.class)
public class MeanScoreResourceIT {

    private static final Float DEFAULT_DIEMTBHC_10 = 1F;
    private static final Float UPDATED_DIEMTBHC_10 = 2F;

    private static final Float DEFAULT_DIEMTBHC_4 = 1F;
    private static final Float UPDATED_DIEMTBHC_4 = 2F;

    private static final Float DEFAULT_DIEMTBTL_10 = 1F;
    private static final Float UPDATED_DIEMTBTL_10 = 2F;

    private static final Float DEFAULT_DIEMTBTL_4 = 1F;
    private static final Float UPDATED_DIEMTBTL_4 = 2F;

    private static final Integer DEFAULT_SOTINCHIDAT = 1;
    private static final Integer UPDATED_SOTINCHIDAT = 2;

    private static final Integer DEFAULT_SOTINCHITICHLUY = 1;
    private static final Integer UPDATED_SOTINCHITICHLUY = 2;

    private static final String DEFAULT_PHAN_LOAI = "AAAAAAAAAA";
    private static final String UPDATED_PHAN_LOAI = "BBBBBBBBBB";

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final Integer DEFAULT_STUDENT_ID = 1;
    private static final Integer UPDATED_STUDENT_ID = 2;

    @Autowired
    private MeanScoreRepository meanScoreRepository;

    /**
     * This repository is mocked in the com.luantm.vnua.repository.search test package.
     *
     * @see com.luantm.vnua.repository.search.MeanScoreSearchRepositoryMockConfiguration
     */
    @Autowired
    private MeanScoreSearchRepository mockMeanScoreSearchRepository;

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

    private MockMvc restMeanScoreMockMvc;

    private MeanScore meanScore;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MeanScoreResource meanScoreResource = new MeanScoreResource(meanScoreRepository, mockMeanScoreSearchRepository);
        this.restMeanScoreMockMvc = MockMvcBuilders.standaloneSetup(meanScoreResource)
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
    public static MeanScore createEntity(EntityManager em) {
        MeanScore meanScore = new MeanScore()
            .diemtbhc10(DEFAULT_DIEMTBHC_10)
            .diemtbhc4(DEFAULT_DIEMTBHC_4)
            .diemtbtl10(DEFAULT_DIEMTBTL_10)
            .diemtbtl4(DEFAULT_DIEMTBTL_4)
            .sotinchidat(DEFAULT_SOTINCHIDAT)
            .sotinchitichluy(DEFAULT_SOTINCHITICHLUY)
            .phanLoai(DEFAULT_PHAN_LOAI)
            .type(DEFAULT_TYPE)
            .studentId(DEFAULT_STUDENT_ID);
        return meanScore;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MeanScore createUpdatedEntity(EntityManager em) {
        MeanScore meanScore = new MeanScore()
            .diemtbhc10(UPDATED_DIEMTBHC_10)
            .diemtbhc4(UPDATED_DIEMTBHC_4)
            .diemtbtl10(UPDATED_DIEMTBTL_10)
            .diemtbtl4(UPDATED_DIEMTBTL_4)
            .sotinchidat(UPDATED_SOTINCHIDAT)
            .sotinchitichluy(UPDATED_SOTINCHITICHLUY)
            .phanLoai(UPDATED_PHAN_LOAI)
            .type(UPDATED_TYPE)
            .studentId(UPDATED_STUDENT_ID);
        return meanScore;
    }

    @BeforeEach
    public void initTest() {
        meanScore = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeanScore() throws Exception {
        int databaseSizeBeforeCreate = meanScoreRepository.findAll().size();

        // Create the MeanScore
        restMeanScoreMockMvc.perform(post("/api/mean-scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meanScore)))
            .andExpect(status().isCreated());

        // Validate the MeanScore in the database
        List<MeanScore> meanScoreList = meanScoreRepository.findAll();
        assertThat(meanScoreList).hasSize(databaseSizeBeforeCreate + 1);
        MeanScore testMeanScore = meanScoreList.get(meanScoreList.size() - 1);
        assertThat(testMeanScore.getDiemtbhc10()).isEqualTo(DEFAULT_DIEMTBHC_10);
        assertThat(testMeanScore.getDiemtbhc4()).isEqualTo(DEFAULT_DIEMTBHC_4);
        assertThat(testMeanScore.getDiemtbtl10()).isEqualTo(DEFAULT_DIEMTBTL_10);
        assertThat(testMeanScore.getDiemtbtl4()).isEqualTo(DEFAULT_DIEMTBTL_4);
        assertThat(testMeanScore.getSotinchidat()).isEqualTo(DEFAULT_SOTINCHIDAT);
        assertThat(testMeanScore.getSotinchitichluy()).isEqualTo(DEFAULT_SOTINCHITICHLUY);
        assertThat(testMeanScore.getPhanLoai()).isEqualTo(DEFAULT_PHAN_LOAI);
        assertThat(testMeanScore.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMeanScore.getStudentId()).isEqualTo(DEFAULT_STUDENT_ID);

        // Validate the MeanScore in Elasticsearch
        verify(mockMeanScoreSearchRepository, times(1)).save(testMeanScore);
    }

    @Test
    @Transactional
    public void createMeanScoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = meanScoreRepository.findAll().size();

        // Create the MeanScore with an existing ID
        meanScore.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeanScoreMockMvc.perform(post("/api/mean-scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meanScore)))
            .andExpect(status().isBadRequest());

        // Validate the MeanScore in the database
        List<MeanScore> meanScoreList = meanScoreRepository.findAll();
        assertThat(meanScoreList).hasSize(databaseSizeBeforeCreate);

        // Validate the MeanScore in Elasticsearch
        verify(mockMeanScoreSearchRepository, times(0)).save(meanScore);
    }


    @Test
    @Transactional
    public void getAllMeanScores() throws Exception {
        // Initialize the database
        meanScoreRepository.saveAndFlush(meanScore);

        // Get all the meanScoreList
        restMeanScoreMockMvc.perform(get("/api/mean-scores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meanScore.getId().intValue())))
            .andExpect(jsonPath("$.[*].diemtbhc10").value(hasItem(DEFAULT_DIEMTBHC_10.doubleValue())))
            .andExpect(jsonPath("$.[*].diemtbhc4").value(hasItem(DEFAULT_DIEMTBHC_4.doubleValue())))
            .andExpect(jsonPath("$.[*].diemtbtl10").value(hasItem(DEFAULT_DIEMTBTL_10.doubleValue())))
            .andExpect(jsonPath("$.[*].diemtbtl4").value(hasItem(DEFAULT_DIEMTBTL_4.doubleValue())))
            .andExpect(jsonPath("$.[*].sotinchidat").value(hasItem(DEFAULT_SOTINCHIDAT)))
            .andExpect(jsonPath("$.[*].sotinchitichluy").value(hasItem(DEFAULT_SOTINCHITICHLUY)))
            .andExpect(jsonPath("$.[*].phanLoai").value(hasItem(DEFAULT_PHAN_LOAI)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].studentId").value(hasItem(DEFAULT_STUDENT_ID)));
    }
    
    @Test
    @Transactional
    public void getMeanScore() throws Exception {
        // Initialize the database
        meanScoreRepository.saveAndFlush(meanScore);

        // Get the meanScore
        restMeanScoreMockMvc.perform(get("/api/mean-scores/{id}", meanScore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(meanScore.getId().intValue()))
            .andExpect(jsonPath("$.diemtbhc10").value(DEFAULT_DIEMTBHC_10.doubleValue()))
            .andExpect(jsonPath("$.diemtbhc4").value(DEFAULT_DIEMTBHC_4.doubleValue()))
            .andExpect(jsonPath("$.diemtbtl10").value(DEFAULT_DIEMTBTL_10.doubleValue()))
            .andExpect(jsonPath("$.diemtbtl4").value(DEFAULT_DIEMTBTL_4.doubleValue()))
            .andExpect(jsonPath("$.sotinchidat").value(DEFAULT_SOTINCHIDAT))
            .andExpect(jsonPath("$.sotinchitichluy").value(DEFAULT_SOTINCHITICHLUY))
            .andExpect(jsonPath("$.phanLoai").value(DEFAULT_PHAN_LOAI))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.studentId").value(DEFAULT_STUDENT_ID));
    }

    @Test
    @Transactional
    public void getNonExistingMeanScore() throws Exception {
        // Get the meanScore
        restMeanScoreMockMvc.perform(get("/api/mean-scores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeanScore() throws Exception {
        // Initialize the database
        meanScoreRepository.saveAndFlush(meanScore);

        int databaseSizeBeforeUpdate = meanScoreRepository.findAll().size();

        // Update the meanScore
        MeanScore updatedMeanScore = meanScoreRepository.findById(meanScore.getId()).get();
        // Disconnect from session so that the updates on updatedMeanScore are not directly saved in db
        em.detach(updatedMeanScore);
        updatedMeanScore
            .diemtbhc10(UPDATED_DIEMTBHC_10)
            .diemtbhc4(UPDATED_DIEMTBHC_4)
            .diemtbtl10(UPDATED_DIEMTBTL_10)
            .diemtbtl4(UPDATED_DIEMTBTL_4)
            .sotinchidat(UPDATED_SOTINCHIDAT)
            .sotinchitichluy(UPDATED_SOTINCHITICHLUY)
            .phanLoai(UPDATED_PHAN_LOAI)
            .type(UPDATED_TYPE)
            .studentId(UPDATED_STUDENT_ID);

        restMeanScoreMockMvc.perform(put("/api/mean-scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMeanScore)))
            .andExpect(status().isOk());

        // Validate the MeanScore in the database
        List<MeanScore> meanScoreList = meanScoreRepository.findAll();
        assertThat(meanScoreList).hasSize(databaseSizeBeforeUpdate);
        MeanScore testMeanScore = meanScoreList.get(meanScoreList.size() - 1);
        assertThat(testMeanScore.getDiemtbhc10()).isEqualTo(UPDATED_DIEMTBHC_10);
        assertThat(testMeanScore.getDiemtbhc4()).isEqualTo(UPDATED_DIEMTBHC_4);
        assertThat(testMeanScore.getDiemtbtl10()).isEqualTo(UPDATED_DIEMTBTL_10);
        assertThat(testMeanScore.getDiemtbtl4()).isEqualTo(UPDATED_DIEMTBTL_4);
        assertThat(testMeanScore.getSotinchidat()).isEqualTo(UPDATED_SOTINCHIDAT);
        assertThat(testMeanScore.getSotinchitichluy()).isEqualTo(UPDATED_SOTINCHITICHLUY);
        assertThat(testMeanScore.getPhanLoai()).isEqualTo(UPDATED_PHAN_LOAI);
        assertThat(testMeanScore.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMeanScore.getStudentId()).isEqualTo(UPDATED_STUDENT_ID);

        // Validate the MeanScore in Elasticsearch
        verify(mockMeanScoreSearchRepository, times(1)).save(testMeanScore);
    }

    @Test
    @Transactional
    public void updateNonExistingMeanScore() throws Exception {
        int databaseSizeBeforeUpdate = meanScoreRepository.findAll().size();

        // Create the MeanScore

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMeanScoreMockMvc.perform(put("/api/mean-scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meanScore)))
            .andExpect(status().isBadRequest());

        // Validate the MeanScore in the database
        List<MeanScore> meanScoreList = meanScoreRepository.findAll();
        assertThat(meanScoreList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MeanScore in Elasticsearch
        verify(mockMeanScoreSearchRepository, times(0)).save(meanScore);
    }

    @Test
    @Transactional
    public void deleteMeanScore() throws Exception {
        // Initialize the database
        meanScoreRepository.saveAndFlush(meanScore);

        int databaseSizeBeforeDelete = meanScoreRepository.findAll().size();

        // Delete the meanScore
        restMeanScoreMockMvc.perform(delete("/api/mean-scores/{id}", meanScore.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MeanScore> meanScoreList = meanScoreRepository.findAll();
        assertThat(meanScoreList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MeanScore in Elasticsearch
        verify(mockMeanScoreSearchRepository, times(1)).deleteById(meanScore.getId());
    }

    @Test
    @Transactional
    public void searchMeanScore() throws Exception {
        // Initialize the database
        meanScoreRepository.saveAndFlush(meanScore);
        when(mockMeanScoreSearchRepository.search(queryStringQuery("id:" + meanScore.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(meanScore), PageRequest.of(0, 1), 1));
        // Search the meanScore
        restMeanScoreMockMvc.perform(get("/api/_search/mean-scores?query=id:" + meanScore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meanScore.getId().intValue())))
            .andExpect(jsonPath("$.[*].diemtbhc10").value(hasItem(DEFAULT_DIEMTBHC_10.doubleValue())))
            .andExpect(jsonPath("$.[*].diemtbhc4").value(hasItem(DEFAULT_DIEMTBHC_4.doubleValue())))
            .andExpect(jsonPath("$.[*].diemtbtl10").value(hasItem(DEFAULT_DIEMTBTL_10.doubleValue())))
            .andExpect(jsonPath("$.[*].diemtbtl4").value(hasItem(DEFAULT_DIEMTBTL_4.doubleValue())))
            .andExpect(jsonPath("$.[*].sotinchidat").value(hasItem(DEFAULT_SOTINCHIDAT)))
            .andExpect(jsonPath("$.[*].sotinchitichluy").value(hasItem(DEFAULT_SOTINCHITICHLUY)))
            .andExpect(jsonPath("$.[*].phanLoai").value(hasItem(DEFAULT_PHAN_LOAI)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].studentId").value(hasItem(DEFAULT_STUDENT_ID)));
    }
}

package com.luantm.vnua.web.rest;

import com.luantm.vnua.VnuaServerApp;
import com.luantm.vnua.domain.Score;
import com.luantm.vnua.repository.ScoreRepository;
import com.luantm.vnua.repository.search.ScoreSearchRepository;
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
 * Integration tests for the {@link ScoreResource} REST controller.
 */
@SpringBootTest(classes = VnuaServerApp.class)
public class ScoreResourceIT {

    private static final String DEFAULT_TEN_MON = "AAAAAAAAAA";
    private static final String UPDATED_TEN_MON = "BBBBBBBBBB";

    private static final String DEFAULT_MA_MON = "AAAAAAAAAA";
    private static final String UPDATED_MA_MON = "BBBBBBBBBB";

    private static final Integer DEFAULT_TC = 1;
    private static final Integer UPDATED_TC = 2;

    private static final Integer DEFAULT_KT_PERCENT = 1;
    private static final Integer UPDATED_KT_PERCENT = 2;

    private static final Integer DEFAULT_THI_PERCENT = 1;
    private static final Integer UPDATED_THI_PERCENT = 2;

    private static final Integer DEFAULT_DIEM_CHUYEN_CAN = 1;
    private static final Integer UPDATED_DIEM_CHUYEN_CAN = 2;

    private static final Float DEFAULT_DIEM_QUA_TRINH = 1F;
    private static final Float UPDATED_DIEM_QUA_TRINH = 2F;

    private static final Float DEFAULT_THI_10 = 1F;
    private static final Float UPDATED_THI_10 = 2F;

    private static final Float DEFAULT_TK_110 = 1F;
    private static final Float UPDATED_TK_110 = 2F;

    private static final Float DEFAULT_TK_10 = 1F;
    private static final Float UPDATED_TK_10 = 2F;

    private static final String DEFAULT_TK_1_CH = "AAAAAAAAAA";
    private static final String UPDATED_TK_1_CH = "BBBBBBBBBB";

    private static final String DEFAULT_TKCH = "AAAAAAAAAA";
    private static final String UPDATED_TKCH = "BBBBBBBBBB";

    private static final Integer DEFAULT_STUDENT_ID = 1;
    private static final Integer UPDATED_STUDENT_ID = 2;

    @Autowired
    private ScoreRepository scoreRepository;

    /**
     * This repository is mocked in the com.luantm.vnua.repository.search test package.
     *
     * @see com.luantm.vnua.repository.search.ScoreSearchRepositoryMockConfiguration
     */
    @Autowired
    private ScoreSearchRepository mockScoreSearchRepository;

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

    private MockMvc restScoreMockMvc;

    private Score score;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ScoreResource scoreResource = new ScoreResource(scoreRepository, mockScoreSearchRepository);
        this.restScoreMockMvc = MockMvcBuilders.standaloneSetup(scoreResource)
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
    public static Score createEntity(EntityManager em) {
        Score score = new Score()
            .tenMon(DEFAULT_TEN_MON)
            .maMon(DEFAULT_MA_MON)
            .tc(DEFAULT_TC)
            .ktPercent(DEFAULT_KT_PERCENT)
            .thiPercent(DEFAULT_THI_PERCENT)
            .diemChuyenCan(DEFAULT_DIEM_CHUYEN_CAN)
            .diemQuaTrinh(DEFAULT_DIEM_QUA_TRINH)
            .thi10(DEFAULT_THI_10)
            .tk110(DEFAULT_TK_110)
            .tk10(DEFAULT_TK_10)
            .tk1ch(DEFAULT_TK_1_CH)
            .tkch(DEFAULT_TKCH)
            .studentId(DEFAULT_STUDENT_ID);
        return score;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Score createUpdatedEntity(EntityManager em) {
        Score score = new Score()
            .tenMon(UPDATED_TEN_MON)
            .maMon(UPDATED_MA_MON)
            .tc(UPDATED_TC)
            .ktPercent(UPDATED_KT_PERCENT)
            .thiPercent(UPDATED_THI_PERCENT)
            .diemChuyenCan(UPDATED_DIEM_CHUYEN_CAN)
            .diemQuaTrinh(UPDATED_DIEM_QUA_TRINH)
            .thi10(UPDATED_THI_10)
            .tk110(UPDATED_TK_110)
            .tk10(UPDATED_TK_10)
            .tk1ch(UPDATED_TK_1_CH)
            .tkch(UPDATED_TKCH)
            .studentId(UPDATED_STUDENT_ID);
        return score;
    }

    @BeforeEach
    public void initTest() {
        score = createEntity(em);
    }

    @Test
    @Transactional
    public void createScore() throws Exception {
        int databaseSizeBeforeCreate = scoreRepository.findAll().size();

        // Create the Score
        restScoreMockMvc.perform(post("/api/scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(score)))
            .andExpect(status().isCreated());

        // Validate the Score in the database
        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeCreate + 1);
        Score testScore = scoreList.get(scoreList.size() - 1);
        assertThat(testScore.getTenMon()).isEqualTo(DEFAULT_TEN_MON);
        assertThat(testScore.getMaMon()).isEqualTo(DEFAULT_MA_MON);
        assertThat(testScore.getTc()).isEqualTo(DEFAULT_TC);
        assertThat(testScore.getKtPercent()).isEqualTo(DEFAULT_KT_PERCENT);
        assertThat(testScore.getThiPercent()).isEqualTo(DEFAULT_THI_PERCENT);
        assertThat(testScore.getDiemChuyenCan()).isEqualTo(DEFAULT_DIEM_CHUYEN_CAN);
        assertThat(testScore.getDiemQuaTrinh()).isEqualTo(DEFAULT_DIEM_QUA_TRINH);
        assertThat(testScore.getThi10()).isEqualTo(DEFAULT_THI_10);
        assertThat(testScore.getTk110()).isEqualTo(DEFAULT_TK_110);
        assertThat(testScore.getTk10()).isEqualTo(DEFAULT_TK_10);
        assertThat(testScore.getTk1ch()).isEqualTo(DEFAULT_TK_1_CH);
        assertThat(testScore.getTkch()).isEqualTo(DEFAULT_TKCH);
        assertThat(testScore.getStudentId()).isEqualTo(DEFAULT_STUDENT_ID);

        // Validate the Score in Elasticsearch
        verify(mockScoreSearchRepository, times(1)).save(testScore);
    }

    @Test
    @Transactional
    public void createScoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scoreRepository.findAll().size();

        // Create the Score with an existing ID
        score.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScoreMockMvc.perform(post("/api/scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(score)))
            .andExpect(status().isBadRequest());

        // Validate the Score in the database
        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeCreate);

        // Validate the Score in Elasticsearch
        verify(mockScoreSearchRepository, times(0)).save(score);
    }


    @Test
    @Transactional
    public void getAllScores() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get all the scoreList
        restScoreMockMvc.perform(get("/api/scores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(score.getId().intValue())))
            .andExpect(jsonPath("$.[*].tenMon").value(hasItem(DEFAULT_TEN_MON)))
            .andExpect(jsonPath("$.[*].maMon").value(hasItem(DEFAULT_MA_MON)))
            .andExpect(jsonPath("$.[*].tc").value(hasItem(DEFAULT_TC)))
            .andExpect(jsonPath("$.[*].ktPercent").value(hasItem(DEFAULT_KT_PERCENT)))
            .andExpect(jsonPath("$.[*].thiPercent").value(hasItem(DEFAULT_THI_PERCENT)))
            .andExpect(jsonPath("$.[*].diemChuyenCan").value(hasItem(DEFAULT_DIEM_CHUYEN_CAN)))
            .andExpect(jsonPath("$.[*].diemQuaTrinh").value(hasItem(DEFAULT_DIEM_QUA_TRINH.doubleValue())))
            .andExpect(jsonPath("$.[*].thi10").value(hasItem(DEFAULT_THI_10.doubleValue())))
            .andExpect(jsonPath("$.[*].tk110").value(hasItem(DEFAULT_TK_110.doubleValue())))
            .andExpect(jsonPath("$.[*].tk10").value(hasItem(DEFAULT_TK_10.doubleValue())))
            .andExpect(jsonPath("$.[*].tk1ch").value(hasItem(DEFAULT_TK_1_CH)))
            .andExpect(jsonPath("$.[*].tkch").value(hasItem(DEFAULT_TKCH)))
            .andExpect(jsonPath("$.[*].studentId").value(hasItem(DEFAULT_STUDENT_ID)));
    }
    
    @Test
    @Transactional
    public void getScore() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get the score
        restScoreMockMvc.perform(get("/api/scores/{id}", score.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(score.getId().intValue()))
            .andExpect(jsonPath("$.tenMon").value(DEFAULT_TEN_MON))
            .andExpect(jsonPath("$.maMon").value(DEFAULT_MA_MON))
            .andExpect(jsonPath("$.tc").value(DEFAULT_TC))
            .andExpect(jsonPath("$.ktPercent").value(DEFAULT_KT_PERCENT))
            .andExpect(jsonPath("$.thiPercent").value(DEFAULT_THI_PERCENT))
            .andExpect(jsonPath("$.diemChuyenCan").value(DEFAULT_DIEM_CHUYEN_CAN))
            .andExpect(jsonPath("$.diemQuaTrinh").value(DEFAULT_DIEM_QUA_TRINH.doubleValue()))
            .andExpect(jsonPath("$.thi10").value(DEFAULT_THI_10.doubleValue()))
            .andExpect(jsonPath("$.tk110").value(DEFAULT_TK_110.doubleValue()))
            .andExpect(jsonPath("$.tk10").value(DEFAULT_TK_10.doubleValue()))
            .andExpect(jsonPath("$.tk1ch").value(DEFAULT_TK_1_CH))
            .andExpect(jsonPath("$.tkch").value(DEFAULT_TKCH))
            .andExpect(jsonPath("$.studentId").value(DEFAULT_STUDENT_ID));
    }

    @Test
    @Transactional
    public void getNonExistingScore() throws Exception {
        // Get the score
        restScoreMockMvc.perform(get("/api/scores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScore() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        int databaseSizeBeforeUpdate = scoreRepository.findAll().size();

        // Update the score
        Score updatedScore = scoreRepository.findById(score.getId()).get();
        // Disconnect from session so that the updates on updatedScore are not directly saved in db
        em.detach(updatedScore);
        updatedScore
            .tenMon(UPDATED_TEN_MON)
            .maMon(UPDATED_MA_MON)
            .tc(UPDATED_TC)
            .ktPercent(UPDATED_KT_PERCENT)
            .thiPercent(UPDATED_THI_PERCENT)
            .diemChuyenCan(UPDATED_DIEM_CHUYEN_CAN)
            .diemQuaTrinh(UPDATED_DIEM_QUA_TRINH)
            .thi10(UPDATED_THI_10)
            .tk110(UPDATED_TK_110)
            .tk10(UPDATED_TK_10)
            .tk1ch(UPDATED_TK_1_CH)
            .tkch(UPDATED_TKCH)
            .studentId(UPDATED_STUDENT_ID);

        restScoreMockMvc.perform(put("/api/scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedScore)))
            .andExpect(status().isOk());

        // Validate the Score in the database
        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeUpdate);
        Score testScore = scoreList.get(scoreList.size() - 1);
        assertThat(testScore.getTenMon()).isEqualTo(UPDATED_TEN_MON);
        assertThat(testScore.getMaMon()).isEqualTo(UPDATED_MA_MON);
        assertThat(testScore.getTc()).isEqualTo(UPDATED_TC);
        assertThat(testScore.getKtPercent()).isEqualTo(UPDATED_KT_PERCENT);
        assertThat(testScore.getThiPercent()).isEqualTo(UPDATED_THI_PERCENT);
        assertThat(testScore.getDiemChuyenCan()).isEqualTo(UPDATED_DIEM_CHUYEN_CAN);
        assertThat(testScore.getDiemQuaTrinh()).isEqualTo(UPDATED_DIEM_QUA_TRINH);
        assertThat(testScore.getThi10()).isEqualTo(UPDATED_THI_10);
        assertThat(testScore.getTk110()).isEqualTo(UPDATED_TK_110);
        assertThat(testScore.getTk10()).isEqualTo(UPDATED_TK_10);
        assertThat(testScore.getTk1ch()).isEqualTo(UPDATED_TK_1_CH);
        assertThat(testScore.getTkch()).isEqualTo(UPDATED_TKCH);
        assertThat(testScore.getStudentId()).isEqualTo(UPDATED_STUDENT_ID);

        // Validate the Score in Elasticsearch
        verify(mockScoreSearchRepository, times(1)).save(testScore);
    }

    @Test
    @Transactional
    public void updateNonExistingScore() throws Exception {
        int databaseSizeBeforeUpdate = scoreRepository.findAll().size();

        // Create the Score

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScoreMockMvc.perform(put("/api/scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(score)))
            .andExpect(status().isBadRequest());

        // Validate the Score in the database
        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Score in Elasticsearch
        verify(mockScoreSearchRepository, times(0)).save(score);
    }

    @Test
    @Transactional
    public void deleteScore() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        int databaseSizeBeforeDelete = scoreRepository.findAll().size();

        // Delete the score
        restScoreMockMvc.perform(delete("/api/scores/{id}", score.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Score in Elasticsearch
        verify(mockScoreSearchRepository, times(1)).deleteById(score.getId());
    }

    @Test
    @Transactional
    public void searchScore() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);
        when(mockScoreSearchRepository.search(queryStringQuery("id:" + score.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(score), PageRequest.of(0, 1), 1));
        // Search the score
        restScoreMockMvc.perform(get("/api/_search/scores?query=id:" + score.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(score.getId().intValue())))
            .andExpect(jsonPath("$.[*].tenMon").value(hasItem(DEFAULT_TEN_MON)))
            .andExpect(jsonPath("$.[*].maMon").value(hasItem(DEFAULT_MA_MON)))
            .andExpect(jsonPath("$.[*].tc").value(hasItem(DEFAULT_TC)))
            .andExpect(jsonPath("$.[*].ktPercent").value(hasItem(DEFAULT_KT_PERCENT)))
            .andExpect(jsonPath("$.[*].thiPercent").value(hasItem(DEFAULT_THI_PERCENT)))
            .andExpect(jsonPath("$.[*].diemChuyenCan").value(hasItem(DEFAULT_DIEM_CHUYEN_CAN)))
            .andExpect(jsonPath("$.[*].diemQuaTrinh").value(hasItem(DEFAULT_DIEM_QUA_TRINH.doubleValue())))
            .andExpect(jsonPath("$.[*].thi10").value(hasItem(DEFAULT_THI_10.doubleValue())))
            .andExpect(jsonPath("$.[*].tk110").value(hasItem(DEFAULT_TK_110.doubleValue())))
            .andExpect(jsonPath("$.[*].tk10").value(hasItem(DEFAULT_TK_10.doubleValue())))
            .andExpect(jsonPath("$.[*].tk1ch").value(hasItem(DEFAULT_TK_1_CH)))
            .andExpect(jsonPath("$.[*].tkch").value(hasItem(DEFAULT_TKCH)))
            .andExpect(jsonPath("$.[*].studentId").value(hasItem(DEFAULT_STUDENT_ID)));
    }
}

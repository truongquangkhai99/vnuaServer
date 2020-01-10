package com.luantm.vnua.web.rest;

import com.luantm.vnua.VnuaServerApp;
import com.luantm.vnua.domain.Student;
import com.luantm.vnua.repository.StudentRepository;
import com.luantm.vnua.repository.search.StudentSearchRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link StudentResource} REST controller.
 */
@SpringBootTest(classes = VnuaServerApp.class)
public class StudentResourceIT {

    private static final Integer DEFAULT_STUDENT_ID = 1;
    private static final Integer UPDATED_STUDENT_ID = 2;

    private static final String DEFAULT_FULLNAME = "AAAAAAAAAA";
    private static final String UPDATED_FULLNAME = "BBBBBBBBBB";

    private static final String DEFAULT_SEX = "AAAAAAAAAA";
    private static final String UPDATED_SEX = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTH_DAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DAY = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_BIRTH_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_BIRTH_PLACE = "BBBBBBBBBB";

    private static final String DEFAULT_CHUYEN_NGANH = "AAAAAAAAAA";
    private static final String UPDATED_CHUYEN_NGANH = "BBBBBBBBBB";

    private static final String DEFAULT_LOP = "AAAAAAAAAA";
    private static final String UPDATED_LOP = "BBBBBBBBBB";

    private static final String DEFAULT_KHOA = "AAAAAAAAAA";
    private static final String UPDATED_KHOA = "BBBBBBBBBB";

    private static final String DEFAULT_KHOA_HOC = "AAAAAAAAAA";
    private static final String UPDATED_KHOA_HOC = "BBBBBBBBBB";

    private static final String DEFAULT_HE_DAO_TAO = "AAAAAAAAAA";
    private static final String UPDATED_HE_DAO_TAO = "BBBBBBBBBB";

    private static final String DEFAULT_CO_VAN_HOC_TAP = "AAAAAAAAAA";
    private static final String UPDATED_CO_VAN_HOC_TAP = "BBBBBBBBBB";

    @Autowired
    private StudentRepository studentRepository;

    /**
     * This repository is mocked in the com.luantm.vnua.repository.search test package.
     *
     * @see com.luantm.vnua.repository.search.StudentSearchRepositoryMockConfiguration
     */
    @Autowired
    private StudentSearchRepository mockStudentSearchRepository;

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

    private MockMvc restStudentMockMvc;

    private Student student;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentResource studentResource = new StudentResource(studentRepository, mockStudentSearchRepository);
        this.restStudentMockMvc = MockMvcBuilders.standaloneSetup(studentResource)
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
    public static Student createEntity(EntityManager em) {
        Student student = new Student()
            .studentID(DEFAULT_STUDENT_ID)
            .fullname(DEFAULT_FULLNAME)
            .sex(DEFAULT_SEX)
            .birthDay(DEFAULT_BIRTH_DAY)
            .birthPlace(DEFAULT_BIRTH_PLACE)
            .chuyenNganh(DEFAULT_CHUYEN_NGANH)
            .lop(DEFAULT_LOP)
            .khoa(DEFAULT_KHOA)
            .khoaHoc(DEFAULT_KHOA_HOC)
            .heDaoTao(DEFAULT_HE_DAO_TAO)
            .coVanHocTap(DEFAULT_CO_VAN_HOC_TAP);
        return student;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Student createUpdatedEntity(EntityManager em) {
        Student student = new Student()
            .studentID(UPDATED_STUDENT_ID)
            .fullname(UPDATED_FULLNAME)
            .sex(UPDATED_SEX)
            .birthDay(UPDATED_BIRTH_DAY)
            .birthPlace(UPDATED_BIRTH_PLACE)
            .chuyenNganh(UPDATED_CHUYEN_NGANH)
            .lop(UPDATED_LOP)
            .khoa(UPDATED_KHOA)
            .khoaHoc(UPDATED_KHOA_HOC)
            .heDaoTao(UPDATED_HE_DAO_TAO)
            .coVanHocTap(UPDATED_CO_VAN_HOC_TAP);
        return student;
    }

    @BeforeEach
    public void initTest() {
        student = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudent() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        // Create the Student
        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isCreated());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate + 1);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getStudentID()).isEqualTo(DEFAULT_STUDENT_ID);
        assertThat(testStudent.getFullname()).isEqualTo(DEFAULT_FULLNAME);
        assertThat(testStudent.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testStudent.getBirthDay()).isEqualTo(DEFAULT_BIRTH_DAY);
        assertThat(testStudent.getBirthPlace()).isEqualTo(DEFAULT_BIRTH_PLACE);
        assertThat(testStudent.getChuyenNganh()).isEqualTo(DEFAULT_CHUYEN_NGANH);
        assertThat(testStudent.getLop()).isEqualTo(DEFAULT_LOP);
        assertThat(testStudent.getKhoa()).isEqualTo(DEFAULT_KHOA);
        assertThat(testStudent.getKhoaHoc()).isEqualTo(DEFAULT_KHOA_HOC);
        assertThat(testStudent.getHeDaoTao()).isEqualTo(DEFAULT_HE_DAO_TAO);
        assertThat(testStudent.getCoVanHocTap()).isEqualTo(DEFAULT_CO_VAN_HOC_TAP);

        // Validate the Student in Elasticsearch
        verify(mockStudentSearchRepository, times(1)).save(testStudent);
    }

    @Test
    @Transactional
    public void createStudentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        // Create the Student with an existing ID
        student.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate);

        // Validate the Student in Elasticsearch
        verify(mockStudentSearchRepository, times(0)).save(student);
    }


    @Test
    @Transactional
    public void getAllStudents() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList
        restStudentMockMvc.perform(get("/api/students?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
            .andExpect(jsonPath("$.[*].studentID").value(hasItem(DEFAULT_STUDENT_ID)))
            .andExpect(jsonPath("$.[*].fullname").value(hasItem(DEFAULT_FULLNAME)))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX)))
            .andExpect(jsonPath("$.[*].birthDay").value(hasItem(DEFAULT_BIRTH_DAY.toString())))
            .andExpect(jsonPath("$.[*].birthPlace").value(hasItem(DEFAULT_BIRTH_PLACE)))
            .andExpect(jsonPath("$.[*].chuyenNganh").value(hasItem(DEFAULT_CHUYEN_NGANH)))
            .andExpect(jsonPath("$.[*].lop").value(hasItem(DEFAULT_LOP)))
            .andExpect(jsonPath("$.[*].khoa").value(hasItem(DEFAULT_KHOA)))
            .andExpect(jsonPath("$.[*].khoaHoc").value(hasItem(DEFAULT_KHOA_HOC)))
            .andExpect(jsonPath("$.[*].heDaoTao").value(hasItem(DEFAULT_HE_DAO_TAO)))
            .andExpect(jsonPath("$.[*].coVanHocTap").value(hasItem(DEFAULT_CO_VAN_HOC_TAP)));
    }
    
    @Test
    @Transactional
    public void getStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", student.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(student.getId().intValue()))
            .andExpect(jsonPath("$.studentID").value(DEFAULT_STUDENT_ID))
            .andExpect(jsonPath("$.fullname").value(DEFAULT_FULLNAME))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX))
            .andExpect(jsonPath("$.birthDay").value(DEFAULT_BIRTH_DAY.toString()))
            .andExpect(jsonPath("$.birthPlace").value(DEFAULT_BIRTH_PLACE))
            .andExpect(jsonPath("$.chuyenNganh").value(DEFAULT_CHUYEN_NGANH))
            .andExpect(jsonPath("$.lop").value(DEFAULT_LOP))
            .andExpect(jsonPath("$.khoa").value(DEFAULT_KHOA))
            .andExpect(jsonPath("$.khoaHoc").value(DEFAULT_KHOA_HOC))
            .andExpect(jsonPath("$.heDaoTao").value(DEFAULT_HE_DAO_TAO))
            .andExpect(jsonPath("$.coVanHocTap").value(DEFAULT_CO_VAN_HOC_TAP));
    }

    @Test
    @Transactional
    public void getNonExistingStudent() throws Exception {
        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Update the student
        Student updatedStudent = studentRepository.findById(student.getId()).get();
        // Disconnect from session so that the updates on updatedStudent are not directly saved in db
        em.detach(updatedStudent);
        updatedStudent
            .studentID(UPDATED_STUDENT_ID)
            .fullname(UPDATED_FULLNAME)
            .sex(UPDATED_SEX)
            .birthDay(UPDATED_BIRTH_DAY)
            .birthPlace(UPDATED_BIRTH_PLACE)
            .chuyenNganh(UPDATED_CHUYEN_NGANH)
            .lop(UPDATED_LOP)
            .khoa(UPDATED_KHOA)
            .khoaHoc(UPDATED_KHOA_HOC)
            .heDaoTao(UPDATED_HE_DAO_TAO)
            .coVanHocTap(UPDATED_CO_VAN_HOC_TAP);

        restStudentMockMvc.perform(put("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudent)))
            .andExpect(status().isOk());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getStudentID()).isEqualTo(UPDATED_STUDENT_ID);
        assertThat(testStudent.getFullname()).isEqualTo(UPDATED_FULLNAME);
        assertThat(testStudent.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testStudent.getBirthDay()).isEqualTo(UPDATED_BIRTH_DAY);
        assertThat(testStudent.getBirthPlace()).isEqualTo(UPDATED_BIRTH_PLACE);
        assertThat(testStudent.getChuyenNganh()).isEqualTo(UPDATED_CHUYEN_NGANH);
        assertThat(testStudent.getLop()).isEqualTo(UPDATED_LOP);
        assertThat(testStudent.getKhoa()).isEqualTo(UPDATED_KHOA);
        assertThat(testStudent.getKhoaHoc()).isEqualTo(UPDATED_KHOA_HOC);
        assertThat(testStudent.getHeDaoTao()).isEqualTo(UPDATED_HE_DAO_TAO);
        assertThat(testStudent.getCoVanHocTap()).isEqualTo(UPDATED_CO_VAN_HOC_TAP);

        // Validate the Student in Elasticsearch
        verify(mockStudentSearchRepository, times(1)).save(testStudent);
    }

    @Test
    @Transactional
    public void updateNonExistingStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Create the Student

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentMockMvc.perform(put("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Student in Elasticsearch
        verify(mockStudentSearchRepository, times(0)).save(student);
    }

    @Test
    @Transactional
    public void deleteStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeDelete = studentRepository.findAll().size();

        // Delete the student
        restStudentMockMvc.perform(delete("/api/students/{id}", student.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Student in Elasticsearch
        verify(mockStudentSearchRepository, times(1)).deleteById(student.getId());
    }

    @Test
    @Transactional
    public void searchStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);
        when(mockStudentSearchRepository.search(queryStringQuery("id:" + student.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(student), PageRequest.of(0, 1), 1));
        // Search the student
        restStudentMockMvc.perform(get("/api/_search/students?query=id:" + student.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
            .andExpect(jsonPath("$.[*].studentID").value(hasItem(DEFAULT_STUDENT_ID)))
            .andExpect(jsonPath("$.[*].fullname").value(hasItem(DEFAULT_FULLNAME)))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX)))
            .andExpect(jsonPath("$.[*].birthDay").value(hasItem(DEFAULT_BIRTH_DAY.toString())))
            .andExpect(jsonPath("$.[*].birthPlace").value(hasItem(DEFAULT_BIRTH_PLACE)))
            .andExpect(jsonPath("$.[*].chuyenNganh").value(hasItem(DEFAULT_CHUYEN_NGANH)))
            .andExpect(jsonPath("$.[*].lop").value(hasItem(DEFAULT_LOP)))
            .andExpect(jsonPath("$.[*].khoa").value(hasItem(DEFAULT_KHOA)))
            .andExpect(jsonPath("$.[*].khoaHoc").value(hasItem(DEFAULT_KHOA_HOC)))
            .andExpect(jsonPath("$.[*].heDaoTao").value(hasItem(DEFAULT_HE_DAO_TAO)))
            .andExpect(jsonPath("$.[*].coVanHocTap").value(hasItem(DEFAULT_CO_VAN_HOC_TAP)));
    }
}

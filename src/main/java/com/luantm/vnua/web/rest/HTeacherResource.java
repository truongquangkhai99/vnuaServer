package com.luantm.vnua.web.rest;

import com.luantm.vnua.domain.Student;
import com.luantm.vnua.domain.Teacher;
import com.luantm.vnua.repository.HTeacherRepository;
import com.luantm.vnua.repository.TeacherRepository;
import com.luantm.vnua.repository.search.TeacherSearchRepository;
import com.luantm.vnua.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing {@link com.luantm.vnua.domain.Teacher}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class HTeacherResource {

    private final Logger log = LoggerFactory.getLogger(TeacherResource.class);

    private static final String ENTITY_NAME = "teacher";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HTeacherRepository hteacherRepository;

    public HTeacherResource(HTeacherRepository hteacherRepository) {
        this.hteacherRepository = hteacherRepository;
    }

    @GetMapping("/search-teachers")
    public ResponseEntity<List<Teacher>> searchStudents(@RequestParam String query) throws URISyntaxException {
        log.debug("REST request to search a list of Teachers:" + query);
        List<Teacher> teachers = hteacherRepository.searchTeacher('%' + query + '%');
        return ResponseEntity.ok().body(teachers);
    }
}

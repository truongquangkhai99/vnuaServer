package com.luantm.vnua.web.rest;

import com.luantm.vnua.web.rest.dto.StudentDTO;
import com.luantm.vnua.repository.HStudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
@Transactional
public class HStudentResource {
    private final Logger log = LoggerFactory.getLogger(TeacherResource.class);

    private static final String ENTITY_NAME = "teacher";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntityManager em;

    private final HStudentRepository hStudentRepository;

    public HStudentResource(HStudentRepository hStudentRepository, EntityManager em) {
        this.hStudentRepository = hStudentRepository;
        this.em = em;
    }

    @GetMapping("/get-top-students")
    public ResponseEntity<List<StudentDTO>> searchStudents() throws URISyntaxException {
        log.debug("REST request to search a list of top students");

        List students = em.createNativeQuery("SELECT ms.student_id, s.fullname, s.sex, s.lop, s.birth_day, ms.diemtbtl_10 " +
            "FROM mean_score ms " +
            "JOIN student s ON ms.student_id = s.student_id " +
            "WHERE ms.type = 1 and ms.diemtbtl_10 is not null " +
            "ORDER BY diemtbtl_10 desc " +
            "LIMIT 100 ", "StudentDTOMapping").getResultList();

//        List studentDTOs = em.createNativeQuery("SELECT ms.student_id as studentID, s.fullname as fullname, s.sex as sex, s.lop as lop, s.birth_day as birth_day, ms.diemtbtl_10 as diemtbtl_10 " +
//            "FROM mean_score ms " +
//            "JOIN student s ON ms.student_id = s.student_id " +
//            "WHERE ms.type = 1 and ms.diemtbtl_10 is not null " +
//            "ORDER BY diemtbtl_10 desc " +
//            "LIMIT 100 ").getResultList();
//
//        List<StudentDTO> students = new ArrayList<>();
//        for (Object object: studentDTOs) {
//            StudentDTO dto = new StudentDTO();
////            dto.setStudentID(Integer.parseInt(object[0].toString()));
////            dto.setFullname(object[1].toString());
////            dto.setSex(object[2].toString());
////            dto.setLop(object[3].toString());
////            dto.setDiemtbtl10((float) object[4]);
//            students.add(dto);
//        }
        ResponseEntity<List<StudentDTO>> body = ResponseEntity.ok().body((List<StudentDTO>) students);
        return body;
    }
}

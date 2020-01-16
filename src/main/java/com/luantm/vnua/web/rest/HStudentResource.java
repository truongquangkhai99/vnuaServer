package com.luantm.vnua.web.rest;

import com.luantm.vnua.domain.Teacher;
import com.luantm.vnua.web.rest.dto.StudentDTO;
import com.luantm.vnua.repository.HStudentRepository;
import com.luantm.vnua.web.rest.dto.StudentRankDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


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
            "WHERE ms.type = 1 and ms.diemtbtl_10 is not null and ms.diemtbtl_10 < 10 " +
            "ORDER BY diemtbtl_10 desc " +
            "LIMIT 100 ", "StudentDTOMapping").getResultList();

        ResponseEntity<List<StudentDTO>> body = ResponseEntity.ok().body((List<StudentDTO>) students);
        return body;
    }

    @GetMapping("/public/classes-by-student-id/{id}")
    public ResponseEntity<List<StudentDTO>> getClassByStudentId(@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to search a list of top students");

        List students = em.createNativeQuery("SELECT ms.student_id, s.fullname, s.sex, s.lop, s.birth_day, ms.diemtbtl_10 " +
            "FROM mean_score ms " +
            "JOIN student s ON ms.student_id = s.student_id " +
            "WHERE ms.type = 1 and ms.diemtbtl_10 is not null and ms.diemtbtl_10 < 10 " +
                "AND s.lop = (SELECT lop from student where student_id = '" + id + "') " +
            "ORDER BY diemtbtl_10 desc ", "StudentDTOMapping").getResultList();

        ResponseEntity<List<StudentDTO>> body = ResponseEntity.ok().body((List<StudentDTO>) students);
        return body;
    }

    /**
     * vi tri hien tai theo lớp, theo khoa, theo khóa, theo trường
     * {@code GET  /student-rank/:id} : get the "id" teacher.
     *
     * @param id the id of the teacher to retrieve.
     */
    @GetMapping("/public/student-rank/{id}")
    public ResponseEntity<StudentRankDTO> getStudentRank(@PathVariable Long id) {
        List<Object[]> singleResult = em.createNativeQuery(
            "SELECT rankvnua_tbl.student_id, 0 as rank_class, 0 as rank_khoa, 0 as rank_khoa_hoc, rankvnua_tbl.rank_of_vnua as rank_vnua " +
                " FROM " +
                " (SELECT ms.student_id, diemtbtl_10, " +
                " RANK () OVER (  " +
                "   ORDER BY diemtbtl_10 DESC " +
                " ) rank_of_vnua " +
                " FROM mean_score ms " +
                " JOIN student s ON ms.student_id = s.student_id " +
                " WHERE type = 1 AND diemtbtl_10 IS NOT NULL AND diemtbtl_10 < 10 ) rankvnua_tbl " +
                "WHERE student_id = '" + id + "'").getResultList();

        if (singleResult.size() == 1) {
            StudentRankDTO result = new StudentRankDTO();
            Object[] objects = singleResult.get(0);

            result.setStudentID((int)objects[0]);
            result.setRankClass((int)objects[1]);
            result.setRankKhoa((int)objects[2]);
            result.setRankKhoaHoc((int)objects[3]);
            result.setRankVnua(Integer.parseInt(objects[4].toString()));

            Optional<StudentRankDTO> studentRankDTO = Optional.of(result);
            return ResponseUtil.wrapOrNotFound(studentRankDTO);
        }
        return ResponseUtil.wrapOrNotFound(null);
    }
}

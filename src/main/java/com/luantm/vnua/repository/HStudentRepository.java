package com.luantm.vnua.repository;

import com.luantm.vnua.domain.Student;
import com.luantm.vnua.web.rest.dto.StudentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import java.util.List;
import java.util.Optional;

@Repository
public interface HStudentRepository extends JpaRepository<Student, Long> {
    @Query(value = "SELECT ms.student_id, s.fullname, s.sex, s.lop, s.birth_day, ms.diemtbtl_10 " +
        "FROM mean_score ms " +
            "JOIN student s ON ms.student_id = s.student_id " +
        "WHERE ms.type = 1 and ms.diemtbtl_10 is not null " +
        "ORDER BY diemtbtl_10 desc " +
        "LIMIT 100 ",
        nativeQuery=true)
    List<StudentDTO> searchTopStudents();

    //TODO: get student rank dto
    @Query(value = "SELECT ms.student_id, s.fullname, s.sex, s.lop, s.birth_day, ms.diemtbtl_10 " +
        "FROM mean_score ms " +
        "JOIN student s ON ms.student_id = s.student_id " +
        "WHERE ms.type = 1 and ms.diemtbtl_10 is not null " +
        "ORDER BY diemtbtl_10 desc " +
        "LIMIT 100 ",
        nativeQuery=true)
    Optional<StudentDTO> getStudentRankDTO(Long id);
}

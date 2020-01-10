package com.luantm.vnua.repository;

import com.luantm.vnua.domain.Student;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Student entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query(value = "SELECT * FROM student s WHERE CAST(student_id as text) like ?1 OR s.fullname like ?1" +
        " OR s.birth_place like ?1" +
        " OR s.sex like ?1 OR s.chuyen_nganh like ?1" +
        " OR s.lop like ?1" +
        " OR s.khoa like ?1" +
        " OR s.khoa_hoc like ?1" +
        " OR s.he_dao_tao like ?1" +
        " OR s.co_van_hoc_tap like ?1" +
        " ORDER BY s.fullname ASC LIMIT 100",
        nativeQuery=true)
    List<Student> searchStudent(String query);
}

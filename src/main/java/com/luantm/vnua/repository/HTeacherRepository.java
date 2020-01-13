package com.luantm.vnua.repository;

import com.luantm.vnua.domain.Student;
import com.luantm.vnua.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Teacher entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HTeacherRepository extends JpaRepository<Teacher, Long> {
    @Query(value = "SELECT * FROM teacher t WHERE CAST(teacher_id as text) like ?1" +
        " OR t.fullname like ?1" +
        " ORDER BY t.fullname ASC LIMIT 100",
        nativeQuery=true)
    List<Teacher> searchTeacher(String query);
}

package com.luantm.vnua.web.rest.dto;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

public class StudentDTO implements Serializable {
    private Integer studentID;

    private String fullname;

    private String sex;

    private LocalDate birthDay;

    private String lop;

    private Float diemtbtl10;

    public StudentDTO(Integer studentID, String fullname, String sex, LocalDate birthDay, String lop, Float diemtbtl10) {
        this.studentID = studentID;
        this.fullname = fullname;
        this.sex = sex;
        this.birthDay = birthDay;
        this.lop = lop;
        this.diemtbtl10 = diemtbtl10;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public Float getDiemtbtl10() {
        return diemtbtl10;
    }

    public void setDiemtbtl10(Float diemtbtl10) {
        this.diemtbtl10 = diemtbtl10;
    }
}

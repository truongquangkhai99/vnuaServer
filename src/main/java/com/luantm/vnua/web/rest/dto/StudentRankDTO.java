package com.luantm.vnua.web.rest.dto;

import java.io.Serializable;

public class StudentRankDTO implements Serializable {
    private Integer studentID;

    private Integer rankClass;

    private Integer rankKhoa;

    private Integer rankKhoaHoc;

    private Integer rankVnua;

    public StudentRankDTO() {
    }

    public StudentRankDTO(Integer studentID, Integer rankClass, Integer rankKhoa, Integer rankKhoaHoc, Integer rankVnua) {
        this.studentID = studentID;
        this.rankClass = rankClass;
        this.rankKhoa = rankKhoa;
        this.rankKhoaHoc = rankKhoaHoc;
        this.rankVnua = rankVnua;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }


    public Integer getRankClass() {
        return rankClass;
    }

    public void setRankClass(Integer rankClass) {
        this.rankClass = rankClass;
    }

    public Integer getRankKhoa() {
        return rankKhoa;
    }

    public void setRankKhoa(Integer rankKhoa) {
        this.rankKhoa = rankKhoa;
    }

    public Integer getRankKhoaHoc() {
        return rankKhoaHoc;
    }

    public void setRankKhoaHoc(Integer rankKhoaHoc) {
        this.rankKhoaHoc = rankKhoaHoc;
    }

    public Integer getRankVnua() {
        return rankVnua;
    }

    public void setRankVnua(Integer rankVnua) {
        this.rankVnua = rankVnua;
    }
}

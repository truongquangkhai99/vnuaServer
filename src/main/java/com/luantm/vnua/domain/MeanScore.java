package com.luantm.vnua.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A MeanScore.
 */
@Entity
@Table(name = "mean_score")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "meanscore")
public class MeanScore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "diemtbhc_10")
    private Float diemtbhc10;

    @Column(name = "diemtbhc_4")
    private Float diemtbhc4;

    @Column(name = "diemtbtl_10")
    private Float diemtbtl10;

    @Column(name = "diemtbtl_4")
    private Float diemtbtl4;

    @Column(name = "sotinchidat")
    private Integer sotinchidat;

    @Column(name = "sotinchitichluy")
    private Integer sotinchitichluy;

    @Column(name = "phan_loai")
    private String phanLoai;

    @Column(name = "type")
    private Integer type;

    @Column(name = "student_id")
    private Integer studentId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getDiemtbhc10() {
        return diemtbhc10;
    }

    public MeanScore diemtbhc10(Float diemtbhc10) {
        this.diemtbhc10 = diemtbhc10;
        return this;
    }

    public void setDiemtbhc10(Float diemtbhc10) {
        this.diemtbhc10 = diemtbhc10;
    }

    public Float getDiemtbhc4() {
        return diemtbhc4;
    }

    public MeanScore diemtbhc4(Float diemtbhc4) {
        this.diemtbhc4 = diemtbhc4;
        return this;
    }

    public void setDiemtbhc4(Float diemtbhc4) {
        this.diemtbhc4 = diemtbhc4;
    }

    public Float getDiemtbtl10() {
        return diemtbtl10;
    }

    public MeanScore diemtbtl10(Float diemtbtl10) {
        this.diemtbtl10 = diemtbtl10;
        return this;
    }

    public void setDiemtbtl10(Float diemtbtl10) {
        this.diemtbtl10 = diemtbtl10;
    }

    public Float getDiemtbtl4() {
        return diemtbtl4;
    }

    public MeanScore diemtbtl4(Float diemtbtl4) {
        this.diemtbtl4 = diemtbtl4;
        return this;
    }

    public void setDiemtbtl4(Float diemtbtl4) {
        this.diemtbtl4 = diemtbtl4;
    }

    public Integer getSotinchidat() {
        return sotinchidat;
    }

    public MeanScore sotinchidat(Integer sotinchidat) {
        this.sotinchidat = sotinchidat;
        return this;
    }

    public void setSotinchidat(Integer sotinchidat) {
        this.sotinchidat = sotinchidat;
    }

    public Integer getSotinchitichluy() {
        return sotinchitichluy;
    }

    public MeanScore sotinchitichluy(Integer sotinchitichluy) {
        this.sotinchitichluy = sotinchitichluy;
        return this;
    }

    public void setSotinchitichluy(Integer sotinchitichluy) {
        this.sotinchitichluy = sotinchitichluy;
    }

    public String getPhanLoai() {
        return phanLoai;
    }

    public MeanScore phanLoai(String phanLoai) {
        this.phanLoai = phanLoai;
        return this;
    }

    public void setPhanLoai(String phanLoai) {
        this.phanLoai = phanLoai;
    }

    public Integer getType() {
        return type;
    }

    public MeanScore type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public MeanScore studentId(Integer studentId) {
        this.studentId = studentId;
        return this;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MeanScore)) {
            return false;
        }
        return id != null && id.equals(((MeanScore) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MeanScore{" +
            "id=" + getId() +
            ", diemtbhc10=" + getDiemtbhc10() +
            ", diemtbhc4=" + getDiemtbhc4() +
            ", diemtbtl10=" + getDiemtbtl10() +
            ", diemtbtl4=" + getDiemtbtl4() +
            ", sotinchidat=" + getSotinchidat() +
            ", sotinchitichluy=" + getSotinchitichluy() +
            ", phanLoai='" + getPhanLoai() + "'" +
            ", type=" + getType() +
            ", studentId=" + getStudentId() +
            "}";
    }
}

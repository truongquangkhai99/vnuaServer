package com.luantm.vnua.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Score.
 */
@Entity
@Table(name = "score")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "score")
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "ten_mon")
    private String tenMon;

    @Column(name = "ma_mon")
    private String maMon;

    @Column(name = "tc")
    private Integer tc;

    @Column(name = "kt_percent")
    private Integer ktPercent;

    @Column(name = "thi_percent")
    private Integer thiPercent;

    @Column(name = "diem_chuyen_can")
    private Integer diemChuyenCan;

    @Column(name = "diem_qua_trinh")
    private Float diemQuaTrinh;

    @Column(name = "thi_10")
    private Float thi10;

    @Column(name = "tk_110")
    private Float tk110;

    @Column(name = "tk_10")
    private Float tk10;

    @Column(name = "tk_1_ch")
    private String tk1ch;

    @Column(name = "tkch")
    private String tkch;

    @Column(name = "student_id")
    private Integer studentId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenMon() {
        return tenMon;
    }

    public Score tenMon(String tenMon) {
        this.tenMon = tenMon;
        return this;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getMaMon() {
        return maMon;
    }

    public Score maMon(String maMon) {
        this.maMon = maMon;
        return this;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public Integer getTc() {
        return tc;
    }

    public Score tc(Integer tc) {
        this.tc = tc;
        return this;
    }

    public void setTc(Integer tc) {
        this.tc = tc;
    }

    public Integer getKtPercent() {
        return ktPercent;
    }

    public Score ktPercent(Integer ktPercent) {
        this.ktPercent = ktPercent;
        return this;
    }

    public void setKtPercent(Integer ktPercent) {
        this.ktPercent = ktPercent;
    }

    public Integer getThiPercent() {
        return thiPercent;
    }

    public Score thiPercent(Integer thiPercent) {
        this.thiPercent = thiPercent;
        return this;
    }

    public void setThiPercent(Integer thiPercent) {
        this.thiPercent = thiPercent;
    }

    public Integer getDiemChuyenCan() {
        return diemChuyenCan;
    }

    public Score diemChuyenCan(Integer diemChuyenCan) {
        this.diemChuyenCan = diemChuyenCan;
        return this;
    }

    public void setDiemChuyenCan(Integer diemChuyenCan) {
        this.diemChuyenCan = diemChuyenCan;
    }

    public Float getDiemQuaTrinh() {
        return diemQuaTrinh;
    }

    public Score diemQuaTrinh(Float diemQuaTrinh) {
        this.diemQuaTrinh = diemQuaTrinh;
        return this;
    }

    public void setDiemQuaTrinh(Float diemQuaTrinh) {
        this.diemQuaTrinh = diemQuaTrinh;
    }

    public Float getThi10() {
        return thi10;
    }

    public Score thi10(Float thi10) {
        this.thi10 = thi10;
        return this;
    }

    public void setThi10(Float thi10) {
        this.thi10 = thi10;
    }

    public Float getTk110() {
        return tk110;
    }

    public Score tk110(Float tk110) {
        this.tk110 = tk110;
        return this;
    }

    public void setTk110(Float tk110) {
        this.tk110 = tk110;
    }

    public Float getTk10() {
        return tk10;
    }

    public Score tk10(Float tk10) {
        this.tk10 = tk10;
        return this;
    }

    public void setTk10(Float tk10) {
        this.tk10 = tk10;
    }

    public String getTk1ch() {
        return tk1ch;
    }

    public Score tk1ch(String tk1ch) {
        this.tk1ch = tk1ch;
        return this;
    }

    public void setTk1ch(String tk1ch) {
        this.tk1ch = tk1ch;
    }

    public String getTkch() {
        return tkch;
    }

    public Score tkch(String tkch) {
        this.tkch = tkch;
        return this;
    }

    public void setTkch(String tkch) {
        this.tkch = tkch;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public Score studentId(Integer studentId) {
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
        if (!(o instanceof Score)) {
            return false;
        }
        return id != null && id.equals(((Score) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Score{" +
            "id=" + getId() +
            ", tenMon='" + getTenMon() + "'" +
            ", maMon='" + getMaMon() + "'" +
            ", tc=" + getTc() +
            ", ktPercent=" + getKtPercent() +
            ", thiPercent=" + getThiPercent() +
            ", diemChuyenCan=" + getDiemChuyenCan() +
            ", diemQuaTrinh=" + getDiemQuaTrinh() +
            ", thi10=" + getThi10() +
            ", tk110=" + getTk110() +
            ", tk10=" + getTk10() +
            ", tk1ch='" + getTk1ch() + "'" +
            ", tkch='" + getTkch() + "'" +
            ", studentId=" + getStudentId() +
            "}";
    }
}

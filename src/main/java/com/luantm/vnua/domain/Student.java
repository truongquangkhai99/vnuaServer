package com.luantm.vnua.domain;
import com.luantm.vnua.web.rest.dto.StudentDTO;
import com.luantm.vnua.web.rest.dto.StudentRankDTO;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "student")
@SqlResultSetMapping(
    name = "StudentDTOMapping",
    classes = @ConstructorResult(
        targetClass = StudentDTO.class,
        columns = {
            @ColumnResult(name = "student_id"),
            @ColumnResult(name = "fullname"),
            @ColumnResult(name = "sex"),
            @ColumnResult(name = "birth_day", type=LocalDate.class),
            @ColumnResult(name = "lop"),
            @ColumnResult(name = "diemtbtl_10")
        }
    )
)

@SqlResultSetMapping(
    name = "StudentRankDTOMapping",
    classes = @ConstructorResult(
        targetClass = StudentRankDTO.class,
        columns = {
            @ColumnResult(name = "student_id"),
            @ColumnResult(name = "rank_class"),
            @ColumnResult(name = "rank_khoa"),
            @ColumnResult(name = "rank_khoa_hoc"),
            @ColumnResult(name = "rank_vnua")
        }
    )
)
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "student_id")
    private Integer studentID;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "sex")
    private String sex;

    @Column(name = "birth_day")
    private LocalDate birthDay;

    @Column(name = "birth_place")
    private String birthPlace;

    @Column(name = "chuyen_nganh")
    private String chuyenNganh;

    @Column(name = "lop")
    private String lop;

    @Column(name = "khoa")
    private String khoa;

    @Column(name = "khoa_hoc")
    private String khoaHoc;

    @Column(name = "he_dao_tao")
    private String heDaoTao;

    @Column(name = "co_van_hoc_tap")
    private String coVanHocTap;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public Student studentID(Integer studentID) {
        this.studentID = studentID;
        return this;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public String getFullname() {
        return fullname;
    }

    public Student fullname(String fullname) {
        this.fullname = fullname;
        return this;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getSex() {
        return sex;
    }

    public Student sex(String sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public Student birthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
        return this;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public Student birthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
        return this;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getChuyenNganh() {
        return chuyenNganh;
    }

    public Student chuyenNganh(String chuyenNganh) {
        this.chuyenNganh = chuyenNganh;
        return this;
    }

    public void setChuyenNganh(String chuyenNganh) {
        this.chuyenNganh = chuyenNganh;
    }

    public String getLop() {
        return lop;
    }

    public Student lop(String lop) {
        this.lop = lop;
        return this;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getKhoa() {
        return khoa;
    }

    public Student khoa(String khoa) {
        this.khoa = khoa;
        return this;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public String getKhoaHoc() {
        return khoaHoc;
    }

    public Student khoaHoc(String khoaHoc) {
        this.khoaHoc = khoaHoc;
        return this;
    }

    public void setKhoaHoc(String khoaHoc) {
        this.khoaHoc = khoaHoc;
    }

    public String getHeDaoTao() {
        return heDaoTao;
    }

    public Student heDaoTao(String heDaoTao) {
        this.heDaoTao = heDaoTao;
        return this;
    }

    public void setHeDaoTao(String heDaoTao) {
        this.heDaoTao = heDaoTao;
    }

    public String getCoVanHocTap() {
        return coVanHocTap;
    }

    public Student coVanHocTap(String coVanHocTap) {
        this.coVanHocTap = coVanHocTap;
        return this;
    }

    public void setCoVanHocTap(String coVanHocTap) {
        this.coVanHocTap = coVanHocTap;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        return id != null && id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", studentID=" + getStudentID() +
            ", fullname='" + getFullname() + "'" +
            ", sex='" + getSex() + "'" +
            ", birthDay='" + getBirthDay() + "'" +
            ", birthPlace='" + getBirthPlace() + "'" +
            ", chuyenNganh='" + getChuyenNganh() + "'" +
            ", lop='" + getLop() + "'" +
            ", khoa='" + getKhoa() + "'" +
            ", khoaHoc='" + getKhoaHoc() + "'" +
            ", heDaoTao='" + getHeDaoTao() + "'" +
            ", coVanHocTap='" + getCoVanHocTap() + "'" +
            "}";
    }
}

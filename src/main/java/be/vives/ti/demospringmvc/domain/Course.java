package be.vives.ti.demospringmvc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {

    @Id
    private String code;
    private String name;
    private Integer studypoints;

    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    private List<Student> students = new ArrayList<>();

    private Course() {} // jpa only

    public Course(String code, String name, Integer studypoints) {
        this.code = code;
        this.name = name;
        this.studypoints = studypoints;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStudypoints() {
        return studypoints;
    }

    public void setStudypoints(Integer studypoints) {
        this.studypoints = studypoints;
    }

    public List<Student> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + code + '\'' +
                ", name='" + name + '\'' +
                ", studypoints=" + studypoints +
                '}';
    }
}

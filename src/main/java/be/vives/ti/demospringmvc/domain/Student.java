package be.vives.ti.demospringmvc.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private LocalDate birthDate;
    private Boolean signedUp;
    private BigDecimal saldo;
    private Gender gender;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "student_courses",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"student_id", "course_id"})})
    private List<Course> courses = new ArrayList<>();

    private Student() {
    } // jpa only

    public Student(String firstName, LocalDate birthDate, Boolean signedUp, BigDecimal saldo, Gender gender) {
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.signedUp = signedUp;
        this.saldo = saldo;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getSignedUp() {
        return signedUp;
    }

    public void setSignedUp(Boolean signedUp) {
        this.signedUp = signedUp;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        this.getCourses().add(course);
        course.getStudents().add(this);
    }

    public void removeCourse(Course course) {
        this.getCourses().remove(course);
        course.getStudents().remove(this);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}



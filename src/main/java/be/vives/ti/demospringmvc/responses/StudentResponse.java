package be.vives.ti.demospringmvc.responses;

import be.vives.ti.demospringmvc.domain.Student;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StudentResponse {

    private Long id;
    private String firstName;
    private LocalDate birthDate;
    private String signedUp;
    private String gender;

    public StudentResponse(Student student) {
        this.id = student.getId();
        this.firstName = student.getFirstName();
        this.birthDate = student.getBirthDate();
        this.signedUp = student.getSignedUp() ? "OK" : "NOK";
        this.gender = student.getGender().name();
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getSignedUp() {
        return signedUp;
    }

    public String getGender() {
        return gender;
    }
}

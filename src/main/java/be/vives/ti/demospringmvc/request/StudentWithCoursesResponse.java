package be.vives.ti.demospringmvc.request;

import be.vives.ti.demospringmvc.domain.Course;
import be.vives.ti.demospringmvc.domain.Student;

import java.util.List;

public class StudentWithCoursesResponse extends StudentResponse {

    private List<Course> courses;

    public StudentWithCoursesResponse(Student student) {
        super(student);
        this.courses = student.getCourses();
    }

    public List<Course> getCourses() {
        return courses;
    }
}

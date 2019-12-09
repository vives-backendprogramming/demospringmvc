package be.vives.ti.demospringmvc.repository;

import be.vives.ti.demospringmvc.domain.Course;
import be.vives.ti.demospringmvc.domain.Gender;
import be.vives.ti.demospringmvc.domain.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void simpleCrud() {
        Student chris = studentRepository.save(new Student("Chris", LocalDate.of(1990, 5, 13), true, BigDecimal.ZERO, Gender.M));
        Long idChris = chris.getId();

        Student chrisFromDb = studentRepository.findById(idChris).get();
        assertThat(chrisFromDb.getFirstName()).isEqualTo("Chris");
        assertThat(chrisFromDb.getBirthDate()).isEqualTo(LocalDate.of(1990, 5, 13));
        assertThat(chrisFromDb.getSignedUp()).isEqualTo(true);
        assertThat(chrisFromDb.getSaldo()).isEqualTo(BigDecimal.ZERO);
        assertThat(chrisFromDb.getGender()).isEqualTo(Gender.M);
        assertThat(chrisFromDb.getCourses()).isEmpty();

        chrisFromDb.setSaldo(new BigDecimal(15.13));
        studentRepository.save(chrisFromDb);

        Student chrisFromDbV2 = studentRepository.findById(idChris).get();
        assertThat(chrisFromDbV2.getFirstName()).isEqualTo("Chris");
        assertThat(chrisFromDbV2.getSaldo()).isEqualTo(new BigDecimal(15.13));

        studentRepository.delete(chrisFromDbV2);
        assertThat(studentRepository.findById(idChris).orElse(null)).isNull();
        assertThat(studentRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    public void addCourseToStudent() {
        Student chris = studentRepository.save(new Student("Chris", LocalDate.of(1990, 5, 13), true, BigDecimal.ZERO, Gender.M));
        Long idChris = chris.getId();

        Course V3KF049 = courseRepository.save(new Course("V3KF049", "Spring Boot", 3));
        Course V3KF050 = courseRepository.save(new Course("V3KF050", "Spring Data JPA", 2));

        chris.addCourse(V3KF049);
        chris.addCourse(V3KF050);
        studentRepository.save(chris);

        Student chris2Courses = studentRepository.findById(idChris).get();
        assertThat(chris2Courses.getCourses()).isNotEmpty().hasSize(2).extracting("code").contains("V3KF049", "V3KF050");

        chris2Courses.removeCourse(V3KF050);
        studentRepository.save(chris2Courses);

        Student chris1Course = studentRepository.findById(idChris).get();
        assertThat(chris1Course.getCourses()).isNotEmpty().hasSize(1).extracting("code").contains("V3KF049");

    }

}
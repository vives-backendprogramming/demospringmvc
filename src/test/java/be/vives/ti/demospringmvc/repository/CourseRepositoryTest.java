package be.vives.ti.demospringmvc.repository;

import be.vives.ti.demospringmvc.domain.Course;
import be.vives.ti.demospringmvc.domain.Gender;
import be.vives.ti.demospringmvc.domain.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void simpleCrud() {
        Course course = new Course("V3KF049", "Spring Boot course", 3);
        courseRepository.save(course);

        Course springBoot = courseRepository.findById("V3KF049").get();
        assertThat(springBoot).isNotNull();
        assertThat(springBoot.getCode()).isEqualTo("V3KF049");
        assertThat(springBoot.getName()).isEqualTo("Spring Boot course");
        assertThat(springBoot.getStudypoints()).isEqualTo(3);

        springBoot.setName("Spring Boot Course!!");
        courseRepository.save(springBoot);

        Course springBootV2 = courseRepository.findById("V3KF049").get();
        assertThat(springBootV2.getCode()).isEqualTo("V3KF049");
        assertThat(springBootV2.getName()).isEqualTo("Spring Boot Course!!");

        assertThat(courseRepository.findAll().size()).isEqualTo(1);

        courseRepository.delete(course);
        assertThat(courseRepository.findById("V3KF049").orElse(null)).isNull();
        assertThat(courseRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    void findAllByStudypoints() {
        courseRepository.save(new Course("V3KF049", "Spring Boot", 3));
        courseRepository.save(new Course("V3KF050", "Spring Data JPA", 2));
        courseRepository.save(new Course("V3KF051", "Spring MVC", 3));
        courseRepository.save(new Course("V3KF052", "Spring Data Rest", 1));

        List<Course> allByStudypoints = courseRepository.findAllByStudypoints(3);
        assertThat(allByStudypoints).extracting("studypoints").containsOnly(3);
        assertThat(allByStudypoints.size()).isEqualTo(2);
    }

    @Test
    void findAllByStudentsId() {
        Student chris = studentRepository.save(new Student("Chris", LocalDate.of(1990, 5, 13), true, BigDecimal.ZERO, Gender.M));
        chris.addCourse(courseRepository.save(new Course("V3KF049", "Spring Boot", 3)));
        chris.addCourse(courseRepository.save(new Course("V3KF050", "Spring Data Jpa", 2)));
        courseRepository.save(new Course("V3KF051", "Spring MVC", 3));
        courseRepository.save(new Course("V3KF052", "Spring Data Rest", 1));

        List<Course> coursesOfChris = courseRepository.findAllByStudentsId(chris.getId());
        assertThat(coursesOfChris.size()).isEqualTo(2);
        assertThat(coursesOfChris).extracting("code").contains("V3KF049", "V3KF050");
    }
}
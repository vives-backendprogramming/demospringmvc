package be.vives.ti.demospringmvc.repository;

import be.vives.ti.demospringmvc.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, String> {

    List<Course> findAllByStudypoints(Integer studypoints);

    List<Course> findAllByStudentsId(Long studentId);

}

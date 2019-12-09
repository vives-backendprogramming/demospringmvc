package be.vives.ti.demospringmvc.repository;

import be.vives.ti.demospringmvc.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByFirstName(String lastName);
}

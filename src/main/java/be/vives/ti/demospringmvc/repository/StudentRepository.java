package be.vives.ti.demospringmvc.repository;

import be.vives.ti.demospringmvc.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {}

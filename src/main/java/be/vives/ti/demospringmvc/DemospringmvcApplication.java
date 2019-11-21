package be.vives.ti.demospringmvc;

import be.vives.ti.demospringmvc.domain.Course;
import be.vives.ti.demospringmvc.domain.Gender;
import be.vives.ti.demospringmvc.domain.Student;
import be.vives.ti.demospringmvc.repository.CourseRepository;
import be.vives.ti.demospringmvc.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class DemospringmvcApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemospringmvcApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(StudentRepository studentRepository, CourseRepository courseRepository){
		return args -> {
			LocalDate now = LocalDate.now();

			Course ios = courseRepository.save(new Course("V3R058", "Apps for iOS", 3));
			Course backend = courseRepository.save(new Course("V3R056","Backend programming", 4));
			Course ad2 =  courseRepository.save(new Course("V3R188", "Analysis & design 2", 3));
			Course gux =  courseRepository.save(new Course("V3R057","Gamification & user experience 2", 3));
			Course crossplatform =  courseRepository.save(new Course("V3R059", "Cross-Platform development", 4));
			Course computerLaw =  courseRepository.save(new Course("V3R049", "Computer Law", 3));
			Course trends =  courseRepository.save(new Course("V3R050", "Trends in B&IT", 2));


			Student student = new Student("Alex",
					now.minusYears(25).minusMonths(3).minusDays(18),
					true,
					BigDecimal.valueOf(120.5),
					Gender.M);
			student.addCourse(ios);
			student.addCourse(backend);
			student.addCourse(ad2);
			student.addCourse(gux);
			student.addCourse(crossplatform);
			student.addCourse(trends);
			student.addCourse(computerLaw);

			studentRepository.save(student);

			Student student2 = new Student("Sophie",
					now.minusYears(23).minusMonths(9).minusDays(14),
					false,
					BigDecimal.valueOf(0),
					Gender.V);
			student2.addCourse(crossplatform);
			student2.addCourse(computerLaw);
			student2.addCourse(trends);
			studentRepository.save(student2);

			Student student3 = new Student("Theo",
					now.minusYears(25).minusMonths(1).minusDays(30),
					true,
					BigDecimal.valueOf(0),
					Gender.M);
			studentRepository.save(student3);
		};
	}
}

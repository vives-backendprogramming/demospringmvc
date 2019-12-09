package be.vives.ti.demospringmvc.controller;

import be.vives.ti.demospringmvc.domain.Course;
import be.vives.ti.demospringmvc.domain.Gender;
import be.vives.ti.demospringmvc.domain.Student;
import be.vives.ti.demospringmvc.exceptions.ResourceNotFoundException;
import be.vives.ti.demospringmvc.repository.CourseRepository;
import be.vives.ti.demospringmvc.repository.StudentRepository;
import be.vives.ti.demospringmvc.request.CourseCodeRequest;
import be.vives.ti.demospringmvc.request.StudentRequest;
import be.vives.ti.demospringmvc.responses.StudentResponse;
import be.vives.ti.demospringmvc.responses.StudentWithCoursesResponse;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
@CrossOrigin("*")
public class StudentController {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentController(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public Page<StudentResponse> retrieveAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable).map(StudentResponse::new);
    }

    @GetMapping(params = "firstName")
    public List<StudentResponse> findAllStudentsWithFirstName(@RequestParam("firstName") String firstName) {
        return studentRepository.findAllByFirstName(firstName).stream().map(StudentResponse::new).collect(Collectors.toList());
    }

    @GetMapping("/{studentId}")
    public StudentResponse retrieveStudentById(@PathVariable(name = "studentId") Long studentId) {
        Optional<Student> optStudent = studentRepository.findById(studentId);
        Student student = optStudent.orElseThrow(() -> new ResourceNotFoundException(studentId.toString()));
        return new StudentResponse(student);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createStudent(@RequestBody @Valid StudentRequest studentRequest){
        Student student = new Student(
                studentRequest.getFirstName(),
                studentRequest.getBirthDate(),
                studentRequest.getSignedUp(),
                studentRequest.getSaldo(),
                Gender.valueOf(studentRequest.getGender())
        );
        Student s = studentRepository.save(student);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(s.getId())
                .toUri();

        //Send location in response
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{studentId}")
    public StudentResponse putStudent(@PathVariable(name = "studentId") String studentId,
                          @RequestBody @Valid StudentRequest studentRequest) {
        Student s = studentRepository.findById(Long.parseLong(studentId)).orElseThrow(() -> new ResourceNotFoundException(studentId));

        s.setFirstName(studentRequest.getFirstName());
        s.setBirthDate(studentRequest.getBirthDate());
        s.setGender(Gender.valueOf(studentRequest.getGender()));
        s.setSaldo(studentRequest.getSaldo());
        s.setSignedUp(studentRequest.getSignedUp());

        return new StudentResponse(studentRepository.save(s));
    }

    @PatchMapping("/{studentId}")
    public StudentResponse patchStudent(@PathVariable("studentId") Long studentId,
                            @RequestBody StudentRequest studentRequest) {

        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException(studentId.toString()));;
        if (studentRequest.getFirstName() != null) {
            student.setFirstName(studentRequest.getFirstName());
        }
        if (studentRequest.getBirthDate() != null) {
            student.setBirthDate(studentRequest.getBirthDate());
        }
        if (studentRequest.getGender() != null) {
            student.setGender(Gender.valueOf(studentRequest.getGender()));
        }
        if (studentRequest.getSaldo() != null) {
            student.setSaldo(studentRequest.getSaldo());
        }
        if (studentRequest.getSignedUp() != null) {
            student.setSignedUp(studentRequest.getSignedUp());
        }
        return new StudentResponse(studentRepository.save(student));
    }

    @DeleteMapping("/{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        try {
            studentRepository.deleteById(studentId);
        } catch (EmptyResultDataAccessException e) {
           /*
            If the student exists when that method is called, it will be deleted.
            If the student doesn’t exist, an EmptyResultDataAccessException will be thrown.
            I’ve chosen to catch the EmptyResultDataAccessException and do nothing with it.
            My thinking here is that if you try to delete a resource that doesn’t exist, the outcome is the same as if it did exist prior to deletion.
            That is, the resource will be nonexistent. Whether it existed before or not is irrelevant.
            Alternatively, I could’ve written deleteStudent() to return a ResponseEntity, setting the body to null and the HTTP status code to NOT FOUND.
            */
        }
    }

    @GetMapping("/{studentId}/courses")
    public StudentResponse retrieveStudentWithCourses(@PathVariable(name = "studentId") Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException(studentId.toString()));
        return new StudentWithCoursesResponse(student);
    }

    @PostMapping("/{studentId}/courses")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCourseToStudent(@PathVariable(name = "studentId") Long studentId, @RequestBody @Valid CourseCodeRequest courseCode){
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException(studentId.toString(), "student"));
        Course course = courseRepository.findById(courseCode.getCode()).orElseThrow(() -> new ResourceNotFoundException(courseCode.getCode(), "course"));
        student.addCourse(course);
        studentRepository.save(student);
    }

    @DeleteMapping("/{studentId}/courses/{courseCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourseOfStudent(@PathVariable(name = "studentId") Long studentId,
                                      @PathVariable(name = "courseCode") String courseCode) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException(studentId.toString(), "student"));
        Course course = courseRepository.findById(courseCode).orElseThrow(() -> new ResourceNotFoundException(courseCode, "course"));
        student.removeCourse(course);
        studentRepository.save(student);
    }

}


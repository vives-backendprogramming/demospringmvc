package be.vives.ti.demospringmvc.controller;

import be.vives.ti.demospringmvc.domain.Course;
import be.vives.ti.demospringmvc.exceptions.ResourceNotFoundException;
import be.vives.ti.demospringmvc.repository.CourseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@CrossOrigin("*")
public class CourseController {

    private final CourseRepository courseRepository;


    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<Course> findAllCourses(@RequestParam(name = "points", required = false) Integer studypoints){
        if(studypoints == null){
            return courseRepository.findAll();
        }
        return courseRepository.findAllByStudypoints(studypoints);
    }

    @GetMapping("/{code}")
    public Course findAllCoursesOfAStudent(@PathVariable("code") String code){
        return courseRepository.findById(code).orElseThrow(() -> new ResourceNotFoundException(code));
    }

    @GetMapping("/student/{studentId}")
    public List<Course> findAllCoursesOfAStudent(@PathVariable("studentId") Long studentId){
        return courseRepository.findAllByStudentsId(studentId);
    }
}

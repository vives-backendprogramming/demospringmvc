package be.vives.ti.demospringmvc.controller;

import be.vives.ti.demospringmvc.domain.Course;
import be.vives.ti.demospringmvc.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CourseRepository courseRepository;

    @Test
    void findAllCourses() throws Exception {

        List<Course> courses = new ArrayList<>();
        courses.add(new Course("V3KF049", "Spring Boot", 3));
        courses.add(new Course("V3KF050", "Spring Data JPA", 2));
        courses.add(new Course("V3KF051", "Spring MVC", 3));
        courses.add(new Course("V3KF052", "Spring Data Rest", 1));
        when(courseRepository.findAll()).thenReturn(courses);

        mvc.perform(get("/courses")).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].code", equalTo("V3KF049")))
                .andExpect(jsonPath("$[1].code", equalTo("V3KF050")))
                .andExpect(jsonPath("$[2].code", equalTo("V3KF051")))
                .andExpect(jsonPath("$[3].code", equalTo("V3KF052")));
    }

    @Test
    void findAllCoursesWithStudyPoints() throws Exception {

        List<Course> courses = new ArrayList<>();
        courses.add(new Course("V3KF049", "Spring Boot", 3));
        courses.add(new Course("V3KF051", "Spring MVC", 3));
        when(courseRepository.findAllByStudypoints(3)).thenReturn(courses);

        mvc.perform(get("/courses")
                .param("points", "3"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].code", equalTo("V3KF049")))
                .andExpect(jsonPath("$[1].code", equalTo("V3KF051")))
                .andExpect(jsonPath("$[0].studypoints", equalTo(3)))
                .andExpect(jsonPath("$[1].studypoints", equalTo(3)));

    }

    @Test
    void testFindCourseNonExistingCode() throws Exception {
        mvc.perform(get("/courses/NONEXISTING"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }
}



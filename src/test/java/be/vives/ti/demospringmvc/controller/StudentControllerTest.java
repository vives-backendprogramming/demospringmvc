package be.vives.ti.demospringmvc.controller;

import be.vives.ti.demospringmvc.domain.Gender;
import be.vives.ti.demospringmvc.domain.Student;
import be.vives.ti.demospringmvc.repository.StudentRepository;
import be.vives.ti.demospringmvc.request.StudentRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentRepository studentRepository;

    @Test
    void createStudent() throws Exception {
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setFirstName("Mats");
        studentRequest.setBirthDate(LocalDate.of(1990, 7, 22));
        studentRequest.setGender("M");
        studentRequest.setSaldo(BigDecimal.ZERO);
        studentRequest.setSignedUp(true);

        Student s = new Student("Mats", LocalDate.of(1990, 7, 22), true, BigDecimal.ZERO, Gender.M);
        s.setId(9L);
        when(studentRepository.save(any(Student.class))).thenReturn(s);

        mockMvc.perform(post("/students")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(studentRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "http://localhost/students/9"));
    }

    @Test
    void createStudent_validationErrors() throws Exception {
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setFirstName("Mats");
        studentRequest.setBirthDate(LocalDate.of(2090, 7, 22));

        mockMvc.perform(post("/students")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(studentRequest)))
                .andExpect(status().isBadRequest());
    }
}
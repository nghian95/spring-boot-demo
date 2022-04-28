package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping()
    public void registerNewStudent(String name, String dob, String email) {
        String[] dobArray = dob.split("-");
        int year = Integer.parseInt(dobArray[0]);
        int month = Integer.parseInt(dobArray[1]);
        int day = Integer.parseInt(dobArray[2]);
        LocalDate localDateDOB = LocalDate.of(year, month, day);
        studentService.addNewStudent(new Student(name, localDateDOB, email));
    }
}

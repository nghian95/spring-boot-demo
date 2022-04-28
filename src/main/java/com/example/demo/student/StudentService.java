package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new IllegalStateException("student with id" + studentId + " does not exist");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Student student){
        Long studentId = student.getId();
        Student dbStudent = studentRepository.findById(studentId).get();
        if (dbStudent == null) {
            throw new IllegalStateException("student with id" + studentId + "does not exist");
        }
        if (student.getName() != null && student.getName() != "") {
            dbStudent.setName(student.getName());
        }
        if (student.getDob() != null) {
            dbStudent.setDob(student.getDob());
        }
        if (student.getEmail() != null && student.getEmail() != "") {
            dbStudent.setEmail(student.getEmail());
        }
        studentRepository.save(dbStudent);
    }
}

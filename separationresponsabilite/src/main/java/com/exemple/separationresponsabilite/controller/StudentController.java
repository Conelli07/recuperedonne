package com.exemple.separationresponsabilite.controller;

import com.exemple.separationresponsabilite.exception.BadRequestException;
import com.exemple.separationresponsabilite.model.Student;
import com.exemple.separationresponsabilite.service.StudentService;
import com.exemple.separationresponsabilite.validator.StudentValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    private final StudentValidator studentValidator;
    private final StudentService studentService;

    public StudentController(StudentValidator studentValidator, StudentService studentService) {
        this.studentValidator = studentValidator;
        this.studentService = studentService;
    }

    @PostMapping("/students")
    public ResponseEntity<?> createStudents(@RequestBody List<Student> newStudents) {
        try {
            studentValidator.validate(newStudents);
            List<Student> result = studentService.addStudents(newStudents);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .header("Content-Type", "application/json")
                    .body(result);
        } catch (BadRequestException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "text/plain")
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur interne du serveur.");
        }
    }

    @GetMapping("/students")
    public ResponseEntity<?> getStudents() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(studentService.getStudents());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur interne du serveur.");
        }
    }
}

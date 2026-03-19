package com.exemple.recuperedonne.controller;

import com.exemple.recuperedonne.model.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TD2Controller {

    private final List<Student> studentStorage = new ArrayList<>();

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(@RequestParam String name) {
        return ResponseEntity.ok("Welcome " + name);
    }

    @PostMapping("/students")
    public ResponseEntity<String> addStudents(@RequestBody List<Student> newStudents) {
        studentStorage.addAll(newStudents);

        String names = studentStorage.stream()
                .map(s -> s.getFirstName() + " " + s.getLastName())
                .collect(Collectors.joining(", "));

        return ResponseEntity.ok(names);
    }

    @GetMapping("/students")
    public ResponseEntity<String> getStudents(
            @RequestHeader(value = "Accept", defaultValue = "text/plain") String acceptHeader) {

        if (!acceptHeader.equals("text/plain")) {
            return ResponseEntity
                    .status(415)
                    .body("Format non supporte.");
        }

        String names = studentStorage.stream()
                .map(s -> s.getFirstName() + " " + s.getLastName())
                .collect(Collectors.joining("\n"));

        return ResponseEntity.ok()
                .header("Content-Type", "text/plain")
                .body(names);
    }
}

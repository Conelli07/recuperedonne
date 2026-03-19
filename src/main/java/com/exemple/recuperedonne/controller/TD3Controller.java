package com.exemple.recuperedonne.controller;

import com.exemple.recuperedonne.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TD3Controller {

    private final List<Student> studentStorage = new ArrayList<>();

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(@RequestParam(required = false) String name) {
        try {
            if (name == null || name.isBlank()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Parametre 'name' manquant ou invalide.");
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Welcome " + name);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur interne du serveur.");
        }
    }

    @PostMapping("/students")
    public ResponseEntity<List<Student>> addStudents(@RequestBody List<Student> newStudents) {
        try {
            studentStorage.addAll(newStudents);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(studentStorage);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @GetMapping(value = "/students", produces = "*/*")
    public ResponseEntity<?> getStudents(
            @RequestHeader(value = "Accept", required = false) String acceptHeader) {
        try {
            if (acceptHeader == null || acceptHeader.isBlank()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Entete 'Accept' manquante.");
            }
            if (!acceptHeader.equals("text/plain") && !acceptHeader.equals("application/json")) {
                return ResponseEntity
                        .status(HttpStatus.NOT_IMPLEMENTED)
                        .body("Format non supporte.");
            }
            if (acceptHeader.equals("text/plain")) {
                String names = studentStorage.stream()
                        .map(s -> s.getFirstName() + " " + s.getLastName())
                        .collect(Collectors.joining("\n"));
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .header("Content-Type", "text/plain")
                        .body(names);
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(studentStorage);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur interne du serveur.");
        }
    }
}
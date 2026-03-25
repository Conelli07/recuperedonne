package com.exemple.separationresponsabilite.service;

import com.exemple.separationresponsabilite.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final List<Student> studentsInMemory = new ArrayList<>();

    public List<Student> addStudents(List<Student> newStudents) {
        studentsInMemory.addAll(newStudents);
        return studentsInMemory;
    }

    public List<Student> getStudents() {
        return studentsInMemory;
    }
}

package com.java.security.service;

import com.java.security.domin.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    List<Student> students = new ArrayList<>(List.of(new Student(1, "sathya", 100),
            new Student(2, "Arun", 99)));

    public List<Student> getStudents() {
        return students;
    }

    public Student addStudent(Student student) {
        students.add(student);
        return student;
    }


}

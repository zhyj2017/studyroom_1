package com.example.studyroom.service;

import com.example.studyroom.dao.StudentDAO;
import com.example.studyroom.dao.StudentDAOImpl;
import com.example.studyroom.pojo.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {
    public StudentDAOImpl studentDAO = new StudentDAOImpl();
    @Test
    void loginCheck() {
        Student student = studentDAO.getStudentById("221701201");
        System.out.println(student.getPassword());
    }
}
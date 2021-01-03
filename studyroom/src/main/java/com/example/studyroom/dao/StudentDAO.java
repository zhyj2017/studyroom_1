package com.example.studyroom.dao;

import com.example.studyroom.pojo.Student;

import java.util.List;

public interface StudentDAO {
    Student getStudentById(String id);
    Student getStudentByCellphone(String cellphone);
    void addStudent(Student bean);
    void updateStudent(Student bean);
    List<Student> find(Student bean);
    List<Student> find(Student bean, int start, int count);
    List<Student> list(int start, int count);
    int getTotal();
    int getFindTotal(Student bean);
    void delete(String id);
    void updatePassword(String id);
    void updateStudents(Student bean);
}

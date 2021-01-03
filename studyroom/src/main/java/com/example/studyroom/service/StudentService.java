package com.example.studyroom.service;


import com.example.studyroom.pojo.Student;
import com.example.studyroom.util.Page;

import java.util.List;

public interface StudentService {
    boolean loginCheck(String id , String password);
    boolean searchCheck(String id, String name);
    int registCheck(String id , String cellphone);
    void addStudent(Student bean);
    Student getStudentById(String id);
    void updateStudent(Student bean);
    List<Student> find(Student bean);
    List<Student> listByPage(Page page);
    List<Student> findByPage(Student student,Page page);
    int total();
    int totalFind(Student bean);
    void delete(String id);
    void reset(String id);
    void updateStudents(Student bean);
    List<Student> preservationSearch(String uid);  //防疫溯源查询
}

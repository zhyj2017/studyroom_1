package com.example.studyroom.mapper;

import com.example.studyroom.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper {
    @Select({"<script>",
            "select * from student",
            "where 1 = 1",

            "</script>"})
    List<Student> find(Student student);
}

package com.example.studyroom.service;

import com.example.studyroom.dao.CreditDAO;
import com.example.studyroom.dao.ReservationDAO;
import com.example.studyroom.dao.StudentDAO;
import com.example.studyroom.mapper.StudentMapper;
import com.example.studyroom.pojo.Reservation;
import com.example.studyroom.pojo.Student;
import com.example.studyroom.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private CreditDAO creditDAO;
    @Autowired
    private ReservationDAO reservationDAO;
//    @Autowired
//    StudentMapper studentMapper;
    public boolean loginCheck(String id , String password){
        Student student = studentDAO.getStudentById(id);
        if (student == null){
            return false;
        } else if (!id.equals(student.getId())||!password.equals(student.getPassword())) {
            return false;
        }
        return true;

    }

    public boolean searchCheck(String id, String name){
        Student student = studentDAO.getStudentById(id);
        if (student == null){
            return false;
        } else if (!id.equals(student.getId())||!name.equals(student.getName())) {
            return false;
        }
        return true;
    }


    public int registCheck(String id, String cellphone){
        Student student1 = studentDAO.getStudentById(id);
        Student student2 = studentDAO.getStudentByCellphone(cellphone);
        if (student1 != null){
            return -1;
        } else if (student1 == null && student2 != null){
            return 0;
        }
        return 1;
    }

    public void addStudent(Student bean){
        studentDAO.addStudent(bean);
    }

    public Student getStudentById(String id){
        Student student = studentDAO.getStudentById(id);
        return student;
    }

    public void updateStudent(Student bean){
        studentDAO.updateStudent(bean);
    }

    public List<Student> find(Student bean){
        List<Student> students = studentDAO.find(bean);
//        List<Student> students = studentMapper.find(bean);
        return students;
    }

    public List<Student> listByPage(Page page){
        List<Student> students = studentDAO.list(page.getStart(),page.getCount());
        return students;
    }

    public List<Student> findByPage(Student student,Page page){
        List<Student> students = studentDAO.find(student,page.getStart(),page.getCount());
        return students;
    }

    public int total(){
        int total=0;
        total=(int) studentDAO.getTotal();
        return total;
    }

    public int totalFind(Student bean){
        int total=0;
        total=(int) studentDAO.getFindTotal(bean);
        return total;
    }

    public void delete(String id){
        studentDAO.delete(id);
        creditDAO.delete(id);
    }

    public void reset(String id){
        studentDAO.updatePassword(id);
    }

    public void updateStudents(Student bean){
        studentDAO.updateStudents(bean);
    }

    public List<Student> preservationSearch(String uid){
        List<Student> students = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE,-30);
        Date d = c.getTime();
        String nowDate = format.format(date);  //现在的日期
        String pastDate = format.format(d);    //30天前的日期
        List<Reservation> reservations1 = reservationDAO.findByUidAndDate(pastDate,nowDate,uid); //指定学生在该时间段的所有签到/完成/迟到记录
        List<String> reservations2 = new ArrayList<>(); //存放其他学生的预约记录的学号
        for (int i=0;i<reservations1.size();i++){
            reservations2.addAll(reservationDAO.findUidByDateAndTime(reservations1.get(i)));
        }
        List<String> uids = new ArrayList<>();
        for (int i=0;i<reservations2.size();i++){       //删除重复学生学号
            if (!uids.contains(reservations2.get(i))){
                uids.add(reservations2.get(i));
            }
        }
        Collections.sort(uids);   //排序
        for (int i=0;i<uids.size();i++){
            students.add(studentDAO.getStudentById(uids.get(i)));
        }
        return students;
    }

}

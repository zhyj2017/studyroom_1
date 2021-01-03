package com.example.studyroom.dao;

import com.example.studyroom.pojo.Student;
import com.example.studyroom.util.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentDAOImpl implements StudentDAO {
    //    @Autowired
//    private JdbcTemplate jdbcTemplate;
    public Student getStudentById(String id) {
        Student bean = null;
//        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "select * from student where id = " + id;
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                bean = new Student();
                String userid = rs.getString("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String cellphone = rs.getString("cellphone");
                String dormitory = rs.getString("dormitory");
                bean.setId(userid);
                bean.setName(name);
                bean.setPassword(password);
                bean.setCellphone(cellphone);
                bean.setDormitory(dormitory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public Student getStudentByCellphone(String cellphone) {
        Student bean = null;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "select * from student where cellphone = " + cellphone;
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                bean = new Student();
                String userid = rs.getString("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String phone = rs.getString("cellphone");
                String dormitory = rs.getString("dormitory");
                bean.setId(userid);
                bean.setName(name);
                bean.setPassword(password);
                bean.setCellphone(phone);
                bean.setDormitory(dormitory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public void addStudent(Student bean) {
        String sql = "insert into student values(?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, bean.getId());
            ps.setString(2, bean.getName());
            ps.setString(3, bean.getPassword());
            ps.setString(4, bean.getCellphone());
            ps.setString(5, bean.getDormitory());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStudent(Student bean) {
        String sql = "update student set name = ?, password = ?, cellphone = ?, dormitory = ? where id = ? ";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, bean.getName());
            ps.setString(2, bean.getPassword());
            ps.setString(3, bean.getCellphone());
            ps.setString(4, bean.getDormitory());
            ps.setString(5, bean.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> find(Student bean){  //找到符合条件的数据
        List<Student> students = new ArrayList<>();
        String sql = "select * from student where 1 = 1";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            if (bean != null){
                if (bean.getId()!=null){
                    sql += "and id =" + bean.getId();
                }
                if (bean.getName()!=null){
                    sql += "and name = '" + bean.getName()+"'";
                }
                if (bean.getCellphone()!=null){
                    sql += "and cellphone = '" + bean.getCellphone()+"'";
                }
                if (bean.getDormitory()!=null){
                    sql += "and id = '" + bean.getDormitory()+"'";
                }
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Student student = new Student();
                String id = rs.getString("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String cellphone = rs.getString("cellphone");
                String dormitory = rs.getString("dormitory");
                student.setId(id);
                student.setName(name);
                student.setPassword(password);
                student.setCellphone(cellphone);
                student.setDormitory(dormitory);
                students.add(student);
            }
        }catch (SQLException e) {

            e.printStackTrace();
        }
        return students;
    }

    public List<Student> find(Student bean, int start, int count){
        List<Student> students = new ArrayList<>();
        String sql = "select * from student where 1 = 1";
        if (bean.getId()!=null && bean.getId()!=""){
            sql += " and id like '%" + bean.getId() +"%'";
        }
        if (bean.getName()!=null && bean.getName()!=""){
            sql += " and name like '%" + bean.getName()+"%'";
        }
        if (bean.getCellphone()!=null && bean.getCellphone()!=""){
            sql += " and cellphone like '%" + bean.getCellphone()+"%'";
        }
        if (bean.getDormitory()!=null && bean.getDormitory()!=""){
            sql += " and dormitory like '%" + bean.getDormitory()+"%'";
        }
        sql += " limit ? , ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Student student = new Student();
                String id = rs.getString("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String cellphone = rs.getString("cellphone");
                String dormitory = rs.getString("dormitory");
                student.setId(id);
                student.setName(name);
                student.setPassword(password);
                student.setCellphone(cellphone);
                student.setDormitory(dormitory);
                students.add(student);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public List<Student> list(int start, int count){
        List<Student> students = new ArrayList<>();
        String sql = "select * from student limit ? , ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Student student = new Student();
                String id = rs.getString("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String cellphone = rs.getString("cellphone");
                String dormitory = rs.getString("dormitory");
                student.setId(id);
                student.setName(name);
                student.setPassword(password);
                student.setCellphone(cellphone);
                student.setDormitory(dormitory);
                students.add(student);
            }
        }catch (SQLException e) {

            e.printStackTrace();
        }
        return students;
    }

    public int getTotal(){
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql=null;
            sql = "select count(*) from student where 1=1 " ;
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return total;
    }

    public int getFindTotal(Student bean){
        int total = 0;
        String sql = "select count(*) from student where 1 = 1";
        if (bean.getId()!=null && bean.getId()!=""){
            sql += " and id like '%" + bean.getId() +"%'";
        }
        if (bean.getName()!=null && bean.getName()!=""){
            sql += " and name like '%" + bean.getName()+"%'";
        }
        if (bean.getCellphone()!=null && bean.getCellphone()!=""){
            sql += " and cellphone like '%" + bean.getCellphone()+"%'";
        }
        if (bean.getDormitory()!=null && bean.getDormitory()!=""){
            sql += " and dormitory like '%" + bean.getDormitory()+"%'";
        }
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void delete(String id){
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "delete from student where id = '" + id +"'";
            s.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePassword(String id){
        String sql = "update student set password = 666666 where id = ? ";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStudents(Student bean){
        String sql = "update student set name = ?,  cellphone = ?, dormitory = ? where id = ? ";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, bean.getName());
            ps.setString(2, bean.getCellphone());
            ps.setString(3, bean.getDormitory());
            ps.setString(4, bean.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


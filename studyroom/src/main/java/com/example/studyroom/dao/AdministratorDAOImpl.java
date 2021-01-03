package com.example.studyroom.dao;

import com.example.studyroom.pojo.Administrator;
import com.example.studyroom.util.DBUtil;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class AdministratorDAOImpl implements AdministratorDAO {
    public Administrator getAdministratorById(String id){
        Administrator bean = null;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "select * from administrator where id = '" + id +"'";
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                bean = new Administrator();
                String adminid = rs.getString("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                bean.setId(adminid);
                bean.setName(name);
                bean.setPassword(password);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }
}

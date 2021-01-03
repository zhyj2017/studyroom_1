package com.example.studyroom.dao;

import com.example.studyroom.pojo.Opening;
import com.example.studyroom.util.DBUtil;
import org.springframework.stereotype.Repository;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class OpeningDAOImpl implements OpeningDAO{
    public Opening get(){
        Opening bean = null;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "select * from opening where id = 1";
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                bean = new Opening();
                int id = rs.getInt("id");
                int open = rs.getInt("open");
                int close = rs.getInt("close");
                bean.setId(id);
                bean.setOpen(open);
                bean.setClose(close);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public void update(Opening opening){
        String sql = "update opening set open = "+opening.getOpen()+", close = "+opening.getClose()+" where id = 1";
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package com.example.studyroom.dao;

import com.example.studyroom.pojo.Warning;
import com.example.studyroom.util.DBUtil;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class WarningDAOImpl implements WarningDAO {

    public Warning getWarningByUId(String uid){
        Warning bean = null;
//        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "select * from warning where uid = " + uid;
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                bean = new Warning();
                int id = rs.getInt("id");
                Date datetime = rs.getDate("datetime");
                bean.setId(id);
                bean.setUid(uid);
                bean.setDatetime(datetime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public void addWarning(Warning warning){
        String sql = "insert into warning values(null,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1,warning.getUid());
            ps.setDate(2,new java.sql.Date(warning.getDatetime().getTime()));
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                warning.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteWarning(Warning warning){
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "delete from warning where uid = " + warning.getUid();
            s.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

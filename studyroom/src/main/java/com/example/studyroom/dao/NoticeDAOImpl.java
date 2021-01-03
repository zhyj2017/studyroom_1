package com.example.studyroom.dao;

import com.example.studyroom.pojo.Notice;
import com.example.studyroom.util.DBUtil;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NoticeDAOImpl implements NoticeDAO {

    public int getTotal(){
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = null;
            sql = "select count(*) from notice where 1=1 ";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return total;
    }

    public List<Notice> list(int start, int count){
        List<Notice> notices = new ArrayList<>();
        String sql = "select * from notice order by id desc limit ? , ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Notice notice = new Notice();
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String text = rs.getString("text");
                Date date = rs.getDate("date");
                notice.setId(id);
                notice.setTitle(title);
                notice.setText(text);
                notice.setDate(date);
                notices.add(notice);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return notices;
    }

    public void addNotice(Notice notice){
        String sql = "insert into notice values(null,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1,notice.getTitle());
            ps.setString(2,notice.getText());
            ps.setDate(3,new java.sql.Date(notice.getDate().getTime()));
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                notice.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Notice getNoticeById(int id){
        Notice bean = null;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "select * from notice where id = " + id;
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                bean = new Notice();
                String title = rs.getString("title");
                String text = rs.getString("text");
                Date date = rs.getDate("date");
                bean.setId(id);
                bean.setTitle(title);
                bean.setText(text);
                bean.setDate(date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public void updateNotice(Notice notice){
        String sql = "update notice set title = ?, text = ? where id = ? ";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, notice.getTitle());
            ps.setString(2, notice.getText());
            ps.setInt(3, notice.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id){
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "delete from notice where id = " + id;
            s.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Notice> find(Notice bean,int start, int count){
        List<Notice> notices = new ArrayList<>();
        String sql = "select * from notice where 1 = 1";
        if (bean.getTitle()!=null){
            sql += " and title like '%" + bean.getTitle() +"%'";
        }
        if (bean.getDate()!=null){
            sql += " and date = '" + bean.getDate()+"'";
        }
        sql += "order by id desc limit ? , ? ";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Notice notice = new Notice();
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String text = rs.getString("text");
                Date date = rs.getDate("date");
                notice.setId(id);
                notice.setTitle(title);
                notice.setText(text);
                notice.setDate(date);
                notices.add(notice);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return notices;
    }

    public int getFindTotal(Notice bean){
        int total = 0;
        String sql = "select count(*) from notice where 1 = 1";
        if (bean.getTitle()!=null){
            sql += " and title like '%" + bean.getTitle() +"%'";
        }
        if (bean.getDate()!=null){
            sql += " and date = '" + bean.getDate()+"'";
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
}

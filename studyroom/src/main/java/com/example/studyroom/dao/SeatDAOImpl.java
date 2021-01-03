package com.example.studyroom.dao;

import com.example.studyroom.pojo.Seat;
import com.example.studyroom.util.DBUtil;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class SeatDAOImpl implements SeatDAO {
    public List<Integer> getCloseSeatID() {
        List<Integer> seats = new ArrayList<>();
        String sql = "select * from seat where status = 0";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                seats.add(id);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return seats;
    }

    public int getTotal() {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = null;
            sql = "select count(*) from seat where 1=1 ";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return total;
    }

    public int getTotalClosed() {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = null;
            sql = "select count(*) from seat where status = 0 ";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return total;
    }

    public List<Seat> list(int start, int count) {
        List<Seat> seats = new ArrayList<>();
        String sql = "select * from seat limit ? , ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Seat seat = new Seat();
                int id = rs.getInt("id");
                int status = rs.getInt("status");
                String address = rs.getString("address");
                seat.setId(id);
                seat.setStatus(status);
                seat.setAddress(address);
                seats.add(seat);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return seats;
    }

    public void updateStatus(Seat seat){
        String sql = "update seat set status = "+seat.getStatus()+" where id = "+seat.getId();
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Seat> find(Seat bean, int start, int count){
        List<Seat> seats = new ArrayList<>();
        String sql = "select * from seat where 1 = 1";
        if (bean.getId()!=-1){
            sql += " and id like '%" + bean.getId() +"%'";
        }
        if (bean.getStatus()!=-1){
            sql += " and status like '%" + bean.getStatus()+"%'";
        }
        sql+= "limit ? , ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Seat seat = new Seat();
                int id = rs.getInt("id");
                int status = rs.getInt("status");
                String address = rs.getString("address");
                seat.setId(id);
                seat.setStatus(status);
                seat.setAddress(address);
                seats.add(seat);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    public int getFindTotal(Seat bean){
        int total = 0;
        String sql = "select count(*) from seat where 1 = 1";
        if (bean.getId()!=-1){
            sql += " and id like '%" + bean.getId() +"%'";
        }
        if (bean.getStatus()!=-1){
            sql += " and status like '%" + bean.getStatus()+"%'";
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
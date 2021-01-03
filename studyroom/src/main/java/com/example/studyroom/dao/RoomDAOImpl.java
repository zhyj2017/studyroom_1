package com.example.studyroom.dao;

import com.example.studyroom.pojo.Room;
import com.example.studyroom.util.DBUtil;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoomDAOImpl implements RoomDAO {
    public Room find(Date date){
        Room room = null;
        String sql = "select * from room where date = '"+date +"'";

        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()){
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()){
                room = new Room();
                int id = rs.getInt("id");
                int num = rs.getInt("num");
                int closed = rs.getInt("closed");
                int reserve = rs.getInt("reserve");
                int sign = rs.getInt("sign");
                int late = rs.getInt("late");
                int violate = rs.getInt("violate");
                room.setId(id);
                room.setNum(num);
                room.setDate(date);
                room.setClosed(closed);
                room.setReserve(reserve);
                room.setSign(sign);
                room.setLate(late);
                room.setViolate(violate);

            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return room;
    }
    public void add(Room room){
        String sql = "insert into room values(null,?,?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, room.getNum());
            ps.setDate(2, new java.sql.Date(room.getDate().getTime()));
            ps.setInt(3, room.getClosed());
            ps.setInt(4, room.getReserve());
            ps.setInt(5, room.getSign());
            ps.setInt(6, room.getLate());
            ps.setInt(7, room.getViolate());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                room.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update(Room room){
        Date date = new Date(room.getDate().getTime());
        String sql = "update room set num = "+room.getNum()+", closed = "+room.getClosed()+", reserve = reserve+"+room.getReserve()
                +", sign = sign+"+room.getSign()+", late = late+"+room.getLate()+", violate = violate+"+room.getViolate()
                +" where date = '"+ date+"'";
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Room> list(int start, int count){
        List<Room> rooms = new ArrayList<>();
        String sql = "select * from room order by date desc limit ? , ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Room room = new Room();
                int id = rs.getInt("id");
                int num = rs.getInt("num");
                Date date = rs.getDate("date");
                int closed = rs.getInt("closed");
                int reserve = rs.getInt("reserve");
                int sign = rs.getInt("sign");
                int late = rs.getInt("late");
                int violate = rs.getInt("violate");
                room.setId(id);
                room.setNum(num);
                room.setDate(date);
                room.setClosed(closed);
                room.setReserve(reserve);
                room.setSign(sign);
                room.setLate(late);
                room.setViolate(violate);
                rooms.add(room);
            }
        }catch (SQLException e) {

            e.printStackTrace();
        }
        return rooms;
    }

    public int getTotal(){
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql=null;
            sql = "select count(*) from room where 1=1 " ;
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return total;
    }

    public List<Room> find(Room bean1, Room bean2, int start, int count){
        List<Room> rooms = new ArrayList<>();
        String sql = "select * from room where 1 = 1";
        if (bean1.getDate()!=null){
            sql += " and date like '%" + bean1.getDate() +"%'";
        }
        if (bean1.getNum()!=-1){
            sql += " and num >= "+bean1.getNum();
        }
        if (bean1.getClosed()!=-1){
            sql += " and closed >= "+bean1.getClosed();
        }
        if (bean1.getReserve()!=-1){
            sql += " and reserve >= "+bean1.getReserve();
        }
        if (bean1.getSign()!=-1){
            sql += " and sign >= "+bean1.getSign();
        }
        if (bean1.getLate()!=-1){
            sql += " and late >= "+bean1.getLate();
        }
        if (bean1.getViolate()!=-1){
            sql += " and violate >= "+bean1.getViolate();
        }
        if (bean2.getNum()!=-1){
            sql += " and num <= "+bean2.getNum();
        }
        if (bean2.getClosed()!=-1){
            sql += " and closed <= "+bean2.getClosed();
        }
        if (bean2.getReserve()!=-1){
            sql += " and reserve <= "+bean2.getReserve();
        }
        if (bean2.getSign()!=-1){
            sql += " and sign <= "+bean2.getSign();
        }
        if (bean2.getLate()!=-1){
            sql += " and late <= "+bean2.getLate();
        }
        if (bean2.getViolate()!=-1){
            sql += " and violate <= "+bean2.getViolate();
        }
        sql += " order by date desc limit ? , ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Room room = new Room();
                int id = rs.getInt("id");
                int num = rs.getInt("num");
                Date date = rs.getDate("date");
                int closed = rs.getInt("closed");
                int reserve = rs.getInt("reserve");
                int sign = rs.getInt("sign");
                int late = rs.getInt("late");
                int violate = rs.getInt("violate");
                room.setId(id);
                room.setNum(num);
                room.setDate(date);
                room.setClosed(closed);
                room.setReserve(reserve);
                room.setSign(sign);
                room.setLate(late);
                room.setViolate(violate);
                rooms.add(room);
            }
        }catch (SQLException e) {

            e.printStackTrace();
        }
        return rooms;
    }

    public int getFindTotal(Room bean1, Room bean2){
        int total = 0;
        String sql = "select count(*) from room where 1 = 1";
        if (bean1.getDate()!=null){
            sql += " and date like '%" + bean1.getDate() +"%'";
        }
        if (bean1.getNum()!=-1){
            sql += " and num >= "+bean1.getNum();
        }
        if (bean1.getClosed()!=-1){
            sql += " and closed >= "+bean1.getClosed();
        }
        if (bean1.getReserve()!=-1){
            sql += " and reserve >= "+bean1.getReserve();
        }
        if (bean1.getSign()!=-1){
            sql += " and sign >= "+bean1.getSign();
        }
        if (bean1.getLate()!=-1){
            sql += " and late >= "+bean1.getLate();
        }
        if (bean1.getViolate()!=-1){
            sql += " and violate >= "+bean1.getViolate();
        }
        if (bean2.getNum()!=-1){
            sql += " and num <= "+bean2.getNum();
        }
        if (bean2.getClosed()!=-1){
            sql += " and closed <= "+bean2.getClosed();
        }
        if (bean2.getReserve()!=-1){
            sql += " and reserve <= "+bean2.getReserve();
        }
        if (bean2.getSign()!=-1){
            sql += " and sign <= "+bean2.getSign();
        }
        if (bean2.getLate()!=-1){
            sql += " and late <= "+bean2.getLate();
        }
        if (bean2.getViolate()!=-1){
            sql += " and violate <= "+bean2.getViolate();
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

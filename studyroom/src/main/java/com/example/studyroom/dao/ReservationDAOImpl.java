package com.example.studyroom.dao;

import com.example.studyroom.pojo.Reservation;
import com.example.studyroom.util.DBUtil;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationDAOImpl implements ReservationDAO {
    public List<Reservation> getReservedByDate(Reservation reservation){
        List<Reservation> reservations = new ArrayList<>();
//        String sql = "select sid from reservation where date = '"+reservation.getDate()+"' and ((starttime >= '"
//                +reservation.getStarttime()+"' and starttime < '"+reservation.getEndtime()+"') or (endtime >"
//                +reservation.getStarttime()+"' and endtime <="+reservation.getEndtime()+"'))";
        String sql = "select * from reservation where date = '"+reservation.getDate()+"'";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Reservation bean = new Reservation();
                int id = rs.getInt("id");
                String uid = rs.getString("uid");
                int sid = rs.getInt("sid");
                Date date = rs.getDate("date");
                Time starttime = rs.getTime("starttime");
                Time endtime = rs.getTime("endtime");
                Time signin = rs.getTime("signin");
                Time signout = rs.getTime("signout");
                int status = rs.getInt("status");
                bean.setId(id);
                bean.setUid(uid);
                bean.setSid(sid);
                bean.setDate(date);
                bean.setStarttime(starttime);
                bean.setEndtime(endtime);
                bean.setSignin(signin);
                bean.setSignout(signout);
                bean.setStatus(status);
                reservations.add(bean);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    public void addReservation(Reservation reservation){
        String sql = "insert into reservation values(null,?,?,?,?,?,null,null,1)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1,reservation.getUid() );
            ps.setInt(2,reservation.getSid());
            ps.setDate(3,new java.sql.Date(reservation.getDate().getTime()));
            ps.setTime(4,reservation.getStarttime());
            ps.setTime(5,reservation.getEndtime());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                reservation.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStatusBySeat(Reservation reservation){
        String sql = "update reservation set status = "+reservation.getStatus()+" where sid = "+reservation.getSid();
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Reservation> listAll(int start, int count){
        List<Reservation> reservations = new ArrayList<>();
        String sql = "select * from reservation order by date desc,starttime asc limit ? , ?  ";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reservation reservation = new Reservation();
                int id = rs.getInt("id");
                String uid = rs.getString("uid");
                int sid = rs.getInt("sid");
                Date date = rs.getDate("date");
                Time starttime = rs.getTime("starttime");
                Time endtime = rs.getTime("endtime");
                Time signin = rs.getTime("signin");
                Time signout = rs.getTime("signout");
                int status = rs.getInt("status");
                reservation.setId(id);
                reservation.setUid(uid);
                reservation.setSid(sid);
                reservation.setDate(date);
                reservation.setStarttime(starttime);
                reservation.setEndtime(endtime);
                reservation.setSignin(signin);
                reservation.setSignout(signout);
                reservation.setStatus(status);
                reservations.add(reservation);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return reservations;
    }


    public int getTotalAll(){
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = null;
            sql = "select count(*) from reservation where 1=1 ";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return total;
    }

    public List<Reservation> listByUId(String uid, int start, int count){
        List<Reservation> reservations = new ArrayList<>();
        String sql = "select * from reservation where uid = '"+uid+"' order by date desc,starttime asc limit ? , ?  ";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reservation reservation = new Reservation();
                int id = rs.getInt("id");

                int sid = rs.getInt("sid");
                Date date = rs.getDate("date");
                Time starttime = rs.getTime("starttime");
                Time endtime = rs.getTime("endtime");
                Time signin = rs.getTime("signin");
                Time signout = rs.getTime("signout");
                int status = rs.getInt("status");
                reservation.setId(id);
                reservation.setUid(uid);
                reservation.setSid(sid);
                reservation.setDate(date);
                reservation.setStarttime(starttime);
                reservation.setEndtime(endtime);
                reservation.setSignin(signin);
                reservation.setSignout(signout);
                reservation.setStatus(status);
                reservations.add(reservation);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return reservations;
    }

    public int getTotalByUId(String uid){
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = null;
            sql = "select count(*) from reservation where uid = '"+uid+"'";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return total;
    }

    public void updateSigninById(Reservation reservation){
        String sql = "update reservation set signin = '"+reservation.getSignin()+"' where id = "+reservation.getId();
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSignoutById(Reservation reservation){
        String sql = "update reservation set signout = '"+reservation.getSignout()+"' where id = "+reservation.getId();
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStatusById(Reservation reservation){
        String sql = "update reservation set status = "+reservation.getStatus()+" where id = "+reservation.getId();
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Reservation getById(int id){
        String sql = "select * from reservation where id = '"+id+"'";
        Reservation bean = new Reservation();
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String uid = rs.getString("uid");
                int sid = rs.getInt("sid");
                Date date = rs.getDate("date");
                Time starttime = rs.getTime("starttime");
                Time endtime = rs.getTime("endtime");
                Time signin = rs.getTime("signin");
                Time signout = rs.getTime("signout");
                int status = rs.getInt("status");
                bean.setId(id);
                bean.setUid(uid);
                bean.setSid(sid);
                bean.setDate(date);
                bean.setStarttime(starttime);
                bean.setEndtime(endtime);
                bean.setSignin(signin);
                bean.setSignout(signout);
                bean.setStatus(status);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public List<Reservation> find(Reservation bean,int start, int count){
        List<Reservation> reservations = new ArrayList<>();
        String sql = "select * from reservation where 1 = 1";
        if (bean.getUid()!=null){
            sql += " and uid like '%" + bean.getUid() +"%'";
        }
        if (bean.getSid()!=-1){
            sql += " and sid like '%" + bean.getSid()+"%'";
        }
        if (bean.getDate()!=null){
            sql += " and date = '" + bean.getDate()+"'";
        }
        if (bean.getStatus()!=-1){
            sql += " and status like '%" + bean.getStatus()+"%'";
        }
        sql+= "order by date desc,starttime asc limit ? , ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Reservation reservation = new Reservation();
                int id = rs.getInt("id");
                String uid = rs.getString("uid");
                int sid = rs.getInt("sid");
                Date date = rs.getDate("date");
                Time starttime = rs.getTime("starttime");
                Time endtime = rs.getTime("endtime");
                Time signin = rs.getTime("signin");
                Time signout = rs.getTime("signout");
                int status = rs.getInt("status");
                reservation.setId(id);
                reservation.setUid(uid);
                reservation.setSid(sid);
                reservation.setDate(date);
                reservation.setStarttime(starttime);
                reservation.setEndtime(endtime);
                reservation.setSignin(signin);
                reservation.setSignout(signout);
                reservation.setStatus(status);
                reservations.add(reservation);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }


    public int getFindTotal(Reservation bean){
        int total = 0;
        String sql = "select count(*) from reservation where 1 = 1";
        if (bean.getUid()!=null){
            sql += " and uid like '%" + bean.getUid() +"%'";
        }
        if (bean.getSid()!=-1){
            sql += " and sid like '%" + bean.getSid()+"%'";
        }
        if (bean.getDate()!=null){
            sql += " and date = '" + bean.getDate()+"'";
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

    public List<Reservation> findByUId(Reservation bean,int start, int count){
        List<Reservation> reservations = new ArrayList<>();
        String sql = "select * from reservation where uid = '"+bean.getUid()+"'";
        if (bean.getSid()!=-1){
            sql += " and sid like '%" + bean.getSid()+"%'";
        }
        if (bean.getDate()!=null){
            sql += " and date = '" + bean.getDate()+"'";
        }
        if (bean.getStatus()!=-1){
            sql += " and status like '%" + bean.getStatus()+"%'";
        }
        sql+= "order by date desc,starttime asc limit ? , ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Reservation reservation = new Reservation();
                int id = rs.getInt("id");
                String uid = rs.getString("uid");
                int sid = rs.getInt("sid");
                Date date = rs.getDate("date");
                Time starttime = rs.getTime("starttime");
                Time endtime = rs.getTime("endtime");
                Time signin = rs.getTime("signin");
                Time signout = rs.getTime("signout");
                int status = rs.getInt("status");
                reservation.setId(id);
                reservation.setUid(uid);
                reservation.setSid(sid);
                reservation.setDate(date);
                reservation.setStarttime(starttime);
                reservation.setEndtime(endtime);
                reservation.setSignin(signin);
                reservation.setSignout(signout);
                reservation.setStatus(status);
                reservations.add(reservation);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    public int getFindByUIdTotal(Reservation bean){
        int total = 0;
        String sql = "select count(*) from reservation where uid = '"+bean.getUid()+"'";
        if (bean.getSid()!=-1){
            sql += " and sid like '%" + bean.getSid()+"%'";
        }
        if (bean.getDate()!=null){
            sql += " and date = '" + bean.getDate()+"'";
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

    public List<Reservation> findByUidAndDate(String date1, String date2, String uid){
        List<Reservation> reservations = new ArrayList<>();
        String sql = "select * from reservation where (status = 3 or status = 4 or status = 5) and uid = '"+uid+"' and date(date) between '"+date1+"' and '"+date2+"'";
//        String sql = "select * from reservation where uid = '"+uid+"' and (status = 3 or status = 4 or status = 5) and date(date) between '"+date1+"' and '"+date2+"' order by date desc, starttime desc";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reservation reservation = new Reservation();
                int id = rs.getInt("id");
                int sid = rs.getInt("sid");
                Date date = rs.getDate("date");
                Time starttime = rs.getTime("starttime");
                Time endtime = rs.getTime("endtime");
                Time signin = rs.getTime("signin");
                Time signout = rs.getTime("signout");
                int status = rs.getInt("status");
                reservation.setId(id);
                reservation.setUid(uid);
                reservation.setSid(sid);
                reservation.setDate(date);
                reservation.setStarttime(starttime);
                reservation.setEndtime(endtime);
                reservation.setSignin(signin);
                reservation.setSignout(signout);
                reservation.setStatus(status);
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    public List<String> findUidByDateAndTime(Reservation bean){
        List<String> reservations = new ArrayList<>();
        String sql = "select * from reservation where date = '"+bean.getDate()+"' and uid <> '"+bean.getUid()+"' and starttime < '"+bean.getEndtime()+"' and endtime > '"+bean.getStarttime()+"' and (status = 3 or status = 4  or status = 5)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uid = rs.getString("uid");
                reservations.add(uid);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return reservations;
    }
}

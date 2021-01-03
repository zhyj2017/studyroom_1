package com.example.studyroom;

import com.example.studyroom.pojo.Reservation;
import com.example.studyroom.pojo.Student;
import com.example.studyroom.service.StudentServiceImpl;
import com.example.studyroom.util.DBUtil;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class test {
    public static void main(String args[]){
//        List<Reservation> reservations = new ArrayList<>();
//       String sql = "select * from reservation where date(date) between '"+"2020-12-13"+"' and '"+"2021-01-03'";
//        String sql = "select * from reservation where time(starttime) between '"+"07:00:00"+"' and '"+"10:00:00' and time(endtime) between '"+"08:00:00"+"' and '"+"12:00:00'" ;
//        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                Reservation reservation = new Reservation();
//                int id = rs.getInt("id");
//                String uid = rs.getString("uid");
//                int sid = rs.getInt("sid");
//                Date date = rs.getDate("date");
//                Time starttime = rs.getTime("starttime");
//                Time endtime = rs.getTime("endtime");
//                Time signin = rs.getTime("signin");
//                Time signout = rs.getTime("signout");
//                int status = rs.getInt("status");
//                reservation.setId(id);
//                reservation.setUid(uid);
//                reservation.setSid(sid);
//                reservation.setDate(date);
//                reservation.setStarttime(starttime);
//                reservation.setEndtime(endtime);
//                reservation.setSignin(signin);
//                reservation.setSignout(signout);
//                reservation.setStatus(status);
//                reservations.add(reservation);
//            }
//        } catch (SQLException e) {
//
//            e.printStackTrace();
//        }
//        System.out.println(reservations.size());
//        for (int i=0;i<reservations.size();i++){
//            System.out.println(reservations.get(i).getId());
//        }
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE,-30);
        java.util.Date d = c.getTime();
        String pastDate = format.format(d);
        String nowDate = format.format(date);
        System.out.println(pastDate);
        List<Reservation> reservations1 = new test().findByUidAndDate(pastDate,nowDate,"221701202");
        List<String> reservations2 = new ArrayList<>(); //存放其他学生的预约记录的学号
        for (int i=0;i<reservations1.size();i++){
            reservations2.addAll(new test().findUidByDateAndTime(reservations1.get(i)));
        }
        List<String> uids = new ArrayList<>();
        for (int i=0;i<reservations2.size();i++){       //删除重复学生学号
            if (!uids.contains(reservations2.get(i))){
                uids.add(reservations2.get(i));
            }
        }
        Collections.sort(uids);   //排序
        for (int i=0;i<uids.size();i++){
            System.out.println(uids.get(i));
        }
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

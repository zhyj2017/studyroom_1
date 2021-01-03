package com.example.studyroom.dao;

import com.example.studyroom.pojo.Credit;
import com.example.studyroom.util.DBUtil;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CreditDAOImpl implements CreditDAO {

    public Credit getCreditByUId(String uid){
            Credit bean = null;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()){
            String sql = "select * from credit where uid = " + uid;
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()){
                bean = new Credit();
                int id = rs.getInt("id");
                String userid = rs.getString("uid");
                int reserve = rs.getInt("reserve");
                int cancel = rs.getInt("cancel");
                int sign = rs.getInt("sign");
                int late = rs.getInt("late");
                int violate = rs.getInt("violate");
                double time = rs.getDouble("time");

                bean.setId(id);
                bean.setUid(userid);
                bean.setReserve(reserve);
                bean.setCancel(cancel);
                bean.setSign(sign);
                bean.setLate(late);
                bean.setViolate(violate);
                bean.setTime(time);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return bean;
    }

    public void addCredit(Credit credit){
        String sql = "insert into credit values(null ,? ,?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, credit.getUid());
            ps.setInt(2, credit.getReserve());
            ps.setInt(3, credit.getCancel());
            ps.setInt(4, credit.getSign());
            ps.setInt(5, credit.getLate());
            ps.setInt(6, credit.getViolate());
            ps.setDouble(7, credit.getTime());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                credit.setId(id);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public void delete(String uid){
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "delete from credit where uid = '" + uid +"'";
            s.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Credit credit){
        String sql = "update credit set reserve = reserve+"+credit.getReserve()+", cancel = cancel+"+credit.getCancel()
                +", sign = sign+"+credit.getSign()+", late = late+"+credit.getLate()+", violate = violate+"+credit.getViolate()
                +", time = time+"+credit.getTime()+" where uid = "+credit.getUid();
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Credit> list(int start, int count){
        List<Credit> credits = new ArrayList<>();
        String sql = "select * from credit order by uid asc limit ? , ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Credit bean = new Credit();
                int id = rs.getInt("id");
                String userid = rs.getString("uid");
                int reserve = rs.getInt("reserve");
                int cancel = rs.getInt("cancel");
                int sign = rs.getInt("sign");
                int late = rs.getInt("late");
                int violate = rs.getInt("violate");
                double time = rs.getDouble("time");

                bean.setId(id);
                bean.setUid(userid);
                bean.setReserve(reserve);
                bean.setCancel(cancel);
                bean.setSign(sign);
                bean.setLate(late);
                bean.setViolate(violate);
                bean.setTime(time);
                credits.add(bean);
            }
        }catch (SQLException e) {

            e.printStackTrace();
        }
        return credits;
    }

    public int getTotal(){
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql=null;
            sql = "select count(*) from credit where 1=1 " ;
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return total;
    }

    public List<Credit> find(Credit bean1, Credit bean2, int start, int count){
        List<Credit> credits = new ArrayList<>();
        String sql = "select * from credit where 1 = 1";
        if (bean1.getUid()!=null){
            sql += " and uid like '%" + bean1.getUid() +"%'";
        }
        if (bean1.getReserve()!=-1){
            sql += " and reserve >= "+bean1.getReserve();
        }
        if (bean1.getCancel()!=-1){
            sql += " and cancel >= "+bean1.getCancel();
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
        if (bean1.getTime()!=-1){
            sql += " and time >= "+bean1.getTime();
        }
        if (bean2.getReserve()!=-1){
            sql += " and reserve <= "+bean2.getReserve();
        }
        if (bean2.getCancel()!=-1){
            sql += " and cancel <= "+bean2.getCancel();
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
        if (bean2.getTime()!=-1){
            sql += " and time <= "+bean2.getTime();
        }
        sql += " order by uid asc limit ? , ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Credit bean = new Credit();
                int id = rs.getInt("id");
                String userid = rs.getString("uid");
                int reserve = rs.getInt("reserve");
                int cancel = rs.getInt("cancel");
                int sign = rs.getInt("sign");
                int late = rs.getInt("late");
                int violate = rs.getInt("violate");
                double time = rs.getDouble("time");

                bean.setId(id);
                bean.setUid(userid);
                bean.setReserve(reserve);
                bean.setCancel(cancel);
                bean.setSign(sign);
                bean.setLate(late);
                bean.setViolate(violate);
                bean.setTime(time);
                credits.add(bean);
            }
        }catch (SQLException e) {

            e.printStackTrace();
        }
        return credits;
    }

    public int getFindTotal(Credit bean1, Credit bean2){
        int total = 0;
        String sql = "select count(*) from credit where 1 = 1";
        if (bean1.getUid()!=null){
            sql += " and uid like '%" + bean1.getUid() +"%'";
        }
        if (bean1.getReserve()!=-1){
            sql += " and reserve >= "+bean1.getReserve();
        }
        if (bean1.getCancel()!=-1){
            sql += " and cancel >= "+bean1.getCancel();
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
        if (bean1.getTime()!=-1){
            sql += " and time >= "+bean1.getTime();
        }
        if (bean2.getReserve()!=-1){
            sql += " and reserve <= "+bean2.getReserve();
        }
        if (bean2.getCancel()!=-1){
            sql += " and cancel <= "+bean2.getCancel();
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
        if (bean2.getTime()!=-1){
            sql += " and time <= "+bean2.getTime();
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

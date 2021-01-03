package com.example.studyroom.pojo;

import java.sql.Time;
import java.util.Date;

public class Reservation {
    private int id;
    private String uid;
    private int sid;
    private Date date;
    private Time starttime;  //预约开始时间
    private Time endtime;    //预约结束时间
    private Time signin;     //签到时间
    private Time signout;    //签退时间
    private int status;      //状态  1-已预约  2-已取消  3-已签到  4-已完成  5-迟到  6-违约  7-服务中止 8-迟到完成预约

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStarttime() {
        return starttime;
    }

    public void setStarttime(Time starttime) {
        this.starttime = starttime;
    }

    public Time getEndtime() {
        return endtime;
    }

    public void setEndtime(Time endtime) {
        this.endtime = endtime;
    }

    public Time getSignin() {
        return signin;
    }

    public void setSignin(Time signin) {
        this.signin = signin;
    }

    public Time getSignout() {
        return signout;
    }

    public void setSignout(Time signout) {
        this.signout = signout;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

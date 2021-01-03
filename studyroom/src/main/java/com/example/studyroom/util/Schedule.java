package com.example.studyroom.util;

import com.example.studyroom.dao.RoomDAO;
import com.example.studyroom.dao.SeatDAO;
import com.example.studyroom.pojo.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Schedule {
    @Autowired
    private RoomDAO roomDAO;
    @Autowired
    private SeatDAO seatDAO;

    @Scheduled(cron="0/10 * * * * ?")
    public void update(){
        Room room = new Room();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = format.format(date);

        room = roomDAO.find(new java.sql.Date(date.getTime()));
        if (room == null){
            Room room1 = new Room();
            room1.setDate(new java.sql.Date(date.getTime()));
            int num = seatDAO.getTotal();
            room1.setNum(num);
            int closed = seatDAO.getTotalClosed();
            room1.setClosed(closed);
            room1.setReserve(0);
            room1.setSign(0);
            room1.setLate(0);
            room1.setViolate(0);
            roomDAO.add(room1);
        }
    }

}

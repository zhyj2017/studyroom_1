package com.example.studyroom.service;

import com.example.studyroom.dao.RoomDAO;
import com.example.studyroom.dao.SeatDAO;
import com.example.studyroom.pojo.Room;
import com.example.studyroom.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private SeatDAO seatDAO;
    @Autowired
    private RoomDAO roomDAO;

    public void update(Room room){
        Room bean = roomDAO.find(new java.sql.Date(room.getDate().getTime()));
        int num = seatDAO.getTotal();
        int closednum = seatDAO.getTotalClosed();
        if (bean == null){
            room.setNum(num);
            room.setClosed(closednum);
            roomDAO.add(room);
        }else{
            room.setNum(num);
            room.setClosed(closednum);
            roomDAO.update(room);
        }

    }

    public List<Room> listByPage(Page page){
        List<Room> rooms = roomDAO.list(page.getStart(),page.getCount());
        return rooms;
    }

    public int getTotal(){
        int total=0;
        total=(int) roomDAO.getTotal();
        return total;
    }

    public List<Room> findByPage(Room room1, Room room2, Page page){
        List<Room> rooms = roomDAO.find(room1,room2,page.getStart(),page.getCount());
        return rooms;
    }

    public int getFindTotal(Room room1, Room room2){
        int total=0;
        total=(int) roomDAO.getFindTotal(room1,room2);
        return total;
    }
}

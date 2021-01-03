package com.example.studyroom.dao;

import com.example.studyroom.pojo.Room;

import java.sql.Date;
import java.util.List;

public interface RoomDAO {
    Room find(Date date);
    void add(Room room);
    void update(Room room);
    List<Room> list(int start, int count);
    int getTotal();
    List<Room> find(Room bean1, Room bean2, int start, int count);
    int getFindTotal(Room bean1, Room bean2);
}

package com.example.studyroom.service;

import com.example.studyroom.pojo.Room;
import com.example.studyroom.util.Page;

import java.util.List;

public interface RoomService {
    void update(Room room);
    List<Room> listByPage(Page page);
    int getTotal();
    List<Room> findByPage(Room room1, Room room2, Page page);
    int getFindTotal(Room room1, Room room2);
}

package com.example.studyroom.service;

import com.example.studyroom.pojo.Seat;
import com.example.studyroom.util.Page;

import java.util.List;

public interface SeatService {
    List<Integer> getCloseSeatID();
    int getTotal();
    int getTotalClosed();
    List<Seat> listByPage(Page page);
    void openSeat(Seat seat);
    void closeSeat(Seat seat);
    List<Seat> findByPage(Seat seat,Page page);
    int totalFind(Seat bean);
}

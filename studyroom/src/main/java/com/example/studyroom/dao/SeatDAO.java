package com.example.studyroom.dao;

import com.example.studyroom.pojo.Seat;

import java.util.List;

public interface SeatDAO {
    List<Integer> getCloseSeatID();
    int getTotal();
    int getTotalClosed();
    List<Seat> list(int start, int count);
    void updateStatus(Seat seat);
    List<Seat> find(Seat bean,int start, int count);
    int getFindTotal(Seat bean);
}

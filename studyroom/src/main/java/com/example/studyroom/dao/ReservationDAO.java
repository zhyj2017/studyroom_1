package com.example.studyroom.dao;

import com.example.studyroom.pojo.Reservation;

import java.util.List;

public interface ReservationDAO {
    List<Reservation> getReservedByDate(Reservation reservation);
    void addReservation(Reservation reservation);
    void updateStatusBySeat(Reservation reservation);
    List<Reservation> listAll(int start, int count);
    int getTotalAll();

    List<Reservation> listByUId(String uid, int start, int count);
    int getTotalByUId(String uid);

    void updateSigninById(Reservation reservation);
    void updateSignoutById(Reservation reservation);
    void updateStatusById(Reservation reservation);
    Reservation getById(int id);

    List<Reservation> find(Reservation bean,int start, int count);
    int getFindTotal(Reservation bean);
    List<Reservation> findByUId(Reservation bean,int start, int count);
    int getFindByUIdTotal(Reservation bean);

    List<Reservation> findByUidAndDate(String date1, String date2, String uid);
    List<String> findUidByDateAndTime(Reservation bean);
}

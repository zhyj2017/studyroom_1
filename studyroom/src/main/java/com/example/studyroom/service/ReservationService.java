package com.example.studyroom.service;

import com.example.studyroom.pojo.Reservation;
import com.example.studyroom.util.Page;

import java.util.List;

public interface ReservationService {
    List<Integer> getReservedSID(Reservation reservation);
    Boolean isReserved(Reservation reservation,String uid);
    void addReservation(Reservation reservation);
    List<Reservation> listAllByPage(Page page);
    int getTotalAll();
    List<Reservation> listByUIdByPage(String uid, Page page);
    int getTotalByUId(String uid);
    void cancelAdmin(Reservation reservation);
    void resumeAdmin(Reservation reservation);
    void finishAdmin(Reservation reservation);
    void breakAdmin(Reservation reservation);
    void signin(Reservation reservation);
    void signout(Reservation reservation);
    void late(Reservation reservation);
    List<Reservation> findByPage(Reservation reservation, Page page);
    int totalFind(Reservation reservation);
    List<Reservation> findByUIdByPage(Reservation reservation, Page page);
    int totalFindByUId(Reservation reservation);
}

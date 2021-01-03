package com.example.studyroom.service;

import com.example.studyroom.dao.ReservationDAO;
import com.example.studyroom.dao.SeatDAO;
import com.example.studyroom.pojo.Reservation;
import com.example.studyroom.pojo.Seat;
import com.example.studyroom.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    private SeatDAO seatDAO;
    @Autowired
    private ReservationDAO reservationDAO;

    public List<Integer> getCloseSeatID(){
        List<Integer> seats = seatDAO.getCloseSeatID();
        return seats;
    }

    public int getTotal(){
        int total=0;
        total=(int) seatDAO.getTotal();
        return total;
    }

    public int getTotalClosed(){
        int total=0;
        total=(int) seatDAO.getTotalClosed();
        return total;
    }

    public List<Seat> listByPage(Page page){
        List<Seat> seats = seatDAO.list(page.getStart(),page.getCount());
        return seats;
    }

    public void openSeat(Seat seat){
        seat.setStatus(1);
        seatDAO.updateStatus(seat);
    }

    public void closeSeat(Seat seat){
        seat.setStatus(0);
        seatDAO.updateStatus(seat);
        Reservation reservation = new Reservation();
        reservation.setSid(seat.getId());
        reservation.setStatus(7);
        reservationDAO.updateStatusBySeat(reservation);
    }

    public List<Seat> findByPage(Seat seat,Page page){
        List<Seat> seats = seatDAO.find(seat,page.getStart(),page.getCount());
        return seats;
    }

    public int totalFind(Seat bean){
        int total=0;
        total=(int) seatDAO.getFindTotal(bean);
        return total;
    }


}

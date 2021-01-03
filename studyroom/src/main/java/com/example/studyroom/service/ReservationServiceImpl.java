package com.example.studyroom.service;

import com.example.studyroom.dao.CreditDAO;
import com.example.studyroom.dao.ReservationDAO;
import com.example.studyroom.dao.RoomDAO;
import com.example.studyroom.dao.SeatDAO;
import com.example.studyroom.pojo.Credit;
import com.example.studyroom.pojo.Reservation;
import com.example.studyroom.pojo.Room;
import com.example.studyroom.util.Page;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationDAO reservationDAO;
    @Autowired
    private CreditDAO creditDAO;
    @Autowired
    private RoomDAO roomDAO;
    @Autowired
    private SeatDAO seatDAO;


    public List<Integer> getReservedSID(Reservation reservation){
        List<Integer> sid = new ArrayList<>();
        List<Reservation> list = reservationDAO.getReservedByDate(reservation);
        for (int i=0;i<list.size();i++){
//            if ((list.get(i).getStarttime().getTime()>=reservation.getStarttime().getTime()&&list.get(i).getStarttime().getTime()<reservation.getStarttime().getTime())||
//                    (list.get(i).getStarttime().getTime()<=reservation.getStarttime().getTime()&&list.get(i).getStarttime().getTime()>reservation.getEndtime().getTime())){
//                sid.add(list.get(i).getSid());
//            }
            if ((list.get(i).getStarttime().toString().compareTo(reservation.getStarttime().toString())>=0&&(list.get(i).getStarttime().toString().compareTo(reservation.getEndtime().toString()))<0)
                    ||(list.get(i).getStarttime().toString().compareTo(reservation.getStarttime().toString())<=0&&list.get(i).getEndtime().toString().compareTo(reservation.getStarttime().toString())>0)){
                sid.add(list.get(i).getSid());
            }
        }
        return sid;
    }

    public Boolean isReserved(Reservation reservation,String uid){
        List<Reservation> list = reservationDAO.getReservedByDate(reservation);
        for (int i=0;i<list.size();i++){
            if ((list.get(i).getStarttime().toString().compareTo(reservation.getStarttime().toString())>=0&&(list.get(i).getStarttime().toString().compareTo(reservation.getEndtime().toString()))<0)
                    ||(list.get(i).getStarttime().toString().compareTo(reservation.getStarttime().toString())<=0&&list.get(i).getEndtime().toString().compareTo(reservation.getStarttime().toString())>0)){
                if (list.get(i).getUid().equals(uid)){
                    return true;
                }
            }
        }
        return false;
    }

    public void addReservation(Reservation reservation){
        reservationDAO.addReservation(reservation);
    }

    public List<Reservation> listAllByPage(Page page){
        List<Reservation> reservations = reservationDAO.listAll(page.getStart(),page.getCount());
        return reservations;
    }

    public int getTotalAll(){
        int total=0;
        total=(int) reservationDAO.getTotalAll();
        return total;
    }

    public List<Reservation> listByUIdByPage(String uid, Page page){
        List<Reservation> reservations = reservationDAO.listByUId(uid, page.getStart(),page.getCount());
        return reservations;
    }

    public int getTotalByUId(String uid){
        int total=0;
        total=(int) reservationDAO.getTotalByUId(uid);
        return total;
    }

    public void cancelAdmin(Reservation reservation){
        reservation.setStatus(2);
        reservationDAO.updateStatusById(reservation);
        Credit credit = new Credit();
        credit.setUid(reservation.getUid());
        credit.setReserve(0);
        credit.setCancel(1);
        credit.setSign(0);
        credit.setLate(0);
        credit.setViolate(0);
        credit.setTime(0);
        creditDAO.update(credit);
    }

    public void resumeAdmin(Reservation reservation){
        reservation.setStatus(1);
        reservationDAO.updateStatusById(reservation);
        Credit credit = new Credit();
        credit.setUid(reservation.getUid());
        credit.setReserve(0);
        credit.setCancel(-1);
        credit.setSign(0);
        credit.setLate(0);
        credit.setViolate(0);
        credit.setTime(0);
        creditDAO.update(credit);
    }

    public void finishAdmin(Reservation reservation){
        Reservation bean = reservationDAO.getById(reservation.getId());
        reservation.setSignin(bean.getStarttime());
        reservation.setSignout(bean.getEndtime());
        String signin = new String();
        Credit credit = new Credit();


        if (bean.getSignin() != null){
            reservationDAO.updateSignoutById(reservation);
             signin = bean.getSignin().toString();
            credit.setSign(0);
        }else {
            reservationDAO.updateSigninById(reservation);
            reservationDAO.updateSignoutById(reservation);
            signin = bean.getStarttime().toString();
            credit.setSign(1);
        }
        if (bean.getStatus()==5){
            reservation.setStatus(8);
        }else{
            reservation.setStatus(4);
        }

        reservationDAO.updateStatusById(reservation);

        String signout = bean.getEndtime().toString();
        String[] si = signin.split(":");
        String[] so = signout.split(":");
        double sih = Double.parseDouble(si[0]);
        double sim = Double.parseDouble(si[1]);
        double soh = Double.parseDouble(so[0]);
        double som = Double.parseDouble(so[1]);
        double time = (soh-sih)+(som-sim)/60;
        double stime = (double)Math.round(time*100)/100;

        credit.setUid(reservation.getUid());
        credit.setReserve(0);
        credit.setCancel(0);
        credit.setLate(0);
        credit.setViolate(0);
        credit.setTime(stime);
        creditDAO.update(credit);
    }

    public void breakAdmin(Reservation reservation){
        reservation.setStatus(6);
        reservationDAO.updateStatusById(reservation);
        Credit credit = new Credit();
        credit.setUid(reservation.getUid());
        credit.setReserve(0);
        credit.setCancel(0);
        credit.setSign(0);
        credit.setLate(0);
        credit.setViolate(1);
        credit.setTime(0);
        creditDAO.update(credit);
        Room room = new Room();
        Reservation bean = reservationDAO.getById(reservation.getId());
        room.setDate(bean.getDate());
        room.setNum(seatDAO.getTotal());
        room.setClosed(seatDAO.getTotalClosed());
        room.setReserve(0);
        room.setSign(0);
        room.setLate(0);
        room.setViolate(1);
        roomDAO.update(room);
    }

    public void signin(Reservation reservation){
        reservation.setStatus(3);
        reservationDAO.updateStatusById(reservation);
        reservationDAO.updateSigninById(reservation);
        Credit credit = new Credit();
        credit.setUid(reservation.getUid());
        credit.setReserve(0);
        credit.setCancel(0);
        credit.setSign(1);
        credit.setLate(0);
        credit.setViolate(0);
        credit.setTime(0);
        creditDAO.update(credit);
        Room room = new Room();
        Reservation bean = reservationDAO.getById(reservation.getId());
        room.setDate(bean.getDate());
        room.setNum(seatDAO.getTotal());
        room.setClosed(seatDAO.getTotalClosed());
        room.setReserve(0);
        room.setSign(1);
        room.setLate(0);
        room.setViolate(0);
        roomDAO.update(room);
    }

    public void signout(Reservation reservation){
        Reservation bean = reservationDAO.getById(reservation.getId());
        String signin = bean.getSignin().toString();
        Credit credit = new Credit();
        if (bean.getStatus()==5){
            reservation.setStatus(8);
        }else{
            reservation.setStatus(4);
        }
        reservationDAO.updateSignoutById(reservation);
        reservationDAO.updateStatusById(reservation);

        String signout = reservation.getSignout().toString();
        String[] si = signin.split(":");
        String[] so = signout.split(":");
        double sih = Double.parseDouble(si[0]);
        double sim = Double.parseDouble(si[1]);
        double soh = Double.parseDouble(so[0]);
        double som = Double.parseDouble(so[1]);
        double time = (soh-sih)+(som-sim)/60;
        double stime = (double)Math.round(time*100)/100;

        credit.setUid(reservation.getUid());
        credit.setReserve(0);
        credit.setCancel(0);
        credit.setLate(0);
        credit.setViolate(0);
        credit.setTime(stime);
        creditDAO.update(credit);
    }

    public void late(Reservation reservation){
        reservation.setStatus(5);
        reservationDAO.updateStatusById(reservation);
        reservationDAO.updateSigninById(reservation);
        Credit credit = new Credit();
        credit.setUid(reservation.getUid());
        credit.setReserve(0);
        credit.setCancel(0);
        credit.setSign(0);
        credit.setLate(1);
        credit.setViolate(0);
        credit.setTime(0);
        creditDAO.update(credit);
        Room room = new Room();
        Reservation bean = reservationDAO.getById(reservation.getId());
        room.setDate(bean.getDate());
        room.setNum(seatDAO.getTotal());
        room.setClosed(seatDAO.getTotalClosed());
        room.setReserve(0);
        room.setSign(0);
        room.setLate(1);
        room.setViolate(0);
        roomDAO.update(room);
    }

    public List<Reservation> findByPage(Reservation reservation,Page page){
        List<Reservation> reservations = reservationDAO.find(reservation,page.getStart(),page.getCount());
        return reservations;
    }

    public int totalFind(Reservation reservation){
        int total=0;
        total=(int) reservationDAO.getFindTotal(reservation);
        return total;
    }

    public List<Reservation> findByUIdByPage(Reservation reservation, Page page){
        List<Reservation> reservations = reservationDAO.findByUId(reservation,page.getStart(),page.getCount());
        return reservations;
    }

    public int totalFindByUId(Reservation reservation){
        int total=0;
        total=(int) reservationDAO.getFindByUIdTotal(reservation);
        return total;
    }
}

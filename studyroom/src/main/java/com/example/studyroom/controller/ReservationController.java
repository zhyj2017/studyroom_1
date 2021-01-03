package com.example.studyroom.controller;

import com.example.studyroom.dao.OpeningDAO;
import com.example.studyroom.pojo.*;
import com.example.studyroom.service.*;
import com.example.studyroom.util.Page;
import com.example.studyroom.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ReservationController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private OpeningDAO openingDAO;
    @Autowired
    private SeatService seatService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private CreditService creditService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private WarningService warningService;

    //预约选座界面
    @RequestMapping("/reserve")
    public String reserve(Model model, HttpSession session){
        String uid = session.getAttribute("userid").toString();
        Student student = new Student();
        student = studentService.getStudentById(uid);
        Opening opening = openingDAO.get();
        model.addAttribute("student",student);
        model.addAttribute("opening",opening);
        return "/user/reserve";
    }
    //预约选座界面2
    @RequestMapping(value = "/reserve/seats",method = RequestMethod.GET)
    public String reserveSeats(Model model, HttpSession session, @RequestParam String year, @RequestParam String month,
                               @RequestParam String day, @RequestParam String starttime, @RequestParam String endtime){
        String uid = session.getAttribute("userid").toString();
        Student student = new Student();
        student = studentService.getStudentById(uid);
        Opening opening = openingDAO.get();
        List<Integer> seats = seatService.getCloseSeatID();
        String x = year+"-"+month+"-"+day;
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat  hmsFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = null;
        Date x1 = null;
        Date x2 = null;
        try {
            date = ymdFormat.parse(x);
            x1 = hmsFormat.parse(starttime);
            x2 = hmsFormat.parse(endtime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.sql.Date date1 = new java.sql.Date(date.getTime());
        Time sTime = new Time(x1.getTime());
        Time eTime = new Time(x2.getTime());
        Reservation reservation = new Reservation();
        reservation.setDate(date1);
        reservation.setStarttime(sTime);
        reservation.setEndtime(eTime);
        List<Integer> reserveSeats = reservationService.getReservedSID(reservation);
        boolean isReserved = reservationService.isReserved(reservation,uid);
        boolean isWarning = warningService.isWarning(uid);

        model.addAttribute("student",student);
        model.addAttribute("opening",opening);
        model.addAttribute("seats",seats);
        model.addAttribute("reservation",reservation);
        model.addAttribute("reserveSeats",reserveSeats);
        model.addAttribute("isReserved",isReserved);
        model.addAttribute("isWarning",isWarning);
        return "/user/reserveSeats";
    }
    //学生选座
    @RequestMapping(value = "/reserve/add",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    @ResponseBody
    public Response reservation(@RequestParam String uid, @RequestParam int sid, @RequestParam String date,
                                @RequestParam String starttime, @RequestParam String endtime){
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat  hmsFormat = new SimpleDateFormat("HH:mm:ss");
        Date x = null;
        Date x1 = null;
        Date x2 = null;
        try {
            x = ymdFormat.parse(date);
            x1 = hmsFormat.parse(starttime);
            x2 = hmsFormat.parse(endtime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.sql.Date date1 = new java.sql.Date(x.getTime());
        Time sTime = new Time(x1.getTime());
        Time eTime = new Time(x2.getTime());
        Reservation reservation = new Reservation();
        reservation.setUid(uid);
        reservation.setSid(sid);
        reservation.setDate(date1);
        reservation.setStarttime(sTime);
        reservation.setEndtime(eTime);
        reservationService.addReservation(reservation);

        Credit credit = new Credit();
        credit.setUid(uid);
        credit.setReserve(1);
        credit.setCancel(0);
        credit.setSign(0);
        credit.setLate(0);
        credit.setViolate(0);
        credit.setTime(0);
        creditService.updateCredit(credit);

        Room room = new Room();
        room.setDate(date1);
        room.setReserve(1);
        room.setSign(0);
        room.setLate(0);
        room.setViolate(0);
        roomService.update(room);

        Response response = new Response();
        response.setFlag(true);
        return response;
    }

    //学生 预约记录界面
    @RequestMapping(value = "/reservation",method = RequestMethod.GET)
    public String list(Model model,@RequestParam(required = false)Integer start, HttpSession session){
        String uid = session.getAttribute("userid").toString();
        Student student = new Student();
        student = studentService.getStudentById(uid);
        Page page;
        if (start == null) {
            page = new Page(0,10);
        }else{
            page = new Page(start,10);
        }
        int total = reservationService.getTotalByUId(uid);
        page.setTotal(total);
        List<Reservation> reservations = reservationService.listByUIdByPage(uid,page);
        model.addAttribute("student",student);
        model.addAttribute("reservations",reservations);
        model.addAttribute("page",page);
        return "/user/reservation";
    }

    //学生 取消预约
    @RequestMapping(value = "/reservation/cancel",method = RequestMethod.GET)
    public String cancel1(@RequestParam Integer id,@RequestParam String uid){
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setUid(uid);
        reservationService.cancelAdmin(reservation);
        return "redirect:/reservation";
    }

    //学生 签到
    @RequestMapping(value = "/reservation/signin",method = RequestMethod.GET)
    public String signin(@RequestParam Integer id,@RequestParam String uid,@RequestParam String signin,@RequestParam Integer status){
        SimpleDateFormat  hmsFormat = new SimpleDateFormat("HH:mm:ss");
        Date x = null;
        try {
            x = hmsFormat.parse(signin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Time signinTime = new Time(x.getTime());
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setUid(uid);
        if (status == 3){
            reservation.setSignin(signinTime);
            reservationService.signin(reservation);
        }else if(status == 5){
            reservation.setSignin(signinTime);
            reservationService.late(reservation);
        }else if(status == 6){
            reservationService.breakAdmin(reservation);
        }
        return "redirect:/reservation";
    }

    //学生 签退
    @RequestMapping(value = "/reservation/signout",method = RequestMethod.GET)
    public String signout(@RequestParam Integer id,@RequestParam String uid,@RequestParam String signout){
        SimpleDateFormat  hmsFormat = new SimpleDateFormat("HH:mm:ss");
        Date x = null;
        try {
            x = hmsFormat.parse(signout);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Time signoutTime = new Time(x.getTime());
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setUid(uid);
        reservation.setSignout(signoutTime);
        reservationService.signout(reservation);
        return "redirect:/reservation";
    }

    //学生 查询
    @RequestMapping(value = "/reservation/search",method = RequestMethod.GET)
    public String search(Model model,@RequestParam(required = false)Integer start, HttpSession session,
                              @RequestParam(required = false) Integer sid, @RequestParam(required = false) String date,
                         @RequestParam(required = false) Integer status){
        String id = session.getAttribute("userid").toString();
        Student student = new Student();
        student = studentService.getStudentById(id);
        Page page;
        if (start == null) {
            page = new Page(0,10);
        }else{
            page = new Page(start,10);
        }
        Reservation reservation = new Reservation();
        reservation.setUid(id);
        if (sid == null){
            reservation.setSid(-1);
        }else {
            reservation.setSid(sid);
        }
        if (date == null || date == ""){
            reservation.setDate(null);
        }else{
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date x = null;
            try {
                x = ymdFormat.parse(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
            java.sql.Date date1 = new java.sql.Date(x.getTime());
            reservation.setDate(date1);
        }
        reservation.setStatus(status);
        int total = reservationService.totalFindByUId(reservation);
        page.setTotal(total);
        List<Reservation> reservations = reservationService.findByUIdByPage(reservation,page);
        model.addAttribute("student",student);
        model.addAttribute("reservations",reservations);
        model.addAttribute("page",page);
        model.addAttribute("condition",reservation);
        return "user/reservationSearch";
    }

    //管理员 选座记录管理
    @RequestMapping(value = "/chosen/reservations",method = RequestMethod.GET)
    public String listAll(Model model,@RequestParam(required = false)Integer start, HttpSession session){
        String adminid = session.getAttribute("adminid").toString();
        Administrator administrator = new Administrator();
        administrator = administratorService.getAdministratorById(adminid);
        Page page;
        if (start == null) {
            page = new Page(0,10);
        }else{
            page = new Page(start,10);
        }
        int total = reservationService.getTotalAll();
        page.setTotal(total);
        List<Reservation> reservations = reservationService.listAllByPage(page);
        model.addAttribute("administrator",administrator);
        model.addAttribute("reservations",reservations);
        model.addAttribute("page",page);
        return "/admin/reservations";
    }

    //管理员  设置取消预约
    @RequestMapping(value = "/chosen/cancel",method = RequestMethod.GET)
    public String cancel(@RequestParam Integer id,@RequestParam String uid){
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setUid(uid);
        reservationService.cancelAdmin(reservation);
        return "redirect:/chosen/reservations";
    }

    //管理员  设置恢复预约
    @RequestMapping(value = "/chosen/resume",method = RequestMethod.GET)
    public String resume(@RequestParam Integer id,@RequestParam String uid){
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setUid(uid);
        reservationService.resumeAdmin(reservation);
        return "redirect:/chosen/reservations";
    }

    //管理员  设置完成预约
    @RequestMapping(value = "/chosen/finish",method = RequestMethod.GET)
    public String finish(@RequestParam Integer id,@RequestParam String uid){
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setUid(uid);
        reservationService.finishAdmin(reservation);
        return "redirect:/chosen/reservations";
    }

    //管理员  设置违约
    @RequestMapping(value = "/chosen/break",method = RequestMethod.GET)
    public String broken(@RequestParam Integer id,@RequestParam String uid){
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setUid(uid);
        reservationService.breakAdmin(reservation);
        return "redirect:/chosen/reservations";
    }

    //管理员 查询
    @RequestMapping(value = "/chosen/search",method = RequestMethod.GET)
    public String searchAdmin(Model model,@RequestParam(required = false)Integer start, HttpSession session,
                         @RequestParam(required = false) String uid, @RequestParam(required = false) Integer sid,
                         @RequestParam(required = false) String date, @RequestParam(required = false) Integer status){
        String adminid = session.getAttribute("adminid").toString();
        Administrator administrator = new Administrator();
        administrator = administratorService.getAdministratorById(adminid);
        Page page;
        if (start == null) {
            page = new Page(0,10);
        }else{
            page = new Page(start,10);
        }
        Reservation reservation = new Reservation();
        reservation.setUid(uid);
        if (sid == null){
            reservation.setSid(-1);
        }else {
            reservation.setSid(sid);
        }
        if (date == null || date == ""){
            reservation.setDate(null);
        }else{
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date x = null;
            try {
                x = ymdFormat.parse(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
            java.sql.Date date1 = new java.sql.Date(x.getTime());
            reservation.setDate(date1);

        }
        reservation.setStatus(status);
        int total = reservationService.totalFind(reservation);
        page.setTotal(total);
        List<Reservation> reservations = reservationService.findByPage(reservation,page);
        model.addAttribute("administrator",administrator);
        model.addAttribute("reservations",reservations);
        model.addAttribute("page",page);
        model.addAttribute("condition",reservation);
        return "admin/reservationsSearch";
    }

}

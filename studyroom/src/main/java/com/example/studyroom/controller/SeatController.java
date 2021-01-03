package com.example.studyroom.controller;

import com.example.studyroom.pojo.Administrator;
import com.example.studyroom.pojo.Seat;
import com.example.studyroom.service.AdministratorService;
import com.example.studyroom.service.SeatService;
import com.example.studyroom.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class SeatController {
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private SeatService seatService;

    @RequestMapping(value = "/seats",method= RequestMethod.GET)
    public String list(Model model, @RequestParam(required = false)Integer start, HttpSession session){
        String adminid = session.getAttribute("adminid").toString();
        Administrator administrator = new Administrator();
        administrator = administratorService.getAdministratorById(adminid);
        Page page;
        if (start == null) {
            page = new Page(0,10);
        }else{
            page = new Page(start,10);
        }
        int total = seatService.getTotal();
        page.setTotal(total);
        List<Seat> seats = seatService.listByPage(page);
        model.addAttribute("administrator",administrator);
        model.addAttribute("seats",seats);
        model.addAttribute("page",page);
        return "/admin/seats";
    }

    @RequestMapping(value = "/seats/open",method= RequestMethod.GET)
    public String open(@RequestParam Integer id){
        Seat seat = new Seat();
        seat.setId(id);
        seatService.openSeat(seat);
        return "redirect:/seats";
    }

    @RequestMapping(value = "/seats/close",method= RequestMethod.GET)
    public String close(@RequestParam Integer id){
        Seat seat = new Seat();
        seat.setId(id);
        seatService.closeSeat(seat);
        return "redirect:/seats";
    }

    @RequestMapping(value = "/seats/search",method= RequestMethod.GET)
    public String search(Model model, @RequestParam(required = false)Integer start, HttpSession session,
    @RequestParam(required = false)Integer id, @RequestParam(required = false)Integer status){
        String adminid = session.getAttribute("adminid").toString();
        Administrator administrator = new Administrator();
        administrator = administratorService.getAdministratorById(adminid);
        Page page;
        if (start == null) {
            page = new Page(0,10);
        }else{
            page = new Page(start,10);
        }
        Seat seat = new Seat();

        if (id == null){
            seat.setId(-1);
        }else{
            seat.setId(id);
        }
        if (status == null){
            seat.setStatus(-1);
        }else {
            seat.setStatus(status);
        }
        int total = seatService.totalFind(seat);
        page.setTotal(total);
        List<Seat> seats = seatService.findByPage(seat,page);
        model.addAttribute("administrator",administrator);
        model.addAttribute("seats",seats);
        model.addAttribute("page",page);
        model.addAttribute("condition",seat);
        return "/admin/seatsSearch";
    }
}

package com.example.studyroom.controller;

import com.example.studyroom.dao.OpeningDAO;
import com.example.studyroom.pojo.*;
import com.example.studyroom.service.AdministratorService;
import com.example.studyroom.service.CreditService;
import com.example.studyroom.service.RoomService;
import com.example.studyroom.service.WarningService;
import com.example.studyroom.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class StatisticController {

    @Autowired
    private CreditService creditService;
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private WarningService warningService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private OpeningDAO openingDAO;

    //管理员 信誉数据管理界面
    @RequestMapping(value = "/credits",method= RequestMethod.GET)
    public String creditList(Model model, @RequestParam(required = false)Integer start, HttpSession session){
        String adminid = session.getAttribute("adminid").toString();
        Administrator administrator = new Administrator();
        administrator = administratorService.getAdministratorById(adminid);
        Page page;
        if (start == null) {
            page = new Page(0,10);
        }else{
            page = new Page(start,10);
        }
        int total = creditService.getTotal();
        page.setTotal(total);
        List<Credit> credits = creditService.listByPage(page);
        for (int i=0; i<credits.size();i++){
            Credit bean = credits.get(i);
            if (warningService.isWarning(bean.getUid())==true){
                credits.get(i).setWarning(1);
            }else{
                credits.get(i).setWarning(0);
            }
        }
        model.addAttribute("administrator",administrator);
        model.addAttribute("credits",credits);
        model.addAttribute("page",page);
        return "admin/credits";
    }

    //管理员 发出警告
    @RequestMapping(value = "/credits/warning",method = RequestMethod.GET)
    public String warning(@RequestParam String uid){
        Warning warning = new Warning();
        warning.setUid(uid);
        Date date = new Date();
        warning.setDatetime(date);
        warningService.add(warning);
        return "redirect:/credits";
    }

    //管理员  取消警告
    @RequestMapping(value = "/credits/cancel",method = RequestMethod.GET)
    public String cancel(@RequestParam String uid){
        Warning warning = new Warning();
        warning.setUid(uid);
        warningService.delete(warning);
        return "redirect:/credits";
    }

    //管理员 查找信誉信息
    @RequestMapping(value = "/credits/search",method= RequestMethod.GET)
    public String creditSearch(Model model, @RequestParam(required = false)Integer start,@RequestParam(required = false)String uid,
                               @RequestParam(required = false)Integer reserve1,@RequestParam(required = false)Integer reserve2,
                               @RequestParam(required = false)Integer cancel1,@RequestParam(required = false)Integer cancel2,
                               @RequestParam(required = false)Integer sign1,@RequestParam(required = false)Integer sign2,
                               @RequestParam(required = false)Integer late1,@RequestParam(required = false)Integer late2,
                               @RequestParam(required = false)Integer violate1,@RequestParam(required = false)Integer violate2,
                               @RequestParam(required = false)Double time1,@RequestParam(required = false)Double time2,HttpSession session){
        String adminid = session.getAttribute("adminid").toString();
        Administrator administrator = new Administrator();
        administrator = administratorService.getAdministratorById(adminid);
        Page page;
        if (start == null) {
            page = new Page(0,10);
        }else{
            page = new Page(start,10);
        }
        Credit credit1 = new Credit();
        Credit credit2 = new Credit();
        credit1.setUid(uid);
        credit2.setUid(uid);
        if (reserve1 == null){
            credit1.setReserve(-1);
        }else{
            credit1.setReserve(reserve1);
        }
        if (cancel1 == null){
            credit1.setCancel(-1);
        }else{
            credit1.setCancel(cancel1);
        }
        if (sign1 == null){
            credit1.setSign(-1);
        }else{
            credit1.setSign(sign1);
        }
        if (late1 == null){
            credit1.setLate(-1);
        }else{
            credit1.setLate(late1);
        }
        if (violate1 == null){
            credit1.setViolate(-1);
        }else{
            credit1.setViolate(violate1);
        }
        if (time1 == null){
            credit1.setTime(-1);
        }else{
            credit1.setTime(time1);
        }

        if (reserve2 == null){
            credit2.setReserve(-1);
        }else{
            credit2.setReserve(reserve2);
        }
        if (cancel2 == null){
            credit2.setCancel(-1);
        }else{
            credit2.setCancel(cancel2);
        }
        if (sign2 == null){
            credit2.setSign(-1);
        }else{
            credit2.setSign(sign2);
        }
        if (late2 == null){
            credit2.setLate(-1);
        }else{
            credit2.setLate(late2);
        }
        if (violate2 == null){
            credit2.setViolate(-1);
        }else{
            credit2.setViolate(violate2);
        }
        if (time2 == null){
            credit2.setTime(-1);
        }else{
            credit2.setTime(time2);
        }

        int total = creditService.getFindTotal(credit1,credit2);
        page.setTotal(total);
        List<Credit> credits = creditService.findByPage(credit1,credit2,page);
        for (int i=0; i<credits.size();i++){
            Credit bean = credits.get(i);
            if (warningService.isWarning(bean.getUid())==true){
                credits.get(i).setWarning(1);
            }else{
                credits.get(i).setWarning(0);
            }
        }
        model.addAttribute("administrator",administrator);
        model.addAttribute("credits",credits);
        model.addAttribute("page",page);
        model.addAttribute("condition1",credit1);
        model.addAttribute("condition2",credit2);
        return "admin/creditsSearch";
    }


    //管理员  自习室情况界面
    @RequestMapping(value = "/rooms",method= RequestMethod.GET)
    public String roomList(Model model, @RequestParam(required = false)Integer start, HttpSession session){
        String adminid = session.getAttribute("adminid").toString();
        Administrator administrator = new Administrator();
        administrator = administratorService.getAdministratorById(adminid);
        Page page;
        if (start == null) {
            page = new Page(0,10);
        }else{
            page = new Page(start,10);
        }
        int total = roomService.getTotal();
        page.setTotal(total);
        List<Room> rooms = roomService.listByPage(page);
        Opening opening = openingDAO.get();
        model.addAttribute("administrator",administrator);
        model.addAttribute("rooms",rooms);
        model.addAttribute("opening",opening);
        model.addAttribute("page",page);
        return "admin/rooms";
    }

    // 管理员 修改开馆闭馆时间
    @RequestMapping(value = "/openUpdate",method = RequestMethod.GET)
    public String openUpdate(@RequestParam Integer open, @RequestParam Integer close){
        Opening opening = new Opening();
        opening.setOpen(open);
        opening.setClose(close);
        openingDAO.update(opening);
        return "redirect:/rooms";
    }

    //管理员 查找自习室信息
    @RequestMapping(value = "/rooms/search",method= RequestMethod.GET)
    public String roomSearch(Model model, @RequestParam(required = false)Integer start,@RequestParam(required = false)String date,
                               @RequestParam(required = false)Integer num1,@RequestParam(required = false)Integer num2,
                               @RequestParam(required = false)Integer closed1,@RequestParam(required = false)Integer closed2,
                               @RequestParam(required = false)Integer reserve1,@RequestParam(required = false)Integer reserve2,
                               @RequestParam(required = false)Integer sign1,@RequestParam(required = false)Integer sign2,
                               @RequestParam(required = false)Integer late1,@RequestParam(required = false)Integer late2,
                               @RequestParam(required = false)Integer violate1,@RequestParam(required = false)Integer violate2,HttpSession session){
        String adminid = session.getAttribute("adminid").toString();
        Administrator administrator = new Administrator();
        administrator = administratorService.getAdministratorById(adminid);
        Page page;
        if (start == null) {
            page = new Page(0,10);
        }else{
            page = new Page(start,10);
        }
        Room room1 = new Room();
        Room room2 = new Room();

        if (date == null || date == ""){
            room1.setDate(null);
            room2.setDate(null);
        }else{
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date x = null;
            try {
                x = ymdFormat.parse(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
            java.sql.Date date1 = new java.sql.Date(x.getTime());
            room1.setDate(date1);
            room2.setDate(date1);
        }

        if (num1 == null){
            room1.setNum(-1);
        }else{
            room1.setNum(num1);
        }
        if (closed1 == null){
            room1.setClosed(-1);
        }else{
            room1.setClosed(closed1);
        }
        if (reserve1 == null){
            room1.setReserve(-1);
        }else{
            room1.setReserve(reserve1);
        }
        if (sign1 == null){
            room1.setSign(-1);
        }else{
            room1.setSign(sign1);
        }
        if (late1 == null){
            room1.setLate(-1);
        }else{
            room1.setLate(late1);
        }
        if (violate1 == null){
            room1.setViolate(-1);
        }else{
            room1.setViolate(violate1);
        }

        if (num2 == null){
            room2.setNum(-1);
        }else{
            room2.setNum(num2);
        }
        if (closed2 == null){
            room2.setClosed(-1);
        }else{
            room2.setClosed(closed2);
        }
        if (reserve2 == null){
            room2.setReserve(-1);
        }else{
            room2.setReserve(reserve2);
        }
        if (sign2 == null){
            room2.setSign(-1);
        }else{
            room2.setSign(sign2);
        }
        if (late2 == null){
            room2.setLate(-1);
        }else{
            room2.setLate(late2);
        }
        if (violate2 == null){
            room2.setViolate(-1);
        }else{
            room2.setViolate(violate2);
        }



        int total = roomService.getFindTotal(room1,room2);
        page.setTotal(total);
        List<Room> rooms = roomService.findByPage(room1,room2,page);

        Opening opening = openingDAO.get();
        model.addAttribute("administrator",administrator);
        model.addAttribute("rooms",rooms);
        model.addAttribute("page",page);
        model.addAttribute("condition1",room1);
        model.addAttribute("condition2",room2);
        model.addAttribute("opening",opening);
        return "admin/roomsSearch";
    }
}

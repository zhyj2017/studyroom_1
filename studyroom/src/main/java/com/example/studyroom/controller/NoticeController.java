package com.example.studyroom.controller;

import com.example.studyroom.pojo.Administrator;
import com.example.studyroom.pojo.Notice;
import com.example.studyroom.pojo.Student;
import com.example.studyroom.service.AdministratorService;
import com.example.studyroom.service.NoticeService;
import com.example.studyroom.service.StudentService;
import com.example.studyroom.util.Page;
import com.example.studyroom.util.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class NoticeController {
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private StudentService studentService;


    //管理员 通知管理界面
    @RequestMapping(value = "/notices/list",method = RequestMethod.GET)
    public String listAll(Model model, @RequestParam(required = false)Integer start, HttpSession session){
        String adminid = session.getAttribute("adminid").toString();
        Administrator administrator = new Administrator();
        administrator = administratorService.getAdministratorById(adminid);
        Page page;
        if (start == null) {
            page = new Page(0,10);
        }else{
            page = new Page(start,10);
        }
        int total = noticeService.getTotal();
        page.setTotal(total);
        List<Notice> notices = noticeService.listByPage(page);
        model.addAttribute("administrator",administrator);
        model.addAttribute("notices",notices);
        model.addAttribute("page",page);
        return "/admin/notices";
    }

    //管理员 新建通知界面
    @RequestMapping(value = "/notices/create",method = RequestMethod.GET)
    public String create(Model model, HttpSession session){
        String adminid = session.getAttribute("adminid").toString();
        Administrator administrator = new Administrator();
        administrator = administratorService.getAdministratorById(adminid);
        model.addAttribute("administrator",administrator);
        return "admin/noticeCreate";
    }

    //管理员 新发布通知
    @RequestMapping(value = "/notices/add",method = RequestMethod.POST)
    @ResponseBody
    public String add(@RequestParam String title, @RequestParam String text){
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date nowdate = new Date();
        String date = ymdFormat.format(nowdate);
        Date x = null;
        try {
            x = ymdFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.sql.Date date1 = new java.sql.Date(x.getTime());
        TextUtil textUtil = new TextUtil();
        String context = textUtil.Text2Html(text);
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setText(context);
        notice.setDate(date1);
        noticeService.addNotice(notice);
        return "/admin/notices";
    }

    //管理员 修改通知界面
    @RequestMapping(value = "/notices/update",method = RequestMethod.GET)
    public String update(Model model, HttpSession session, @RequestParam Integer id){
        String adminid = session.getAttribute("adminid").toString();
        Administrator administrator = new Administrator();
        administrator = administratorService.getAdministratorById(adminid);
        Notice notice = noticeService.getNoticeById(id);
        TextUtil textUtil = new TextUtil();
        String context = textUtil.Html2Text(notice.getText());
        notice.setText(context);
        model.addAttribute("administrator",administrator);
        model.addAttribute("notice",notice);
        return "admin/noticeUpdate";
    }

    //管理员 修改通知
    @RequestMapping(value = "/notices/updating",method = RequestMethod.POST)
    @ResponseBody
    public String updating(@RequestParam Integer id, @RequestParam String title, @RequestParam String text){
        TextUtil textUtil = new TextUtil();
        String context = textUtil.Text2Html(text);
        Notice notice = new Notice();
        notice.setId(id);
        notice.setTitle(title);
        notice.setText(context);
        noticeService.updateNotice(notice);
        return "/admin/notices";
    }

    //管理员 删除通知
    @RequestMapping(value = "/notices/delete",method = RequestMethod.GET)
    public String delete(@RequestParam Integer id){
        noticeService.delete(id);
        return "redirect:/notices/list";
    }

    //管理员 查看通知详情
    @RequestMapping(value = "/notices/detail",method = RequestMethod.GET)
    public String detailAll(Model model, HttpSession session, @RequestParam Integer id){
        String adminid = session.getAttribute("adminid").toString();
        Administrator administrator = new Administrator();
        administrator = administratorService.getAdministratorById(adminid);
        Notice notice = noticeService.getNoticeById(id);
        TextUtil textUtil = new TextUtil();
        String context = textUtil.Html2Text(notice.getText());
        notice.setText(context);
        model.addAttribute("administrator",administrator);
        model.addAttribute("notice",notice);
        return "admin/noticeDetails";
    }

    //管理员 搜索界面
    @RequestMapping(value = "/notices/search",method = RequestMethod.GET)
    public String searchAll(Model model, @RequestParam(required = false)Integer start, HttpSession session,
                            @RequestParam String title, @RequestParam String date){
        String adminid = session.getAttribute("adminid").toString();
        Administrator administrator = new Administrator();
        administrator = administratorService.getAdministratorById(adminid);
        Page page;
        if (start == null) {
            page = new Page(0,10);
        }else{
            page = new Page(start,10);
        }
        Notice notice = new Notice();
        notice.setTitle(title);
        if (date == null || date == ""){
            notice.setDate(null);
        }else{
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date x = null;
            try {
                x = ymdFormat.parse(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
            java.sql.Date date1 = new java.sql.Date(x.getTime());
            notice.setDate(date1);

        }
        int total = noticeService.totalFind(notice);
        page.setTotal(total);
        List<Notice> notices = noticeService.findByPage(notice,page);
        model.addAttribute("administrator",administrator);
        model.addAttribute("notices",notices);
        model.addAttribute("page",page);
        model.addAttribute("condition",notice);
        return "admin/noticesSearch";
    }


    //学生 通知查看界面
    @RequestMapping(value = "/notice/list",method = RequestMethod.GET)
    public String list(Model model, @RequestParam(required = false)Integer start, HttpSession session){
        String userid = session.getAttribute("userid").toString();
        Student student = new Student();
        student = studentService.getStudentById(userid);
        Page page;
        if (start == null) {
            page = new Page(0,10);
        }else{
            page = new Page(start,10);
        }
        int total = noticeService.getTotal();
        page.setTotal(total);
        List<Notice> notices = noticeService.listByPage(page);
        model.addAttribute("student",student);
        model.addAttribute("notices",notices);
        model.addAttribute("page",page);
        return "/user/notice";
    }

    //学生 查看通知详情
    @RequestMapping(value = "/notice/detail",method = RequestMethod.GET)
    public String detail(Model model, HttpSession session, @RequestParam Integer id){
        String userid = session.getAttribute("userid").toString();
        Student student = new Student();
        student = studentService.getStudentById(userid);
        Notice notice = noticeService.getNoticeById(id);
        TextUtil textUtil = new TextUtil();
        String context = textUtil.Html2Text(notice.getText());
        notice.setText(context);
        model.addAttribute("student",student);
        model.addAttribute("notice",notice);
        return "user/noticeDetail";
    }

    //学生 搜索界面
    @RequestMapping(value = "/notice/search",method = RequestMethod.GET)
    public String search(Model model, @RequestParam(required = false)Integer start, HttpSession session,
                            @RequestParam String title, @RequestParam String date){
        String userid = session.getAttribute("userid").toString();
        Student student = new Student();
        student = studentService.getStudentById(userid);
        Page page;
        if (start == null) {
            page = new Page(0,10);
        }else{
            page = new Page(start,10);
        }
        Notice notice = new Notice();
        notice.setTitle(title);
        if (date == null || date == ""){
            notice.setDate(null);
        }else{
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date x = null;
            try {
                x = ymdFormat.parse(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
            java.sql.Date date1 = new java.sql.Date(x.getTime());
            notice.setDate(date1);

        }
        int total = noticeService.totalFind(notice);
        page.setTotal(total);
        List<Notice> notices = noticeService.findByPage(notice,page);
        model.addAttribute("student",student);
        model.addAttribute("notices",notices);
        model.addAttribute("page",page);
        model.addAttribute("condition",notice);
        return "user/noticeSearch";
    }
}

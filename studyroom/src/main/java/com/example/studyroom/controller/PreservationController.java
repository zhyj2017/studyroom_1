package com.example.studyroom.controller;

import com.example.studyroom.pojo.Administrator;
import com.example.studyroom.pojo.Student;
import com.example.studyroom.service.AdministratorService;
import com.example.studyroom.service.StudentService;
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
import java.util.ArrayList;
import java.util.List;

@Controller
public class PreservationController {
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private StudentService studentService;

    //管理员 防疫溯源界面
    @RequestMapping(value = "/preservation",method= RequestMethod.GET)
    public String preservation(Model model, HttpSession session){
        String adminid = session.getAttribute("adminid").toString();
        Administrator administrator = new Administrator();
        administrator = administratorService.getAdministratorById(adminid);
        model.addAttribute("administrator",administrator);
        return "admin/preservation";
    }

    //判断学生是否存在
    @RequestMapping(value = "/isSearch",produces = "application/json;charset=utf-8",method= RequestMethod.GET)
    @ResponseBody
    public Response isSearch(@RequestParam String id, @RequestParam String name){
        Response response = new Response();
        boolean search = studentService.searchCheck(id,name);
        if (search){
            response.setFlag(true);
        }else{
            response.setFlag(false);
        }
        return response;
    }

    //管理员 防疫溯源查询
    @RequestMapping(value = "/preservation/search",method= RequestMethod.GET)
    public String preservationSearch(Model model,@RequestParam(required = false)Integer start,@RequestParam String id,
                                     @RequestParam String name, HttpSession session){
        String adminid = session.getAttribute("adminid").toString();
        Administrator administrator = new Administrator();
        administrator = administratorService.getAdministratorById(adminid);
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        List<Student> students = studentService.preservationSearch(id);
        Page page;
        if (start == null) {
            page = new Page(0,10);
        }else{
            page = new Page(start,10);
        }
        page.setTotal(students.size());
        List<Student> list = new ArrayList<>();
        if (page.getStart()*page.getCount()+page.getCount()>students.size()){
            list = students.subList(page.getStart()*page.getCount(),students.size());
        }else{
            list = students.subList(page.getStart()*page.getCount(),page.getStart()*page.getCount()+page.getCount());
        }

        model.addAttribute("administrator",administrator);
        model.addAttribute("students",list);
        model.addAttribute("condition",student);
        model.addAttribute("page",page);
        return "admin/preservationSearch";
    }
}

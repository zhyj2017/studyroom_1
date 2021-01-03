package com.example.studyroom.controller;

import com.example.studyroom.service.AdministratorService;
import com.example.studyroom.service.StudentService;
import com.example.studyroom.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller

public class LoginController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private AdministratorService administratorService;

    //学生登录界面
    @RequestMapping("/index")
    public String index(){
        return "/user/login";
    }

    //学生登录判断账号密码是否正确
    @RequestMapping(value = "/islogin",produces = "application/json;charset=utf-8",method= RequestMethod.GET)
    @ResponseBody
    public Response isLogin(@RequestParam String id,@RequestParam String password){
        Response response = new Response();
        boolean login =studentService.loginCheck(id,password);
        if (login){
            response.setFlag(true);
        }else{
            response.setFlag(false);
        }
        return response;
    }

    //学生登录接口
    @RequestMapping(value = "/login",method= RequestMethod.POST)
    public String login(@RequestParam String userid,@RequestParam String password, HttpSession session){
        session.setAttribute("userid",userid);
        return "redirect:/user/personality";
    }

    //学生退出接口
    @RequestMapping(value="/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "/user/login";
    }

    //管理员登录界面
    @RequestMapping("/admin/index")
    public String adminIndex(){
        return "/admin/login";
    }

    //学生管理界面
    @RequestMapping("/admin/student")
    public String adminStudent(){
        return "/admin/student";
    }

    //管理员登录判断账号密码是否正确
    @RequestMapping(value = "/admin/islogin",produces = "application/json;charset=utf-8",method= RequestMethod.GET)
    @ResponseBody
    public Response isAdminLogin(@RequestParam String id,@RequestParam String password){
        Response response = new Response();
        boolean login =administratorService.loginCheck(id,password);
        if (login){
            response.setFlag(true);
        }else{
            response.setFlag(false);
        }
        return response;
    }

    //管理员登录接口
    @RequestMapping(value = "/admin/login",method= RequestMethod.POST)
    public String adminLogin(@RequestParam String adminid,@RequestParam String password, HttpSession session){
        session.setAttribute("adminid",adminid);
        return "redirect:/students";
    }

    //管理员退出接口
    @RequestMapping(value="/admin/logout")
    public String adminLogout(HttpSession session){
        session.invalidate();
        return "/admin/login";
    }
}

package com.example.studyroom.controller;

import com.example.studyroom.pojo.Administrator;
import com.example.studyroom.pojo.Credit;
import com.example.studyroom.pojo.Student;
import com.example.studyroom.service.AdministratorService;
import com.example.studyroom.service.CreditService;
import com.example.studyroom.service.StudentService;
import com.example.studyroom.util.Page;
import com.example.studyroom.util.Response;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private CreditService creditService;
    @Autowired
    private AdministratorService administratorService;

//    @RequestMapping("/personality")
//    public String personality(){
//        return "/user/personality";
//    }
    //学生个人中心界面
    @RequestMapping(value = "/user/personality",method= RequestMethod.GET)
    public String personality(Model model, HttpSession session){
        String uid = session.getAttribute("userid").toString();
        Student student = new Student();
        Credit credit = new Credit();
        student = studentService.getStudentById(uid);
        credit = creditService.getCreditByUId(uid);
        model.addAttribute("student",student);
        model.addAttribute("credit",credit);
        return "/user/personality";
    }

    //学生修改个人信息
    @RequestMapping(value = "/user/update",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response update(@RequestParam String userid, @RequestParam String name, @RequestParam String password,
                           @RequestParam String cellphone, @RequestParam String confirmPassword, @RequestParam String dormitory){
        Student student = new Student();
        student.setId(userid);
        student.setName(name);
        student.setPassword(password);
        student.setCellphone(cellphone);
        student.setDormitory(dormitory);
        studentService.updateStudent(student);
        Response response = new Response();
        response.setFlag(true);
        response.setData(student);
        return response;
    }

    //管理员 学生信息界面
    @RequestMapping(value = "/students",method= RequestMethod.GET)
    public String list(Model model,@RequestParam(required = false)Integer start, HttpSession session){
        String adminid = session.getAttribute("adminid").toString();
        Administrator administrator = new Administrator();
        administrator = administratorService.getAdministratorById(adminid);
        Page page;
        if (start == null) {
            page = new Page(0,10);
        }else{
            page = new Page(start,10);
        }
        int total = studentService.total();
        page.setTotal(total);
        List<Student> students = studentService.listByPage(page);
//        Student student = new Student();
//        PageHelper.startPage(pn,1);
//        List<Student> students = studentService.find(student);
//        PageInfo<Student> p = new PageInfo<>(students);
        model.addAttribute("administrator",administrator);
        model.addAttribute("students",students);
        model.addAttribute("page",page);
        return "admin/student";
    }

    //删除学生
    @RequestMapping(value = "/students/delete",method= RequestMethod.GET)
    public String delete(@RequestParam String id){
        studentService.delete(id);
        return "redirect:/students";
    }

    //重置密码
    @RequestMapping(value = "/students/reset",method= RequestMethod.GET)
    public String reset(@RequestParam String id){
        studentService.reset(id);
        return "redirect:/students";
    }

    //管理员获得指定学生的信息
    @RequestMapping(value = "/students/details",method= RequestMethod.GET)
    @ResponseBody
    public Response getDetails(@RequestParam String id){
        Response response = new Response();
        Student student = studentService.getStudentById(id);
        Map<String,Object> map = new HashMap<>();
        map.put("student",student);
        map.put("id",id);
        response.setFlag(true);
        response.setData(map);
        return response;
    }

    //管理员修改指定学生信息
    @RequestMapping(value = "/students/update",produces = "application/json;charset=utf-8",method= RequestMethod.POST)
    @ResponseBody
    public Response updateDeatils(@RequestParam String userid, @RequestParam String name,
                           @RequestParam String cellphone, @RequestParam String dormitory){
        Student student = new Student();
        student.setId(userid);
        student.setName(name);
        student.setCellphone(cellphone);
        student.setDormitory(dormitory);
        studentService.updateStudents(student);
        Response response = new Response();
        response.setFlag(true);
        response.setData(student);
        return response;
    }

    //管理员 查找学生信息
    @RequestMapping(value = "/students/search",method= RequestMethod.GET)
    public String search(Model model,@RequestParam(required = false)Integer start,
                         @RequestParam(required = false)String id, @RequestParam(required = false)String name,
                         @RequestParam(required = false)String cellphone, @RequestParam(required = false)String dormitory,HttpSession session){
        String adminid = session.getAttribute("adminid").toString();
        Administrator administrator = new Administrator();
        administrator = administratorService.getAdministratorById(adminid);
        Page page;
        if (start == null) {
            page = new Page(0,1);
        }else{
            page = new Page(start,1);
        }
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setCellphone(cellphone);
        student.setDormitory(dormitory);
        int total = studentService.totalFind(student);
        page.setTotal(total);
        List<Student> students = studentService.findByPage(student,page);
        model.addAttribute("administrator",administrator);
        model.addAttribute("students",students);
        model.addAttribute("page",page);
        model.addAttribute("condition",student);
        return "admin/studentSearch";
    }

}

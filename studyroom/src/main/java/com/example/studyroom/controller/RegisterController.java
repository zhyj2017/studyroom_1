package com.example.studyroom.controller;

import com.example.studyroom.pojo.Credit;
import com.example.studyroom.pojo.Student;
import com.example.studyroom.service.CreditService;
import com.example.studyroom.service.StudentService;
import com.example.studyroom.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegisterController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private CreditService creditService;

    @RequestMapping("/register")
    public String register(){
        return "/user/register";
    }

    @RequestMapping(value = "/isregist",produces = "application/json;charset=utf-8",method= RequestMethod.GET)
    @ResponseBody
    public Response isRegist(@RequestParam String id, @RequestParam String cellphone){
        Response response = new Response();
        int regist =studentService.registCheck(id,cellphone);
        if (regist == 1){
            response.setMessage("OK");
        }else if (regist == 0){
            response.setMessage("cellphone");
        }else if (regist == -1){
            response.setMessage("id");
        }
        return response;
    }

    @RequestMapping(value = "/regist",method= RequestMethod.POST)
    public String regist(@RequestParam String userid, @RequestParam String name, @RequestParam String password,
                         @RequestParam String cellphone, @RequestParam String confirmPassword, @RequestParam String dormitory){
        Student student = new Student();
        student.setId(userid);
        student.setName(name);
        student.setPassword(password);
        student.setCellphone(cellphone);
        student.setDormitory(dormitory);
        studentService.addStudent(student);
        Credit credit = new Credit();
        credit.setId(1);
        credit.setUid(userid);
        credit.setReserve(0);
        credit.setCancel(0);
        credit.setSign(0);
        credit.setLate(0);
        credit.setViolate(0);
        credit.setTime(0);
        creditService.addCredit(credit);
        return "redirect:/user/login";
    }
}

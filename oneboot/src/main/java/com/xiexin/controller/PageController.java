package com.xiexin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class PageController {  ///page/studentList
    @RequestMapping("/studentList")
    public String studentList(){
        return "studentlist";
    }
    @RequestMapping("/login") //page/login
    public String login() {
        return "login";
    }
    @RequestMapping("/add")
    public String add(){
        return "add";
    }
}

package mh.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApplicationController {

    @RequestMapping("/")
    public String main(){
        return "index";
    }

    @RequestMapping("/index.html")
    public String index(){
        return "index";
    }

    @RequestMapping("/login.html")
    public String login(){
        return "login";
    }

    @RequestMapping("/page.html")
    public String page(){
        return "page";
    }

    @RequestMapping("/page_user.html")
    public String pageUser(){
        return "page_user";
    }

    @RequestMapping("/page_admin.html")
    public String pageAdmin(){
        return "page_admin";
    }

}

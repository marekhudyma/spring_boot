package mh.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApplicationController {

    @RequestMapping("/")
    public String main(){
        return "index";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/page")
    public String page(){
        return "page";
    }

    @RequestMapping("/page_user")
    public String pageUser(){
        return "page_user";
    }

    @RequestMapping("/page_admin")
    public String pageAdmin(){
        return "page_admin";
    }

    @RequestMapping("/404")
    public String page404(){
        return "404";
    }

    @RequestMapping("/403")
    public String page403(){
        return "403";
    }

}

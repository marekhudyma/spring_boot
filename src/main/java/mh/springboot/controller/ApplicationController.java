package mh.springboot.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApplicationController implements ErrorController {

    public static final String ERROR_PATH = "/error";

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

    @RequestMapping(ERROR_PATH)
    public String error(){
        return ERROR_PATH;
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}

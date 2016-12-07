package mh.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionTemplate {

    @RequestMapping("/exception")
    public String onLoad(Model model){
        if(1==1) {
            throw new NullPointerException("Emulated exception");
        }
        return "exception";
    }
}

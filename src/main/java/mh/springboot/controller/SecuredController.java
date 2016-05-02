package mh.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecuredController {

    @RequestMapping("/secured")
    public String secured(){
        return "secured";
    }

}

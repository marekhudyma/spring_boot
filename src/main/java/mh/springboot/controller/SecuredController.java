package mh.springboot.controller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class SecuredController {

    @RequestMapping("/secured")
    public String secured(Model model, Principal principal){
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else if(principal instanceof OAuth2Authentication) {
            username = principal.getName(); //number of Facebook User
        }

        model.addAttribute("username", username);

        return "secured";
    }

}

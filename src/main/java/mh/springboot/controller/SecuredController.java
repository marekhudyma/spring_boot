package mh.springboot.controller;

import mh.springboot.model.security.User;
import mh.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class SecuredController {

    @Value("${facebook.client.clientId}")
    private String facebookClientId;

    @Value("${google.client.clientId}")
    private String googleClientId;

    @Autowired
    private UserService userService;

    @RequestMapping("/secured")
    public String secured(Model model){
        String username = "";
        Optional<User> userOptional = userService.getLoggedUser();
        if(userOptional.isPresent()) {
            username = userOptional.get().getUsername();
        }
        model.addAttribute("username", username);
        return "secured";
    }

}

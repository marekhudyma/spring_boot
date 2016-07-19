package mh.springboot.controller;

import mh.springboot.model.security.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecuredController {

    @Value("${facebook.client.clientId}")
    private String facebookClientId;

    @Value("${google.client.clientId}")
    private String googleClientId;

    @RequestMapping("/secured")
    public String secured(Model model){
        String username = "";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof OAuth2Authentication) {
            String clientId = ((OAuth2Authentication)authentication).getOAuth2Request().getClientId();
            if(facebookClientId.equals(clientId)) {
                username = "FACEBOOK_" + authentication.getPrincipal().toString();
            } else if(googleClientId.equals(clientId)) {
                username = "GOOGLE_" + authentication.getPrincipal().toString();
            }
        } else if(authentication instanceof UsernamePasswordAuthenticationToken) {
            username = "DATABASE_" + ((User)authentication.getPrincipal()).getId();
        }
        model.addAttribute("username", username);
        return "secured";
    }
}

package mh.springboot.controller;

import com.google.common.collect.ImmutableSet;
import mh.springboot.model.security.RoleEnum;
import mh.springboot.model.security.User;
import mh.springboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class OAuth2AuthenticationSuccessEventHandler {

    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(OAuth2AuthenticationSuccessEventHandler.class);

    @Autowired
    public OAuth2AuthenticationSuccessEventHandler(UserService userService) {
        this.userService = userService;
    }

    @Value("${facebook.client.clientId}")
    private String facebookClientId;

    @Value("${google.client.clientId}")
    private String googleClientId;

    private final String FACEBOOK_PREFIX = "facebook_";

    private final String GOOGLE_PREFIX = "google_";

    @RequestMapping("/logged")
    public String logged(){
        User userAssembled = assembleUserFromExternalId(facebookClientId, googleClientId);
        if(userAssembled != null) {
            logger.info("User {} logged in",  userAssembled);
            User userLoaded = userService.findByExternalId(userAssembled.getExternalId());
            if(userLoaded != null) {
                UsernamePasswordAuthenticationToken token =
                        new UsernamePasswordAuthenticationToken(userLoaded, null, userLoaded.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(token);
            } else {
                userService.createUser(userAssembled, ImmutableSet.of(RoleEnum.USER));
                UsernamePasswordAuthenticationToken token =
                        new UsernamePasswordAuthenticationToken(userAssembled, null, userAssembled.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }
        return "index";
    }

    private User assembleUserFromExternalId(String facebookClientId, String googleClientId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof OAuth2Authentication) {
            String clientId = ((OAuth2Authentication)authentication).getOAuth2Request().getClientId();
            if(facebookClientId.equals(clientId)) {
                String externalId = FACEBOOK_PREFIX + authentication.getPrincipal().toString();
                String name = (String) ((Map)((OAuth2Authentication) authentication)
                        .getUserAuthentication().getDetails()).get("name");
                return new User(externalId, externalId, null, name);
            } else if(googleClientId.equals(clientId)) {
                String externalId = GOOGLE_PREFIX + authentication.getPrincipal().toString();
                String name = (String) ((Map)((OAuth2Authentication) authentication)
                        .getUserAuthentication().getDetails()).get("name");
                return new User(externalId, externalId, null, name);
            }
        }
        return null;
    }

}

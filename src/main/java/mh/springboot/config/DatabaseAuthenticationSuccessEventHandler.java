package mh.springboot.config;

import mh.springboot.model.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class DatabaseAuthenticationSuccessEventHandler implements ApplicationListener<AuthenticationSuccessEvent> {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseAuthenticationSuccessEventHandler.class);

    @Override
    public void onApplicationEvent(final AuthenticationSuccessEvent event) {
        User user = (User) event.getAuthentication().getPrincipal();
        logger.info("User {} logged in", user);
    }
}

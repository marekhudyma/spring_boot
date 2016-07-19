package mh.springboot.controller;

import mh.springboot.model.security.User;
import mh.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.web.context.WebApplicationContext.SCOPE_SESSION;

/**
 * Controller that demostrate how to get user and save state
 */
@RestController
@RequestMapping("/state")
@Scope(value= SCOPE_SESSION)
public class StateController {

    private int i;

    @Autowired
    private UserService userService;

    @RequestMapping(method= RequestMethod.GET)
    public Result state() {
        Optional<User> loggedUser = userService.getLoggedUser();

        String username = "";
        if (loggedUser.isPresent()) {
            username = loggedUser.get().getUsername();
        }
        return new Result(username, i++);
    }

    class Result {
        private final String username;
        private final int counter;

        public Result(final String username, final int counter) {
            this.username = username;
            this.counter = counter;
        }

        public String getUsername() {
            return username;
        }

        public int getCounter() {
            return counter;
        }
    }
}

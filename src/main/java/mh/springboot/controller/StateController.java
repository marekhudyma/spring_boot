package mh.springboot.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.context.WebApplicationContext.SCOPE_SESSION;

/**
 * Controller that demostrate how to get user and save state
 */
@RestController
@RequestMapping("/state")
@Scope(value= SCOPE_SESSION)
public class StateController {

    private int i;

    //TODO HUDYMA - check it
    //    public Principal user(Principal principal) {
    @RequestMapping(method= RequestMethod.GET)
    public Result state() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
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

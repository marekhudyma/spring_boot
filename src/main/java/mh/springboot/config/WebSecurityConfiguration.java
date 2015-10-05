package mh.springboot.config;


import org.springframework.context.annotation.Configuration;

@Configuration
//TODO HUDYMA - REMOVE SECURITY
//@EnableWebSecurity
//@EnableWebMvcSecurity  //TODO HUDYMA sprawdzic czy potrzebuje obu
public class WebSecurityConfiguration { //extends WebSecurityConfigurerAdapter {
    //TODO HUDYMA - REMOVE SECURITY
//
//    @Autowired
//    private UserService userService;
//
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.userDetailsService(userService);
////    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                //.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/",
//                             "/login.html",
//                             "/api/sampleentity").permitAll()
//
//                .antMatchers("/users").hasRole("ADMIN")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                        //.loginPage("/alogin.html")
//                        //.loginPage("login")
//
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/");
//
////		http
////				.authorizeRequests()
////				.antMatchers("/resources/**").permitAll()
////				.anyRequest().authenticated()
////				.and()
////				.formLogin()
////				.loginPage("/login")
////				.permitAll()
////				.and()
////				.logout()
////				.permitAll();
//    }
//
//
//
//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

}

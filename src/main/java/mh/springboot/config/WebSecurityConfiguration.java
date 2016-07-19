package mh.springboot.config;

import mh.springboot.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

// When user is logged in with Facebook or Google, user is redirected to page:
// /logged (OAuth2AuthenticationSuccessEventHandler),
// if there is no user with specific external_id, new is created.
@EnableWebSecurity
@Import(SecurityAutoConfiguration.class)
@Profile("!test") //it would be skipped for profile "test"
@EnableOAuth2Client
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    OAuth2ClientContext oauth2ClientContext;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userRepository);
    }

//to use in-memory-db
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //TODO CHANGE IT !!!
                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
                //.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
                .authorizeRequests()
                .antMatchers("/api/sampleentity").authenticated()
                .antMatchers("/page_user.html").hasRole("USER")
                .antMatchers("/page_admin.html").hasRole("ADMIN")
                .and().formLogin().loginPage("/login.html").permitAll()
                .and().logout()
                .permitAll()
                .deleteCookies("remove")
                .invalidateHttpSession(true)
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    @Bean
    @ConfigurationProperties("google.client")
    AuthorizationCodeResourceDetails google() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("google.resource")
    ResourceServerProperties googleResource() {
        return new ResourceServerProperties();
    }

    @Bean
    @ConfigurationProperties("facebook.client")
    OAuth2ProtectedResourceDetails facebook() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("facebook.resource")
    ResourceServerProperties facebookResource() {
        return new ResourceServerProperties();
    }

    private Filter ssoFilter() {
        CompositeFilter compositeFilter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();
        {
            OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter("/login");
            OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
            filter.setRestTemplate(facebookTemplate);
            filter.setTokenServices(new UserInfoTokenServices(facebookResource().getUserInfoUri(), facebook().getClientId()));
            filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/logged"));
            filters.add(filter);
        }
        {
            OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter("/login_google");
            OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(google(), oauth2ClientContext);
            filter.setRestTemplate(googleTemplate);
            filter.setTokenServices(new UserInfoTokenServices(googleResource().getUserInfoUri(), google().getClientId()));
            filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/logged"));
            filters.add(filter);

        }
        compositeFilter.setFilters(filters);
        return compositeFilter;
    }

//TODO HUDYMA - REMOVE OR WHAT ?
//    private Filter csrfHeaderFilter() {
//		return new OncePerRequestFilter() {
//			@Override
//			protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//				CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
//				if (csrf != null) {
//					Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
//					String token = csrf.getToken();
//					if (cookie == null || token != null && !token.equals(cookie.getValue())) {
//						cookie = new Cookie("XSRF-TOKEN", token);
//						cookie.setPath("/");
//						response.addCookie(cookie);
//					}
//				}
//				filterChain.doFilter(request, response);
//			}
//		};
//	}
//	private CsrfTokenRepository csrfTokenRepository() {
//		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
//		repository.setHeaderName("X-XSRF-TOKEN");
//		return repository;
//	}
//TODO add hash of password
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(new BCryptPasswordEncoder());
//    }
}

package ru.ulstu.is.sbapp.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.ulstu.is.sbapp.user.controller.UserSignupMvcController;
import ru.ulstu.is.sbapp.user.model.UserRole;
import ru.ulstu.is.sbapp.user.service.UserService;

import javax.annotation.security.RolesAllowed;

import static ru.ulstu.is.sbapp.user.model.UserRole.NOOB;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);
    private static final String LOGIN_URL = "/login";
    private final UserService userService;

    public SecurityConfiguration(UserService userService) {
        this.userService = userService;
        createAdminOnStartup();
    }

    private void createAdminOnStartup() {
        final String kate = "kate";
        if (userService.findByLogin(kate) == null) {
            log.info("Admin user successfully created");
            userService.createUser(kate, kate, kate, UserRole.ADMIN);
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin().and()
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(UserSignupMvcController.SIGNUP_URL).permitAll()
                .antMatchers(HttpMethod.GET, LOGIN_URL).permitAll()
                .antMatchers("/*").hasRole(NOOB.name())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(LOGIN_URL).permitAll()
                .and()
                .logout().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/css/**")
                .antMatchers("/js/**")
                .antMatchers("/templates/**")
                .antMatchers("/webjars/**");

    }
}
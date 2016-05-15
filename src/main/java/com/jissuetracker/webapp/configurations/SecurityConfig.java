package com.jissuetracker.webapp.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/**
 * Created by jovin on 29/1/16.
 */


@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.jissuetracker.webapp")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("loginService")
    UserDetailsService loginService;

    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService).passwordEncoder(passwordEncoder());
    }


    //By default there are four roles, on addition of a new role in the application it has to be added here
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/app/**").access("hasAnyAuthority('Administrator','Manager','Tester','Developer','Reporter')")
                .and().formLogin().loginPage("/login")
                .successHandler(authenticationSuccessHandler)
                .failureUrl("/login?error")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout()
                .deleteCookies("remove")
                .invalidateHttpSession(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .and().csrf()
                .and().exceptionHandling().accessDeniedPage("/403");
    }


    //For encoding the string password provided via login form
    @Bean
    public Md5PasswordEncoder passwordEncoder(){
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        return encoder;
    }


}

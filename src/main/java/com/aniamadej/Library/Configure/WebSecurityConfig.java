package com.aniamadej.Library.Configure;

import com.aniamadej.Library.Models.Security.BasicUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    BasicUserDetailsService basicUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(
                        "/book/add/**",
                        "/profile/**",
                        "/passwordChange/**",
                        "/book/**/edit/**",
                        "/book/**/delete/**"
                                    ).authenticated()
                .anyRequest().permitAll()
                .and()
                    .csrf().disable()
                    .formLogin()
                        .loginPage("/login")
                        .failureUrl("/login?error=true")
                        .defaultSuccessUrl("/books")
                        .loginProcessingUrl("/login-process")
                        .usernameParameter("login")
                        .passwordParameter("password")
                .and()
                    .exceptionHandling().accessDeniedPage("/denied");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(basicUserDetailsService);
    }
}

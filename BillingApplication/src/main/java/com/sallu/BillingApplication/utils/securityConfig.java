package com.sallu.BillingApplication.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class securityConfig{

    @Autowired
    private LandingPage landingPage;
    @Bean
    public UserDetailsManager manager(DataSource source) {
        return new JdbcUserDetailsManager(source);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/images/brand-logo.png").permitAll()
                        .requestMatchers("/css/styles.css").permitAll()
                        .requestMatchers("/signup").permitAll()
                        .requestMatchers("/createUser").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form->
                form
                        .loginPage("/login-form")
                        .loginProcessingUrl("/custom-login")
                        .successHandler(landingPage)
                        .permitAll()
                )
                .logout(logout ->
                logout
                        .permitAll()
                );
        return httpSecurity.build();
    }
}

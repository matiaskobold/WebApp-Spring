package com.springboot.webApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

@Configuration
@EnableWebSecurity

public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("userLoginDetailsServiceImpl")
    @Autowired
    private UserDetailsService userLoginDetailsService;

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userLoginDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()  //permits /login (mapped to HomeController.loginPage()) so everyone can see login page
                .antMatchers("/users").permitAll()  //permits /users from API REST through Spring Security without authentication
                .antMatchers("/users/{id}").permitAll()
                .antMatchers("/newUserLogin").permitAll()
                .antMatchers("/saveUserLogin").permitAll()
                .anyRequest().authenticated()   //all others requests should be authenticated
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //  /logout is the request link that should be used for logging out
                .logoutSuccessUrl("/logout-success").permitAll();       // when logout request is called, go to /logout-success (mapped by HomeController.logout())

    }
/*  @Bean
    @Override
    protected UserDetailsService userDetailsService() {

        List<UserDetails> usersDetails = new ArrayList<>();
        usersDetails.add((UserDetails) User.withDefaultPasswordEncoder()
                .username("matias").password("123").roles("USER").build());

        return new InMemoryUserDetailsManager(usersDetails);
    }*/
}

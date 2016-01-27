package com.ss.academy.java.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
 
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
 
    @Autowired
    @Qualifier("customUserDetailsService")
    UserDetailsService userDetailsService;
     
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }
     
     
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
     
     
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        
        return authenticationProvider;
    }
     
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.csrf().disable()
      	.authorizeRequests()
      		.antMatchers("/", "/register").permitAll()
	      	.antMatchers("/authors/**" , "/users/**", "/restapi/**", "/books/**", "/messages/**")
	      	.hasAnyAuthority("USER", "ADMIN")
	      	.and().formLogin()
        	.loginPage("/login").failureUrl("/login?error")
        	.defaultSuccessUrl("/")
        	.usernameParameter("username")
        	.passwordParameter("password")
        	.permitAll()
        	.and().logout()
      		.logoutUrl("/logout").logoutSuccessUrl("/")
      		.invalidateHttpSession(true).deleteCookies("JSESSIONID")
      		.permitAll()
      		.and().httpBasic()
      		.and().exceptionHandling().accessDeniedPage("/Access_Denied");
    }
}
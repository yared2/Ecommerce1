package com.shopme.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new ShopmeUserDetailService();
	}
	
	@Bean
	public PasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public DaoAuthenticationProvider authenticationProvider(){ //telling spring we are using database based authenticatiob
		
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(PasswordEncoder());
		return  authenticationProvider;
	}
	
	
	
	@Override  //to configur DaoAuthenticationProvider 
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
	
		http.authorizeRequests()
		.antMatchers("/users/**").hasAuthority("Admin")
		.antMatchers("/categories/**").hasAnyAuthority("Editor","Admin")
		.anyRequest()
		.authenticated()
		.and()
		.formLogin()   // specify login form 
		   .loginPage("/login") //the login form  permite to all
		   .usernameParameter("email")  // change the custome username to email spring login custome is userName
		   .permitAll()
		   .and().logout().permitAll()
		   .and().rememberMe().key("abcdefghijklmnopqrs_1234567890")
		   .tokenValiditySeconds(7*24*60*60);
		http.headers().frameOptions().sameOrigin();
	}


	@Override
	public void configure(WebSecurity web) throws Exception {
	web.ignoring().antMatchers("/images/**","/js/**","/webjars/**");
	}



}

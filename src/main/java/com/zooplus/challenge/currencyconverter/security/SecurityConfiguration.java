package com.zooplus.challenge.currencyconverter.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.zooplus.challenge.currencyconverter.service.LoginService;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginService loginService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable().authorizeRequests()
				.antMatchers("/resources/**").permitAll()
				.antMatchers("/registration").permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/").permitAll()
				.anyRequest()
				.authenticated()
				.and().formLogin().usernameParameter("email").loginPage("/login").permitAll()
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(loginService).passwordEncoder(new BCryptPasswordEncoder());
	}

}
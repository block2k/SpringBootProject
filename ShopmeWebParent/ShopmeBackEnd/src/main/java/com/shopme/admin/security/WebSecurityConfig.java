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
	return new ShopmeUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
	http.authorizeRequests().antMatchers("/users/**").hasAuthority("Admin").antMatchers("/categories/**")
		.hasAnyAuthority("Admin", "Editor").anyRequest().authenticated().and().formLogin().loginPage("/login")
		.usernameParameter("email").permitAll().and().logout().permitAll().and().rememberMe();
//	http.authorizeRequests().anyRequest().permitAll();
    }

    public DaoAuthenticationProvider authenticationProvider() {
	DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	authenticationProvider.setUserDetailsService(userDetailsService());
	authenticationProvider.setPasswordEncoder(passwordEncoder());
	return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
	// cho phép truy cập các đường dẫn bên dưới
	web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }

}

package com.enfiny.tokens.tokengenerator.config;

import com.enfiny.tokens.tokengenerator.service.UserService;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.reactive.CorsConfigurationSource;
//import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
//import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;


@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	private static final Logger LOG = LoggerFactory.getLogger(WebSecurityConfiguration.class);
	@Autowired
	private UserService userService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().exceptionHandling()
				.authenticationEntryPoint(
						(request, response, authException) -> response.sendError(HttpStatus.UNAUTHORIZED.value(),"UNAUTHORIZED"))
				.and().authorizeRequests().antMatchers("/**").authenticated().and().httpBasic();
//		http.csrf().disable().exceptionHandling()
//				.authenticationEntryPoint(
//						(request, response, authException) -> response.sendError(HttpStatus.UNAUTHORIZED.value(),"Khai k ho koni :@"))
//				.and().authorizeRequests().antMatchers("/createClient").permitAll();
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/clients/**");
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("Checking User");
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
		System.out.println("User checked");
	}

}

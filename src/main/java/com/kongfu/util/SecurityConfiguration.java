package com.kongfu.util;

import static com.kongfu.util.SecurityConstants.REMEMBER_ME_KEY;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import com.kongfu.util.handler.RestAccessDeniedHandler;
import com.kongfu.util.handler.RestAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private SimpleUrlAuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	private RestAuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Autowired
	RestAccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private RestUnauthorizedEntryPoint restAuthenticationEntryPoint;
	
	@Autowired
	private RememberMeServices rememberMeServices;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http://localhost:8080/index.html才是允许的
		http.httpBasic().and().authorizeRequests()
		.antMatchers("/index.html", "/views/**", "/","/favicon.ico","/user/toLogin","/user/login")
			.permitAll()
		.anyRequest()
			.authenticated()
			.and()
		.exceptionHandling()
			.authenticationEntryPoint(restAuthenticationEntryPoint)
			.accessDeniedHandler(accessDeniedHandler)
			.and()
		.formLogin()
			.loginProcessingUrl("/authenticate")
			.successHandler(authenticationSuccessHandler)
			.failureHandler(authenticationFailureHandler)
			.usernameParameter("username")
			.passwordParameter("password")
			.permitAll()
			.and()
		.logout()
			.logoutUrl("/logout")
			.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
			.deleteCookies("JSESSIONID")
			.permitAll()
			.and()
		.rememberMe()
			.rememberMeServices(rememberMeServices)
			.key(REMEMBER_ME_KEY)
			.and()
		.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class).csrf()
		.csrfTokenRepository(csrfTokenRepository());
	}
	
	//Customize CSRF header cookie for angular 
		private CsrfTokenRepository csrfTokenRepository() {
			HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
			repository.setHeaderName("X-XSRF-TOKEN");
			return repository;
		}
	
	
}

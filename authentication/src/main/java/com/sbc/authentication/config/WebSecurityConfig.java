package com.sbc.authentication.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.sbc.authentication.security.JwtConfigurer;
import com.sbc.authentication.security.TokenProvider;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final TokenProvider tokenProvider;

	@Autowired
	@Qualifier("customUserDetailsService")
	private UserDetailsService customUserDetailsService;
	
	@Autowired
	private DataSource dataSource;
	
	@Value("${spring.queries.users-query}")
	private String usersQuery;
	
	@Value("${spring.queries.roles-query}")
	private String rolesQuery;
	
	public WebSecurityConfig(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder authentication) throws Exception {
		authentication.
			jdbcAuthentication()
				.dataSource(dataSource)
				.usersByUsernameQuery(usersQuery)
	            .authoritiesByUsernameQuery(rolesQuery)
				.passwordEncoder(passwordEncoder());
//		System.out.println(authentication.toString());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}


	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
	    // @formatter:off
		
		//For rest service authentication with jwt
			httpSecurity
			  .csrf()
			    .disable()
			  .cors()
			    .and()
//			  .sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//			    .and()
			  .authorizeRequests()
			    .antMatchers("/api/public/**").permitAll()
			    .antMatchers("/api/**").authenticated()
			     .and()
			  .apply(new JwtConfigurer(this.tokenProvider));
			  
	    //For webserver authentication without jwt
			httpSecurity
			  .httpBasic()
			  .and()
			  .authorizeRequests()
			    .antMatchers("/","/login","/registre").permitAll()
			    .antMatchers("/**").hasAuthority("USER")
				.antMatchers("/admin/**").hasAuthority("ADMIN").anyRequest()
				.authenticated().and().csrf().disable().formLogin()
				.loginPage("/login").failureUrl("/login?error=true")
				.defaultSuccessUrl("/")
				.usernameParameter("username")
				.passwordParameter("password")
				.and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/").and().exceptionHandling()
				.accessDeniedPage("/access-denied");
			
			   
			// @formatter:on
	}
	
	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
		webSecurity
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/color/**", "/fonts/**", "/font-awesome/**");
	}

}

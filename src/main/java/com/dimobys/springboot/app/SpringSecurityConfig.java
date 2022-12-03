package com.dimobys.springboot.app;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import com.dimobys.springboot.app.auth.handler.LoginSuccessHandler;
import com.dimobys.springboot.app.models.service.JpaUserDetailsService;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig{
	
	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Autowired
	private JpaUserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar").permitAll()
				/* .antMatchers("/ver/**").hasAnyRole("USER") */
				/* .antMatchers("/uploads/**").hasAnyRole("USER") */
				/* .antMatchers("/form/**").hasAnyRole("ADMIN") */
				/* .antMatchers("/eliminar/**").hasAnyRole("ADMIN") */
				/* .antMatchers("/factura/**").hasAnyRole("ADMIN") */
				.anyRequest().authenticated()
				.and()
					.formLogin()
						.successHandler(successHandler)
						.loginPage("/login")
					.permitAll()
				.and()
				.logout().permitAll()
				.and()
				.exceptionHandling().accessDeniedPage("/error_403");
		return http.build();
	}
	
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
		build.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
	}
}

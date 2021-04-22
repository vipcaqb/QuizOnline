package hl.quizonline.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import hl.quizonline.service.AccountService;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	AccountService userService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userService) // Cung cáp userservice cho spring security
            .passwordEncoder(passwordEncoder()); // cung cấp password encoder
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf().disable()
			.authorizeRequests()
		    	.antMatchers("/", "/home","/js/**","/css/**","/webjars/**","/images/**","/fonts/**","/register","/test","/assets/**",
		    			"/listexam")
		    		.permitAll()
		    	.antMatchers("/profile","/manage/**").hasAnyRole("STUDENT","CREATOR","ADMIN")	
		    	.antMatchers("/admin/**").hasRole("ADMIN")
		    	.antMatchers(HttpMethod.POST,"/register")
					.permitAll()
				.anyRequest().authenticated()
        .and()
	        .formLogin()
	        .loginPage("/login")
	        .defaultSuccessUrl("/home")
	        .permitAll()
        .and()
	        .logout()
	        .permitAll();
	    
	    	
    }
}

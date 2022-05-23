package spring.catering.authentication;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired DataSource datasource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/", "/index", "/userLogin", "/register", "/css/**", "/images/**").permitAll()
			
			.antMatchers(HttpMethod.POST, "/userLogin", "/register").permitAll()
			
			//admin role mancanti
			
			.anyRequest().authenticated()
			
			.and().formLogin()
			
			.loginPage("/userLogin")
			
			.defaultSuccessUrl("/index")
			
			.and()
			
			.logout()
			
			.logoutUrl("/index")
			
			.logoutSuccessUrl("/index")
			
			.invalidateHttpSession(true)
			
			.deleteCookies("JSESSIONID")
			
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			
			.clearAuthentication(true).permitAll();
		
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.jdbcAuthentication()  //autenticazione su database
				
				.dataSource(this.datasource)
				
				.authoritiesByUsernameQuery("SELECT usurname, role FROM credentials WHERE usurname=?") //query per ottenere le credenziali per nome utente e ruolo corrispondente
				
				.usersByUsernameQuery("SELECT usurname, password, 1 as enabled FROM credentials WHERE usurname=?");  //query per ottenere usurname e password
		
	}
	
	
}

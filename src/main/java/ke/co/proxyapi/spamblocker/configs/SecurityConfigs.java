package ke.co.proxyapi.spamblocker.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfigs extends WebSecurityConfigurerAdapter
{
	@Value("${app.pass}")
	private String password;

	@Override
	protected void configure(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception
	{
		http.cors().disable()
				.csrf().disable()
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/incoming", "/spamblocker/incoming").permitAll()
				.antMatchers(HttpMethod.POST, "/keywords", "/spamblocker/keywords").authenticated()
				.anyRequest().denyAll()
				.and()
				.httpBasic();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
	{
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		auth.inMemoryAuthentication()
				.passwordEncoder(encoder)
				.withUser("spamblocker")
				.password(encoder.encode(password))
				.roles("USER");
	}
}

package dev.idion.idionkim.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import static dev.idion.idionkim.board.domain.enums.SocialType.FACEBOOK;
import static dev.idion.idionkim.board.domain.enums.SocialType.GOOGLE;
import static dev.idion.idionkim.board.domain.enums.SocialType.KAKAO;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		http
			.authorizeRequests()
				.antMatchers("/", "/oauth2/**", "/login/**", "/css/**", "/images/**", "/js/**", "/console/**").permitAll()
				.antMatchers("/facebook").hasAuthority(FACEBOOK.getRoleType())
				.antMatchers("/google").hasAuthority(GOOGLE.getRoleType())
				.antMatchers("/kakao").hasAuthority(KAKAO.getRoleType())
				.anyRequest().authenticated()
			.and()
				.oauth2Login()
				.defaultSuccessUrl("/loginSuccess")
				.failureUrl("/loginFailure")
			.and()
				.headers().frameOptions().disable()
			.and()
				.exceptionHandling()
				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
			.and()
				.formLogin()
				.successForwardUrl("/board/list")
			.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.deleteCookies("JSESSIONID")
				.invalidateHttpSession(true)
			.and()
				.addFilterBefore(filter, CsrfFilter.class)
				.csrf().disable();
	}

}

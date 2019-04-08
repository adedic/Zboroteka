package hr.tvz.java.zboroteka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

   
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //.antMatchers("/offer/search/**", "/offer/results/**", "/offer/details/**", "/profile/**")
        		.antMatchers("/**")
                .permitAll()
               /* .antMatchers("/review/newReview/**")
                .hasAnyRole("USER, ADMIN")
                .antMatchers("/offer/new", "/offer/edit/**", "offer/delete/**")
                .hasAnyRole("PROVIDER, ADMIN")
                .antMatchers("/reservation/**")
                .hasAnyRole("USER", "ADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/offer/search?loginSuccess=true", true)
                .and()
                .rememberMe()
                .key("rem-me-key")
                .rememberMeParameter("rememberMe")
                .rememberMeCookieName("team-building-remember-me")
                .tokenValiditySeconds(259200) //3 dana
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout")*/
                .and()
                .exceptionHandling()
                .and()
                .csrf().disable();
    }

    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordencoder() {
        return new BCryptPasswordEncoder();
    }
}
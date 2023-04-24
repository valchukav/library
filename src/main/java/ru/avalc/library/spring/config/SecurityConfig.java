package ru.avalc.library.spring.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.avalc.library.spring.auth.AuthHandler;

/**
 * @author Alexei Valchuk, 11.04.2023, email: a.valchukav@gmail.com
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthHandler authHandler;
    private final DataSource dataSource;

    @Autowired
    @Lazy
    public SecurityConfig(AuthHandler authHandler, DataSource dataSource) {
        this.authHandler = authHandler;
        this.dataSource = dataSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .antMatchers("/pages/spr.xhtml").hasRole("ADMIN")
                .antMatchers("/pages/books.xhtml").hasAnyRole("ADMIN", "USER")
                .and()
                .exceptionHandling().accessDeniedPage("/index.xhtml")
                .and()
                .csrf().disable()
                .formLogin()
                .loginPage("/index.xhtml")
                .failureHandler(authHandler)
                .defaultSuccessUrl("/pages/books.xhtml")
                .loginProcessingUrl("/login")
                .passwordParameter("password")
                .usernameParameter("username")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/index.xhtml")
                .deleteCookies("JSESSIONID", "SPRING_SECURITY_REMEMBER_ME_COOKIE")
                .invalidateHttpSession(true);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username,password,enabled from users where username = ?")
                .authoritiesByUsernameQuery(
                        "select username,role from user_roles where username = ?").passwordEncoder(passwordEncoder());
    }
}

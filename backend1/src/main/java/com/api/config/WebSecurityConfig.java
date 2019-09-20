package com.api.config;

import com.api.utils.LdapSha512PasswordEncoder;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.*;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final Environment environment;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/networks/*/*/*/image.png").permitAll()
            .antMatchers("/networks/*/*/*/map/image.png").permitAll()
            .antMatchers("/user/*").permitAll()
            .anyRequest().authenticated()
            .and()
            .httpBasic()
            .and().headers().frameOptions().disable().and()
            .cors().and()
            .formLogin().disable();

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        if (Objects.equals(environment.getProperty("ldap.enabled", "false"), "false")) {
            auth.inMemoryAuthentication()
                .withUser("admin").password("{noop}test1234").roles("ADMIN");
        } else {
            auth
                .ldapAuthentication()
                .userDnPatterns(
                    environment.getProperty("ldap.userDnPattern", "uid={0},ou=users")
                )
                .contextSource()
                .url(
                    environment.getProperty("ldap.url", "ldap://localhost:10389/dc=example,dc=com")
                )
                .managerDn(
                    environment.getProperty("ldap.userDn", "uid=admin,ou=users,dc=example,dc=com")
                )
                .managerPassword(
                    environment.getProperty("ldap.userPassword", "test1234")
                )
                .and()
                .passwordCompare()
                .passwordEncoder(new LdapSha512PasswordEncoder())
                .passwordAttribute("userPassword");
        }
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


}

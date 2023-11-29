package com.mrhouse.mrhouse;

import com.mrhouse.mrhouse.servicios.ServicioCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter {
@Autowired 
private ServicioCliente servicioCliente;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(servicioCliente)
                        .passwordEncoder(new BCryptPasswordEncoder());
   
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/css/*", "/js/*", "/img/*", "/**")
                .permitAll();
                    http
                .authorizeRequests()
                            .antMatchers("/admin/*").hasAnyRole("ADMIN")
                            .antMatchers("/ente/*").hasAnyRole("ENTE","ADMIN")
                            .antMatchers("/cliente/*").hasAnyRole("CLIENTE","ADMIN")
                            .antMatchers("/css/*", "/js/*", "img/*", "/**")
                            .permitAll()
                 .and().formLogin()
                         .loginPage("/login")
                        .loginProcessingUrl("/logincheck")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/")
                        .permitAll()
                .and().logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                .and().csrf()
                        .disable();
                

    }
}

package com.myshop.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public UserDetailsService userDetailsService() {
        return new MyShopUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/users/**", "/settings/**").hasAuthority("Admin")


                .antMatchers( "/products/edit/**", "/products/save","/brands/{brandId}/categories").hasAnyAuthority("Admin", "Editor", "Salesperson")
                .antMatchers("/products","/products/", "/products/page/**", "/products/{id}/view_detail").hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")
                .antMatchers( "/products/new", "/products/delete/**").hasAnyAuthority("Admin", "Editor")
                .antMatchers("/products/**").hasAnyAuthority("Admin", "Editor")
                .antMatchers("/customers/**", "/orders/**").hasAnyAuthority("Admin", "Salesperson")
                .antMatchers("/categories/**", "/brands/**").hasAnyAuthority("Admin", "Editor")
                .anyRequest().authenticated()

                .and()
                .formLogin().loginPage("/login").usernameParameter("email").defaultSuccessUrl("/", true).permitAll()
                .and().logout()
                .and().rememberMe().tokenValiditySeconds(7 * 24 * 60 * 60).key("abcdakljsdlflsajfl").userDetailsService(userDetailsService());
    }

    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenProvider = new DaoAuthenticationProvider();
        authenProvider.setUserDetailsService(userDetailsService());
        authenProvider.setPasswordEncoder(passwordEncoder());
        return authenProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**", "/css/**");
    }
}

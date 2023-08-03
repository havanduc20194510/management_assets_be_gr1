package com.example.manageasset.infrastructure.shared.configs;

import com.example.manageasset.infrastructure.shared.security.CustomAccessDeniedHandler;
import com.example.manageasset.infrastructure.shared.security.CustomAuthenticationEntryPoint;
import com.example.manageasset.infrastructure.shared.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final JwtAuthenticationFilter authenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**", "/").permitAll()

                .antMatchers("/swagger-ui/**",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/v2/api-docs",
                        "/webjars/**"
                ).permitAll()

                // user
                .antMatchers("/user/**").hasAnyAuthority("ADMIN")

                // department
                .antMatchers("/department/**").hasAnyAuthority("ADMIN")

                // asset
                .antMatchers("/asset/get-by-id/**").hasAnyAuthority("ADMIN", "STAFF", "USER")
                .antMatchers("/asset/list*").hasAnyAuthority("ADMIN", "STAFF", "USER")
                .antMatchers("/asset/update").hasAnyAuthority("ADMIN", "STAFF")
                .antMatchers("/asset/create").hasAnyAuthority("ADMIN", "STAFF")
                .antMatchers("/asset/delete/**").hasAnyAuthority("ADMIN", "STAFF")
                .antMatchers("/asset/update-state/**").hasAnyAuthority("ADMIN", "STAFF")

                // category
                .antMatchers("/category/create").hasAnyAuthority("ADMIN", "STAFF")
                .antMatchers("/category/update").hasAnyAuthority("ADMIN", "STAFF")
                .antMatchers("/category/get/**").hasAnyAuthority("ADMIN", "STAFF", "USER")
                .antMatchers("/category/delete/**").hasAnyAuthority("ADMIN", "STAFF")
                .antMatchers("/category/list*").hasAnyAuthority("ADMIN", "STAFF", "USER")
                .antMatchers("/category/get/**").hasAnyAuthority("ADMIN", "STAFF", "USER")

                // lease
                .antMatchers("/lease-contract/create").hasAnyAuthority("ADMIN", "STAFF", "USER")
                .antMatchers("/lease-contract/update").hasAnyAuthority("ADMIN", "STAFF", "USER")
                .antMatchers("/lease-contract/get/**").hasAnyAuthority("ADMIN", "STAFF", "USER")
                .antMatchers("/lease-contract/delete/**").hasAnyAuthority("ADMIN", "STAFF", "USER")
                .antMatchers("/lease-contract/list-by-user*").hasAnyAuthority("ADMIN", "STAFF", "USER")
                .antMatchers("/lease-contract/update-status/**").hasAnyAuthority("ADMIN", "STAFF")
                .antMatchers("/lease-contract/list*").hasAnyAuthority("ADMIN", "STAFF")

                // maintenance
                .antMatchers("/maintenance-asset-leased/create").hasAnyAuthority("ADMIN", "STAFF", "USER")
                .antMatchers("/maintenance-asset-leased/update").hasAnyAuthority("ADMIN", "STAFF", "USER")
                .antMatchers("/maintenance-asset-leased/get/**").hasAnyAuthority("ADMIN", "STAFF", "USER")
                .antMatchers("/maintenance-asset-leased/delete/**").hasAnyAuthority("ADMIN", "STAFF", "USER")
                .antMatchers("/maintenance-asset-leased/list-by-user*").hasAnyAuthority("ADMIN", "STAFF", "USER")
                .antMatchers("/maintenance-asset-leased/update-status/**").hasAnyAuthority("ADMIN", "STAFF")
                .antMatchers("/maintenance-asset-leased/list*").hasAnyAuthority("ADMIN", "STAFF")

                // revoke
                .antMatchers("/revoke-contract/create").hasAnyAuthority("ADMIN", "STAFF", "USER")
                .antMatchers("/revoke-contract/update").hasAnyAuthority("ADMIN", "STAFF", "USER")
                .antMatchers("/revoke-contract/get/**").hasAnyAuthority("ADMIN", "STAFF", "USER")
                .antMatchers("/revoke-contract/delete/**").hasAnyAuthority("ADMIN", "STAFF", "USER")
                .antMatchers("/revoke-contract/list-by-user*").hasAnyAuthority("ADMIN", "STAFF", "USER")
                .antMatchers("/revoke-contract/list*").hasAnyAuthority("ADMIN", "STAFF")

                // statistic
                .antMatchers("/asset/statistic*").hasAnyAuthority("ADMIN", "STAFF")

                .anyRequest()
                .authenticated()
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

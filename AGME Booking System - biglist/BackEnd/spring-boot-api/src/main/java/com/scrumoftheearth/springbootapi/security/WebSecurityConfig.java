package com.scrumoftheearth.springbootapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

// Debug is insecure, only use it if needing to see all requests
@EnableWebSecurity(debug = false)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SecurityUserService securityUserService;
    private final PasswordEncoder  bCryptPasswordEncoder;

    public WebSecurityConfig(SecurityUserService securityUserService, PasswordEncoder bCryptPasswordEncoder) {
        this.securityUserService = securityUserService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .authorizeRequests()
                    .antMatchers(SecurityConstants.AUTH_WHITELIST_PATHS)
                        .permitAll()
                // Put here any routes that don't require role authorization
                // Allow all GET requests without Authorization.
                // This is temporary to allow front end to connect.
                    .anyRequest().permitAll()
                // Possible allowed paths.
//                    .antMatchers(HttpMethod.POST, "/api/user/").permitAll()
//                    .antMatchers(HttpMethod.GET, "/api/Business/**").permitAll()
//                    .antMatchers(HttpMethod.GET, "/api/worker/**").permitAll()
//                    .antMatchers(HttpMethod.GET, "/api/service**").permitAll()
                .and()
                .addFilter(new JWTAuthorizationFilter(securityUserService, authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().cors(withDefaults());
        http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues(); // Defaults to open CORS
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://localhost:3000", "https://master.d2a37x6ipkc0ka.amplifyapp.com", "ec2-52-91-214-226.compute-1.amazonaws.com:8080"));
//        configuration.addAllowedOrigin("*");
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET","POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

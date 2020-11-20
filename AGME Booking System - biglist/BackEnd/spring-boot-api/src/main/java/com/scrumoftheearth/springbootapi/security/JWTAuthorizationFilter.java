package com.scrumoftheearth.springbootapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/* https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/ */
/* https://grobmeier.solutions/spring-security-5-jwt-basic-auth.html */

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final SecurityUserService securityUserService;

    public JWTAuthorizationFilter(SecurityUserService securityUserService, AuthenticationManager authManager) {
        super(authManager);
        this.securityUserService = securityUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        String header = request.getHeader(SecurityConstants.HEADER_TAG);

        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            SecurityContextHolder.clearContext();
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("", null, Collections.emptyList()));
            filterChain.doFilter(request, response);
            return;
        }

        String userName = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY))
                .build()
                .verify(header.replace(SecurityConstants.TOKEN_PREFIX, ""))
                .getSubject();

        if (userName == null || userName.isEmpty()) {
            SecurityContextHolder.clearContext();
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("", null, Collections.emptyList()));
            filterChain.doFilter(request, response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userName, null, securityUserService.getUserRolesByUsername(userName)));
        filterChain.doFilter(request, response);
    }
}
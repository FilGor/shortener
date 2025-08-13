package com.shortener.filters;

import com.shortener.security.TokenResolver;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.*;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenResolver tokenResolver;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public JwtAuthenticationFilter(TokenResolver tokenResolver,
                                   AuthenticationEntryPoint authenticationEntryPoint) {
        this.tokenResolver = tokenResolver;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        try {
            UserDetails userDetails = tokenResolver.resolve(req.getHeader("Authorization"));
            SecurityContext ctx = SecurityContextHolder.getContext();
            if (userDetails != null && ctx.getAuthentication() == null) {
                var auth = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                ctx.setAuthentication(auth);
            }
            chain.doFilter(req, res);
        } catch (ExpiredJwtException | SignatureException | MalformedJwtException tokenException) {
            authenticationEntryPoint.commence(req, res, new BadCredentialsException("Invalid token: ".concat(tokenException.getMessage()), tokenException));
        }catch (Exception e){
            authenticationEntryPoint.commence(req, res,
                    new BadCredentialsException("Authentication failed: ".concat(e.getMessage()), e));
        }
    }
}

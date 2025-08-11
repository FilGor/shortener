package com.shortener.filters;

import com.shortener.security.JwtTokenResolver;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.*;
import org.springframework.security.web.authentication.*;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenResolver resolver;
    public JwtAuthenticationFilter(JwtTokenResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        var userDetails = resolver.resolve(req.getHeader("Authorization"));
        var ctx = SecurityContextHolder.getContext();
        if (userDetails != null && ctx.getAuthentication() == null) {
            var auth = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            ctx.setAuthentication(auth);
        }
        chain.doFilter(req, res);
    }
}

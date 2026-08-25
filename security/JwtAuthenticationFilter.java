package com.example.trainbooking.security;

import com.example.trainbooking.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService) {

        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Get Authorization header
        String authHeader = request.getHeader("Authorization");

        // 2. If header is missing or doesn't start with Bearer,
        // continue without authentication
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Remove "Bearer " from the token
        String token = authHeader.substring(7);

        try {

            // 4. Validate JWT
            if (jwtService.isTokenValid(token)) {

                // 5. Extract email from JWT
                String email = jwtService.extractEmail(token);

                // 6. Make sure there is no existing authentication
                if (email != null &&
                        SecurityContextHolder
                                .getContext()
                                .getAuthentication() == null) {

                    // 7. Load user from database
                    UserDetails userDetails =
                            userDetailsService
                                    .loadUserByUsername(email);

                    // 8. Create authentication object
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    // 9. Add request details
                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    // 10. Store authentication in SecurityContext
                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);
                }
            }

        } catch (Exception e) {

            // Invalid / expired JWT.
            // Do not authenticate the request.
            SecurityContextHolder.clearContext();
        }

        // 11. Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
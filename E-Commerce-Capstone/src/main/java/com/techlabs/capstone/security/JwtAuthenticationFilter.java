package com.techlabs.capstone.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Component
@AllArgsConstructor
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // get JWT token from HTTP request
        String token = getTokenFromRequest(request);

        // validate token
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {

            // get email (used as username) from token
            String email = jwtTokenProvider.getEmail(token);

            // load the user associated with email
            org.springframework.security.core.userdetails.UserDetails userDetails = userDetailsService
                    .loadUserByUsername(email);

            // create authentication token
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // set authentication in SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }

        // pass the request along the filter chain
        filterChain.doFilter(request, response);
    }

    // Method to extract the token from the request
    private String getTokenFromRequest(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.split(" ")[1].trim(); // Get the token part after "Bearer "
        }

        return null; // Return null if no token is present
    }
}

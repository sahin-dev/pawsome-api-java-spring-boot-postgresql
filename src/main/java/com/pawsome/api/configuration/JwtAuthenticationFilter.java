package com.pawsome.api.configuration;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.pawsome.api.auth.JwtService;
import com.pawsome.api.exception.JwtAuthenticationException;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
        JwtService jwtService,
        UserDetailsService userDetailsService,
        HandlerExceptionResolver handlerExceptionResolver
    ){
        this.jwtService = jwtService;
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
                final String authHeader = request.getHeader("Authorization");

                System.out.println(authHeader);

                if(authHeader == null || !authHeader.startsWith("Bearer")){
                    filterChain.doFilter(request, response);
                    return;
                }

                try{
                    final String jwtToken = authHeader.substring(7);
                    final String userEmail = jwtService.extractUsername(jwtToken);

                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                    if(userEmail != null && authentication == null){
                        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                        if(this.jwtService.isTokenValid(jwtToken, userDetails)){
                            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        } else {
                            throw new JwtAuthenticationException("Token is not valid");
                        }
                    } else if(userEmail == null) {
                        throw new JwtAuthenticationException("Token is not valid or expired");
                    }

                    filterChain.doFilter(request, response);
                }catch(JwtAuthenticationException ex){
                    System.out.println(ex.getMessage());
                    handlerExceptionResolver.resolveException(request, response, null, ex);
                }catch(Exception ex){
                    System.out.println(ex);
                    handlerExceptionResolver.resolveException(request, response, null, new JwtAuthenticationException("Authentication failed: " + ex.getMessage(), ex));
                }
    }

    

}

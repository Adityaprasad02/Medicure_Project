package com.patient_management.project_03.Security.Filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.patient_management.project_03.Security.JWTutil.JWTService;
import com.patient_management.project_03.Security.service.CustomUserDetailService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JWTService jwtService;

    @Autowired
    CustomUserDetailService customUserDetailService;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
                String authHeader = request.getHeader("Authorization");

                //System.out.println("Authorization header: " + request.getHeader("Authorization"));
                

                String username = null ; 
                String jwt_token = null ; 

                //System.out.println("JWT token: " + jwt_token);
                //System.out.println("Username from token: " + username);

                if(authHeader != null && authHeader.startsWith("Bearer ")) {
                    jwt_token = authHeader.substring(7);
                    username = jwtService.extractUsername(jwt_token);
                }

          if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            if(jwtService.validateToken(jwt_token)) {
                
                UserDetails userdetails  = this.customUserDetailService.loadUserByUsername(username);

                System.out.println("Loaded user: " + userdetails.getUsername());
                System.out.println("Authorities: " + userdetails.getAuthorities());
                
                UsernamePasswordAuthenticationToken authenticationToken = 
                        new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());
               
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContext context =  SecurityContextHolder.createEmptyContext();

                context.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(context);
            
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired JWT token");
                return;
            }
          }

          filterChain.doFilter(request, response); // Continue with the filter chain
    }



}

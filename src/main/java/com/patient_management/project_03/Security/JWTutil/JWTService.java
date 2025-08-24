package com.patient_management.project_03.Security.JWTutil;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.patient_management.project_03.Security.model.Admins;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
      private static final String SECRET_KEY = "xSLAcB1S+mSxvV4ODk+XY21WUBZU6fUNh9d2qFNsoMUChacegDwBsbfLEVbqWKPm";

        private SecretKey generateSecretKey() {
            return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        }

    public String generateToken(Admins admin) {
         
        Map<String, Object> claims = new java.util.HashMap<>();
        claims.put("username", admin.getUsername());
        claims.put("role", admin.getRole().name());
        if (admin.getTestDepartment() != null) {
            claims.put("departmentId", admin.getTestDepartment().getDepartmentId());
        } else {
            claims.put("departmentId", null);
        }

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(admin.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 1000)) // Token valid for 5 minutes
                .signWith(generateSecretKey()) // Use a secure key in production
                .compact();
    }
    private Claims getClaims(String jwt_token) {
        Claims claim = Jwts.parserBuilder()
                .setSigningKey(generateSecretKey())
                .build()
                .parseClaimsJws(jwt_token)
                .getBody();
        return claim;
    }
    
    public String extractUsername(String jwt_token) {
         return getClaims(jwt_token).getSubject();    
    }

    public boolean validateToken(String jwt_token) {
         
        Date expirationDate = getClaims(jwt_token).getExpiration();
        if(expirationDate.before(new Date(System.currentTimeMillis()))) {
            return false; // Token is expired
        }
        else return true ; 
    }


}

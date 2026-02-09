package com.citasalut.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    // Inyectamos valores desde application.properties
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    // Genera la clave criptográfica usando el secreto configurado
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Extraer el usuario (subject) del token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extraer fecha de caducidad
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token); // 1. Abre el token entero
        return claimsResolver.apply(claims);           // 2. Aplica la herramienta que le has pasado
    }

    // "Abre" el token para leer lo que hay dentro usando la clave secreta
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // --- CREACIÓN DEL TOKEN (Login) ---
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        // Buscamos el rol del usuario (Ej: ROLE_PACIENTE)
        // Si no tiene, le asignamos "USER" por defecto
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("USER");

        claims.put("role", role); // Guardamos el rol DENTRO del token
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Sumamos el tiempo configurado
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // --- VALIDACIÓN (En cada petición) ---
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        // Es válido si el usuario coincide y no ha caducado
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
package com.citasalut.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null; // como username usare el email
        String jwt = null;

        // Comprobar si hay cabecera y empieza por Bearer
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                // Extraemos el email del token
                username = jwtTokenUtil.extractUsername(jwt);
            } catch (Exception e) {
                // Si el token está mal formado, lo registramos en el log
                logger.error("Error al extraer info del JWT: " + e.getMessage());
            }
        }

        // Si encontramos usuario y NO está autenticado todavía (.getAuthentication() == null)
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Cargamos usuario desde BDD
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Validamos token
            if (jwtTokenUtil.validateToken(jwt, userDetails)) {

                // Creamos la autenticación
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establecemos la autenticación en el contexto
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        chain.doFilter(request, response);
    }
}
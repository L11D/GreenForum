package com.liid.greenforum.config;

import com.liid.greenforum.entity.UserEntity;
import com.liid.greenforum.exception.NotFoundException;
import com.liid.greenforum.repository.UserRepository;
import com.liid.greenforum.utils.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtTokenUtils tokenUtils;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String jwt = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);

            try {
                UUID userId = tokenUtils.getUserIdFromToken(jwt);
//                if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                    UserEntity user = userRepository.findById(userId).orElse(null);
//                    if (user != null) {
//                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
//                                user, jwt, Collections.emptyList()
//                        );
//                        SecurityContextHolder.getContext().setAuthentication(token);
//                    }
//                }
                UserEntity user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

                if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                            userId, null, user.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            } catch (ExpiredJwtException e) {
                log.debug("Token is expired");
            } catch (Exception e) {
                log.error("Error processing JWT", e);
            }
        }
        filterChain.doFilter(request, response);
    }
}
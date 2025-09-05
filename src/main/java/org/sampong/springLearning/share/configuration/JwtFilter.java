package org.sampong.springLearning.share.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.sampong.springLearning.share.enumerate.RoleStatus;
import org.sampong.springLearning.share.utils.JwtUtil;
import org.sampong.springLearning.users.controller.dto.response.Context;
import org.sampong.springLearning.users.service.UserDetailService;
import org.sampong.springLearning.users.service.implement.UserServiceImp;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    @Lazy
    private final UserDetailsService userServiceImp;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        var role = new ArrayList<RoleStatus>();
        var mapper = new ObjectMapper();
        var context = new Context();

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);

            try {
                if (!jwtUtil.validateToken(jwtToken)) {
                    // Extract claims
                    logger.error("Invalid JWT Token");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
                    return;
                } else {
                    context = mapper.readValue(jwtUtil.getUserFromToken(jwtToken), Context.class);
                }
            } catch (ExpiredJwtException e) {
                logger.error("JWT Token has expired", e);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token expired");
                return;
            } catch (Exception e) {
                logger.error("Invalid JWT Token", e);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
                return;
            }
        } else {
            logger.debug("No Bearer token found in Authorization header");
            request.setAttribute("invalid", "Required full authentication");
        }

        if (context.getUsername() != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = userServiceImp.loadUserByUsername(context.getUsername());
            if (userDetails != null) {
                var userPassAuth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                userPassAuth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(userPassAuth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
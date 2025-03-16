package org.example.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.enums.ErrorStatus;
import org.example.domain.user.enums.UserRole;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtSecurityFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpRequest,
            @NonNull HttpServletResponse httpResponse,
            @NonNull FilterChain chain
    ) throws ServletException, IOException {
        String authorizationHeader = httpRequest.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = jwtUtil.substringToken(authorizationHeader);
            try {
                Claims claims = jwtUtil.extractClaims(jwt);
                Long userId = Long.valueOf(claims.getSubject());
                String nickname = claims.get("nickname", String.class);
                UserRole userRole = UserRole.of(claims.get("userRole", String.class));

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    AuthUser authUser = new AuthUser(userId, nickname, userRole);
                    JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(authUser);
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

            } catch (SecurityException | MalformedJwtException e) {
                handleErrorResponse(httpResponse, ErrorStatus._UNAUTHORIZED_INVALID_TOKEN);
                return;
            } catch (ExpiredJwtException e) {
                handleErrorResponse(httpResponse, ErrorStatus._UNAUTHORIZED_EXPIRED_TOKEN);
                return;
            } catch (UnsupportedJwtException e) {
                handleErrorResponse(httpResponse, ErrorStatus._BAD_REQUEST_UNSUPPORTED_TOKEN);
                return;
            } catch (Exception e) {
                handleErrorResponse(httpResponse, ErrorStatus._INTERNAL_SERVER_ERROR);
                return;
            }
        }

        chain.doFilter(httpRequest, httpResponse);
    }

    private void handleErrorResponse(HttpServletResponse httpResponse, ErrorStatus errorStatus) throws IOException {
        httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN); // 기본 상태코드 설정 (필요에 따라 변경)
        httpResponse.setContentType("application/json; charset=UTF-8"); // UTF-8 인코딩 설정
        httpResponse.getWriter().write("{\"error\": {\"code\": \"" + errorStatus.getErrorCode() + "\", \"message\": \"" + errorStatus.getMessage() + "\"}}");
    }
}


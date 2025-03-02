package br.com.foursales.ecommerce.api.config;

import br.com.foursales.ecommerce.api.exceptions.TokenInvalidoException;
import br.com.foursales.ecommerce.api.utils.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (request.getRequestURI().contains("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            if (isNull(authHeader)) {
                throw new TokenInvalidoException("É necessário informar o token de autorização");
            }
            final String jwt = authHeader.substring(7);
            final String userEmail = JwtService.extractUsername(jwt);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            if ((isNull(userEmail) && isNull(authentication)) || !JwtService.isTokenValid(jwt, userDetails)) {
                throw new TokenInvalidoException("Token invalido");
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);
        }
        catch (Exception e) {
            response.setStatus(UNAUTHORIZED.value());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            ProblemDetail error = ProblemDetail.forStatusAndDetail(UNAUTHORIZED, e.getMessage());
            response.getWriter().write(new ObjectMapper().writeValueAsString(error));
            response.getWriter().flush();
            response.getWriter().close();
        }
    }
}
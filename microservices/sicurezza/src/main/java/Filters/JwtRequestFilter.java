package Filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import Services.UserMServiceImpl;
import Utils.JwtUtil;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Lazy
@Component
	public class JwtRequestFilter extends OncePerRequestFilter {

	    private final UserMServiceImpl userService;
	    private final JwtUtil jwtUtil;

	    @Autowired
	    public JwtRequestFilter(UserMServiceImpl customerService, JwtUtil jwtUtil) {
	        this.userService = customerService;
	        this.jwtUtil = jwtUtil;
	    }
	    

	    @Bean
	    public RestTemplate restTemplate() {
	        return new RestTemplate();
	    }

	    @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
	        String authHeader = request.getHeader("Authorization");
	        String token = null;
	        String email = null;

	        // Skip JWT check for signup endpoint
	        // Skip JWT check for login endpoint
	        if (request.getRequestURI().equals("/api/signup") || request.getRequestURI().equals("/api/login")) {
	            try {
					filterChain.doFilter(request, response);
				} catch (java.io.IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            return;
	        }

	        if (authHeader != null && authHeader.startsWith("Bearer ")) {
	            token = authHeader.substring(7);
	            //questo è l'username in realtà, devo creare un claim per la mail
	            email = jwtUtil.extractEmail(token);
	            
	            System.out.println("email------------->"+email);
	        }

	        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            //UserDetails userDetails = userService.loadUserByUsername(username);
	        	UserDetails userDetails = userService.loadUserByEmail(email);

	            if (jwtUtil.validateToken(token, userDetails)) {
	                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	            }

	        }

	        try {
				filterChain.doFilter(request, response);
			} catch (java.io.IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }
	}


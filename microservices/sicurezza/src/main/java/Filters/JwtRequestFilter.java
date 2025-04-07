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
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws java.io.IOException, ServletException{
	    	  String authHeader = request.getHeader("Authorization");
	    	    String token = null;
	    	    String email = null;

	    	    System.out.println("📌 Request URI: " + request.getRequestURI());

	    	    if (request.getRequestURI().equals("/api/signup") || request.getRequestURI().equals("/api/login")) {
	    	        System.out.println("✅ Skip JWT check for: " + request.getRequestURI());
	    	        filterChain.doFilter(request, response);
	    	        return;
	    	    }
	    	    
	    	    final String requestURI = request.getRequestURI();
	    	    if (requestURI.startsWith("/videochiamate/")) {
	    	    	filterChain.doFilter(request, response);
	    	    	System.out.println("✅ Skip JWT check for: " + request.getRequestURI());
	    	        return;
	    	    }

	    	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	    	        token = authHeader.substring(7);
	    	        email = jwtUtil.extractEmail(token);
	    	        System.out.println("📌 Token found: " + token);
	    	        System.out.println("📌 Extracted Email: " + email);
	    	    } else {
	    	        System.out.println("❌ No valid token found!");
	    	    }

	    	    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	    	        UserDetails userDetails = userService.loadUserByEmail(email);
	    	        System.out.println("📌 Loaded User: " + userDetails);

	    	        if (jwtUtil.validateToken(token, userDetails)) {
	    	            System.out.println("✅ Token is valid!");
	    	            UsernamePasswordAuthenticationToken authenticationToken =
	    	                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	    	            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	    	            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	    	        } else {
	    	            System.out.println("❌ Invalid Token!");
	    	        }
	    	    }

	    	    filterChain.doFilter(request, response);
	    	}
}


package Security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import Filters.JwtRequestFilter;
import Model.UserM;
import Repository.Sicurezza.UserMRepository;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
	public class WebSecurityConfigurationS {

	    private final JwtRequestFilter jwtRequestFilter;
	 
	    @Autowired
	    public WebSecurityConfigurationS(JwtRequestFilter jwtRequestFilter) {
	        this.jwtRequestFilter = jwtRequestFilter;
	    }


	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
	    	System.out.println("📌 Configurazione Security inizializzata!");

	    	return security.csrf().disable()
	        		 .cors()
		                .and()
	                .authorizeHttpRequests()
	                .requestMatchers("/api/signup", "/api/login").permitAll()
	                .requestMatchers("/videochiamate/**").permitAll()  // Accesso pubblico alle videochiamate
	                .requestMatchers("/appuntamento/**").authenticated()
	               .anyRequest().authenticated()  // 🔐 Tutto il resto è protetto
	                //  .and()
	                // .authorizeHttpRequests().requestMatchers("/**").permitAll()
	               // .authenticated()
	                .and()
	                .sessionManagement()
	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	                .and()
	                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
	                .build();
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
    }

	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
	        return configuration.getAuthenticationManager();
	    }

	    @Bean
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/**")
	                        .allowedOrigins("https://gestionale-dentista-frontend-etx4dp5x2.vercel.app",  // URL corretto
	                                "https://gestionale-dentista-frontend-etx4dp5x2.vercel.app/api",
	                                "https://gestionale-dentista-frontend-kk9969xsx.vercel.app",
	                                "https://gestionale-dentista-frontend-kk9969xsx.vercel.app/api"
	                              //  "https://gestionale-dentista-frontend-7nxhklfpb.vercel.app",
	                              //  "https://gestionale-dentista-frontend-7nxhklfpb.vercel.app/api"
	                                )
	                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
	                        .allowedHeaders("Authorization", "Content-Type", "Accept", "X-Requested-With", "Origin", "*")
	                        .allowCredentials(true);
	            }
	        };
	    }
	   
}

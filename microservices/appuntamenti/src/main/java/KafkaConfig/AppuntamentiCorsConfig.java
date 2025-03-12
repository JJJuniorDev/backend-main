package KafkaConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppuntamentiCorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/appuntamenti/**") // Mappa tutti gli endpoint del controller
                .allowedOrigins("http://localhost:4200",
                		"https://gestionale-dentista-frontend-7nxhklfpb.vercel.app/api") // Consenti l'origine del tuo frontend o l'indirizzo del microservizio Sicurezza
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    
    
 /*   registry.addMapping("/api/operazioni/**") // Mappa tutti gli endpoint del controller Operazioni
    .allowedOrigins("http://localhost:4200",
    		"https://gestionale-dentista-frontend-7nxhklfpb.vercel.app/api") // Stesso dominio frontend
    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
    .allowedHeaders("*")
    .allowCredentials(true);*/
    
    registry.addMapping("/api/pazienti/**")
    .allowedOrigins("http://localhost:4200",
    		"https://gestionale-dentista-frontend-7nxhklfpb.vercel.app/api") // Stesso dominio frontend
    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
    .allowedHeaders("*")
    .allowCredentials(true);
}

}

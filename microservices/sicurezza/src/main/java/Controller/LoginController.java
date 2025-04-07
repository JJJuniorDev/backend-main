package Controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import DTO.LoginRequest;
import DTO.LoginResponse;
import Model.ExtendedUserDetails;
import Services.UserMServiceImpl;
import Utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final AuthenticationManager authenticationManager;

    private final UserMServiceImpl userMService;

    private final JwtUtil jwtUtil;


    @Autowired
    public LoginController(AuthenticationManager authenticationManager, UserMServiceImpl userMService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userMService = userMService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) throws IOException {
    	 System.out.println("Login request received for email: " + loginRequest.getEmail());
    	try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            System.out.println("✅ Autenticazione riuscita per: " + loginRequest.getEmail());
    	} catch (BadCredentialsException e) {
        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Incorrect email or password.");
        	 System.out.println("❌ Autenticazione FALLITA: " + e.getMessage());
        	return null;
        } catch (DisabledException disabledException) {
        	 response.sendError(HttpServletResponse.SC_NOT_FOUND, "Customer is not activated.");
        	 System.out.println("❌ Autenticazione FALLITA: " + disabledException.getMessage());
        	 return null;
        }
    	catch (Exception e) {
    		 System.out.println("❌ Autenticazione FALLITA: " + e.getMessage());
    	}
        final ExtendedUserDetails userDetails =(ExtendedUserDetails) userMService.loadUserByEmail(loginRequest.getEmail());
        System.out.println("User details caricato: " + userDetails);
        final String jwt = jwtUtil.generateToken( userDetails.getId(), userDetails.getEmail(), userDetails.getRuolo());
        System.out.println("JWT generato: " + jwt);
        response.addHeader("Authorization", "Bearer " + jwt);

        return new LoginResponse(jwt);

    }

    @PostMapping("/check-email")
    public boolean checkEmail(@RequestBody Map<String, String> requestBody) {
    	String email = requestBody.get("email");
        // Implementa la logica per verificare se l'email esiste già nel database
    	boolean emailExists = userMService.emailExists(email);
        return emailExists;
    }
    
    @GetMapping("/verificaToken")
    public ResponseEntity<Boolean> verificaToken(@RequestHeader("Authorization") String token) {
        try {
            // Logica per verificare il token
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtUtil.SECRET) // Utilizza la tua chiave segreta
                    .parseClaimsJws(token.substring(7)) // Rimuovi "Bearer "
                    .getBody();
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }
}
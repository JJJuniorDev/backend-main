package Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import DTO.SignupRequest;
import Model.UserM;
import Repository.Sicurezza.UserMRepository;


@Service
public class AuthServiceImplMS implements AuthServiceM {

    private final UserMRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImplMS(UserMRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserM createUserM(SignupRequest signupRequest) {
        /*if (userRepository.findByEmail(signupRequest.getEmail()) !=null) {
        	System.out.println("user con email: "+signupRequest.getEmail()+" già esistente.");
            return null;
        } */

        UserM user = new UserM();
        System.out.println("creato user con email: "+signupRequest.getEmail());
       user.setEmail(signupRequest.getEmail());
        user.setUsername(signupRequest.getUsername());
        // Hash della password prima di salvarla
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        System.out.println("RUOLO==="+signupRequest.getRuolo());
        user.setRuolo("user");
        System.out.println("Prima del salvataggio nel database");
        UserM createdUserM = userRepository.save(user);
        System.out.println("Dopo il salvataggio nel database");
        System.out.println("User salvato: " + createdUserM);
        user.setId(createdUserM.getId());
        System.out.println("ID: " + createdUserM.getId());
        System.out.println("Email: " + createdUserM.getEmail());
        System.out.println("Username: " + createdUserM.getUsername());
        System.out.println("Password: " + createdUserM.getPassword());

        return user;
    }
}
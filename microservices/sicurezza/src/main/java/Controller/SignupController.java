package Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import DTO.SignupRequest;
import Model.UserM;
import Services.AuthServiceM;

@RestController
@RequestMapping("/api")
public class SignupController {

    private final AuthServiceM authService;

    @Autowired
    public SignupController(AuthServiceM authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupCustomer(@RequestBody SignupRequest signupRequest) {
        UserM createdUserM = authService.createUserM(signupRequest);
        System.out.println(signupRequest);
        if (createdUserM != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUserM);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create customer");
        }
    }

}


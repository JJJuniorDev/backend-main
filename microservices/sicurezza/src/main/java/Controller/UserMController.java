package Controller;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Model.UserM;
import Services.UserMServiceImpl;

@RestController
@RequestMapping("/api/users")
public class UserMController {

	@Autowired
    private UserMServiceImpl userService;

    @GetMapping
    public List<UserM> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public UserM getUserById(@PathVariable String userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity <UserM> getUserByEmail(@PathVariable String email) {
    	 Optional<UserM> user = userService.getUserByEmail(email);
         if (user.isPresent()) {
             return ResponseEntity.ok(user.get());
         } else {
             return ResponseEntity.notFound().build();
         }
    }
    
    @PostMapping
    public UserM createUser(@RequestBody UserM user) {
        return userService.createUser(user);
    }
    
    @PutMapping("/role/{userId}")
    public ResponseEntity<?> updateUserRole(@PathVariable String userId, @RequestBody Map<String, Object> requestData) {
    	String ruolo= (String) requestData.get("ruolo");
    	UserM updatedUser = new UserM();
    	updatedUser.setRuolo(ruolo);
    	try {
    		  UserM user = userService.updateUserRole(userId, updatedUser);
              return ResponseEntity.ok(user);
          } catch (IllegalArgumentException e) {
        	   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Incorrect password"));
    	}
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody Map<String, Object> requestData) {
    	 String email = (String) requestData.get("email");
         String newPassword = (String) requestData.get("newPassword");
         String currentPassword = (String) requestData.get("currentPassword");
        // String ruolo= (String) requestData.get("ruolo");
         // Crea un oggetto UserM aggiornato solo con i campi necessari
         UserM updatedUser = new UserM();
         updatedUser.setEmail(email);
         updatedUser.setPassword(newPassword);  // Solo se fornita
       //  updatedUser.setRuolo(ruolo);
         try {
             UserM user = userService.updateUser(userId, updatedUser, currentPassword);
             return ResponseEntity.ok(user);
         } catch (IllegalArgumentException e) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Incorrect password"));
         } catch (NoSuchElementException e) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
         }
     }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/ruolo/{ruolo}")
    public List<UserM> getUsersByRole(@PathVariable String ruolo) {
        return userService.getUsersByRole(ruolo);
    }
}


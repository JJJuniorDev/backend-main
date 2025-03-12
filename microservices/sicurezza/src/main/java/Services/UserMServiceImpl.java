package Services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import Model.UserM;
import Repository.Sicurezza.UserMRepository;
import jakarta.annotation.PostConstruct;

@Service
public class UserMServiceImpl implements UserDetailsService {

    private final UserMRepository userRepository;
   
    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserMServiceImpl(UserMRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {

    	System.out.println("Loading user by email: " + email);

    	   try {
    		   Optional <UserM> user = userRepository.findByEmail(email);
    		//   Optional <UserM> userUsername= userRepository.findByUsername(email);
			  if (user.isEmpty()) {
		            throw new UsernameNotFoundException("User not found with email: " + email);
		        }
			return user.get();
		}
    	   catch (IncorrectResultSizeDataAccessException e) {
               throw new UsernameNotFoundException("Multiple users found with email: " + email);
           }
    	   catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    	System.out.println("Loading user by username: " + username);

    	   try {
    		   Optional <UserM> user = userRepository.findByEmail(username);
    		//   Optional <UserM> userUsername= userRepository.findByUsername(email);
			  if (user.isEmpty()) {
		            throw new UsernameNotFoundException("User not found with username: " + username);
		        }
			return user.get();
		}
    	   catch (IncorrectResultSizeDataAccessException e) {
               throw new UsernameNotFoundException("Multiple users found with username: " + username);
           }
    	   catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<UserM> getAllUsers() {
        return userRepository.findAll();
    }

    public UserM getUserById(String userId) {
    	return userRepository.findById(userId).orElse(null);
    }
    
    public Optional <UserM> getUserByEmail(String email) {
    	return userRepository.findByEmail(email);
    }

    public UserM createUser(UserM user) {
        return userRepository.save(user);
    }
    
    public UserM updateUserRole (String userId, UserM updatedUser) {
    	UserM existingUser = getUserById(userId);
        if (existingUser == null) {
            throw new NoSuchElementException("Utente non trovato con ID: " + userId);
        }
        if (updatedUser.getRuolo() != null) {
            existingUser.setRuolo(updatedUser.getRuolo());
        }
        return userRepository.save(existingUser);
    }

    public UserM updateUser(String userId, UserM updatedUser, String currentPassword) {
       UserM existingUser = getUserById(userId);
       if (existingUser == null) {
           throw new NoSuchElementException("Utente non trovato con ID: " + userId);
       }
       // Se è stata fornita una nuova password, verifica la password corrente
       if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
           if (!passwordEncoder.matches(currentPassword, existingUser.getPassword())) {
               throw new IllegalArgumentException("Incorrect password");
           }
           // Se la password corrente è corretta, aggiorna con la nuova password
           existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
       }
       // Aggiorna gli altri campi solo se presenti
       if (updatedUser.getEmail() != null) {
           existingUser.setEmail(updatedUser.getEmail());
       }
      /* if (updatedUser.getRuolo() != null) {
           existingUser.setRuolo(updatedUser.getRuolo());
       } */
        return userRepository.save(existingUser);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public List<UserM> getUsersByRole(String ruolo) {
        return userRepository.findByRuolo(ruolo);
    }
}

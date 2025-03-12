package Repository.Sicurezza;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import Model.UserM;

@Repository
public interface UserMRepository extends MongoRepository<UserM, String>{

	Optional <UserM> findByUsername(String username);
	Optional <UserM> findByEmail(String email);
	 boolean existsByEmail(String email);
	 List<UserM> findByRuolo(String ruolo);
}

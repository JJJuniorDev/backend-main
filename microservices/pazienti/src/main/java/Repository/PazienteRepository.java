package Repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import Model.Paziente;

public interface PazienteRepository extends MongoRepository<Paziente, ObjectId>{
	List<Paziente> findByDottoreId(String dottoreId);
	Optional <Paziente> findByCodiceFiscale(String codiceFiscale);
}

package Repository;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import Model.Nota;
import Model.Paziente;

public interface NotaRepository extends MongoRepository<Nota, ObjectId> {
	List<Nota> findByPazienteId(ObjectId pazienteId);
	List<Nota> findByDottoreId(ObjectId dottoreId);
}

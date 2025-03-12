package Repository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import Model.Nota;

public interface NotaRepository extends MongoRepository<Nota, ObjectId> {

}

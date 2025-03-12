package Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import Model.Allegato;

import java.util.List;

public interface AllegatoRepository extends MongoRepository<Allegato, ObjectId> {
    List<Allegato> findByPazienteId(ObjectId pazienteId);
    List<Allegato> findByDottoreId(ObjectId dottoreId);
}

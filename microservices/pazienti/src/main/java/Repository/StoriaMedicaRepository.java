package Repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import Model.StoriaMedica;

public interface StoriaMedicaRepository extends MongoRepository<StoriaMedica, ObjectId> {
    StoriaMedica findByPazienteId(ObjectId pazienteId);
   
    /*
     *La query cerca documenti in cui un farmaco con _id uguale a 
     *farmacoId esiste all'interno dell'array farmaciInUso. 
     */
    @Query("{ 'farmaciInUso._id': ?0 }")
    Optional<StoriaMedica> findByFarmacoId(ObjectId farmacoId);

	void save(Optional<StoriaMedica> storiaMedica);

   
}

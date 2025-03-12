package Repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import Model.EventoDelTrattamento;


public interface EventoDelTrattamentoRepository extends MongoRepository <EventoDelTrattamento, ObjectId>{
List <EventoDelTrattamento> findByDottoreId(ObjectId dottoreId);
@Query("{ 'dottoreId': ?0, 'deleted': false }")
List<EventoDelTrattamento> findByDottoreIdAndDeletedFalse(ObjectId dottoreId);
}

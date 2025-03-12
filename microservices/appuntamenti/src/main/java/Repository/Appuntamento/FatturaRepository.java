package Repository.Appuntamento;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import Model.Fattura;

public interface FatturaRepository extends MongoRepository<Fattura, ObjectId>{

	Fattura save (Fattura fattura);
	Fattura findByAppuntamentoId(ObjectId appuntamentoId);
}

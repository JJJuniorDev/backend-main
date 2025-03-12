package Repository;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import Model.FarmacoInUso;

public interface FarmacoInUsoRepository extends MongoRepository<FarmacoInUso, String> {

	FarmacoInUso findByNomeFarmacoAndPazienteIdAndDosaggioAndFrequenzaAndDataInizioTrattamento(String nomeFarmaco, ObjectId pazienteId,
			String dosaggio, String frequenza, Date dataInizioTrattamento);
}

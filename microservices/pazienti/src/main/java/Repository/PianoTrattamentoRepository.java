package Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import Model.PianoTrattamento;

import java.util.List;
import java.util.Optional;

public interface PianoTrattamentoRepository extends MongoRepository<PianoTrattamento, ObjectId>{

List <PianoTrattamento> findByPazienteId(ObjectId pazienteId);
	PianoTrattamento findById(String id);
	Optional<PianoTrattamento> findByIdAndPazienteId(ObjectId pianoId, ObjectId pazienteId);
}

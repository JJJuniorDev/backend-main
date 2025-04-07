package Repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import Model.Videochiamata;

public interface VideochiamataRepository extends MongoRepository<Videochiamata, ObjectId> {
	 Optional<Videochiamata> findByLink(String link);
}

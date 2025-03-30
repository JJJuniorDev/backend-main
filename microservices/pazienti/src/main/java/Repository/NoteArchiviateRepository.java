package Repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import Model.NoteArchiviate;

@Repository
public interface NoteArchiviateRepository extends MongoRepository<NoteArchiviate, ObjectId> {
	List<NoteArchiviate> findByDottoreId(ObjectId dottoreId);
}
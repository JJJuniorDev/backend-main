package Repository.Appuntamento;
import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import DTO.AppuntamentoDTO;
import Model.Appuntamento;

public interface AppuntamentoRepository extends MongoRepository<Appuntamento, ObjectId>{
	 
    List<Appuntamento> findByDottoreId(ObjectId dottoreId);

	Appuntamento save(AppuntamentoDTO newAppuntamento);
	
	@Query("SELECT a FROM Appuntamento a WHERE a.dataEOrario > :now")
	List<Appuntamento> findAppuntamentiFuturi(@Param("now") LocalDateTime now);
}

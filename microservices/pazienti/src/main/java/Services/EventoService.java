package Services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Helpers.IdHelper;
import Model.EventoDelTrattamento;
import Repository.EventoDelTrattamentoRepository;

@Service
public class EventoService {

	@Autowired 
	private EventoDelTrattamentoRepository eventoRepository;
	 @Autowired
	    private IdHelper idHelper;
	 
	 public List <EventoDelTrattamento> getEventiPerDottoreId(String dottoreId) {
		 return eventoRepository.findByDottoreIdAndDeletedFalse(idHelper.stringToObjectId(dottoreId));
		//return eventoRepository.findByDottoreId(idHelper.stringToObjectId(dottoreId)); 
	 }

	public Optional<EventoDelTrattamento> getEvento(String id) {
		  ObjectId objectId = new ObjectId(id);
	        return eventoRepository.findById(objectId);
	}

	public void salvaEvento(EventoDelTrattamento evento) {
	 eventoRepository.save(evento);
		
	}
}

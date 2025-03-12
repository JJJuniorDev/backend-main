package Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import DTO.AllegatoDTO;
import DTO.EventoDelTrattamentoDTO;
import Helpers.IdHelper;
import Model.EventoDelTrattamento;
import Services.EventoService;

@RestController
@RequestMapping("/api/eventi")
public class EventoController {

	@Autowired
	private EventoService eventoService;
	
	  @Autowired
	    private IdHelper idHelper;
	  
	  @GetMapping("/{dottoreId}")
	 public List<EventoDelTrattamentoDTO> getEventiPerDottore(@PathVariable String dottoreId) {
	List<EventoDelTrattamento> listaEventi= eventoService.getEventiPerDottoreId(dottoreId);
	 List<EventoDelTrattamentoDTO> listaDto = new ArrayList<>();

	    for (EventoDelTrattamento evento : listaEventi) {
	        EventoDelTrattamentoDTO dto = new EventoDelTrattamentoDTO();
	        dto.setId(evento.getId().toHexString());
	        dto.setPianoTrattamentoId(evento.getPianoTrattamentoId().toHexString());
	        dto.setDescrizione(evento.getDescrizione());
	        dto.setDataScade(evento.getDataScade());
	        dto.setDeleted(evento.isDeleted());
	        dto.setTipologia(evento.getTipologia());
	        dto.setDottoreId(evento.getDottoreId().toHexString());

	        listaDto.add(dto);
	    }

	    return listaDto;
	}
	  
	  @GetMapping("/eventoSingolo/{id}")
	  public ResponseEntity<EventoDelTrattamentoDTO> getEvento(@PathVariable(name= "id") String id){
		  if (!ObjectId.isValid(id)) {
           //   logger.warn("ID non valido: {}", id);  // Log WARN per un ID non valido
              return ResponseEntity.badRequest().build();
          }
		  try {
	    		 Optional<EventoDelTrattamento> evento=eventoService.getEvento(id);
	    		  //AppuntamentoDTO appuntamentoDto= new AppuntamentoDTO(appuntamento, idHelper);
	    		  Optional<EventoDelTrattamentoDTO> eventoDtoOpt = evento.map(a -> new EventoDelTrattamentoDTO(a, idHelper));
	    		  return eventoDtoOpt
	    		      .map(ResponseEntity::ok)
	    		      .orElseGet(() -> {
	    		 //        logger.info("Appuntamento non trovato per ID: {}", id);  // Log INFO se l'appuntamento non è trovato
	    		          return ResponseEntity.notFound().build();
	    		      });
	    	  }   	 
	    	  catch (Exception e) {
	            //  logger.error("Errore nel recupero dell'appuntamento con id: {}", id, e); 
	              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	          }
	    }
	  
	  
	  

@DeleteMapping("/delete/{id}")
@Transactional  // Assicura che tutte le operazioni siano gestite come una singola transazione
public ResponseEntity<Void> deleteEvento(@PathVariable String id) {
    if (!ObjectId.isValid(id)) {
        return ResponseEntity.badRequest().build();  // Restituisce un 400 se l'ID non è valido
    }

  
        // Ottieni l'evento
      Optional<EventoDelTrattamento> eventoOpt = eventoService.getEvento(id);
      if (eventoOpt.isEmpty()) {
          return ResponseEntity.notFound().build(); // Restituisce 404 se l'evento non esiste
      }
      // Segna l'evento come eliminato
      EventoDelTrattamento evento = eventoOpt.get();
      evento.setDeleted(true);
      eventoService.salvaEvento(evento);
      return ResponseEntity.status(HttpStatus.ACCEPTED).build();
}
	  }
  
	 





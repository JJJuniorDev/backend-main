package Services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import DTO.AppuntamentoDTO;
import DTO.PazienteDTO;
import Helpers.IdHelper;
import Model.Appuntamento;
import Model.Fattura;
import Repository.Appuntamento.AppuntamentoRepository;
import Repository.Appuntamento.FatturaRepository;

@Service
public class AppuntamentoService {

	 //@Value("${pazienti.service.url}")
	    private String pazientiServiceUrl="http://localhost:8080";

	    private final RestTemplate restTemplate;
	    
	  private final KafkaTemplate<String, Object> kafkaTemplate;
	  
    @Autowired
    private AppuntamentoRepository appuntamentoRepository;

    @Autowired
    private FatturaRepository fatturaRepository;
    
    @Autowired
    private IdHelper idHelper;
    
    @Autowired
    public AppuntamentoService(KafkaTemplate<String, Object> kafkaTemplate, 
    		AppuntamentoRepository appuntamentoRepository,
    		RestTemplate restTemplate,
    		IdHelper idHelper) {
        this.kafkaTemplate = kafkaTemplate;
        this.appuntamentoRepository= appuntamentoRepository;
        this.restTemplate = restTemplate;
        this.idHelper=idHelper;
        System.out.println("IdHelper iniettato: " + (idHelper != null));
    }
    
    private static final Logger logger = LoggerFactory.getLogger(AppuntamentoService.class);
    
    // Chiamata API al microservizio Pazienti
    public Optional<PazienteDTO> getPazienteById(String pazienteId) {
    
        try {
            PazienteDTO paziente = restTemplate.getForObject(
                pazientiServiceUrl + "/api/pazienti/" + pazienteId, PazienteDTO.class
            );
            return Optional.ofNullable(paziente);
        } catch (HttpClientErrorException.NotFound e) {
            logger.warn("Paziente non trovato: ID {}", pazienteId); // Log di livello WARN per 404
            return Optional.empty();
        } catch (HttpClientErrorException e) {
            logger.error("Errore nella richiesta HTTP: " + e.getMessage(), e); // Log di livello ERROR per altre eccezioni HTTP
            return Optional.empty();
        } catch (RestClientException e) {
            logger.error("Errore di connessione al servizio Pazienti: " + e.getMessage(), e); // Problemi di rete o connessione
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Errore generico nel recupero del paziente: " + e.getMessage(), e); // Log di livello ERROR per eccezioni generiche
            return Optional.empty();
        }
    }
    
    public List<AppuntamentoDTO> getAppuntamentiByIds(List<String> appuntamentiIds) {
    	  // Converte la lista di stringhe in una lista di ObjectId
        List<ObjectId> objectIds = appuntamentiIds.stream()
            .map(idHelper::stringToObjectId) // Usa il metodo di conversione
            .collect(Collectors.toList());
        return appuntamentoRepository.findAllById(objectIds)
            .stream()
            .map(appuntamento -> new AppuntamentoDTO(appuntamento, idHelper))
            .collect(Collectors.toList());
    }
   
    // Metodo per recuperare l'appuntamento con i dettagli del paziente
    public Optional<AppuntamentoDTO> getAppuntamentoConPaziente(String appuntamentoId) { //passo stringa appuntamento dal FE
        Optional<Appuntamento> appuntamentoOpt = getAppuntamento(appuntamentoId); //becca appuntamento passandolo da stringa a objectId
        if (appuntamentoOpt.isPresent()) {
            Appuntamento appuntamento = appuntamentoOpt.get();
            Optional<PazienteDTO> pazienteOpt = getPazienteById(idHelper.objectIdToString(appuntamento.getPazienteId()));
            
            if (pazienteOpt.isPresent()) {
                AppuntamentoDTO appuntamentoDTO = new AppuntamentoDTO(appuntamento, pazienteOpt.get(), idHelper);
                return Optional.of(appuntamentoDTO);
            }
        }
        return Optional.empty();
    }
    
    public Map<String, Object> getAppuntamentiStatistics(String dentistaId) {
        Map<String, Object> stats = new HashMap<>();

        // Numero totale di appuntamenti
        long totaleAppuntamenti = appuntamentoRepository.count();

        // Numero di appuntamenti per dentista loggato
        long appuntamentiPerDottore = appuntamentoRepository.findByDottoreId(idHelper.stringToObjectId(dentistaId)).size();

        // In futuro puoi implementare la logica per calcolare appuntamenti futuri e completati
    //    long appuntamentiFuturi = appuntamentoRepository.countByDataEOrarioAfter(new Date());
      //  long appuntamentiCompletati = appuntamentoRepository.countByDataEOrarioBefore(new Date());

        stats.put("totaleAppuntamenti", totaleAppuntamenti);
        stats.put("appuntamentiPerDottore", appuntamentiPerDottore);
        stats.put("appuntamentiCompletati", 60); //appuntamentiCompletati
        stats.put("appuntamentiFuturi", 12); //appuntamentiFuturi
      

        return stats;
    }
    
    public List<Appuntamento> getAppuntamentiByDottoreId(ObjectId dottoreId) {
        return appuntamentoRepository.findByDottoreId(dottoreId);
    }
    
    public List<AppuntamentoDTO> getAllAppuntamenti() {
        List<Appuntamento> appuntamenti = appuntamentoRepository.findAll();
        List<AppuntamentoDTO> appuntamentiDTO = new ArrayList<>();
        logger.info("Numero di appuntamenti trovati: " + appuntamenti.size()); 
        for (Appuntamento appuntamento : appuntamenti) {
        	String idPaziente=idHelper.objectIdToString(appuntamento.getPazienteId()); 
            Optional<PazienteDTO> pazienteOpt = getPazienteById(idPaziente);
            
         try {
                AppuntamentoDTO appuntamentoDTO = new AppuntamentoDTO(appuntamento, pazienteOpt.get(), idHelper);
                appuntamentiDTO.add(appuntamentoDTO);
            } catch (Exception e) {
                logger.error("Paziente non trovato per l'appuntamento con ID: " + appuntamento.getId()); // Log di livello ERROR per eccezioni generiche
                logger.error("Errore nel recupero del paziente: " + e.getMessage(), e);
            }
        }

        return appuntamentiDTO;
    }

    
  /*  public List<Appuntamento> getAllAppuntamenti() {
    	System.out.println(appuntamentoRepository.count());
        return appuntamentoRepository.findAll();
    } */

    public Optional<Appuntamento> getAppuntamento(String id) {
        
        //if (id != null && id.length() == 24 && ObjectId.isValid(id)) {
    	System.out.println("LUNGHEZZA ID CHE DEVE ESSERE 24==="+id.length());
            ObjectId objectId = new ObjectId(id);
        return appuntamentoRepository.findById(objectId);
    }

    public Appuntamento createAppuntamento(Appuntamento appuntamento) {
    	  Appuntamento savedAppuntamento = appuntamentoRepository.save(appuntamento);

          // Solo se l'appuntamento è stato salvato correttamente, invia l'evento a Kafka
         // kafkaTemplate.send("appuntamento-creato", savedAppuntamento);

          return savedAppuntamento;
    }

    public boolean deleteAppuntamento(String id) {
        ObjectId objectId = idHelper.stringToObjectId(id);  // Usa IdHelper per convertire

        // Controlla se l'appuntamento esiste
        if (appuntamentoRepository.existsById(objectId)) {
            // Se esiste, lo eliminiamo
            appuntamentoRepository.deleteById(objectId);
            return true;
        } else {
            // Se non esiste, restituiamo false
            return false;
        }
    }
    public Appuntamento updateAppuntamento(String id, AppuntamentoDTO newAppuntamento) {
        ObjectId objectId = idHelper.stringToObjectId(id);
        return appuntamentoRepository.findById(objectId)
            .map(appuntamento -> {
            	   try {
                       if (newAppuntamento.getDataEOrario() != null) {
                           appuntamento.setDataEOrario(newAppuntamento.getDataEOrario().toLocalDateTime());
                       }
                       if (newAppuntamento.getTrattamento() != null) {
                           appuntamento.setTrattamento(newAppuntamento.getTrattamento());
                       }
                       if (newAppuntamento.getNote() != null) {
                           appuntamento.setNote(newAppuntamento.getNote());
                       }
                      /* if (newAppuntamento.getOperazioni() != null) {
                           appuntamento.setOperazioni(newAppuntamento.getOperazioni());
                       }*/
                       if (newAppuntamento.getCodiceFiscalePaziente() != null) {
                           appuntamento.setCodiceFiscalePaziente(newAppuntamento.getCodiceFiscalePaziente());
                       }
                       if (newAppuntamento.getStato() != null) {
                           appuntamento.setStato(newAppuntamento.getStato());
                       }
                       return appuntamentoRepository.save(appuntamento);
                   } catch (Exception e) {
                       System.out.printf("Error during mapping: {}", e.getMessage());
                       throw new RuntimeException("Errore durante l'aggiornamento dell'appuntamento", e);
                   }
            })
            .orElseThrow(() -> new IllegalArgumentException("Appuntamento non trovato"));
            }

     /*   public Appuntamento addOperazioneToAppuntamento(String appuntamentoId, Operazione operazione) {
            ObjectId objectId = new ObjectId(appuntamentoId);
            Optional<Appuntamento> optionalAppuntamento = appuntamentoRepository.findById(objectId);
            if (optionalAppuntamento.isPresent()) {
                Appuntamento appuntamento = optionalAppuntamento.get();
                appuntamento.getOperazioni().add(operazione);
                return appuntamentoRepository.save(appuntamento);
            } else {
                throw new RuntimeException("Appuntamento non trovato con ID: " + appuntamentoId);
            }
        }
            */
            public AppuntamentoDTO convertToDTO(Appuntamento appuntamento) {
                AppuntamentoDTO dto = new AppuntamentoDTO();
                dto.setId(appuntamento.getId().toString());
                dto.setDataEOrario(
            		    appuntamento.getDataEOrario()
            		        .atZone(ZoneId.of("Europe/Rome"))
            		        .toOffsetDateTime()
            		);
                dto.setTrattamento(appuntamento.getTrattamento());
                dto.setNote(appuntamento.getNote());
                return dto;
            }
  

public List<AppuntamentoDTO> getAppuntamentiFuturi(LocalDateTime now) {
	  List<Appuntamento> appuntamentiFuturi = appuntamentoRepository.findAppuntamentiFuturi(now);
	    return appuntamentiFuturi.stream()
	              .map(this::convertToDto)  // Mappa ogni entità in un DTO
	              .collect(Collectors.toList());  // Restituisci una lista di DTO
}

private AppuntamentoDTO convertToDto(Appuntamento appuntamento) {
    AppuntamentoDTO dto = new AppuntamentoDTO();
    // Esegui qui la mappatura dei campi
    dto.setId(idHelper.objectIdToString(appuntamento.getId()));
    dto.setDataEOrario(
		    appuntamento.getDataEOrario()
		        .atZone(ZoneId.of("Europe/Rome"))
		        .toOffsetDateTime()
		);
   // dto.setPaziente(appuntamento.getPaziente());
    // Aggiungi altri campi rilevanti

    return dto;
}

public Fattura createFattura(Fattura fattura) {
	Fattura fatturaSalvata= fatturaRepository.save(fattura);
	return fatturaSalvata;
	
}

public Fattura getFatturaByAppuntamentoId(ObjectId appuntamentoId) {
	return fatturaRepository.findByAppuntamentoId(appuntamentoId);
}

public Appuntamento updateStatusAppuntamento(String appuntamentoId, String nuovoStato) throws Exception {
    ObjectId objectId = idHelper.stringToObjectId(appuntamentoId);
    return appuntamentoRepository.findById(objectId)
            .map(appuntamento -> {
                appuntamento.setStato(nuovoStato);
                return appuntamentoRepository.save(appuntamento); // Salva l'oggetto modificato nel database
            })
            .orElseThrow(() -> new Exception("Appuntamento non trovato con ID: " + appuntamentoId));
}




}    


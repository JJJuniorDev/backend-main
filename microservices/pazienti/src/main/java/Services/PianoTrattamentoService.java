package Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.login.AccountNotFoundException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;

import DTO.AppuntamentoDTOPaz;
import DTO.EventoDelTrattamentoDTO;
import DTO.PianoTrattamentoDTO;
import Helpers.IdHelper;
import Model.EventoDelTrattamento;
import Model.PianoTrattamento;
import Repository.EventoDelTrattamentoRepository;
import Repository.PianoTrattamentoRepository;

@Service
public class PianoTrattamentoService {

	 @Value("${appuntamento.service.url:http://localhost:8080}")
	    private String appuntamentoServiceUrl="http://localhost:8080";
	
	@Autowired
	private PianoTrattamentoRepository pianoTrattamentoRepository;

	@Autowired
	private EventoDelTrattamentoRepository eventoTrattamentoRepository;
	
	@Autowired
	private IdHelper idHelper;
	
	private final RestTemplate restTemplate;
	
	public PianoTrattamentoService (PianoTrattamentoRepository pianoTrattamentoRepository,
			IdHelper idHelper, RestTemplate restTemplate) {
		this.pianoTrattamentoRepository=pianoTrattamentoRepository;
		this.idHelper= idHelper;
		this.restTemplate=restTemplate;
	}
	
	
	public void salvaPiano(PianoTrattamento piano) {
		pianoTrattamentoRepository.save(piano);
			
		}
	
	public Optional<PianoTrattamento> getPiano(String treatmentPlanId) {
		return this.pianoTrattamentoRepository.findById(idHelper.stringToObjectId(treatmentPlanId));
	}
	
	public PianoTrattamentoDTO creaPiano(PianoTrattamentoDTO pianoDTO) {
		  // Convertire PianoTrattamentoDTO in PianoTrattamento
	    PianoTrattamento piano = new PianoTrattamento();
	   piano.setPazienteId(idHelper.stringToObjectId(pianoDTO.getPazienteId()));
	    piano.setNomePiano(pianoDTO.getNomePiano());
	    piano.setAttivo(pianoDTO.isAttivo());
	    piano.setDataInizio(pianoDTO.getDataInizio());
	    piano.setDataFine(pianoDTO.getDataFine());
	    piano.setAppuntamentiIds(new ArrayList<>());    // Salva il piano
        piano.setEventiTrattamentoIds(new ArrayList<>());
        System.out.println("Appuntamenti prima del salvataggio: " + piano.getAppuntamentiIds());
	    piano = pianoTrattamentoRepository.save(piano);
	    // Convertire PianoTrattamento in PianoTrattamentoDTO
	    System.out.println("Piano salvato: " + piano);
	    return convertToDTO(piano);
	}
	
	public List<PianoTrattamentoDTO> getPianiTrattamentoByPazienteId(String pazienteId) {
	    ObjectId objectId = idHelper.stringToObjectId(pazienteId); // Converte String in ObjectId  

	    // 1-> PRENDO TUTTI I PIANI
	    return pianoTrattamentoRepository.findByPazienteId(objectId).stream()
	        .map(piano -> {
	        	//1.1 CONVERTO PIANO IN DTO
	            PianoTrattamentoDTO dto = convertToDTO(piano);
            
             // 1.2 GESTIONE EVENTI
	            List<EventoDelTrattamentoDTO> eventi = piano.getEventiTrattamentoIds().stream()
		                .map(eventoId -> {
		                
		                  
		                    EventoDelTrattamento evento = eventoTrattamentoRepository.findById(idHelper.stringToObjectId(eventoId))
		                        .orElseThrow(() -> new IllegalArgumentException("evento non trovata per ID: " + eventoId));
		                
		               	 EventoDelTrattamentoDTO eventoDTO = convertEventoToDTO(evento);
		                 
	           
	           
	            return eventoDTO;
	            })
		        .collect(Collectors.toList());
	            dto.setEventi(eventi);
	            
	            // 1.3 GESTIONE APPUNTAMENTI
	            if (piano.getAppuntamentiIds() != null && !piano.getAppuntamentiIds().isEmpty()) {
	            	System.out.println("PRIMA DELLA FETCH n appuntamenti: "+ piano.getAppuntamentiIds().size());
	                List<AppuntamentoDTOPaz> appuntamenti = fetchAppuntamentiFromMicroservice(piano.getAppuntamentiIds());
	                System.out.println("Appuntamenti trovati per il piano: " + appuntamenti.size());
	                dto.setAppuntamenti(appuntamenti);
	            }

	            return dto;
	        })
	        .collect(Collectors.toList());
	}
	
	private List<AppuntamentoDTOPaz> fetchAppuntamentiFromMicroservice(List<String> appuntamentiIds) {
	    String url = appuntamentoServiceUrl+"/api/appuntamenti/by-ids"; // Endpoint remoto del microservizio

	    try {
	        // Preparo la richiesta con RestTemplate
	        HttpHeaders headers = new HttpHeaders();
	      //  headers.setContentType(MediaType.APPLICATION_JSON);
	     //   headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON)); // <- IMPORTANTE!
	    /*    headers.setContentType(MediaType.APPLICATION_XML);
	        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
	        HttpEntity<List<String>> requestEntity = new HttpEntity<>(appuntamentiIds, headers);
	        RestTemplate restTemplate = new RestTemplate();
	         // Aggiungi MappingJackson2XmlHttpMessageConverter per la gestione XML
            MappingJackson2XmlHttpMessageConverter xmlConverter = new MappingJackson2XmlHttpMessageConverter();
            xmlConverter.setObjectMapper(new XmlMapper());  // Usa l'XmlMapper per la gestione XML
            restTemplate.getMessageConverters().clear(); // Pulisce i vecchi convertitori
            restTemplate.getMessageConverters().add(xmlConverter); // Aggiungi il nuovo convertitore XML
*/
	        headers.setContentType(MediaType.APPLICATION_JSON); // Cambia per JSON
	        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON)); // Cambia per JSON
	        HttpEntity<List<String>> requestEntity = new HttpEntity<>(appuntamentiIds, headers);
	        
	        // Usa RestTemplate di Spring Boot con supporto JSON
	        RestTemplate restTemplate = new RestTemplate();

            // Effettua la chiamata al microservizio
            ResponseEntity<AppuntamentoDTOPaz[]> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                AppuntamentoDTOPaz[].class
            );

	        String contentType = responseEntity.getHeaders().getContentType().toString();
	        System.out.println("Content-Type della risposta: " + contentType);
	        // Converto l'array ricevuto in una lista
	        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
	            return Arrays.asList(responseEntity.getBody());
	        } else {
	            System.err.println("Errore nella risposta del microservizio Appuntamenti: " + responseEntity.getStatusCode());
	            return Collections.emptyList();
	        }
	    } catch (Exception e) {
	        System.err.println("Errore durante la chiamata al microservizio Appuntamenti: " + e.getMessage());
	        return Collections.emptyList();
	    }
	}
	 /*
     * ************************************************************************************************************
     *              INIZIO METODI DI EVENTI DEL PIANO                                                                                             *
     * ************************************************************************************************************
     * 
     */
	
	@Transactional
	public PianoTrattamento aggiungiEventoAlPiano(String pazienteId, String planId, EventoDelTrattamentoDTO eventoDTO) throws AccountNotFoundException {
	    ObjectId planIdObjectId = idHelper.stringToObjectId(planId);
	    ObjectId pazienteIdObjectId = idHelper.stringToObjectId(pazienteId);

	    PianoTrattamento piano = pianoTrattamentoRepository.findByIdAndPazienteId(planIdObjectId, pazienteIdObjectId)
	            .orElseThrow(() -> new RuntimeException("Piano non trovato"));

	    // Creare una nuova milestone dal DTO
	    EventoDelTrattamento newEvento = new EventoDelTrattamento();
	    newEvento.setDescrizione(eventoDTO.getDescrizione());
	    newEvento.setDataScade(eventoDTO.getDataScade());
	    newEvento.setDeleted(eventoDTO.isDeleted());
	    newEvento.setPianoTrattamentoId(planIdObjectId);
        newEvento.setTipologia(eventoDTO.getTipologia());
        newEvento.setDottoreId(idHelper.stringToObjectId(eventoDTO.getDottoreId()));
	    // Salva la milestone nel repository
	    EventoDelTrattamento eventoSalvato = eventoTrattamentoRepository.save(newEvento);

	    // Aggiungere l'ID della milestone al piano di trattamento
	    piano.getEventiTrattamentoIds().add(idHelper.objectIdToString(eventoSalvato.getId()));

	    // Salva il piano aggiornato
	    return pianoTrattamentoRepository.save(piano);
	}
	
	public PianoTrattamento aggiungiAppuntamentoAlPiano(String pazienteId, String planId,
String  appuntamentoId) {
		ObjectId planIdObjectId = idHelper.stringToObjectId(planId);
	    ObjectId pazienteIdObjectId = idHelper.stringToObjectId(pazienteId);
	    
	    PianoTrattamento piano = pianoTrattamentoRepository.findByIdAndPazienteId(planIdObjectId, pazienteIdObjectId)
	            .orElseThrow(() -> new RuntimeException("Piano non trovato"));
	
	 // Controlla se la lista appuntamenti è null e inizializzala se necessario
	    if (piano.getAppuntamentiIds() == null) {
	        piano.setAppuntamentiIds(new ArrayList<>());
	    }
	    
	 // Aggiunge l'ID al piano di trattamento
	    piano.getAppuntamentiIds().add(appuntamentoId);
	    
	   return pianoTrattamentoRepository.save(piano);
	}
	
    /*
     * ************************************************************************************************************
     *              INIZIO METODI DI CONVERSIONE DEI DTO                                                                                             *
     * ************************************************************************************************************
     * 
     */
    
 // Metodo di conversione (da Model a DTO) DI PIANOTRATTAMENTO
   	private PianoTrattamentoDTO convertToDTO(PianoTrattamento piano) {
    	    PianoTrattamentoDTO dto = new PianoTrattamentoDTO();
    	    dto.setId(idHelper.objectIdToString(piano.getId()));
    	    dto.setPazienteId(piano.getPazienteId().toHexString());
    	    dto.setNomePiano(piano.getNomePiano());
    	    dto.setAttivo(piano.isAttivo());
    	    dto.setDataInizio(piano.getDataInizio());
    	    dto.setDataFine(piano.getDataFine());

    	  
    	    
    	    List<EventoDelTrattamentoDTO> eventiDTO = piano.getEventiTrattamentoIds().stream()
        	        .map(id -> new ObjectId(id)) // Converte ogni String in ObjectId
        	        .map(eventoTrattamentoRepository::findById) // Cerca nel repository (restituisce Optional)
        	        .flatMap(Optional::stream) // Filtra i valori vuoti e prende quelli presenti
        	        .map(this::convertEventoToDTO) // Converte in DTO
        	        .collect(Collectors.toList());
    	   
    	
        	    dto.setEventi(eventiDTO);
    	    return dto;
    	}

 
    
    private EventoDelTrattamentoDTO convertEventoToDTO(EventoDelTrattamento evento) {
    	EventoDelTrattamentoDTO dto = new EventoDelTrattamentoDTO();
        dto.setId(idHelper.objectIdToString(evento.getId()));
        dto.setPianoTrattamentoId(idHelper.objectIdToString(evento.getPianoTrattamentoId()));
        dto.setDescrizione(evento.getDescrizione());
        dto.setDataScade(evento.getDataScade());
        dto.setDeleted(evento.isDeleted());
        dto.setTipologia(evento.getTipologia());
        dto.setDottoreId(idHelper.objectIdToString(evento.getDottoreId()));
        return dto;
    }
		
	
    /*
     * ************************************************************************************************************
     *             FINE METODI DI CONVERSIONE DEI DTO                                                                                             *
     * ************************************************************************************************************
     * 
     */
}

package Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.security.auth.login.AccountNotFoundException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;

import DTO.PazienteDTO;
import Helpers.IdHelper;
import Model.Paziente;
import Repository.PazienteRepository;

@Service
public class PazienteService {

	 @Autowired
	    private PazienteRepository pazienteRepository;

	  @Autowired
	    private IdHelper idHelper; // Iniettiamo il nostro helper
	  
	  public PazienteService(
			  PazienteRepository pazienteRepository,
			  IdHelper idHelper) {
		  this.pazienteRepository=pazienteRepository;
		  this.idHelper=idHelper;
	  }
	
	  public ResponseEntity<Map<String, Object>> getAllPazienti() {
		  System.out.println("In getAllPazienti");
		    // Recupera tutti i pazienti
		    List<PazienteDTO> pazientiDTOList = pazienteRepository.findAll().stream()
		            .map(paziente -> {
		                PazienteDTO pazienteDTO = new PazienteDTO();
		                pazienteDTO.setId(idHelper.objectIdToString(paziente.getId())); // Converte ObjectId in stringa
		                pazienteDTO.setNome(paziente.getNome());
		                pazienteDTO.setCognome(paziente.getCognome());
		                pazienteDTO.setCodiceFiscale(paziente.getCodiceFiscale());
		                pazienteDTO.setDataDiNascita(paziente.getDataDiNascita());
		                pazienteDTO.setSesso(paziente.getSesso());
		                pazienteDTO.setIndirizzo(paziente.getIndirizzo());
		                pazienteDTO.setNumeroDiCellulare(paziente.getNumeroDiCellulare());
		                pazienteDTO.setDottoreId(paziente.getDottoreId());
		                pazienteDTO.setStato(paziente.getStato());
		                // Non serve convertire manualmente `appuntamentiIds` in stringhe
		                pazienteDTO.setAppuntamentiIds(paziente.getAppuntamentiIds());
		    
		                return pazienteDTO;
		            })
		            .collect(Collectors.toList());

		    // Creazione della risposta finale
		    Map<String, Object> response = new HashMap<>();
		    response.put("pazienti", pazientiDTOList);
		    return ResponseEntity.ok(response);
		}

	    public ResponseEntity<Map<String, Object>> getPazientiByDottoreId(String dottoreId) {
	       // return pazienteRepository.findByDottoreId(dottoreId);
	        List<PazienteDTO> pazientiDTOList = pazienteRepository.findByDottoreId(dottoreId).stream()
		            .map(paziente -> {
		                PazienteDTO pazienteDTO = new PazienteDTO();
		                pazienteDTO.setId(idHelper.objectIdToString(paziente.getId())); // Converte ObjectId in stringa
		                pazienteDTO.setNome(paziente.getNome());
		                pazienteDTO.setCognome(paziente.getCognome());
		                pazienteDTO.setCodiceFiscale(paziente.getCodiceFiscale());
		                pazienteDTO.setDataDiNascita(paziente.getDataDiNascita());
		                pazienteDTO.setSesso(paziente.getSesso());
		                pazienteDTO.setIndirizzo(paziente.getIndirizzo());
		                pazienteDTO.setNumeroDiCellulare(paziente.getNumeroDiCellulare());
		                pazienteDTO.setDottoreId(paziente.getDottoreId());
		                pazienteDTO.setStato(paziente.getStato());
		                // Non serve convertire manualmente `appuntamentiIds` in stringhe
		                pazienteDTO.setAppuntamentiIds(paziente.getAppuntamentiIds());
		    
		                return pazienteDTO;
		            })
		            .collect(Collectors.toList());

		    // Creazione della risposta finale
		    Map<String, Object> response = new HashMap<>();
		    response.put("pazienti", pazientiDTOList);
		    return ResponseEntity.ok(response);
	    }


	    public Optional<PazienteDTO> getPaziente(String id) {
	    	ObjectId objectId = idHelper.stringToObjectId(id);
	    	 Optional<Paziente> pazienteOpt = pazienteRepository.findById(objectId);
	    	   if (pazienteOpt.isPresent()) {
	    	        Paziente paziente = pazienteOpt.get();
	    	        PazienteDTO pazienteDTO = new PazienteDTO();
	    	        
	    	        // Converti l'ObjectId in String
	    	        pazienteDTO.setId(paziente.getId().toHexString());
	    	        pazienteDTO.setNome(paziente.getNome());
	    	        pazienteDTO.setCognome(paziente.getCognome());
	    	        pazienteDTO.setCodiceFiscale(paziente.getCodiceFiscale());
	    	        pazienteDTO.setDataDiNascita(paziente.getDataDiNascita());
	    	        pazienteDTO.setSesso(paziente.getSesso());
	    	        pazienteDTO.setIndirizzo(paziente.getIndirizzo());
	    	        pazienteDTO.setNumeroDiCellulare(paziente.getNumeroDiCellulare());
	    	        pazienteDTO.setDottoreId(paziente.getDottoreId());
	    	        pazienteDTO.setStato(paziente.getStato());
                    pazienteDTO.setAppuntamentiIds(paziente.getAppuntamentiIds());
                   
	    	        return Optional.of(pazienteDTO);
	    	    }

	    	    return Optional.empty();
	    	}

	    public Optional<PazienteDTO> getPazienteByCF(String codiceFiscale) {
	    	 Optional<Paziente> pazienteOpt = pazienteRepository.findByCodiceFiscale(codiceFiscale);
	    	   if (pazienteOpt.isPresent()) {
	    	        Paziente paziente = pazienteOpt.get();
	    	        PazienteDTO pazienteDTO = new PazienteDTO();
	    	        System.out.println("paziente nome: "+paziente.getNome());
	    	        System.out.println("paziente appIds: "+paziente.getAppuntamentiIds());
	    	        // Converti l'ObjectId in String
	    	        pazienteDTO.setId(paziente.getId().toHexString());
	    	        pazienteDTO.setNome(paziente.getNome());
	    	        pazienteDTO.setCognome(paziente.getCognome());
	    	        pazienteDTO.setCodiceFiscale(paziente.getCodiceFiscale());
	    	        pazienteDTO.setDataDiNascita(paziente.getDataDiNascita());
	    	        pazienteDTO.setSesso(paziente.getSesso());
	    	        pazienteDTO.setIndirizzo(paziente.getIndirizzo());
	    	        pazienteDTO.setNumeroDiCellulare(paziente.getNumeroDiCellulare());
	    	        pazienteDTO.setDottoreId(paziente.getDottoreId());
	    	        pazienteDTO.setStato(paziente.getStato());
                    pazienteDTO.setAppuntamentiIds(paziente.getAppuntamentiIds());
                    
	    	        return Optional.of(pazienteDTO);
	    	    }

	    	    return Optional.empty();
	    	}

	    
	    public Paziente addPaziente(Paziente paziente) {
	        return pazienteRepository.save(paziente);
	    }

	    public void deletePaziente(String id) throws AccountNotFoundException {
	    	ObjectId objectId = new ObjectId(id);
				if (pazienteRepository.existsById(objectId)) {
				        pazienteRepository.deleteById(objectId);
				    } else {
				        throw new AccountNotFoundException ("Paziente con ID " + id + " non trovato.");
				    }
			}
	    

	    // Altri metodi di servizio per la logica di business, ad esempio aggiungi, modifica, cancella
     public Paziente updatePaziente(PazienteDTO pazienteDTO) throws AccountNotFoundException {
    	 ObjectId objectId = idHelper.stringToObjectId(pazienteDTO.getId());
    	 Optional<Paziente> pazienteOpt = pazienteRepository.findById(objectId); 
    	 if (pazienteOpt.isPresent()) {
    	        Paziente paziente = pazienteOpt.get();
    	        // Aggiorna i campi del paziente
    	        paziente.setNome(pazienteDTO.getNome());
    	        paziente.setCognome(pazienteDTO.getCognome());
    	        paziente.setCodiceFiscale(pazienteDTO.getCodiceFiscale());
    	        paziente.setDataDiNascita(pazienteDTO.getDataDiNascita());
    	        paziente.setSesso(pazienteDTO.getSesso());
    	        paziente.setIndirizzo(pazienteDTO.getIndirizzo());
    	        paziente.setNumeroDiCellulare(pazienteDTO.getNumeroDiCellulare());
    	        paziente.setDottoreId(pazienteDTO.getDottoreId());    
    	        // Recupera la lista esistente di appuntamenti dal database
    	        List<ObjectId> appuntamentiEsistenti = paziente.getAppuntamentiIds();
    	        
    	     // Aggiorna la lista di appuntamenti in base alla lista passata dal DTO
    	        if (appuntamentiEsistenti != null) {
    	            appuntamentiEsistenti.retainAll(pazienteDTO.getAppuntamentiIds()); // Mantieni solo gli ID presenti nel DTO
    	        } else {
    	            appuntamentiEsistenti = new ArrayList<>(pazienteDTO.getAppuntamentiIds());
    	        }
    	        
    	     // Sovrascrivi direttamente la lista con quella del DTO
    	        paziente.setAppuntamentiIds(pazienteDTO.getAppuntamentiIds());
    	        
  
    	        return pazienteRepository.save(paziente);
    	 }
    	 else {
    		 throw new AccountNotFoundException("Paziente con ID " + pazienteDTO.getId() + " non trovato.");
    	 }
      }
     
     //***************************************************************
     //GENERAZIONE DATI CASUALI CON FAKER
     private static final String DENTISTA_ID = "66cdfd35a45c4216e88af3fc";
     private static final Random random = new Random();

    
     private final Faker faker = new Faker();

     public void generatePazienti(int numberOfPazienti) {
         List<Paziente> pazienti = new ArrayList<>();
         for (int i = 0; i < numberOfPazienti; i++) {
             Paziente paziente = new Paziente(
                     faker.name().firstName(),
                     faker.name().lastName(),
                     generateCodiceFiscale(),
                     faker.date().birthday(18, 80).toInstant().toString().substring(0, 10),
                     random.nextBoolean() ? "M" : "F",
                     faker.address().streetAddress(),
                     "3" + (30 + random.nextInt(30)) + faker.number().digits(7),
                     DENTISTA_ID,
                     randomStato(),
                     new ArrayList<>()                                        
             );
             pazienti.add(paziente);
         }
         pazienteRepository.saveAll(pazienti);
     }

     private String randomStato() {
         String[] stati = {"in attesa", "attivo", "concluso"};
         return stati[random.nextInt(stati.length)];
     }

     private String generateCodiceFiscale() {
         return faker.letterify("??????") + faker.numerify("##") + faker.date().birthday().toInstant().toString().substring(0, 6).replaceAll("-", "");
     } 
}

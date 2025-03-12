package Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import DTO.PazienteDTO;
import Helpers.IdHelper;
import Model.Paziente;
import Services.PazienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pazienti")
public class PazienteController {

	 @Autowired
	    private PazienteService pazienteService;

	 @Autowired
	    private IdHelper idHelper; // Iniettiamo il nostro helper
	 
	// Nuovo endpoint per aggiornare il paziente con un nuovo appuntamento
	 @Transactional
	    @PutMapping("/{pazienteId}/aggiungi-appuntamento")
	    public ResponseEntity<?> aggiungiAppuntamentoAlPaziente(
	            @PathVariable String pazienteId,
	            @RequestBody String appuntamentoId) {
	        
	        Optional<PazienteDTO> pazienteOptional = pazienteService.getPaziente(pazienteId);

	        if (pazienteOptional.isPresent()) {
	            PazienteDTO pazienteDTO = pazienteOptional.get();
	            if ( pazienteDTO.getAppuntamentiIds() == null) {
	            	 pazienteDTO.setAppuntamentiIds(new ArrayList<>());
	            }
	           pazienteDTO.getAppuntamentiIds().add(idHelper.stringToObjectId(appuntamentoId)); // Aggiunge l'ID del nuovo appuntamento
	            try {
					pazienteService.updatePaziente(pazienteDTO);
				} catch (AccountNotFoundException e) {
					e.printStackTrace();
				}
	            return ResponseEntity.ok().build();
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paziente non trovato");
	        }
	    }
	    
	    @PutMapping("/{pazienteId}/rimuovi-appuntamento")
	    public ResponseEntity<?> rimuoviAppuntamento(@PathVariable String pazienteId, @RequestBody String appuntamentoId) {
	    	System.out.println("SIAMO IN RIMUOVI APP DAL PAZIENTE CONTROLLER");
	    	Optional<PazienteDTO> pazienteOptional = pazienteService.getPaziente(pazienteId);

	        if (pazienteOptional.isPresent()) {
	            PazienteDTO pazienteDTO = pazienteOptional.get();
	            pazienteDTO.getAppuntamentiIds().remove((idHelper.stringToObjectId(appuntamentoId))); // Rimuove l'ID
	            System.out.println("RIMOSSO APP ID: "+appuntamentoId);
	            try {
	                pazienteService.updatePaziente(pazienteDTO);
	                System.out.println("PAZIENTE UPDATATO");
	            } catch (AccountNotFoundException e) {
	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	            }
	            return ResponseEntity.ok().build();
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paziente non trovato");
	        }
	    }
	 
	    @GetMapping
	    public ResponseEntity<Map<String, Object>> getAllPazienti(@RequestParam String dottoreId) {
	        return pazienteService.getPazientiByDottoreId(dottoreId);
	    }
	    
	    @GetMapping("/{pazienteId}")
	    public Optional<PazienteDTO> getPaziente(@PathVariable String pazienteId) {
	        return pazienteService.getPaziente(pazienteId);
	    }
	    
	    @GetMapping("/{codiceFiscale}/byCF")
	    public Optional<PazienteDTO> getPazienteByCF(@PathVariable String codiceFiscale) {
	        return pazienteService.getPazienteByCF(codiceFiscale);
	    }
	    
	    @PostMapping
	    public Paziente createPaziente(@Valid @RequestBody Paziente paziente) {
	    	return pazienteService.addPaziente(paziente);
	    }
	    	    
	    @GetMapping("/generatePazienti")
	    public String generatePazienti() {
	        int numberOfPazienti = 50; // Puoi cambiare il numero di pazienti qui
	        pazienteService.generatePazienti(numberOfPazienti);
	        return numberOfPazienti + " pazienti generati e salvati in MongoDB!";
	    }
}

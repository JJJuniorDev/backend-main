package Controller;

import java.util.Optional;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import DTO.FarmacoInUsoDTO;
import Helpers.IdHelper;
import Model.Farmaco;
import Model.FarmacoInUso;
import Model.StoriaMedica;
import Repository.FarmacoInUsoRepository;
import Repository.FarmacoRepository;
import Repository.StoriaMedicaRepository;

@RestController
@RequestMapping("/api/farmaco-in-uso")
public class FarmacoInUsoController {

    @Autowired
    private FarmacoInUsoRepository farmacoInUsoRepository;

    @Autowired
    private StoriaMedicaRepository storiaMedicaRepository;
    
    @Autowired
    private FarmacoRepository farmacoRepository;
    
    @Autowired
    private IdHelper idHelper;
    
    @Transactional
    @PostMapping("/aggiungi")
    public ResponseEntity<FarmacoInUso> aggiungiFarmacoInUso(@RequestBody FarmacoInUsoDTO farmacoInUso) {
    	   // Recupera la storia medica del paziente
        StoriaMedica storiaMedica = storiaMedicaRepository.findByPazienteId(idHelper.stringToObjectId(farmacoInUso.getPazienteId()));
        if (storiaMedica == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        // Aggiungi il farmaco alla storia medica
        System.out.println("Aggiungendo farmaco: " + farmacoInUso.getNomeFarmaco());
        
        // Cerca il farmaco nella collezione FARMACI tramite il nome
        Farmaco farmaco = farmacoRepository.findByNomeFarmaco(farmacoInUso.getNomeFarmaco());
        if (farmaco == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Farmaco non trovato
        }
        
        // Verifica se il farmaco è già presente nella lista dei farmaci in uso
           boolean farmacoGiaPresente = storiaMedica.getFarmaciInUso().stream()
               .anyMatch(f -> f.getNomeFarmaco().equals(farmacoInUso.getNomeFarmaco()) &&
                              f.getDosaggio().equals(farmacoInUso.getDosaggio()) &&
                              f.getFrequenza().equals(farmacoInUso.getFrequenza()) &&
                              f.getDataInizioTrattamento().equals(farmacoInUso.getDataInizioTrattamento()));
           if (farmacoGiaPresente) {
        	   System.out.println("FARMACO già presente in storia medica con tutti i campi identici");
               return ResponseEntity.status(HttpStatus.FOUND).body(null); // Evita il duplicato
           } 
           
        FarmacoInUso farmacoInUsoFromDTO = new FarmacoInUso();
        farmacoInUsoFromDTO.setDosaggio(farmacoInUso.getDosaggio());
        farmacoInUsoFromDTO.setFrequenza(farmacoInUso.getFrequenza());
        farmacoInUsoFromDTO.setNomeFarmaco(farmacoInUso.getNomeFarmaco());
        farmacoInUsoFromDTO.setPazienteId(idHelper.stringToObjectId(farmacoInUso.getPazienteId()));
        farmacoInUsoFromDTO.setAttivo(farmacoInUso.getAttivo());
        farmacoInUsoFromDTO.setDataInizioTrattamento(farmacoInUso.getDataInizioTrattamento());
        farmacoInUsoFromDTO.setDataFineTrattamento(farmacoInUso.getDataFineTrattamento());    
        farmacoInUsoFromDTO.setEfficacia("0% DEFAULT");
        farmacoInUsoFromDTO.setRemissione("DEFAULT");
            // Assegna l'ID del farmaco trovato
        farmacoInUsoFromDTO.setFarmacoId(farmaco.getId());
        // Salva in FarmacoInUso solo se non esiste
        FarmacoInUso esistente = farmacoInUsoRepository.findByNomeFarmacoAndPazienteIdAndDosaggioAndFrequenzaAndDataInizioTrattamento(
        		farmacoInUsoFromDTO.getNomeFarmaco(),
           farmacoInUsoFromDTO.getPazienteId(),
        		   farmacoInUsoFromDTO.getDosaggio(),
        		   farmacoInUsoFromDTO.getFrequenza(),
        		   farmacoInUsoFromDTO.getDataInizioTrattamento());

        if (esistente == null) {
            farmacoInUsoRepository.save(farmacoInUsoFromDTO);
        }

        // Aggiungi il farmaco alla lista dei farmaci nella StoriaMedica
        storiaMedica.getFarmaciInUso().add(farmacoInUsoFromDTO);
        storiaMedicaRepository.save(storiaMedica); // Salva la storia medica e il farmaco

        return ResponseEntity.ok(farmacoInUsoFromDTO); // Restituisce il farmaco aggiunto
    }
    
    @Transactional
    @PutMapping("/aggiornaDisattiva/{id}")
    public ResponseEntity<FarmacoInUso> aggiornaFarmacoInUso(@PathVariable String id, @RequestBody FarmacoInUsoDTO farmacoInUsoDTO) {
        // Recupera il farmaco esistente dal database
        FarmacoInUso farmacoInUsoEsistente = farmacoInUsoRepository.findById(id).orElse(null);
        if (farmacoInUsoEsistente == null) {
        	System.out.println("FARMACO NON TROVATO.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Farmaco non trovato
        }

        // Aggiorna il farmaco con i nuovi dati (dataFineTrattamento, attivo, ecc.)
        farmacoInUsoEsistente.setAttivo(farmacoInUsoDTO.getAttivo());
        farmacoInUsoEsistente.setDataFineTrattamento(farmacoInUsoDTO.getDataFineTrattamento());
        farmacoInUsoEsistente.setDataInizioTrattamento(farmacoInUsoDTO.getDataInizioTrattamento());
        // Aggiungi altri campi da aggiornare come dosaggio, frequenza, ecc.
        StoriaMedica storiaMedica = storiaMedicaRepository.findByPazienteId(idHelper.stringToObjectId(farmacoInUsoDTO.getPazienteId()));
        if (storiaMedica == null) {
            System.out.println("STORIA MEDICA NON TROVATA.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Storia medica non trovata
        }
        // Cerca il farmaco specifico nell'array `farmaciInUso` della storia medica
        ObjectId objectId = idHelper.stringToObjectId(id);
        FarmacoInUso farmacoInUsoNellaStoria = storiaMedica.getFarmaciInUso().stream()
            .filter(farmaco -> farmaco.getId().equals(objectId))
            .findFirst()
            .orElse(null);
        if (farmacoInUsoNellaStoria == null) {
        	System.out.println("FARMACO NON TROVATO NELLA STORIA MEDICA.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Farmaco non trovato nella storia medica
        }
        // Aggiorna i campi del farmaco
        farmacoInUsoNellaStoria.setAttivo(farmacoInUsoDTO.getAttivo());
        farmacoInUsoNellaStoria.setDataFineTrattamento(farmacoInUsoDTO.getDataFineTrattamento());
        farmacoInUsoNellaStoria.setDataInizioTrattamento(farmacoInUsoDTO.getDataInizioTrattamento());
        farmacoInUsoNellaStoria.setDosaggio(farmacoInUsoDTO.getDosaggio());
        farmacoInUsoNellaStoria.setFrequenza(farmacoInUsoDTO.getFrequenza());
        farmacoInUsoNellaStoria.setNomeFarmaco(farmacoInUsoDTO.getNomeFarmaco());
        farmacoInUsoNellaStoria.setEfficacia(farmacoInUsoDTO.getEfficacia());
        farmacoInUsoNellaStoria.setRemissione(farmacoInUsoDTO.getRemissione());
        
        try {
            farmacoInUsoRepository.save(farmacoInUsoEsistente);
            storiaMedicaRepository.save(storiaMedica); // Salva entrambe le entità
        } catch (Exception e) {
            System.out.println("Errore nel salvataggio: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(farmacoInUsoEsistente); // Restituisci il farmaco aggiornato
    }
    
    @Transactional
    @PutMapping("/modifica/{id}")
    public ResponseEntity<FarmacoInUsoDTO> modificaFarmacoInUso(@PathVariable String id, @RequestBody FarmacoInUsoDTO farmacoInUsoDTO) {
        // Recupera il farmaco esistente dal database
        FarmacoInUso farmacoInUsoEsistente = farmacoInUsoRepository.findById(id).orElse(null);
        if (farmacoInUsoEsistente == null) {
        	System.out.println("FARMACO NON TROVATO.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Farmaco non trovato
        }
        System.out.println("ID "+farmacoInUsoDTO.getId());
        System.out.println("ID PAZ "+farmacoInUsoDTO.getPazienteId());

        // Aggiorna il farmaco con i nuovi dati (dataFineTrattamento, attivo, ecc.)
      
      
        farmacoInUsoEsistente.setAttivo(farmacoInUsoDTO.getAttivo());
        farmacoInUsoEsistente.setDataFineTrattamento(farmacoInUsoDTO.getDataFineTrattamento());
        farmacoInUsoEsistente.setDataInizioTrattamento(farmacoInUsoDTO.getDataInizioTrattamento());
        farmacoInUsoEsistente.setDosaggio(farmacoInUsoDTO.getDosaggio());
        farmacoInUsoEsistente.setFrequenza(farmacoInUsoDTO.getFrequenza());
        farmacoInUsoEsistente.setNomeFarmaco(farmacoInUsoDTO.getNomeFarmaco());
        farmacoInUsoRepository.save(farmacoInUsoEsistente);
       
        // Aggiungi altri campi da aggiornare come dosaggio, frequenza, ecc.
        StoriaMedica storiaMedica = storiaMedicaRepository.findByPazienteId(idHelper.stringToObjectId(farmacoInUsoDTO.getPazienteId()));
        if (storiaMedica == null) {
            System.out.println("STORIA MEDICA NON TROVATA.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Storia medica non trovata
        }
        // Cerca il farmaco specifico nell'array `farmaciInUso` della storia medica
        ObjectId objectId = idHelper.stringToObjectId(id);
        FarmacoInUso farmacoInUsoNellaStoria = storiaMedica.getFarmaciInUso().stream()
            .filter(farmaco -> farmaco.getId().equals(objectId))
            .findFirst()
            .orElse(null);
        if (farmacoInUsoNellaStoria == null) {
        	System.out.println("FARMACO NON TROVATO NELLA STORIA MEDICA.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Farmaco non trovato nella storia medica
        }
        // Aggiorna i campi del farmaco
       
        farmacoInUsoNellaStoria.setAttivo(farmacoInUsoDTO.getAttivo());
        farmacoInUsoNellaStoria.setDataFineTrattamento(farmacoInUsoDTO.getDataFineTrattamento());
        farmacoInUsoNellaStoria.setDataInizioTrattamento(farmacoInUsoDTO.getDataInizioTrattamento());
        farmacoInUsoNellaStoria.setDosaggio(farmacoInUsoDTO.getDosaggio());
        farmacoInUsoNellaStoria.setFrequenza(farmacoInUsoDTO.getFrequenza());
        farmacoInUsoNellaStoria.setNomeFarmaco(farmacoInUsoDTO.getNomeFarmaco());
        farmacoInUsoNellaStoria.setEfficacia(farmacoInUsoDTO.getEfficacia());
        farmacoInUsoNellaStoria.setRemissione(farmacoInUsoDTO.getRemissione());
        storiaMedicaRepository.save(storiaMedica); // Salva la storia medica e il farmaco

        
        
        // Ritorna il DTO con l'ID come stringa
        FarmacoInUsoDTO responseDTO = new FarmacoInUsoDTO();
        responseDTO.setId(idHelper.objectIdToString(farmacoInUsoEsistente.getId()));  // ID come stringa
        responseDTO.setAttivo(farmacoInUsoEsistente.getAttivo());
        responseDTO.setDataFineTrattamento(farmacoInUsoEsistente.getDataFineTrattamento());
        responseDTO.setDataInizioTrattamento(farmacoInUsoEsistente.getDataInizioTrattamento());
        responseDTO.setDosaggio(farmacoInUsoEsistente.getDosaggio());
        responseDTO.setFrequenza(farmacoInUsoEsistente.getFrequenza());
        responseDTO.setNomeFarmaco(farmacoInUsoEsistente.getNomeFarmaco());
        responseDTO.setEfficacia(farmacoInUsoEsistente.getEfficacia());
        responseDTO.setRemissione(farmacoInUsoDTO.getRemissione());
    
        return ResponseEntity.ok(responseDTO); // Restituisci il farmaco aggiornato
    }
    
    @Transactional
    @DeleteMapping("/{id}/{pazienteId}")
    public ResponseEntity<Void> eliminaFarmacoInUso(@PathVariable String id, @PathVariable String pazienteId) {
     
        StoriaMedica storiaMedica = storiaMedicaRepository.findByPazienteId(idHelper.stringToObjectId(pazienteId));
        
        if (storiaMedica == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Storia medica non trovata
        }
       
        ObjectId objectId=idHelper.stringToObjectId(id);
        boolean removed = storiaMedica.getFarmaciInUso().removeIf(farmaco -> farmaco.getId().equals(objectId));
        // Aggiorna la storia medica
        if (removed) {
            storiaMedicaRepository.save(storiaMedica); // Salva la storia medica aggiornata
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Farmaco non trovato nella storia
        }

        return ResponseEntity.noContent().build(); // Operazione completata con successo
    }
    
   
    
}
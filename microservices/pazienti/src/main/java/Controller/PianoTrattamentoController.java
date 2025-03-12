package Controller;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import DTO.AppuntamentoDTOPaz;
import DTO.EventoDelTrattamentoDTO;
import DTO.PianoTrattamentoDTO;
import Model.EventoDelTrattamento;
import Model.PianoTrattamento;
import Services.EventoService;
import Services.PianoTrattamentoService;

import java.util.List;
import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

@RestController
@RequestMapping("/api/pazienti")
public class PianoTrattamentoController {

    private final PianoTrattamentoService pianoTrattamentoService;

    private final EventoService eventoService;
    
    public PianoTrattamentoController(PianoTrattamentoService pianoTrattamentoService, EventoService eventoService) {
        this.pianoTrattamentoService = pianoTrattamentoService;
        this.eventoService=eventoService;
    }
    
   /* @PostMapping("/{pazienteId}/piani")
    public PianoTrattamentoDTO creaPiano(@PathVariable String pazienteId, @RequestBody PianoTrattamentoDTO pianoDTO) {
        return pianoTrattamentoService.creaPiano(pazienteId, pianoDTO);
    }*/
    
    @PostMapping("/piani")
    public PianoTrattamentoDTO creaPianoTrattamento(@RequestBody PianoTrattamentoDTO pianoDTO) {
        return pianoTrattamentoService.creaPiano(pianoDTO);
    }

    @GetMapping("/{pazienteId}/piani")
    public List<PianoTrattamentoDTO> getPianiByPazienteId(@PathVariable String pazienteId) {
    	System.out.println("SIAMO NELLA GETPIANI");
    	 return pianoTrattamentoService.getPianiTrattamentoByPazienteId(pazienteId);
    }
    
    @PostMapping("/{pazienteId}/piani/{planId}/appointments/addAppointment")
    public ResponseEntity<PianoTrattamento> addAppointmentToPlan(
            @PathVariable String pazienteId,
            @PathVariable String planId,
            @RequestBody String appuntamentoId) throws AccountNotFoundException {
        try {
            // Chiama il servizio per aggiungere la tappa
            PianoTrattamento pianoAggiornato = pianoTrattamentoService.aggiungiAppuntamentoAlPiano(pazienteId, planId, appuntamentoId);
            return ResponseEntity.ok(pianoAggiornato);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{pazienteId}/piani/{planId}/events/addEvent")
    public ResponseEntity<PianoTrattamento> addEventToPlan(
            @PathVariable String pazienteId,
            @PathVariable String planId,
            @RequestBody EventoDelTrattamentoDTO eventoDTO) {
        try {
            // Chiama il servizio per aggiungere la tappa
            PianoTrattamento pianoAggiornato = pianoTrattamentoService.aggiungiEventoAlPiano(pazienteId, planId, eventoDTO);
            return ResponseEntity.ok(pianoAggiornato);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @DeleteMapping("/{id}")
    @Transactional  // Assicura che tutte le operazioni siano gestite come una singola transazione
    public ResponseEntity<Void> deleteEvento(@PathVariable String id) {
        // Controlla se l'ID è valido prima di procedere
        if (!ObjectId.isValid(id)) {
            return ResponseEntity.badRequest().build();  // Restituisce un 400 se l'ID non è valido
        }

        try {
            // Ottieni l'evento
            Optional<EventoDelTrattamento> eventoOpt = eventoService.getEvento(id);
            if (eventoOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            EventoDelTrattamento evento = eventoOpt.get();
            evento.setDeleted(true);  // Imposta l'evento come cancellato
            eventoService.salvaEvento(evento);
                System.out.println("EVENTO ELIMINATO");
                return ResponseEntity.noContent().build();  // 204 No Content
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    @DeleteMapping("/deactivatePlan/{treatmentPlanId}")
    @Transactional  // Assicura che tutte le operazioni siano gestite come una singola transazione
    public ResponseEntity<Void> deactivatePlan(@PathVariable String treatmentPlanId) {
        // Controlla se l'ID è valido prima di procedere
        if (!ObjectId.isValid(treatmentPlanId)) {
            return ResponseEntity.badRequest().build();  // Restituisce un 400 se l'ID non è valido
        }

        try {
            // Ottieni l'evento
            Optional<PianoTrattamento> pianoOpt = pianoTrattamentoService.getPiano(treatmentPlanId);
            if (pianoOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            PianoTrattamento piano = pianoOpt.get();
            piano.setAttivo(false);  // Imposta l'evento come cancellato
            pianoTrattamentoService.salvaPiano(piano);
                System.out.println("PIANO DISATTIVATO");
                return ResponseEntity.noContent().build();  // 204 No Content
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}

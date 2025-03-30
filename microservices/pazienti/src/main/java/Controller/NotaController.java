package Controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import DTO.FarmacoInUsoDTO;
import DTO.NotaDTO;
import DTO.NoteArchiviateDTO;
import Helpers.IdHelper;
import Model.FarmacoInUso;
import Model.Nota;
import Model.NoteArchiviate;
import Model.StoriaMedica;
import Repository.FarmacoInUsoRepository;
import Repository.NotaRepository;
import Repository.NoteArchiviateRepository;
import Repository.StoriaMedicaRepository;

@RestController
@RequestMapping("/api/nota")
public class NotaController {

	@Autowired
	private NotaRepository notaRepository;
	
	@Autowired
    private NoteArchiviateRepository noteArchiviateRepository;
	  
	@Autowired
    private IdHelper idHelper;
	
	  @Transactional
	    @PostMapping("/crea")
	    public ResponseEntity<NotaDTO> creaNota(@RequestBody NotaDTO notaDTO) {
		  try {
			System.out.println("Ricevuto JSON: " + new ObjectMapper().writeValueAsString(notaDTO));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        Nota notaInUsoFromDTO= new Nota();
	        notaInUsoFromDTO.setDottoreId(idHelper.stringToObjectId(notaDTO.getDottoreId()));
	        if (notaDTO.getPazienteId() != null) {
	        notaInUsoFromDTO.setPazienteId(idHelper.stringToObjectId(notaDTO.getPazienteId()));
	        }
	        if (notaDTO.getAppuntamentoId() != null) {
	        notaInUsoFromDTO.setAppuntamentoId(idHelper.stringToObjectId(notaDTO.getAppuntamentoId()));
	        }
	        notaInUsoFromDTO.setContenuto(notaDTO.getContenuto());
	        notaInUsoFromDTO.setDataCreazione(new Date());
	        notaInUsoFromDTO.setPriorita(notaDTO.getPriorita());
	        notaInUsoFromDTO.setTipoNota(notaDTO.getTipoNota());
	        notaInUsoFromDTO.setVisibilita(notaDTO.isVisibilita());
	        notaRepository.save(notaInUsoFromDTO);
	        // 🔹 Convertiamo `Nota` in `NotaDTO` per la risposta
	        NotaDTO responseDto = new NotaDTO();
	        responseDto.setId(idHelper.objectIdToString(notaInUsoFromDTO.getId())); // Converte `ObjectId` in `String`
	        responseDto.setDottoreId(notaInUsoFromDTO.getDottoreId().toHexString());
	        responseDto.setContenuto(notaInUsoFromDTO.getContenuto());
	        responseDto.setDataCreazione(notaInUsoFromDTO.getDataCreazione());
	        responseDto.setTipoNota(notaInUsoFromDTO.getTipoNota());
	        responseDto.setPriorita(notaInUsoFromDTO.getPriorita());
	        responseDto.setVisibilita(notaInUsoFromDTO.isVisibilita());

	        return ResponseEntity.ok(responseDto);
}
	  
	  @Transactional
	  @GetMapping("/getByIds")
	  public ResponseEntity<List<NotaDTO>> getNotesByIds(@RequestParam List<String> ids) {
	      List<ObjectId> objectIds = ids.stream()
	                                    .map(idHelper::stringToObjectId)
	                                    .collect(Collectors.toList());
	      List<Nota> notes = notaRepository.findAllById(objectIds);
	      // Mappare ogni Nota in NotaDTO
	      List<NotaDTO> noteDTOs = notes.stream()
	                                    .map(this::convertToDTO)
	                                    .collect(Collectors.toList());
	      return ResponseEntity.ok(noteDTOs);
	  }
	  
	  @GetMapping
	  public ResponseEntity<List<NotaDTO>> getNotes(String dottoreId){
		  ObjectId objId= idHelper.stringToObjectId(dottoreId);
				  List <Nota> note= notaRepository.findByDottoreId(objId);
		  List <NotaDTO> notes= new ArrayList<NotaDTO>();
		  for (Nota nota : note) { // 🔹 Usa un foreach invece di un for con indice
		        NotaDTO notaDTO = convertToDTO(nota);
		        notes.add(notaDTO); // ✅ Usa add() per aggiungere elementi alla lista
		    }
		  return ResponseEntity.ok(notes); 
	  }
	  
	  @PutMapping("/updateAssignation")
	  public ResponseEntity<NotaDTO> updateNoteAssignation(@RequestBody NotaDTO notaDTO){
		  Optional<Nota> optionalNota = notaRepository.findById(idHelper.stringToObjectId(notaDTO.getId()));
		  if (optionalNota.isPresent()) {
		        Nota nota = optionalNota.get(); 
		        nota.setPazienteId(idHelper.stringToObjectId(notaDTO.getPazienteId())); 
		        notaRepository.save(nota); // Salva l'oggetto Nota aggiornato nel database
		        return ResponseEntity.ok(notaDTO);
		    } else {
		        return ResponseEntity.notFound().build(); 
		    }
	  }
	  
	  @Transactional
	  @GetMapping("/byPatientId")
	  public ResponseEntity<List<NotaDTO>> getNotesByPatient(@RequestParam String patientId) {
		    try {
		        List<Nota> notes = notaRepository.findByPazienteId(idHelper.stringToObjectId(patientId));
		        
		        if (notes.isEmpty()) {
		            return ResponseEntity.noContent().build();
		        }

		        // Converte le entità in DTO
		        List<NotaDTO> noteDTOs = notes.stream()
		                .map(nota -> new NotaDTO(nota)) // Assicurati che NotaDTO abbia un costruttore che accetta un'entità Nota
		                .collect(Collectors.toList());

		        return ResponseEntity.ok(noteDTOs);
		    } catch (IllegalArgumentException e) {
		        return ResponseEntity.badRequest().body(null);
		    }
	  }
	  
	  
	  @Transactional
	    @PutMapping("/archivia/{id}")
	    public ResponseEntity<Map<String, String>> archiviaNota(@PathVariable String id) {
	        ObjectId objectId = idHelper.stringToObjectId(id);
	        Optional<Nota> optionalNota = notaRepository.findById(objectId);

	        if (optionalNota.isPresent()) {
	            Nota nota = optionalNota.get();

	            // Creiamo un'istanza di NotaArchiviate
	            NoteArchiviate notaArchiviata = new NoteArchiviate(nota);
	            noteArchiviateRepository.save(notaArchiviata); // Salviamo la nota archiviata

	            notaRepository.delete(nota); // Rimuoviamo la nota originale

	            // Restituiamo un JSON con un messaggio
	            Map<String, String> response = new HashMap<>();
	            response.put("message", "Nota archiviata con successo!");
	            return ResponseEntity.ok(response);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    @GetMapping("/archiviateByDottoreId")
	    public ResponseEntity<List<NoteArchiviateDTO>> getNoteArchiviateByDottoreId(@RequestParam String dottoreId) {
	    	ObjectId dottoreIdObj= idHelper.stringToObjectId(dottoreId);
	        List<NoteArchiviate> archiviate = noteArchiviateRepository.findByDottoreId(dottoreIdObj);
	        List<NoteArchiviateDTO> archiviateDTO = archiviate.stream()
	                .map(NoteArchiviateDTO::new)
	                .collect(Collectors.toList());

	        return ResponseEntity.ok(archiviateDTO);
	    }
	  
	  
	  private NotaDTO convertToDTO(Nota nota) {
		    NotaDTO dto = new NotaDTO();
		    dto.setId(nota.getId().toHexString());
		    dto.setDataCreazione(nota.getDataCreazione());
		    dto.setDataModifica(nota.getDataModifica());
		    dto.setContenuto(nota.getContenuto());
		    if (nota.getDottoreId() != null) {
		    dto.setDottoreId(nota.getDottoreId().toHexString());
		    }
		    if (nota.getPazienteId() != null) {
		    dto.setPazienteId(nota.getPazienteId().toHexString());
		    }
		    if (nota.getAppuntamentoId() != null) {
		    dto.setAppuntamentoId(nota.getAppuntamentoId().toHexString());
		    }
		    dto.setTipoNota(nota.getTipoNota());
		    dto.setPriorita(nota.getPriorita());
		    dto.setVisibilita(nota.isVisibilita());
		    return dto;
	  }
}

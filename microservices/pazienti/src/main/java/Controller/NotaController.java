package Controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import DTO.FarmacoInUsoDTO;
import DTO.NotaDTO;
import Helpers.IdHelper;
import Model.FarmacoInUso;
import Model.Nota;
import Model.StoriaMedica;
import Repository.FarmacoInUsoRepository;
import Repository.NotaRepository;
import Repository.StoriaMedicaRepository;

@RestController
@RequestMapping("/api/nota")
public class NotaController {

	@Autowired
	private NotaRepository notaRepository;
	
	  @Autowired
	    private FarmacoInUsoRepository farmacoInUsoRepository;
	  
	  @Autowired
	  private StoriaMedicaRepository storiaMedicaRepository;
	  
	  
	@Autowired
    private IdHelper idHelper;
	
	  @Transactional
	    @PostMapping("/crea")
	    public ResponseEntity<Nota> creaNota(@RequestBody NotaDTO notaDTO) {
	    	
	        Nota notaInUsoFromDTO= new Nota();
	        notaInUsoFromDTO.setFarmacoInUsoId(idHelper.stringToObjectId(notaDTO.getFarmacoInUsoId()));
	        notaInUsoFromDTO.setContenuto(notaDTO.getContenuto());
	        notaInUsoFromDTO.setDataCreazione(new Date());
	        notaInUsoFromDTO.setPriorita(notaDTO.getPriorita());
	        notaInUsoFromDTO.setTipoNota(notaDTO.getTipoNota());
	        //notaInUsoFromDTO.setUtente(notaDTO.getUtente());
	        notaInUsoFromDTO.setVisibilita(notaDTO.isVisibilita());
	        notaRepository.save(notaInUsoFromDTO);

	        // Recupero del FarmacoInUso associato alla nota
	        FarmacoInUso farmacoInUso = farmacoInUsoRepository.findById(notaDTO.getFarmacoInUsoId()).orElse(null);
	        // Verifica se il farmaco esiste
	        if (farmacoInUso != null) {
	            // Aggiungi l'ID della nota nella lista note, se non è già presente
	            if (farmacoInUso.getNote() == null) {
	                farmacoInUso.setNote(new ArrayList<>());
	            }
	            if (!farmacoInUso.getNote().contains(notaInUsoFromDTO.getId())) {
	                farmacoInUso.getNote().add(notaInUsoFromDTO.getId());
	                farmacoInUsoRepository.save(farmacoInUso);  // Salva l'aggiornamento
	            }
	      
	    }
	        
	        // Recupero della StoriaMedica che contiene il FarmacoInUso
	        Optional<StoriaMedica> storiaMedicaOpt = storiaMedicaRepository.findByFarmacoId(idHelper.stringToObjectId(notaDTO.getFarmacoInUsoId()));
	        if (storiaMedicaOpt.isPresent()) {
	            StoriaMedica storiaMedica = storiaMedicaOpt.get();
	            // Ora puoi lavorare con storiaMedica direttamente
	        
	        if (storiaMedica != null) {
	            // Verifica se il FarmacoInUso è presente nella lista dei farmaci della StoriaMedica
	            for (FarmacoInUso farmaco : storiaMedica.getFarmaciInUso()) {
	                if (farmaco.getId().equals(idHelper.stringToObjectId(notaDTO.getFarmacoInUsoId()))) {
	                    // Aggiungi l'ID della nota al farmaco in uso nella storia medica
	                    if (farmaco.getNote() == null) {
	                        farmaco.setNote(new ArrayList<>());
	                    }
	                    if (!farmaco.getNote().contains(notaInUsoFromDTO.getId())) {
	                        farmaco.getNote().add(notaInUsoFromDTO.getId());
	                        // Salva la storia medica aggiornata
	                        storiaMedicaRepository.save(storiaMedica);
	                    }
	                    break; // Esce dal ciclo, poiché abbiamo trovato il farmaco
	                }
	            }
	        }
	     
}
       return ResponseEntity.ok(notaInUsoFromDTO); // Restituisce il farmaco aggiunto
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
	  
	  private NotaDTO convertToDTO(Nota nota) {
		    NotaDTO dto = new NotaDTO();
		    dto.setId(nota.getId().toHexString());
		    dto.setDataCreazione(nota.getDataCreazione());
		    dto.setDataModifica(nota.getDataModifica());
		    dto.setContenuto(nota.getContenuto());
		    dto.setFarmacoInUsoId(nota.getFarmacoInUsoId().toHexString());
		    dto.setUtente(nota.getUtente());
		    dto.setTipoNota(nota.getTipoNota());
		    dto.setPriorita(nota.getPriorita());
		    dto.setVisibilita(nota.isVisibilita());
		    return dto;
	  }
}

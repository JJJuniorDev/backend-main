package Controller;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import DTO.AllegatoDTO;
import Helpers.IdHelper;
import Model.Allegato;
import Services.AllegatoService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/allegati")
public class AllegatoController {

    @Autowired
    private AllegatoService allegatoService;

    @Autowired
    private IdHelper idHelper;
    
    @PostMapping("/upload")
    public ResponseEntity<?> uploadAllegato(@RequestParam("file") MultipartFile file,
                                            @RequestParam(value = "pazienteId", required = false) String pazienteId,
                                            @RequestParam(value="dottoreId", required = false) String dottoreId) {
    	try {
        	AllegatoDTO allegatoDTO = allegatoService.salvaAllegato(file, pazienteId, dottoreId);
            return ResponseEntity.ok(allegatoDTO);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Errore durante il caricamento del file.");
        }
    }

    @GetMapping("/paziente/{pazienteId}")
    public List<AllegatoDTO> getAllegatiPerPaziente(@PathVariable String pazienteId) {
    	 return allegatoService.getAllegatiPerPaziente(pazienteId);
    }

    @GetMapping("/dottore/{dottoreId}")
    public List<AllegatoDTO> getAllegatiPerDottore(@PathVariable String dottoreId) {
        return allegatoService.getAllegatiPerDottore(dottoreId);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadAllegato(@PathVariable String id) {
        return allegatoService.getAllegato(id)
                .map(allegato -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + allegato.getNomeFile())
                        .contentType(MediaType.parseMediaType(allegato.getTipoFile()))
                        .body(allegato.getContenuto()))
                .orElse(ResponseEntity.notFound().build());
    }
}

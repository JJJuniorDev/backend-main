package Controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import DTO.VideochiamataDTO;
import Helpers.IdHelper;
import Model.Videochiamata;
import Services.VideochiamataService;

@RestController
@RequestMapping("/videochiamate")
public class VideochiamataController {

    @Autowired
    private VideochiamataService videochiamataService;

    @Autowired
    private IdHelper idHelper;
    
    @PostMapping("/crea")
    public ResponseEntity<VideochiamataDTO> creaVideochiamata(@RequestBody VideochiamataDTO request) {
       ObjectId objDottoreId= idHelper.stringToObjectId(request.getDottoreId());
     //  ObjectId objPazienteId= idHelper.stringToObjectId(request.getPazienteId());
        Videochiamata videochiamata = videochiamataService.creaVideochiamata(
          objDottoreId,
            request.getDataChiamata()
        );
        
        // Crea un DTO di risposta, includendo il link generato
        VideochiamataDTO response = new VideochiamataDTO();
        response.setId(idHelper.objectIdToString(videochiamata.getId()));
        response.setLink(videochiamata.getLink());
        response.setDataChiamata(videochiamata.getDataChiamata());
        response.setDottoreId(request.getDottoreId());
//        response.setPazienteId(request.getPazienteId());
        
        
        return new ResponseEntity<VideochiamataDTO>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{link}")
    public ResponseEntity<VideochiamataDTO> getVideochiamata(@PathVariable String link) {
        Videochiamata videochiamata = videochiamataService.getVideochiamataByLink(link);
        if (videochiamata == null) {
            System.out.println("⚠️ Nessuna videochiamata trovata per il link: " + link);
            return ResponseEntity.noContent().build(); // ✅ 204 No Content invece di 404
        }
        VideochiamataDTO response = new VideochiamataDTO();
        if (videochiamata != null) {     	
             response.setId(idHelper.objectIdToString(videochiamata.getId()));
             response.setLink(videochiamata.getLink());
             response.setDataChiamata(videochiamata.getDataChiamata());
             response.setDottoreId(idHelper.objectIdToString(videochiamata.getDottoreId()));
            // response.setPazienteId(idHelper.objectIdToString(videochiamata.getPazienteId()));
                   } 
        return ResponseEntity.ok(response);
    }
    
    
    
    
    
    
    
    
}

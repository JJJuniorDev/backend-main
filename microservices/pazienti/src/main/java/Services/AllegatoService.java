package Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import DTO.AllegatoDTO;
import Helpers.IdHelper;
import Model.Allegato;
import Repository.AllegatoRepository;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AllegatoService {

    @Autowired
    private AllegatoRepository allegatoRepository;

    @Autowired
    private IdHelper idHelper;
    
    public AllegatoDTO salvaAllegato(MultipartFile file, String pazienteId, String dottoreId) throws IOException {
        Allegato allegato = new Allegato();
        allegato.setNomeFile(file.getOriginalFilename());
        allegato.setTipoFile(file.getContentType());
        allegato.setContenuto(file.getBytes()); // Oppure salva su file system e memorizza il percorso
        allegato.setDataCaricamento(new Date());

        if (pazienteId != null && !pazienteId.isEmpty() && !pazienteId.equalsIgnoreCase("default")) {
            allegato.setPazienteId(idHelper.stringToObjectId(pazienteId));
        }
        if (dottoreId!= null && !dottoreId.isEmpty()) {
        	allegato.setDottoreId(idHelper.stringToObjectId(dottoreId));
        }
        Allegato savedAllegato = allegatoRepository.save(allegato);
        return convertToDTO(savedAllegato);  // Restituisci direttamente il DTO
    }

    public List<AllegatoDTO> getAllegatiPerPaziente(String pazienteId) {
        List<Allegato> allegati = allegatoRepository.findByPazienteId(idHelper.stringToObjectId(pazienteId));
        return allegati.stream()
                       .map(this::convertToDTO)
                       .collect(Collectors.toList());
    }

    public List<AllegatoDTO> getAllegatiPerDottore(String dottoreId) {
        List<Allegato> allegati = allegatoRepository.findByDottoreId(idHelper.stringToObjectId(dottoreId));
        return allegati.stream()
                       .map(this::convertToDTO)
                       .collect(Collectors.toList());
    }

    public Optional<AllegatoDTO> getAllegato(String id) {
        return allegatoRepository.findById(idHelper.stringToObjectId(id))
                .map(this::convertToDTO);
    }
    
    // Metodo di conversione da Allegato a AllegatoDTO
    private AllegatoDTO convertToDTO(Allegato allegato) {
        AllegatoDTO dto = new AllegatoDTO();
        dto.setId(idHelper.objectIdToString(allegato.getId()));  // Converti ObjectId in Stringa
        dto.setNomeFile(allegato.getNomeFile());
        dto.setTipoFile(allegato.getTipoFile());
        dto.setDataCaricamento(allegato.getDataCaricamento());
        dto.setContenuto(allegato.getContenuto());
        if (allegato.getPazienteId() != null) {
            dto.setPazienteId(idHelper.objectIdToString(allegato.getPazienteId()));
        }

        if (allegato.getDottoreId() != null) {
            dto.setDottoreId(idHelper.objectIdToString(allegato.getDottoreId()));
        }

        return dto;
    }
}

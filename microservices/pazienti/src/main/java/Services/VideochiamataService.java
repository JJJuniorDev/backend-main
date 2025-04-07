package Services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Model.Videochiamata;
import Repository.VideochiamataRepository;

@Service
public class VideochiamataService {

    @Autowired
    private VideochiamataRepository videochiamataRepository;

    public Videochiamata creaVideochiamata(ObjectId dottoreId, LocalDateTime dataChiamata) {
        String link = UUID.randomUUID().toString(); // Generazione del link univoco
        Videochiamata videochiamata = new Videochiamata();
        videochiamata.setLink(link);
        videochiamata.setDottoreId(dottoreId);
     //   videochiamata.setPazienteId(pazienteId);
        videochiamata.setDataChiamata(dataChiamata);

        return videochiamataRepository.save(videochiamata);
    }

    public Videochiamata getVideochiamataByLink(String link) {
        return videochiamataRepository.findByLink(link).orElse(null);
    }
}

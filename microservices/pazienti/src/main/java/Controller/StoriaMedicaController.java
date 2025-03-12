package Controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import DTO.FarmacoInUsoDTO;
import DTO.StoriaMedicaDTO;
import Helpers.IdHelper;
import Model.StoriaMedica;
import Repository.StoriaMedicaRepository;

@RestController
@RequestMapping("/api/pazienti/medical-history")
public class StoriaMedicaController {

	@Autowired
    private StoriaMedicaRepository storiaMedicaRepository;

	@Autowired
    private IdHelper idHelper; // Iniettiamo il nostro helper
	
    @PostMapping
    public StoriaMedica aggiungiStoriaMedica(@RequestBody StoriaMedica storiaMedica) {
        storiaMedica.setProssimoControllo(new Date());
        return storiaMedicaRepository.save(storiaMedica);
    }

    @GetMapping("/{pazienteId}")
    public StoriaMedicaDTO ottieniStoriaMedica(@PathVariable String pazienteId) throws Exception {
    	System.out.println("SIAMO IN OTTIENI STORIA MEDICA");

        ObjectId pazienteIdObj;
        try {
            pazienteIdObj = idHelper.stringToObjectId(pazienteId);
        } catch (IllegalArgumentException e) {
            throw new Exception("L'ID fornito non è valido: " + pazienteId);
        }

        StoriaMedica storiaMedica = storiaMedicaRepository.findByPazienteId(pazienteIdObj);
        if (storiaMedica == null) {
        	  // Se non esiste, crea una nuova storia medica
            System.out.println("Storia medica non trovata. Creazione di una nuova storia medica per il paziente con ID: " + pazienteId);
            storiaMedica = new StoriaMedica();
            storiaMedica.setPazienteId(pazienteIdObj);
            storiaMedica.setAnamnesiGenerica("Anamnesi di Default"); // Inizializza anamnesi vuota
            storiaMedica.setFarmaciInUso(new ArrayList<>()); // Lista vuota di farmaci
            storiaMedica = storiaMedicaRepository.save(storiaMedica); // Salva nel database
        }
        // Convertiamo in DTO
        StoriaMedicaDTO dto = new StoriaMedicaDTO();
        dto.setPazienteId(storiaMedica.getPazienteId().toString());
        dto.setAnamnesi(storiaMedica.getAnamnesiGenerica());
        dto.setFarmaciInUso(
        		storiaMedica.getFarmaciInUso().stream().map(farmaco -> {
        	        FarmacoInUsoDTO farmacoDTO = new FarmacoInUsoDTO();
        	        farmacoDTO.setId(farmaco.getId().toString());
        	        farmacoDTO.setDosaggio(farmaco.getDosaggio());
        	        farmacoDTO.setFrequenza(farmaco.getFrequenza());
        	        farmacoDTO.setNomeFarmaco(farmaco.getNomeFarmaco());
        	        farmacoDTO.setDataInizioTrattamento(farmaco.getDataInizioTrattamento());
        	        farmacoDTO.setDataFineTrattamento(farmaco.getDataFineTrattamento());
        	        farmacoDTO.setPazienteId(idHelper.objectIdToString(farmaco.getPazienteId()));
        	        farmacoDTO.setAttivo(farmaco.getAttivo());
        	        farmacoDTO.setEfficacia(farmaco.getEfficacia());
        	        farmacoDTO.setRemissione(farmaco.getRemissione());
        	        
        	     
        	       farmacoDTO.setFarmacoId(idHelper.objectIdToString(farmaco.getFarmacoId()));
        	       farmacoDTO.setNote(
        	    		    farmaco.getNote().stream()
        	    		        .map(idHelper::objectIdToString)
        	    		        .collect(Collectors.toList())
        	    		);
        	    // Calcola la durata attuale come differenza tra data di inizio trattamento e data attuale
                   String durataAttuale = calcolaDurataAttuale(farmaco.getDataInizioTrattamento());
                   farmacoDTO.setDurataAttuale(durataAttuale);
                   
        	        return farmacoDTO;
        	    }).collect(Collectors.toList()) // Usa Collectors.toList() per raccogliere lo stream in una lista
        	);
  
        return dto;
    }
   
    

private String calcolaDurataAttuale(Date dataInizioTrattamento) {
    if (dataInizioTrattamento == null) {
        return "Data di inizio trattamento non valida";
    }

    System.out.println("DATA INIZIO "+dataInizioTrattamento);
    // Converti la Date in LocalDate
    LocalDate startDate = new java.sql.Date(dataInizioTrattamento.getTime()).toLocalDate();
    LocalDate currentDate = LocalDate.now();

    long giorniTotali = ChronoUnit.DAYS.between(startDate, currentDate);
    long anni = giorniTotali / 365;
    giorniTotali = giorniTotali % 365;

    long mesi = giorniTotali / 30;
    giorniTotali = giorniTotali % 30;

    StringBuilder durata = new StringBuilder();

    if (anni > 0) {
        durata.append(anni).append(" anno").append(anni > 1 ? "i" : "");
    }

    if (mesi > 0) {
        if (durata.length() > 0) durata.append(", ");
        durata.append(mesi).append(" mese").append(mesi > 1 ? "i" : "");
    }

    if (giorniTotali > 0) {
        if (durata.length() > 0) durata.append(", ");
        durata.append(giorniTotali).append(" giorno").append(giorniTotali > 1 ? "i" : "");
    }

    return durata.toString();
}
}

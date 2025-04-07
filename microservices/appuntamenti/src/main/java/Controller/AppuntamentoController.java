package Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import DTO.AppuntamentoDTO;
import DTO.PazienteDTO;
import Helpers.IdHelper;
import Model.Appuntamento;
import Model.Fattura;
import Services.AppuntamentoService;
import Services.EmailService;
import Services.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/appuntamenti")
public class AppuntamentoController {

    @Autowired
    private AppuntamentoService appuntamentoService;

   /* @Autowired
    private EmailService emailService;*/
    
   // @Autowired
  //  private WhatsAppService whatsAppService;
    
    /*@Autowired
    private FileStorageService fileStorageService;*/
    
    @Autowired
    private RestTemplate restTemplateR;
    
    
    
    @Autowired
    private IdHelper idHelper;
    
    private final String sicurezzaServiceUrl = "http://localhost:8080/api/sicurezza";
    
    private static final Logger logger = LoggerFactory.getLogger(AppuntamentoController.class);
    
    
    
    @PostMapping(value = "/by-ids", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppuntamentoDTO>> getAppuntamentiByIds(@RequestBody List<String> appuntamentiIds) {
    
    	System.out.println("appuntamentiIds ricevuti: "+appuntamentiIds);
    	List<AppuntamentoDTO> appuntamenti = appuntamentoService.getAppuntamentiByIds(appuntamentiIds);
        System.out.println(appuntamenti);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(appuntamenti);
    }
    
  //Metodo per verificare il token JWT con il microservizio Sicurezza
    private boolean verificaTokenJWT(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Boolean> response = restTemplateR.exchange(sicurezzaServiceUrl + "/verificaToken", HttpMethod.GET, entity, Boolean.class);
        return response.getBody() != null && response.getBody();
    }
    
    @GetMapping("/onlyAppointment/{id}")
    public ResponseEntity<AppuntamentoDTO> getAppuntamento(@PathVariable(name= "id") String id){
    	  if (!ObjectId.isValid(id)) {
              logger.warn("ID non valido: {}", id);  // Log WARN per un ID non valido
              return ResponseEntity.badRequest().build();
          }
    	  try {
    		 Optional<Appuntamento> appuntamento=appuntamentoService.getAppuntamento(id);
    		  //AppuntamentoDTO appuntamentoDto= new AppuntamentoDTO(appuntamento, idHelper);
    		  Optional<AppuntamentoDTO> appuntamentoDtoOpt = appuntamento.map(a -> new AppuntamentoDTO(a, idHelper));
    		  return appuntamentoDtoOpt
    		      .map(ResponseEntity::ok)
    		      .orElseGet(() -> {
    		          logger.info("Appuntamento non trovato per ID: {}", id);  // Log INFO se l'appuntamento non è trovato
    		          return ResponseEntity.notFound().build();
    		      });
    	  }   	 
    	  catch (Exception e) {
              logger.error("Errore nel recupero dell'appuntamento con id: {}", id, e); 
              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
          }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AppuntamentoDTO> getAppuntamentoConPaziente(@PathVariable("id") String id) {
        if (!ObjectId.isValid(id)) {
            logger.warn("ID non valido: {}", id);  // Log WARN per un ID non valido
            return ResponseEntity.badRequest().build();
        }
        try {
            Optional<AppuntamentoDTO> appuntamentoDTOOpt = appuntamentoService.getAppuntamentoConPaziente(id);
            return appuntamentoDTOOpt
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.info("Appuntamento non trovato per ID: {}", id);  // Log INFO se l'appuntamento non è trovato
                    return ResponseEntity.notFound().build();
                });
        } catch (Exception e) {
            logger.error("Errore nel recupero dell'appuntamento con paziente: {}", id, e);  // Log ERROR per eccezioni generiche
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public List<AppuntamentoDTO> getAllAppuntamenti() {
       logger.info("IN getAllAppuntamenti");
        return appuntamentoService.getAllAppuntamenti();
    }
    
    @GetMapping("/dottore/{dottoreId}")
    public List<AppuntamentoDTO> getAppuntamentiPerDottore(@PathVariable("dottoreId") String dottoreId) {
       ObjectId idDottoreObj= idHelper.stringToObjectId(dottoreId);
       List<Appuntamento> appuntamenti = appuntamentoService.getAppuntamentiByDottoreId(idDottoreObj);

       // Trasforma l'ObjectId in stringa per ogni appuntamento
       List<AppuntamentoDTO> appuntamentiDTO = appuntamenti.stream()
           .map(appuntamento -> {
               AppuntamentoDTO dto = new AppuntamentoDTO();
               dto.setId(idHelper.objectIdToString(appuntamento.getId())); // Converti l'ObjectId in stringa
               dto.setDottoreId(idHelper.objectIdToString(appuntamento.getDottoreId()));
               dto.setPazienteId(idHelper.objectIdToString(appuntamento.getPazienteId())); // Converti l'ObjectId in stringa
               dto.setCodiceFiscalePaziente(appuntamento.getCodiceFiscalePaziente());
               dto.setDataEOrario(
            		    appuntamento.getDataEOrario()
            		        .atZone(ZoneId.of("Europe/Rome"))
            		        .toOffsetDateTime()
            		);
               dto.setStato(appuntamento.getStato());
               dto.setNote(appuntamento.getNote());
               
               // Copia altre proprietà dell'appuntamento
               return dto;
           })
           .collect(Collectors.toList());

       return appuntamentiDTO;
    }


   /* @PostMapping
    public Appuntamento createAppuntamento(@RequestBody Appuntamento appuntamento) {
        return appuntamentoService.createAppuntamento(appuntamento);
    }
    */

 
    @Transactional
@PostMapping
public AppuntamentoDTO createAppuntamento(@RequestBody AppuntamentoDTO appuntamentoDTO,
		 HttpServletRequest request) {
    Appuntamento appuntamento = new Appuntamento();
    appuntamento.setDataEOrario(appuntamentoDTO.getDataEOrario().toLocalDateTime());
    appuntamento.setTrattamento(appuntamentoDTO.getTrattamento());
    appuntamento.setNote(appuntamentoDTO.getNote());
    appuntamento.setStato(appuntamentoDTO.getStato());
   appuntamento.setPazienteId(idHelper.stringToObjectId(appuntamentoDTO.getPazienteId()));
   appuntamento.setDottoreId(idHelper.stringToObjectId(appuntamentoDTO.getDottoreId()));
 // Crea l'appuntamento
    Appuntamento nuovoAppuntamento = appuntamentoService.createAppuntamento(appuntamento);
    // Chiamata al servizio Paziente per aggiornare il paziente con il nuovo appuntamento
   
    String pazienteId = appuntamentoDTO.getPazienteId();
    String appuntamentoId = idHelper.objectIdToString(nuovoAppuntamento.getId());  // Ottieni l'ID dell'appuntamento creato
    // Chiamata HTTP per aggiornare il paziente
    //AGGIORNO IL PAZIENTE MANDANDOGLI L'ID DELL'APPUNTAMENTO DA INSERIRE
    String pazienteServiceUrl = "http://localhost:8080/api/pazienti/" + pazienteId + "/aggiungi-appuntamento";
    // ✅ Ottieni token dalla richiesta in entrata
    String token = request.getHeader("Authorization");
    
    // ✅ Prepara headers con token e tipo contenuto
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", token); // ⚠️ Deve includere Bearer
    headers.setContentType(MediaType.TEXT_PLAIN);

    HttpEntity<String> entity = new HttpEntity<>(appuntamentoId, headers);

    try {
        // ✅ Esegui la PUT con il token incluso
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(
            pazienteServiceUrl,
            HttpMethod.PUT,
            entity,
            Void.class
        );
    } catch (RestClientException e) {
        System.err.println("Errore nell'aggiornare il paziente: " + e.getMessage());
        throw new RuntimeException("Errore nell'aggiornare il paziente con il nuovo appuntamento", e);
    }

    // Converti l'Appuntamento in AppuntamentoDTO da restituire
    AppuntamentoDTO nuovoAppuntamentoDTO = new AppuntamentoDTO();
    nuovoAppuntamentoDTO.setId(appuntamentoId); // Converti l'ID in stringa
    nuovoAppuntamentoDTO.setDataEOrario(
    	    appuntamento.getDataEOrario()
    	        .atZone(ZoneId.of("Europe/Rome"))
    	        .toOffsetDateTime()
    	);
    nuovoAppuntamentoDTO.setTrattamento(nuovoAppuntamento.getTrattamento());
    nuovoAppuntamentoDTO.setNote(nuovoAppuntamento.getNote());
    nuovoAppuntamentoDTO.setStato(nuovoAppuntamento.getStato());
    nuovoAppuntamentoDTO.setPazienteId(pazienteId);

    return nuovoAppuntamentoDTO;
}


@PutMapping("/{id}")
public ResponseEntity<Appuntamento> updateAppuntamento(@PathVariable String id, @RequestBody AppuntamentoDTO appuntamentoDTO) {
    try {
        Appuntamento updatedAppuntamento = appuntamentoService.updateAppuntamento(id, appuntamentoDTO);
        return ResponseEntity.ok(updatedAppuntamento);  // Restituisce l'appuntamento aggiornato con un codice 200
    } catch (IllegalArgumentException e) {
        return ResponseEntity.notFound().build();  // Restituisce un 404 se non è stato trovato
    } catch (Exception e) {
        return ResponseEntity.status(500).build();  // Restituisce un 500 in caso di errore generico
    }
}


@DeleteMapping("/{id}")
@Transactional  // Assicura che tutte le operazioni siano gestite come una singola transazione
public ResponseEntity<Void> deleteAppuntamento(@PathVariable String id) {
    // Controlla se l'ID è valido prima di procedere
    if (!ObjectId.isValid(id)) {
        return ResponseEntity.badRequest().build();  // Restituisce un 400 se l'ID non è valido
    }

    try {
        // Ottieni l'appuntamento e il paziente associato
      Optional<Appuntamento> appuntamentoOpt = appuntamentoService.getAppuntamento(id);
        //if (appuntamentoOpt.isEmpty()) {
          //  return ResponseEntity.notFound().build();
        //}
        String pazienteId=idHelper.objectIdToString((appuntamentoOpt).get().getPazienteId());
     
        // Elimina l'appuntamento
        boolean deleted = appuntamentoService.deleteAppuntamento(id);
        if (deleted) {
        	System.out.println("APP ELIMINATO");
        	try {
        		String pazienteServiceUrl="http://localhost:8080/api/pazienti/"+pazienteId + "/rimuovi-appuntamento";
        		  restTemplateR.put(pazienteServiceUrl, id); // Passa l'ID dell'appuntamento da rimuovere
        	}
        	catch (Exception e) {
                // Gestisci errori di connessione o fallimenti del microservizio Paziente
                return ResponseEntity.status(500).body(null);
            }
            return ResponseEntity.noContent().build();  // Restituisce 204 No Content se l'operazione ha successo
        } else {
            return ResponseEntity.notFound().build();   // Restituisce 404 se l'appuntamento non è stato trovato
        }
    } catch (Exception e) {
        // Gestione degli errori: se qualcosa fallisce, la transazione viene automaticamente annullata (rollback)
        return ResponseEntity.status(500).build();
    }
}
 
@GetMapping("/upcoming")
public ResponseEntity<List<AppuntamentoDTO>> getAppuntamentiFuturi() {
    LocalDateTime now = LocalDateTime.now(); // Data e ora attuali
    List<AppuntamentoDTO> appuntamentiFuturi = appuntamentoService.getAppuntamentiFuturi(now);
    System.out.println(appuntamentiFuturi);
    return ResponseEntity.ok(appuntamentiFuturi);
}



/*******************************************************************************************************************************************
 * SEZIONE FATTURA
 * @param appointmentId
 * @return
 * *****************************************************************************************************************************************
 */

@PostMapping("/{appuntamentoId}/newFattura")
public Fattura creaFattura(@PathVariable String appuntamentoId, @RequestBody Fattura fattura) {
    Optional<Appuntamento> appuntamento = appuntamentoService.getAppuntamento(appuntamentoId);
    if (!appuntamento.isPresent()) {
        throw new RuntimeException("Appuntamento non trovato");
    }

    String idTrasformatoPaziente = idHelper.objectIdToString(appuntamento.get().getPazienteId());
    PazienteDTO paziente = restTemplateR.getForObject("http://localhost:8080/api/pazienti/" + idTrasformatoPaziente, PazienteDTO.class);
    if (paziente == null) {
        throw new RuntimeException("Paziente non trovato");
    }

    double costoTotale = 0;

    // Creazione della fattura
    Fattura fatturaCreata = new Fattura();
    fatturaCreata.setAppuntamentoId(appuntamento.get().getId().toHexString());
    fatturaCreata.setPazienteId(paziente.getId());
    fatturaCreata.setNomePaziente(paziente.getNome());
    fatturaCreata.setCognomePaziente(paziente.getCognome());
    fatturaCreata.setCosto(costoTotale);

    appuntamentoService.createFattura(fatturaCreata);

    // Creazione del PDF
    String pdfDirectory = "C:\\gestionaleDentista\\fatture";
    String pdfFilePath = pdfDirectory + "\\fattura_" + fatturaCreata.getFatturaId() + ".pdf";
    
    // Assicurati che la directory esista, altrimenti crea la cartella
    File directory = new File(pdfDirectory);
    if (!directory.exists()) {
        directory.mkdirs(); // Crea la directory se non esiste
    }
//  createPdf(pdfFilePath, fattura); -->fattura prende dal FE, fatturaCreata no
  
    createPdf(pdfFilePath, fatturaCreata);
    return fatturaCreata;
}


@GetMapping("/{appuntamentoId}/getFattura")
public ResponseEntity<Fattura> getFatturaByAppuntamentoId(@PathVariable String appuntamentoId) {
	System.out.println("SIAMO IN FATTURA");
	  try {
	        ObjectId objectIdAPP = new ObjectId(appuntamentoId); // Converte l'appuntamentoId in ObjectId
	        Fattura fattura = appuntamentoService.getFatturaByAppuntamentoId(objectIdAPP);
	       
	       	        if (fattura == null) { System.out.println("FATTURA NOME PAZIENTE ==="+fattura.getNomePaziente());
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fattura non trovata");
	        }
	        return ResponseEntity.ok(fattura);
	    } catch (IllegalArgumentException e) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID dell'appuntamento non valido");
	    }
}
private void createPdf(String filePath, Fattura fattura) {
    Document document = new Document();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    String formattedDate = dateFormat.format(new Date());

    try {
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        // Font personalizzati
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.DARK_GRAY);
        Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.DARK_GRAY);
        Font sectionTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

        // Titolo della fattura
        Paragraph title = new Paragraph("Studio Dentistico - Fattura", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(10);
        document.add(title);

        // Data della fattura
        Paragraph date = new Paragraph("Data: " + formattedDate, subtitleFont);
        date.setAlignment(Element.ALIGN_RIGHT);
        document.add(date);

        // Linea divisoria
        LineSeparator separator = new LineSeparator();
        separator.setLineColor(BaseColor.LIGHT_GRAY);
        document.add(new Chunk(separator));

        // Dettagli paziente
        document.add(new Paragraph("Dettagli Paziente", sectionTitleFont));
        document.add(new Paragraph("Nome: " + fattura.getNomePaziente(), normalFont));
        document.add(new Paragraph("Cognome: " + fattura.getCognomePaziente(), normalFont));
        document.add(new Paragraph("ID Paziente: " + fattura.getPazienteId(), normalFont));
        document.add(new Paragraph("ID Appuntamento: " + fattura.getAppuntamentoId(), normalFont));
        document.add(new Chunk(separator));

        // Riepilogo delle Operazioni e dei Costi
        document.add(new Paragraph("Riepilogo Operazioni", sectionTitleFont));
        PdfPTable table = new PdfPTable(2); // Tabella con 2 colonne
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Intestazione tabella
        PdfPCell cell1 = new PdfPCell(new Phrase("Operazione", sectionTitleFont));
        cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell1.setPadding(5);
        table.addCell(cell1);

        PdfPCell cell2 = new PdfPCell(new Phrase("Costo (€)", sectionTitleFont));
        cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell2.setPadding(5);
        table.addCell(cell2);

        String idAPP=fattura.getAppuntamentoId();
       Optional<Appuntamento> appuntamento=appuntamentoService.getAppuntamento(idAPP);
       document.add(new Paragraph("DATA APPUNTAMENTO: " + appuntamento.get().getDataEOrario(), normalFont));
       document.add(new Paragraph("TRATTAMENTO: " + appuntamento.get().getTrattamento(), normalFont));
       document.add(new Paragraph("CF PAZIENTE: " + appuntamento.get().getCodiceFiscalePaziente(), normalFont));
        // Itera attraverso le operazioni e aggiungi i dettagli alla tabella
       table.addCell(new PdfPCell(new Phrase("", normalFont)));
   //     for (OperazioneDTO operazione : appuntamento.get().getOperazioniIDS()) {
     //       table.addCell(new PdfPCell(new Phrase(operazione.getDescrizione(), normalFont)));
            table.addCell(new PdfPCell(new Phrase(String.format("%.2f", fattura.getCosto()), normalFont)));
       // } 

        document.add(table);

        // Totale costo
        Paragraph total = new Paragraph("Totale: € " + String.format("%.2f", fattura.getCosto()), sectionTitleFont);
        total.setAlignment(Element.ALIGN_RIGHT);
        total.setSpacingBefore(10);
        document.add(total);

    } catch (DocumentException | IOException e) {
        e.printStackTrace();
        throw new RuntimeException("Errore nella generazione del PDF", e);
    } finally {
        document.close();
    }
}

/*
@PostMapping("/{appuntamentoId}/sendEmail")
public ResponseEntity<String> sendEmail(@PathVariable String appuntamentoId,  @RequestBody Map<String, String> request) {
	try { 
	String email = request.get("email");
	// Ottieni la fattura dal database
    Fattura fattura = appuntamentoService.getFatturaByAppuntamentoId(idHelper.stringToObjectId(appuntamentoId));
    if (fattura == null) {
        logger.error("Fattura non trovata per l'appuntamento: " + appuntamentoId);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fattura non trovata");
    } 
    	Fattura newFattura=this.creaFattura(appuntamentoId, fattura);
    	logger.info("FATTURA CREATA "+newFattura);
    

    String pdfDirectory = "C:\\gestionaleDentista\\fatture";
    String pdfFilePath = pdfDirectory+"\\fattura_" + newFattura.getFatturaId() + ".pdf"; // Percorso al PDF della fattura

    
        // Invia l'email
        emailService.sendEmail(email, "Fattura Creata", "In allegato trovi la tua fattura", pdfFilePath);
        return ResponseEntity.ok("Email inviata con successo");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore nell'invio dell'email");
    }
}
*/
/*
@PostMapping("/{appuntamentoId}/sendWhatsApp")
public ResponseEntity<String> sendWhatsApp(@PathVariable String appuntamentoId, @RequestBody Map<String, String> request) {
    String phoneNumber = request.get("phoneNumber");
    if (phoneNumber == null || phoneNumber.isEmpty()) {
        return ResponseEntity.badRequest().body("Numero WhatsApp non fornito");
    }
    // Ottieni la fattura dal database
    Fattura fattura = appuntamentoService.getFatturaByAppuntamentoId(idHelper.stringToObjectId(appuntamentoId));
    if (fattura == null) {
        logger.error("Fattura non trovata per l'appuntamento: " + appuntamentoId);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fattura non trovata");
    }
    
    Fattura newFattura=this.creaFattura(appuntamentoId, fattura);
	logger.info("FATTURA CREATA CON ID  "+newFattura.getFatturaId());

    String pdfDirectory = "C:\\gestionaleDentista\\fatture";
    String pdfFilePath = pdfDirectory + "\\fattura_" + newFattura.getFatturaId() + ".pdf";

    try {
        // Condividi il PDF su WhatsApp
        String publicUrl = fileStorageService.uploadToPublicServer(pdfFilePath); // Devi implementare questa funzione
        String message = "Ciao, trovi in allegato la fattura relativa al tuo appuntamento.";
       // whatsAppService.sendWhatsAppMessage(phoneNumber, message, publicUrl);
        System.out.println("SIAMO QUA");
        return ResponseEntity.ok("Messaggio WhatsApp inviato con successo");
    } catch (Exception e) {
        logger.error("Errore nell'invio del messaggio WhatsApp", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore nell'invio del messaggio WhatsApp");
    }
}*/
@PutMapping("/{appuntamentoId}/updateStatus")
public Appuntamento updateAppuntamento (@PathVariable String appuntamentoId, @RequestBody String nuovoStato) throws Exception {
	return appuntamentoService.updateStatusAppuntamento(appuntamentoId, nuovoStato);
}
}
/* 
    @PutMapping("/{appuntamentoId}")
    public Appuntamento updateAppuntamento(@PathVariable String appuntamentoId, @RequestBody AppuntamentoDTO appuntamento) {
        return appuntamentoService.updateAppuntamento(appuntamentoId, appuntamento);
    }

   
    @DeleteMapping("/{appuntamentoId}")
    public void deleteAppuntamento(@PathVariable String appuntamentoId) {
        appuntamentoService.deleteAppuntamento(appuntamentoId);
    }
*/


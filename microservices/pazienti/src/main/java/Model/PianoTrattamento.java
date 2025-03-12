package Model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "PIANI_TRATTAMENTO")
public class PianoTrattamento {

	 @Id
	    private ObjectId id;
	 
	 @Field(name="PAZIENTE_ID")
	  private ObjectId pazienteId;  // Collegherà questo piano a un paziente
	  
	 @Field(name="NOME_PIANO")
	  private String nomePiano;
	  
	 @Field(name="ATTIVO")
	  private boolean attivo;
	
	   @Field(name = "EVENTI_TRATTAMENTO_IDS")
	    private List<String> eventiTrattamentoIds;
	   
	   @Field(name = "APPUNTAMENTI_IDS")
	    private List<String> appuntamentiIds = new ArrayList<>();
	   
	 @Field(name="DATA_INIZIO")
	  private String dataInizio;
	  
	 @Field(name="DATA_FINE")
	  private String dataFine;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public ObjectId getPazienteId() {
		return pazienteId;
	}

	public void setPazienteId(ObjectId pazienteId) {
		this.pazienteId = pazienteId;
	}

	public String getNomePiano() {
		return nomePiano;
	}

	public void setNomePiano(String nomePiano) {
		this.nomePiano = nomePiano;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}

	public String getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(String dataInizio) {
		this.dataInizio = dataInizio;
	}

	public String getDataFine() {
		return dataFine;
	}

	public void setDataFine(String dataFine) {
		this.dataFine = dataFine;
	}

	public List<String> getEventiTrattamentoIds() {
		return eventiTrattamentoIds;
	}

	public void setEventiTrattamentoIds(List<String> eventiTrattamentoIds) {
		this.eventiTrattamentoIds = eventiTrattamentoIds;
	}

	public List<String> getAppuntamentiIds() {
		return appuntamentiIds;
	}

	public void setAppuntamentiIds(List<String> appuntamentiIds) {
		this.appuntamentiIds = appuntamentiIds;
	}
	 	 
	
}

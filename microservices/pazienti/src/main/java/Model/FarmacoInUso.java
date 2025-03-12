package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "FARMACO_IN_USO")
public class FarmacoInUso {
	 @Id
	    private ObjectId id;
 
	  @Field("nome_farmaco")
    private String nomeFarmaco; // Nome del farmaco (es. Lorazepam)
   
	  @Field("paziente_id")
	    private ObjectId pazienteId;  // ID del paziente a cui è prescritto
	  
	  @Field("dosaggio")
	  private String dosaggio;  
	  
	  @Field("frequenza")
    private String frequenza;   // Frequenza (es. due volte al giorno)
	 
	  @Field("attivo")
	    private Boolean attivo;
	  
	  @Field("data_inizio_trattamento")
	    private Date dataInizioTrattamento;
	  
	  @Field("data_fine_trattamento")
	    private Date DataFineTrattamento;
  
	  @Field("note")
	  private List<ObjectId> note= new ArrayList<>();
	  
	  @Field("farmaco_id")
	  private ObjectId farmacoId;
	  
	  @Field("efficacia")
	  private String efficacia;
	  
	   @Field("remissione")
	   private String remissione;
	   
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getNomeFarmaco() {
		return nomeFarmaco;
	}

	public void setNomeFarmaco(String nomeFarmaco) {
		this.nomeFarmaco = nomeFarmaco;
	}

	public String getDosaggio() {
		return dosaggio;
	}

	public void setDosaggio(String dosaggio) {
		this.dosaggio = dosaggio;
	}

	public String getFrequenza() {
		return frequenza;
	}

	public void setFrequenza(String frequenza) {
		this.frequenza = frequenza;
	}


	public ObjectId getPazienteId() {
		return pazienteId;
	}

	public void setPazienteId(ObjectId pazienteId) {
		this.pazienteId = pazienteId;
	}

	public Boolean getAttivo() {
		return attivo;
	}

	public void setAttivo(Boolean attivo) {
		this.attivo = attivo;
	}

	public Date getDataInizioTrattamento() {
		return dataInizioTrattamento;
	}

	public void setDataInizioTrattamento(Date dataInizioTrattamento) {
		this.dataInizioTrattamento = dataInizioTrattamento;
	}

	public Date getDataFineTrattamento() {
		return DataFineTrattamento;
	}

	public void setDataFineTrattamento(Date dataFineTrattamento) {
		DataFineTrattamento = dataFineTrattamento;
	}

	public List<ObjectId> getNote() {
		return note;
	}

	public void setNote(List<ObjectId> note) {
		this.note = note;
	}

	public ObjectId getFarmacoId() {
		return farmacoId;
	}

	public void setFarmacoId(ObjectId farmacoId) {
		this.farmacoId = farmacoId;
	}

	public String getEfficacia() {
		return efficacia;
	}

	public void setEfficacia(String efficacia) {
		this.efficacia = efficacia;
	}

	public String getRemissione() {
		return remissione;
	}

	public void setRemissione(String remissione) {
		this.remissione = remissione;
	}

   
}

package Model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Document(collection = "NOTA")
public class Nota {
	 @Id
	private ObjectId id; // Un identificatore unico per la nota
   
	 @Field("data_creazione")
	 private Date dataCreazione; // La data in cui la nota è stata aggiunta
   
	 @Field("data_modifica")
	 private Date dataModifica; // Data dell'eventuale modifica della nota
   
	 @Field("contenuto")
	 private String contenuto; // Il testo della nota
 
	 @Field("dottore_id")
	 private ObjectId dottoreId; // L'utente che ha creato o modificato la nota
  
	 @Field("paziente_id")
	 private ObjectId pazienteId;
	 
	 @Field("appuntamento_id")
	 private ObjectId appuntamentoId;
	 
	 @Field("tipo_nota")
	 private String tipoNota; // Tipo di nota, ad esempio "medica", "osservazione"
  
	 @Field("priorita")
	 private String priorita; // Priorità della nota (alta, media, bassa)
 
	 @Field("visibilita")
	 private boolean visibilita; // Visibilità della nota (ad esempio, solo per gli operatori)

    // Costruttore
    public Nota(String contenuto, ObjectId dottoreId, ObjectId pazienteId, ObjectId appuntamentoId, String tipoNota, String priorita) {
        this.dataCreazione = new Date(); // Imposta la data di creazione
        this.dataModifica = new Date(); // Imposta la data di modifica iniziale
        this.contenuto = contenuto;
        this.pazienteId = pazienteId;
        this.appuntamentoId = appuntamentoId;
        this.dottoreId = dottoreId;
        this.tipoNota = tipoNota;
        this.priorita = "media"; // Impostazione di default della priorità
        this.visibilita = true; // Impostazione di default come visibile
    }
    
    public Nota() {
    	
    }

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Date getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public String getContenuto() {
		return contenuto;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}

	
	public ObjectId getDottoreId() {
		return dottoreId;
	}

	public void setDottoreId(ObjectId dottoreId) {
		this.dottoreId = dottoreId;
	}

	
	
	public ObjectId getPazienteId() {
		return pazienteId;
	}

	public void setPazienteId(ObjectId pazienteId) {
		this.pazienteId = pazienteId;
	}

	public ObjectId getAppuntamentoId() {
		return appuntamentoId;
	}

	public void setAppuntamentoId(ObjectId appuntamentoId) {
		this.appuntamentoId = appuntamentoId;
	}

	public String getTipoNota() {
		return tipoNota;
	}

	public void setTipoNota(String tipoNota) {
		this.tipoNota = tipoNota;
	}

	public String getPriorita() {
		return priorita;
	}

	public void setPriorita(String priorita) {
		this.priorita = priorita;
	}

	public boolean isVisibilita() {
		return visibilita;
	}

	public void setVisibilita(boolean visibilita) {
		this.visibilita = visibilita;
	}


}





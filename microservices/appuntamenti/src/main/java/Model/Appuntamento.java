package Model;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;




@Document(collection = "APPUNTAMENTO")
public class Appuntamento {

	@Id
	 private ObjectId id;

	@Field("DATA_E_ORARIO")
    private LocalDateTime dataEOrario;

	@Field("CODICE_FISCALE_PAZIENTE")
    private String codiceFiscalePaziente;


    @Field("TRATTAMENTO")
    private String trattamento;


    @Field("NOTE")
    private String note;


    @Field("PAZIENTE_ID")
    private ObjectId pazienteId;
    
    @Field("DOTTORE_ID") // Nuovo campo per salvare il riferimento al dentista
    private ObjectId dottoreId;
    
    @Field("STATO")
    private String stato;
    
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

	public LocalDateTime getDataEOrario() {
		return dataEOrario;
	}

	public void setDataEOrario(LocalDateTime dataEOrario) {
		this.dataEOrario = dataEOrario;
	}

	public String getCodiceFiscalePaziente() {
		return codiceFiscalePaziente;
	}

	public void setCodiceFiscalePaziente(String codiceFiscalePaziente) {
		this.codiceFiscalePaziente = codiceFiscalePaziente;
	}

	public String getTrattamento() {
		return trattamento;
	}

	public void setTrattamento(String trattamento) {
		this.trattamento = trattamento;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	   public ObjectId getPazienteId() {
	        return pazienteId;
	    }
	   
	  public void setPazienteId(ObjectId pazienteId) {
	        this.pazienteId=pazienteId;
	    }

	  public ObjectId getDottoreId() {
	        return dottoreId;
	    }

	    public void setDottoreId(ObjectId dottoreId) {
	        this.dottoreId =dottoreId;
	    }

		

		public String getStato() {
			return stato;
		}

		public void setStato(String stato) {
			this.stato = stato;
		}
	    
	    
}

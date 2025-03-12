package Model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "FATTURA")
public class Fattura {

	  @Id
	  private String fatturaId;
	  
	  @Field("APPUNTAMENTO_ID")
	  private String appuntamentoId;
	  
	  @Field("PAZIENTE_ID")
	    private String pazienteId;
	    
	  @Field("NOME_PAZIENTE")
	    private String nomePaziente;
	    
	  @Field("COGNOME_PAZIENTE")
	    private String cognomePaziente;
	    
	  @Field("COSTO")
	    private double costo;

	public String getFatturaId() {
		return fatturaId;
	}

	public void setFatturaId(String fatturaId) {
		this.fatturaId = fatturaId;
	}

	public String getAppuntamentoId() {
		return appuntamentoId;
	}

	public void setAppuntamentoId(String appuntamentoId) {
		this.appuntamentoId = appuntamentoId;
	}

	public String getPazienteId() {
		return pazienteId;
	}

	public void setPazienteId(String pazienteId) {
		this.pazienteId = pazienteId;
	}

	public String getNomePaziente() {
		return nomePaziente;
	}

	public void setNomePaziente(String nomePaziente) {
		this.nomePaziente = nomePaziente;
	}

	public String getCognomePaziente() {
		return cognomePaziente;
	}

	public void setCognomePaziente(String cognomePaziente) {
		this.cognomePaziente = cognomePaziente;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}
	  
	  
}

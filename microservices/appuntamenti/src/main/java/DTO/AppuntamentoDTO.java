package DTO;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import Helpers.IdHelper;
import Model.Appuntamento;

@JsonIgnoreProperties(ignoreUnknown = true) // Ignora eventuali campi extra nel JSON
public class AppuntamentoDTO {


	  @JsonProperty("id")
		 private String id;
	  
	  @JsonProperty("dataEOrario")
		    private LocalDateTime dataEOrario;
	  
	  @JsonProperty("trattamento")
		    private String trattamento;
		    
	  @JsonProperty("note")
		    private String note;
		    
		    
	  @JsonProperty("dottoreId")
		    private String dottoreId;
		    
	  @JsonProperty("pazienteId")
		    private String pazienteId;
		    
		  
		    
	  @JsonProperty("codiceFiscalePaziente")
		    private String codiceFiscalePaziente;
		    
		   
		
		    @JsonProperty("stato")
		    private String stato;
		    
		    public AppuntamentoDTO() {}

		    public AppuntamentoDTO(Optional<Appuntamento> appuntamento, IdHelper idHelper) {
		    	
		    	this.id=idHelper.objectIdToString(appuntamento.get().getId());
		    	this.dottoreId=idHelper.objectIdToString(appuntamento.get().getDottoreId());
		    	this.setCodiceFiscalePaziente(appuntamento.get().getCodiceFiscalePaziente());
		    	this.dataEOrario=appuntamento.get().getDataEOrario();
		    	this.trattamento=appuntamento.get().getTrattamento();
		    	this.note=appuntamento.get().getNote();
		    	 this.dottoreId = idHelper.objectIdToString(appuntamento.get().getDottoreId());
		    	 this.stato=appuntamento.get().getStato();
		    			 }
		    		
		    // Costruttore che combina appuntamento e paziente
		    public AppuntamentoDTO(Appuntamento appuntamento, PazienteDTO paziente, IdHelper idHelper) {
		    	
		        this.id = idHelper.objectIdToString(appuntamento.getId());
		        this.dataEOrario = appuntamento.getDataEOrario();
		        this.trattamento = appuntamento.getTrattamento();
		        this.setCodiceFiscalePaziente(appuntamento.getCodiceFiscalePaziente());
		        this.stato=appuntamento.getStato();
		   
		        this.pazienteId=idHelper.objectIdToString(appuntamento.getPazienteId());
		            this.dottoreId = idHelper.objectIdToString(appuntamento.getDottoreId());
		    }
		    
			public AppuntamentoDTO(Appuntamento a, IdHelper idHelper2) {
		
		    	this.id=idHelper2.objectIdToString(a.getId());
		    	//this.dentistaId=idHelper.objectIdToString(appuntamento.getDentistaId());
		    	this.setCodiceFiscalePaziente(a.getCodiceFiscalePaziente());
		    	this.dataEOrario=a.getDataEOrario();
		    	this.trattamento=a.getTrattamento();
		    	this.note=a.getNote();
		    	 this.dottoreId = idHelper2.objectIdToString(a.getDottoreId());
		    	 this.stato=a.getStato();
			}

			public String getId() {
				return id;
			}
			public void setId(String id) {
				this.id = id;
			}
			public LocalDateTime getDataEOrario() {
				return dataEOrario;
			}
			public void setDataEOrario(LocalDateTime localDateTime) {
				this.dataEOrario = localDateTime;
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
	   	 
			/* public PazienteDTO getPaziente() {
			        return paziente;
			    }

			    public void setPaziente(PazienteDTO paziente) {
			        this.paziente = paziente;
			    }*/

			
			
			    public String getDottoreId() {
			        return dottoreId;
			    }

			    public String getPazienteId() {
					return pazienteId;
				}

				public void setPazienteId(String pazienteId) {
					this.pazienteId = pazienteId;
				}

				public void setDottoreId(String dottoreId) {
			        this.dottoreId = dottoreId;
			    }

				public String getCodiceFiscalePaziente() {
					return codiceFiscalePaziente;
				}

				public void setCodiceFiscalePaziente(String codiceFiscalePaziente) {
					this.codiceFiscalePaziente = codiceFiscalePaziente;
				}

				public String getStato() {
					return stato;
				}

				public void setStato(String stato) {
					this.stato = stato;
				}
				
				
	}



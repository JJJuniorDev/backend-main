package DTO;

import java.util.List;

public class PianoTrattamentoDTO {
	  private String id;
	  private String pazienteId;
	 private String nomePiano;
	    private boolean attivo;
	    private List<EventoDelTrattamentoDTO> eventi;
	    private List<AppuntamentoDTOPaz> appuntamenti;
	    private String dataInizio;
	    private String dataFine;
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
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getPazienteId() {
			return pazienteId;
		}
		public void setPazienteId(String pazienteId) {
			this.pazienteId = pazienteId;
		}
	
		public List<EventoDelTrattamentoDTO> getEventi() {
			return eventi;
		}
		public void setEventi(List<EventoDelTrattamentoDTO> eventi) {
			this.eventi = eventi;
		}
		public List<AppuntamentoDTOPaz> getAppuntamenti() {
			return appuntamenti;
		}
		public void setAppuntamenti(List<AppuntamentoDTOPaz> appuntamenti) {
			this.appuntamenti = appuntamenti;
		}
	    
	    
	
}

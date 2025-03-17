package Model;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "EVENTO_DEL_TRATTAMENTO")
public class EventoDelTrattamento {

	 @Id
	    private ObjectId id;

	    @Field(name = "PIANO_TRATTAMENTO_ID")
	    private ObjectId pianoTrattamentoId; // ID del piano a cui appartiene

	    @Field(name = "DESCRIZIONE")
	    private String descrizione;

	    @Field(name = "DATA_E_ORARIO")
	    private LocalDateTime dataEOrario; // Data in cui la milestone deve essere raggiunta

	    @Field(name = "DELETED")
	    private boolean deleted; // Stato della milestone

	    @Field(name = "TIPOLOGIA")
	    private String tipologia;
	    
	    @Field(name = "DOTTORE_ID")
	    private ObjectId dottoreId;
	    
	    
		public ObjectId getId() {
			return id;
		}

		public void setId(ObjectId id) {
			this.id = id;
		}

		public ObjectId getPianoTrattamentoId() {
			return pianoTrattamentoId;
		}

		public void setPianoTrattamentoId(ObjectId pianoTrattamentoId) {
			this.pianoTrattamentoId = pianoTrattamentoId;
		}

		public String getDescrizione() {
			return descrizione;
		}

		public void setDescrizione(String descrizione) {
			this.descrizione = descrizione;
		}



		public LocalDateTime getDataEOrario() {
			return dataEOrario;
		}

		public void setDataEOrario(LocalDateTime dataEOrario) {
			this.dataEOrario = dataEOrario;
		}

		public boolean isDeleted() {
			return deleted;
		}

		public void setDeleted(boolean deleted) {
			this.deleted = deleted;
		}

		public String getTipologia() {
			return tipologia;
		}

		public void setTipologia(String tipologia) {
			this.tipologia = tipologia;
		}

		public ObjectId getDottoreId() {
			return dottoreId;
		}

		public void setDottoreId(ObjectId dottoreId) {
			this.dottoreId = dottoreId;
		}
	 
	    
}

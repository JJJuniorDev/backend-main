package Model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "NOTE_ARCHIVIATE")
public class NoteArchiviate {
	  @Id
	    private ObjectId id;
	    
	    @Field("data_creazione")
	    private Date dataCreazione;

	    @Field("data_modifica")
	    private Date dataModifica;

	    @Field("contenuto")
	    private String contenuto;

	    @Field("dottore_id")
	    private ObjectId dottoreId;

	    @Field("paziente_id")
	    private ObjectId pazienteId;

	    @Field("appuntamento_id")
	    private ObjectId appuntamentoId;

	    @Field("tipo_nota")
	    private String tipoNota;

	    @Field("priorita")
	    private String priorita;

	    @Field("visibilita")
	    private boolean visibilita;

	    // Costruttore vuoto richiesto da Spring Data
	    public NoteArchiviate() {}

	    // Costruttore per convertire una `Nota` in `NotaArchiviate`
	    public NoteArchiviate(Nota nota) {
	        this.dataCreazione = nota.getDataCreazione();
	        this.dataModifica = nota.getDataModifica();
	        this.contenuto = nota.getContenuto();
	        this.dottoreId = nota.getDottoreId();
	        this.pazienteId = nota.getPazienteId();
	        this.appuntamentoId = nota.getAppuntamentoId();
	        this.tipoNota = nota.getTipoNota();
	        this.priorita = nota.getPriorita();
	        this.visibilita = nota.isVisibilita();
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

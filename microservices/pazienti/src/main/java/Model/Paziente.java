package Model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "PAZIENTE")
public class Paziente {

	    @Id
	    private ObjectId id;

	    @Field(name="NOME")
	    private String nome;

	    @Field(name="COGNOME")
	    private String cognome;

	    @Field(name="CODICE_FISCALE")
	    private String codiceFiscale;

	    @Field(name="DATA_DI_NASCITA")
	    private String dataDiNascita;

	    @Field(name="SESSO")
	    private String sesso;

	    @Field(name="INDIRIZZO")
	    private String indirizzo;

	    @Field(name="NUMERO_DI_CELLULARE")
	    private String numeroDiCellulare;

	    @Field(name="DOTTORE_ID") //foreign key
	    private String dottoreId;

	    @Field(name="APPUNTAMENTI_IDS")
	    private List<ObjectId> appuntamentiIds;
	
	    @Field(name="STATO") //foreign key
	    private String stato;
	    
	    public Paziente(String nome, String cognome, String codiceFiscale, String dataDiNascita, 
                String sesso, String indirizzo, String numeroDiCellulare, String dottoreId, 
                String stato,List<ObjectId> appuntamentiIds) {
    this.nome = nome;
    this.cognome = cognome;
    this.codiceFiscale = codiceFiscale;
    this.dataDiNascita = dataDiNascita;
    this.sesso = sesso;
    this.indirizzo = indirizzo;
    this.numeroDiCellulare = numeroDiCellulare;
    this.dottoreId = dottoreId;
    this.stato = stato;
    this.appuntamentiIds=appuntamentiIds;
  
}
	    
	    public Paziente() {
	    }
	    
	    
		public ObjectId getId() {
			return id;
		}

		public void setId(ObjectId id) {
			this.id = id;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getCognome() {
			return cognome;
		}

		public void setCognome(String cognome) {
			this.cognome = cognome;
		}

		public String getCodiceFiscale() {
			return codiceFiscale;
		}

		public void setCodiceFiscale(String codiceFiscale) {
			this.codiceFiscale = codiceFiscale;
		}

		public String getDataDiNascita() {
			return dataDiNascita;
		}

		public void setDataDiNascita(String dataDiNascita) {
			this.dataDiNascita = dataDiNascita;
		}

		public String getSesso() {
			return sesso;
		}

		public void setSesso(String sesso) {
			this.sesso = sesso;
		}

		public String getIndirizzo() {
			return indirizzo;
		}

		public void setIndirizzo(String indirizzo) {
			this.indirizzo = indirizzo;
		}

		public String getNumeroDiCellulare() {
			return numeroDiCellulare;
		}

		public void setNumeroDiCellulare(String numeroDiCellulare) {
			this.numeroDiCellulare = numeroDiCellulare;
		}

		public String getDottoreId() {
			return dottoreId;
		}

		public void setDottoreId(String dottoreId) {
			this.dottoreId = dottoreId;
		}

		public List<ObjectId> getAppuntamentiIds() {
			return appuntamentiIds;
		}

		public void setAppuntamentiIds(List<ObjectId> appuntamentiIds) {
			this.appuntamentiIds = appuntamentiIds;
		}

		public String getStato() {
			return stato;
		}

		public void setStato(String stato) {
			this.stato = stato;
		}
		
		
}

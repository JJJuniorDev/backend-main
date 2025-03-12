package Model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "ALLEGATI")
public class Allegato {
    @Id
    private ObjectId id;
    
    @Field(name="NOME_FILE")
    private String nomeFile;
    
    @Field(name="TIPO_FILE")
    private String tipoFile;
    
    @Field(name="CONTENUTO")
    private byte[] contenuto; // Se salviamo direttamente su DB, altrimenti usiamo un URL
    
    @Field(name="PAZIENTE_ID")
    private ObjectId pazienteId; // Se associato a un paziente
   
    @Field(name="DOTTORE_ID")
    private ObjectId dottoreId; // Dottore proprietario
   
    @Field(name="DATA_CARICAMENTO")
    private Date dataCaricamento;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getTipoFile() {
		return tipoFile;
	}

	public void setTipoFile(String tipoFile) {
		this.tipoFile = tipoFile;
	}

	public byte[] getContenuto() {
		return contenuto;
	}

	public void setContenuto(byte[] contenuto) {
		this.contenuto = contenuto;
	}

	public ObjectId getPazienteId() {
		return pazienteId;
	}

	public void setPazienteId(ObjectId pazienteId) {
		this.pazienteId = pazienteId;
	}

	public ObjectId getDottoreId() {
		return dottoreId;
	}

	public void setDottoreId(ObjectId dottoreId) {
		this.dottoreId = dottoreId;
	}

	public Date getDataCaricamento() {
		return dataCaricamento;
	}

	public void setDataCaricamento(Date dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}

    // Getters e Setters
    
}

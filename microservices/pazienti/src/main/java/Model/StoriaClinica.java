package Model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "STORIA_CLINICA")
public class StoriaClinica {

    @Id
    private ObjectId id;
    
    @Field(name="PAZIENTE_ID")
    private ObjectId pazienteId;
    
    @Field(name="DIAGNOSI")
    private List<String> diagnosi; 
    // Elenco delle diagnosi
 
    @Field(name="TRATTAMENTI_PRECEDENTI")
    private List<String> trattamentiPrecedenti;  // Trattamenti precedenti eseguiti
 
    @Field(name="ALLERGIE")
    private List<String> allergie;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public ObjectId getPazienteId() {
		return pazienteId;
	}

	public void setPazienteId(ObjectId pazienteId) {
		this.pazienteId = pazienteId;
	}

	public List<String> getDiagnosi() {
		return diagnosi;
	}

	public void setDiagnosi(List<String> diagnosi) {
		this.diagnosi = diagnosi;
	}

	public List<String> getTrattamentiPrecedenti() {
		return trattamentiPrecedenti;
	}

	public void setTrattamentiPrecedenti(List<String> trattamentiPrecedenti) {
		this.trattamentiPrecedenti = trattamentiPrecedenti;
	}

	public List<String> getAllergie() {
		return allergie;
	}

	public void setAllergie(List<String> allergie) {
		this.allergie = allergie;
	}

    // Getters and setters
    
    
}

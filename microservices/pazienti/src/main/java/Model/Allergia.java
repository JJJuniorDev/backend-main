package Model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "ALLERGIA")
public class Allergia {

    @Id
    private ObjectId id;

    @Field("allergene")
    private String allergene;

    @Field("reazione")
    private String reazione;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getAllergene() {
		return allergene;
	}

	public void setAllergene(String allergene) {
		this.allergene = allergene;
	}

	public String getReazione() {
		return reazione;
	}

	public void setReazione(String reazione) {
		this.reazione = reazione;
	}
    
    
}

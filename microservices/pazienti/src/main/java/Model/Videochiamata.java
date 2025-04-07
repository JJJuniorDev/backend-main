package Model;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "VIDEOCHIAMATA")
public class Videochiamata {

	 @Id
	    private ObjectId id;
	 
	 @Field(name = "LINK")
	 private String link; // Link della videochiamata
	 
	 @Field(name = "DATA_CHIAMATA")
	 private LocalDateTime dataChiamata;
	    
	  @Field(name = "PAZIENTE_ID")
	    private ObjectId pazienteId;
	  
	  @Field(name = "DOTTORE_ID")
	    private ObjectId dottoreId;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public LocalDateTime getDataChiamata() {
		return dataChiamata;
	}

	public void setDataChiamata(LocalDateTime dataChiamata) {
		this.dataChiamata = dataChiamata;
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
	  
	  
	  
}

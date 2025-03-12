package Helpers;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class IdHelper {
	
	 public IdHelper() {
	        System.out.println("IdHelper inizializzato.");
	    }

	public ObjectId stringToObjectId(String id) {
		   if (id == null || id.length() != 24) {
		        throw new IllegalArgumentException("Invalid ID format: " + id);
		    }
	        return new ObjectId(id);
	}

	public String objectIdToString(ObjectId id) {
	    return id != null ? id.toHexString() : null;
	}

}

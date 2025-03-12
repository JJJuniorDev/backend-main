package Repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class FarmacoRepositoryImpl{

	  @Autowired
	    private MongoTemplate mongoTemplate;

	    
	    public List<String> findDistinctCategoria() {
	        // Usa distinct e converte in List
	        List<String> categorie = new ArrayList<>();
	        mongoTemplate.getCollection("FARMACI")
	            .distinct("categoria", String.class)
	            .forEach(categorie::add); // Aggiungi ciascun elemento alla lista
	        return categorie;
	    }}

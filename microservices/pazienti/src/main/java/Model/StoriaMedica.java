package Model;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "STORIA_MEDICA")
public class StoriaMedica {
    @Id
    private ObjectId id;

    @Field(name="pazienteId")
    private ObjectId pazienteId; // ID del paziente associato

    @Field(name="allergie")
    private List<Allergia> allergie; // Lista di allergie (embedded)

    @Field(name="farmaciInUso")
    private List<FarmacoInUso> farmaciInUso; // Lista di farmaci in uso (embedded)

    @Field(name="anamnesi")
    private String anamnesiGenerica;

    @Field(name="controllo_periodico")
    private boolean controlloPeriodico;
    
    @Field(name="prossimo_controllo")
    private Date prossimoControllo;
    // Getters e Setters

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


	public List<Allergia> getAllergie() {
		return allergie;
	}

	public void setAllergie(List<Allergia> allergie) {
		this.allergie = allergie;
	}

	public List<FarmacoInUso> getFarmaciInUso() {
		return farmaciInUso;
	}

	public void setFarmaciInUso(List<FarmacoInUso> farmaciInUso) {
		this.farmaciInUso = farmaciInUso;
	}

	
	public String getAnamnesiGenerica() {
		return anamnesiGenerica;
	}

	public void setAnamnesiGenerica(String anamnesiGenerica) {
		this.anamnesiGenerica = anamnesiGenerica;
	}

	public boolean isControlloPeriodico() {
		return controlloPeriodico;
	}

	public void setControlloPeriodico(boolean controlloPeriodico) {
		this.controlloPeriodico = controlloPeriodico;
	}

	public Date getProssimoControllo() {
		return prossimoControllo;
	}

	public void setProssimoControllo(Date prossimoControllo) {
		this.prossimoControllo = prossimoControllo;
	}
    
    
}

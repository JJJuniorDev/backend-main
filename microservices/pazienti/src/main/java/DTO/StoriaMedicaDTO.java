package DTO;

import java.util.List;

public class StoriaMedicaDTO {
    private String pazienteId;
    private List<FarmacoInUsoDTO> farmaciInUso;
    private String anamnesi;
    
	public String getPazienteId() {
		return pazienteId;
	}
	public void setPazienteId(String pazienteId) {
		this.pazienteId = pazienteId;
	}
	public List<FarmacoInUsoDTO> getFarmaciInUso() {
		return farmaciInUso;
	}
	public void setFarmaciInUso(List<FarmacoInUsoDTO> farmaciInUso) {
		this.farmaciInUso = farmaciInUso;
	}
	public String getAnamnesi() {
		return anamnesi;
	}
	public void setAnamnesi(String anamnesi) {
		this.anamnesi = anamnesi;
	}

 
}



package DTO;

import java.time.LocalDateTime;

import Helpers.IdHelper;
import Model.EventoDelTrattamento;

public class EventoDelTrattamentoDTO {
private String id;
private String pianoTrattamentoId;
private String descrizione;
private LocalDateTime dataEOrario;
private boolean deleted;
private String tipologia;
private String dottoreId;

public EventoDelTrattamentoDTO(EventoDelTrattamento a, IdHelper idHelper) {
	this.descrizione= a.getDescrizione();
	this.dataEOrario= a.getDataEOrario();
	this.deleted=a.isDeleted();
	this.tipologia=a.getTipologia();
	this.id=idHelper.objectIdToString(a.getId());
	this.pianoTrattamentoId=idHelper.objectIdToString(a.getPianoTrattamentoId());
	this.dottoreId= idHelper.objectIdToString(a.getDottoreId());
}

public EventoDelTrattamentoDTO() {
	
}

public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getPianoTrattamentoId() {
	return pianoTrattamentoId;
}
public void setPianoTrattamentoId(String pianoTrattamentoId) {
	this.pianoTrattamentoId = pianoTrattamentoId;
}
public String getDescrizione() {
	return descrizione;
}
public void setDescrizione(String descrizione) {
	this.descrizione = descrizione;
}
public LocalDateTime getDataEOrario() {
	return dataEOrario;
}
public void setDataEOrario(LocalDateTime dataEOrario) {
	this.dataEOrario = dataEOrario;
}
public boolean isDeleted() {
	return deleted;
}
public void setDeleted(boolean deleted) {
	this.deleted = deleted;
}
public String getTipologia() {
	return tipologia;
}
public void setTipologia(String tipologia) {
	this.tipologia = tipologia;
}
public String getDottoreId() {
	return dottoreId;
}
public void setDottoreId(String dottoreId) {
	this.dottoreId = dottoreId;
}


}

package DTO;

import java.time.LocalDateTime;

public class VideochiamataDTO {
	  private String id;
	  private String link; 
	  private LocalDateTime dataChiamata;
	  private String dottoreId;
	  private String pazienteId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public String getDottoreId() {
		return dottoreId;
	}
	public void setDottoreId(String dottoreId) {
		this.dottoreId = dottoreId;
	}
	public String getPazienteId() {
		return pazienteId;
	}
	public void setPazienteId(String pazienteId) {
		this.pazienteId = pazienteId;
	}
	  
	  
}

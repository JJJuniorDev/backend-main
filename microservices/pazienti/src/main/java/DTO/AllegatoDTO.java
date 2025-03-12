package DTO;

import java.util.Date;

public class AllegatoDTO {

    private String id;  // ID come stringa
    private String nomeFile;
    private String tipoFile;
    private byte[] contenuto;
    private String pazienteId;  // ID del paziente come stringa
    private String dottoreId;   // ID del dottore come stringa
    private Date dataCaricamento;

    // Getter e Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getPazienteId() {
        return pazienteId;
    }

    public void setPazienteId(String pazienteId) {
        this.pazienteId = pazienteId;
    }

    public String getDottoreId() {
        return dottoreId;
    }

    public void setDottoreId(String dottoreId) {
        this.dottoreId = dottoreId;
    }

    public Date getDataCaricamento() {
        return dataCaricamento;
    }

    public void setDataCaricamento(Date dataCaricamento) {
        this.dataCaricamento = dataCaricamento;
    }

	public byte[] getContenuto() {
		return contenuto;
	}

	public void setContenuto(byte[] contenuto) {
		this.contenuto = contenuto;
	}
    
    
}

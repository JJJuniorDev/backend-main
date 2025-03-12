package DTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PazienteDTO {
    private String id;
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String dataDiNascita;
    private String sesso;
    private String indirizzo;
    private String numeroDiCellulare;
    private String dottoreId;
    // Aggiungi questo campo per tracciare gli ID degli appuntamenti
    private List<ObjectId> appuntamentiIds;
    private String stato;
    
    @JsonProperty("appuntamentiIds") // Serializza questo getter al posto del campo originale
    public List<String> getAppuntamentiIdsAsString() {
        return appuntamentiIds != null
            ? appuntamentiIds.stream()
                .map(ObjectId::toHexString) // Converte ogni ObjectId in stringa
                .collect(Collectors.toList())
            : new ArrayList<>(); // Ritorna una lista vuota se null
    }
    
    
    // Getter e Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(String dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getNumeroDiCellulare() {
        return numeroDiCellulare;
    }

    public void setNumeroDiCellulare(String numeroDiCellulare) {
        this.numeroDiCellulare = numeroDiCellulare;
    }

    public String getDottoreId() {
        return dottoreId;
    }

    public void setDottoreId(String dottoreId) {
        this.dottoreId = dottoreId;
    }

	public List<ObjectId> getAppuntamentiIds() {
		return appuntamentiIds;
	}

	public void setAppuntamentiIds(List<ObjectId> appuntamentiIds) {
		this.appuntamentiIds = appuntamentiIds;
	}

	
	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
    
    
}

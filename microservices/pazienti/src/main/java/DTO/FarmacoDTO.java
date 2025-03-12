package DTO;

import java.util.List;

import Model.Farmaco;

public class FarmacoDTO {
    private String nomeFarmaco;
    private String categoria;
    private String classeTerapeutica;
    private String principioAttivo;
    private List<String> indicazioni;
    private List<String> controindicazioni;
    private List<String> effettiCollaterali;
    private List<String> interazioni;
    private String dataCreazione;
    private String dataAggiornamento;

    // Costruttore
    public FarmacoDTO(Farmaco farmaco) {
        this.nomeFarmaco = farmaco.getNomeFarmaco();
        this.categoria = farmaco.getCategoria();
        this.classeTerapeutica = farmaco.getClasseTerapeutica();
        this.principioAttivo = farmaco.getPrincipioAttivo();
        this.indicazioni = farmaco.getIndicazioni();
        this.controindicazioni = farmaco.getControindicazioni();
        this.effettiCollaterali = farmaco.getEffettiCollaterali();
        this.interazioni = farmaco.getInterazioni();
        this.dataCreazione = farmaco.getDataCreazione();
        this.dataAggiornamento = farmaco.getDataAggiornamento();
    }

	public FarmacoDTO() {
			}

	public String getNomeFarmaco() {
		return nomeFarmaco;
	}

	public void setNomeFarmaco(String nomeFarmaco) {
		this.nomeFarmaco = nomeFarmaco;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getClasseTerapeutica() {
		return classeTerapeutica;
	}

	public void setClasseTerapeutica(String classeTerapeutica) {
		this.classeTerapeutica = classeTerapeutica;
	}

	public String getPrincipioAttivo() {
		return principioAttivo;
	}

	public void setPrincipioAttivo(String principioAttivo) {
		this.principioAttivo = principioAttivo;
	}

	public List<String> getIndicazioni() {
		return indicazioni;
	}

	public void setIndicazioni(List<String> indicazioni) {
		this.indicazioni = indicazioni;
	}

	public List<String> getControindicazioni() {
		return controindicazioni;
	}

	public void setControindicazioni(List<String> controindicazioni) {
		this.controindicazioni = controindicazioni;
	}

	public List<String> getEffettiCollaterali() {
		return effettiCollaterali;
	}

	public void setEffettiCollaterali(List<String> effettiCollaterali) {
		this.effettiCollaterali = effettiCollaterali;
	}

	public List<String> getInterazioni() {
		return interazioni;
	}

	public void setInterazioni(List<String> interazioni) {
		this.interazioni = interazioni;
	}

	public String getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getDataAggiornamento() {
		return dataAggiornamento;
	}

	public void setDataAggiornamento(String dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

}

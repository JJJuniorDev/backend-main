package DTO;

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
	    
	    // Costruttori, getter e setter
	    public PazienteDTO() {}

		public PazienteDTO(String id, String nome, String cognome, String codiceFiscale, String sesso, String indirizzo,
				String numeroDiCellulare, String dottoreId) {
			super();
			this.id = id;
			this.nome = nome;
			this.cognome = cognome;
			this.codiceFiscale = codiceFiscale;
			this.sesso = sesso;
			this.indirizzo = indirizzo;
			this.numeroDiCellulare = numeroDiCellulare;
			this.dottoreId = dottoreId;
		}

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

		public String getDataDiNascita() {
			return dataDiNascita;
		}

		public void setDataDiNascita(String dataDiNascita) {
			this.dataDiNascita = dataDiNascita;
		}

		public PazienteDTO(String id, String nome, String cognome, String codiceFiscale, String dataDiNascita,
				String sesso, String indirizzo, String numeroDiCellulare, String dottoreId) {
			super();
			this.id = id;
			this.nome = nome;
			this.cognome = cognome;
			this.codiceFiscale = codiceFiscale;
			this.dataDiNascita = dataDiNascita;
			this.sesso = sesso;
			this.indirizzo = indirizzo;
			this.numeroDiCellulare = numeroDiCellulare;
			this.dottoreId = dottoreId;
		}
	    
	    
}

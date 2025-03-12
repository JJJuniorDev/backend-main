package Model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "FARMACI")
public class Farmaco {
    @Id
    private ObjectId id;

    @Field("nomeFarmaco")
    private String nomeFarmaco;

    @Field("categoria")
    private String categoria; // Es. "Ansiolitico", "Antidepressivo"

    @Field("classeTerapeutica")
    private String classeTerapeutica;

    @Field("principioAttivo")
    private String principioAttivo;

    @Field("indicazioni")
    private List<String> indicazioni;

    @Field("controindicazioni")
    private List<String> controindicazioni;

    @Field("effettiCollaterali")
    private List<String> effettiCollaterali;

    @Field("interazioni")
    private List<String> interazioni;

    @Field("dataCreazione")
    private String dataCreazione;

    @Field("dataAggiornamento")
    private String dataAggiornamento;

    // Getter e Setter

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

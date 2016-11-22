package app.srusso.usrealsanfelice.to;

/**
 *
 */

public class Giocatore {


    private int id;
    private String nome;
    private String cognome;
    private int numeroMaglia;
    private String ruolo;
    private String urlAvatar;
    private int peso;
    private double altezza;
    private String nascita;
    private String comune;


    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public double getAltezza() {
        return altezza;
    }

    public void setAltezza(double altezza) {
        this.altezza = altezza;
    }

    public String getNascita() {
        return nascita;
    }

    public void setNascita(String nascita) {
        this.nascita = nascita;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getNumeroMaglia() {
        return numeroMaglia;
    }

    public void setNumeroMaglia(int numeroMaglia) {
        this.numeroMaglia = numeroMaglia;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }
}

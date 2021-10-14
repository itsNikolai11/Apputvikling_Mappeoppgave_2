package no.nkopperudmoen.mappeoppgave2.Models;

public class Restaurant {
    Long _ID;
    String navn;
    String adresse;
    int postNr;
    String telefon;
    String type;

    public Restaurant(String navn, String adresse, int postNr, String telefon, String type) {
        this.navn = navn;
        this.adresse = adresse;
        this.postNr = postNr;
        this.telefon = telefon;
        this.type = type;
    }

    public Restaurant(Long _ID, String navn, String adresse, int postNr, String telefon, String type) {
        this._ID = _ID;
        this.navn = navn;
        this.adresse = adresse;
        this.postNr = postNr;
        this.telefon = telefon;
        this.type = type;
    }

    public Restaurant() {

    }

    public Long get_ID() {
        return _ID;
    }

    public void set_ID(Long _ID) {
        this._ID = _ID;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getPostNr() {
        return postNr;
    }

    public void setPostNr(int postNr) {
        this.postNr = postNr;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

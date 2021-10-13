package no.nkopperudmoen.mappeoppgave_2.Models;

public class Kontakt {
    Long _ID;
    String fornavn;
    String etternavn;
    String telefon;

    public Kontakt(String fornavn, String etternavn, String telefon) {
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.telefon = telefon;
    }

    public Kontakt(Long _ID, String fornavn, String etternavn, String telefon) {
        this._ID = _ID;
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.telefon = telefon;
    }

    public Kontakt() {

    }

    public Long get_ID() {
        return _ID;
    }

    public void set_ID(Long _ID) {
        this._ID = _ID;
    }

    public String getFornavn() {
        return fornavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getEtternavn() {
        return etternavn;
    }

    public void setEtternavn(String etternavn) {
        this.etternavn = etternavn;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}

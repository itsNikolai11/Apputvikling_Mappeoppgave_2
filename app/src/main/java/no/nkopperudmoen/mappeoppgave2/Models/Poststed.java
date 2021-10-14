package no.nkopperudmoen.mappeoppgave2.Models;

public class Poststed {
    int postNr;
    String poststed;

    public Poststed(int postNr, String poststed) {
        this.postNr = postNr;
        this.poststed = poststed;
    }

    public Poststed() {

    }

    public int getPostNr() {
        return postNr;
    }

    public void setPostNr(int postNr) {
        this.postNr = postNr;
    }

    public String getPoststed() {
        return poststed;
    }

    public void setPoststed(String poststed) {
        this.poststed = poststed;
    }
}

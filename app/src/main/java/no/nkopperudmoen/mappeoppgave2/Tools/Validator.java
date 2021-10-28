package no.nkopperudmoen.mappeoppgave2.Tools;

import android.app.Activity;

import no.nkopperudmoen.mappeoppgave2.Models.Bestilling;
import no.nkopperudmoen.mappeoppgave2.Models.Kontakt;
import no.nkopperudmoen.mappeoppgave2.Models.Restaurant;

public class Validator {
    public static boolean validerKontakt(Kontakt k) {
        boolean gyldig = true;
        if (k.getFornavn().isEmpty()) {
            gyldig = false;
        }
        if (k.getEtternavn().isEmpty()) {
            gyldig = false;
        }
        if (k.getTelefon().isEmpty()) {
            gyldig = false;
        }
        return gyldig;
    }

    public static boolean validerRestaurant(Restaurant r) {
        boolean gyldig = true;
        if (r.getNavn().isEmpty()) {
            gyldig = false;
        }
        if (r.getAdresse().isEmpty()) {
            gyldig = false;
        }
        if (r.getPostNr() == 0) {
            gyldig = false;
        }
        if (r.getTelefon().isEmpty()) {
            gyldig = false;
        }
        if (r.getType().isEmpty()) {
            gyldig = false;
        }
        return gyldig;
    }

    public static boolean validerBestilling(Bestilling b) {
        boolean gyldig = true;
        if (b.getRestaurantID() == null) {
            gyldig = false;
        }
        if (b.getTid() == null) {
            gyldig = false;
        }
        return gyldig;
    }
}

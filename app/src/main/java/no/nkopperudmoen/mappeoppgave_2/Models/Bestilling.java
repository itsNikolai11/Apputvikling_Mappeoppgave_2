package no.nkopperudmoen.mappeoppgave_2.Models;

import java.util.ArrayList;
import java.util.Date;

public class Bestilling {
    Long _ID;
    Long restaurantID;
    Date tid;
    ArrayList<Kontakt> venner;

    public Bestilling(Long restaurantID, Date tid, ArrayList<Kontakt> venner) {
        this.restaurantID = restaurantID;
        this.tid = tid;
        this.venner = venner;
    }

    public Bestilling(Long _ID, Long restaurantID, Date tid, ArrayList<Kontakt> venner) {
        this._ID = _ID;
        this.restaurantID = restaurantID;
        this.tid = tid;
        this.venner = venner;
    }

    public Bestilling() {

    }

    public Long get_ID() {
        return _ID;
    }

    public void set_ID(Long _ID) {
        this._ID = _ID;
    }

    public Long getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(Long restaurantID) {
        this.restaurantID = restaurantID;
    }

    public Date getTid() {
        return tid;
    }

    public void setTid(Date tid) {
        this.tid = tid;
    }

    public ArrayList<Kontakt> getVenner() {
        return venner;
    }

    public void setVenner(ArrayList<Kontakt> venner) {
        this.venner = venner;
    }
}
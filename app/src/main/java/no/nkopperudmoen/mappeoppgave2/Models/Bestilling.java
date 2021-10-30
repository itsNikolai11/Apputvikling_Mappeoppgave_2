package no.nkopperudmoen.mappeoppgave2.Models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    public String getKlokkeslett() {
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm");
        return DATE_FORMAT.format(this.getTid());
    }
    public String getTidAsString(){
        DateFormat DATE_FORMAT = new SimpleDateFormat("E dd MMM yyyy HH:mm");
        return DATE_FORMAT.format(this.getTid());
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

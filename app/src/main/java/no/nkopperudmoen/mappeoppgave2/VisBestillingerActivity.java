package no.nkopperudmoen.mappeoppgave2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import no.nkopperudmoen.mappeoppgave2.Models.Bestilling;
import no.nkopperudmoen.mappeoppgave2.Models.DBHandler;
import no.nkopperudmoen.mappeoppgave2.Models.Kontakt;
import no.nkopperudmoen.mappeoppgave2.Models.Restaurant;

public class VisBestillingerActivity extends AppCompatActivity {
    DBHandler db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extra = getIntent().getExtras();
        db = new DBHandler(getApplicationContext());
        setContentView(R.layout.activity_vis_bestilling);
        if (extra != null) {
            long id = extra.getLong("id");
            Bestilling bestilling = db.hentBestilling(id);
            populateFields(bestilling);
        } else {
            lastBestilling();
        }
    }

    public void lastBestilling() {
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.sharedPrefs), Context.MODE_PRIVATE);
        long id = prefs.getLong(getString(R.string.visBestillingID), 0);
        Bestilling b = db.hentBestilling(id);
        populateFields(b);
    }

    public void populateFields(Bestilling b) {
        TextView resNavn = findViewById(R.id.viewOrdreNavn);
        TextView resTlf = findViewById(R.id.viewOrdreTlf);
        TextView ordreTid = findViewById(R.id.viewOrdreTid);
        Restaurant r = db.hentRestaurant(b.getRestaurantID());
        resNavn.setText("Restaurant: " + r.getNavn());
        resTlf.setText("Telefon: " + r.getTelefon());
        ordreTid.setText("Tid: " + b.getTidAsString());
        visVenner(b);
    }

    public void visVenner(Bestilling b) {
        TableLayout tl = findViewById(R.id.viewOrdreVenner);
        TableRow tr;
        TextView tv;
        for (Kontakt k : b.getVenner()) {
            tr = (TableRow) getLayoutInflater().inflate(R.layout.tablerow_ordre_kontakt, null);
            tv = tr.findViewById(R.id.ordreKNavn);
            tv.setText(k.getFornavn() + " " + k.getEtternavn());
            tr.findViewById(R.id.ordreFjernKontakt).setVisibility(View.INVISIBLE);
            tl.addView(tr);
        }
    }

    @Override
    protected void onDestroy() {
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.sharedPrefs), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(getString(R.string.visBestillingID), 0);
        editor.apply();
        super.onDestroy();
    }
}

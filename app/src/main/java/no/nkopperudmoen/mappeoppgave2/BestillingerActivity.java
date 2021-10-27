package no.nkopperudmoen.mappeoppgave2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import no.nkopperudmoen.mappeoppgave2.Models.Bestilling;
import no.nkopperudmoen.mappeoppgave2.Models.Restaurant;

public class BestillingerActivity extends AppCompatActivity {
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bestillinger);
        db = new DBHandler(getApplicationContext());
        visBestillinger(db.hentBestillinger());
    }

    public void visBestillinger(ArrayList<Bestilling> bestillinger) {
        TableLayout tl = findViewById(R.id.bestillingerList);
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("E dd MMM yyyy HH:mm");
        TableRow tr;
        TextView tv;
        for (Bestilling b : bestillinger) {
            Restaurant r = db.hentRestaurant(b.getRestaurantID());
            tr = (TableRow) getLayoutInflater().inflate(R.layout.tablerow_bestillinger, null);
            tv = tr.findViewById(R.id.ordreKNavn);
            tv.setText(r.getNavn());
            tv = tr.findViewById(R.id.ordreKTelefon);
            tv.setText(r.getAdresse());
            tv = tr.findViewById(R.id.tid);
            tv.setText(dateFormat.format(b.getTid()));
            Long id = b.get_ID();
            Button btn = tr.findViewById(R.id.ordreFjernKontakt);
            btn.setOnClickListener(view -> {
                slettBestilling(id);
            });
            btn = tr.findViewById(R.id.ordreRedigerBtn);
            btn.setOnClickListener(view -> {
                //rediger-metode
            });
            tl.addView(tr);
        }

    }

    public void reloadBestillinger() {
        TableLayout tl = findViewById(R.id.bestillingerList);
        tl.removeAllViews();
        visBestillinger(db.hentBestillinger());
    }
    @Override
    public void onResume() {
        super.onResume();
        reloadBestillinger();
    }

    public void slettBestilling(Long id) {
        db.slettBestilling(id);
        reloadBestillinger();

    }

    public void nyBestilling(View view) {
        Intent i = new Intent(this, AddBestillingActivity.class);
        startActivity(i);
    }
}

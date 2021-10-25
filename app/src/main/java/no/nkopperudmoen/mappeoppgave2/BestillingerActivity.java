package no.nkopperudmoen.mappeoppgave2;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import no.nkopperudmoen.mappeoppgave2.Models.Bestilling;

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
        TableRow tr;

    }
}

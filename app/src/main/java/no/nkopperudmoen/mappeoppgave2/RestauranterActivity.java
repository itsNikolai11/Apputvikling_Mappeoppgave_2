package no.nkopperudmoen.mappeoppgave2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import no.nkopperudmoen.mappeoppgave2.Models.Restaurant;

public class RestauranterActivity extends AppCompatActivity {
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restauranter);
        db = new DBHandler(getApplicationContext());
        visRestauranter(db.hentRestauranter());
        FloatingActionButton fab = findViewById(R.id.addResNav);
        fab.setOnClickListener(view -> {
            Intent i = new Intent(RestauranterActivity.this, AddRestaurantActivity.class);
            startActivity(i);
        });
    }

    private void visRestauranter(ArrayList<Restaurant> restauranter) {
        TableLayout tl = findViewById(R.id.resListLayout);
        TableRow tr;
        String navn;
        String adresse;
        String telefon;
        String type;
        TextView tv;
        for (Restaurant r : restauranter) {
            tr = (TableRow) getLayoutInflater().inflate(R.layout.tablerow_restauranter, null);
            tv = tr.findViewById(R.id.navn);
            navn = r.getNavn();
            tv.setText(navn);
            tv = tr.findViewById(R.id.adresse);
            adresse = r.getAdresse();
            tv.setText(adresse);
            tv = tr.findViewById(R.id.telefon);
            telefon = "Tlf: " + r.getTelefon();
            tv.setText(telefon);
            type = r.getType();
            tl.addView(tr);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadResLayout();
    }

    private void reloadResLayout() {
        TableLayout tl = findViewById(R.id.resListLayout);
        tl.removeAllViews();
        visRestauranter(db.hentRestauranter());
    }

    public void slettRestaurant(View v) {

    }

    public void redigerRestaurant(View v) {

    }
}

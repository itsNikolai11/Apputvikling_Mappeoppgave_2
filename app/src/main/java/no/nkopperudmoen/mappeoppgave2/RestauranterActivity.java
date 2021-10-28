package no.nkopperudmoen.mappeoppgave2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
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
        Button btn;
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
            btn = tr.findViewById(R.id.ordreFjernKontakt);
            Long id = r.get_ID();
            btn.setOnClickListener(view -> {
                slettRestaurant(id);
            });
            btn = tr.findViewById(R.id.ordreRedigerBtn);
            btn.setOnClickListener(view -> {
                redigerRestaurant(id);
            });
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

    public void slettRestaurant(Long id) {
        db.slettRestaurant(id);
        reloadResLayout();
    }

    public void redigerRestaurant(Long id) {
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.sharedPrefs), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(getString(R.string.editRestaurant), id);
        editor.apply();
        Intent i = new Intent(this, EditRestaurantActivity.class);
        startActivity(i);
    }
}

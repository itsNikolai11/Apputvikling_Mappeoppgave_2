package no.nkopperudmoen.mappeoppgave_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import no.nkopperudmoen.mappeoppgave_2.Models.Kontakt;

public class KontakterActivity extends AppCompatActivity {
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontakter);
        FloatingActionButton fab = findViewById(R.id.addKontaktNav);
        fab.setOnClickListener(view -> {
            Intent i = new Intent(KontakterActivity.this, AddKontaktActivity.class);
            startActivity(i);
        });
        db = new DBHandler(getApplicationContext());
        lastKontakter(db.hentKontakter());
    }

    public void lastKontakter(ArrayList<Kontakt> kontakter) {
        TableLayout tl = findViewById(R.id.vennerList);
        String navn;
        String tlf;
        String id;
        TextView tv;
        TableRow tr;
        for (Kontakt k : kontakter) {
            navn = k.getFornavn() + " " + k.getEtternavn();
            tlf="Tlf: "+ k.getTelefon();
            id = k.get_ID() + "";
            tr = (TableRow) getLayoutInflater().inflate(R.layout.tablerow_venner, null);
            tv = tr.findViewById(R.id.navn);
            tv.setText(navn);
            tv = tr.findViewById(R.id.telefon);
            tv.setText(tlf);
            tv = tr.findViewById(R.id.kontaktID);
            tv.setText(id);
            tl.addView(tr);
        }

    }

    public void slettKontakt(View view) {
        /*TextView tv = view.findViewById(R.id.kontaktID);
        int id = Integer.parseInt(tv.getText().toString());
        db.slettKontakt(id);*/
    }

    public void redigerKontakt(View view) {

    }

}

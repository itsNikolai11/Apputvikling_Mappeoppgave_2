package no.nkopperudmoen.mappeoppgave2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import no.nkopperudmoen.mappeoppgave2.Models.Kontakt;

public class KontakterActivity extends AppCompatActivity {
    DBHandler db;
    Logger log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontakter);
        FloatingActionButton fab = findViewById(R.id.addKontaktNav);
        fab.setOnClickListener(view -> {
            Intent i = new Intent(KontakterActivity.this, AddKontaktActivity.class);
            startActivity(i);
        });
        log = Logger.getLogger("APP");
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
        Button btn;
        for (Kontakt k : kontakter) {
            navn = k.getFornavn() + " " + k.getEtternavn();
            tlf = "Tlf: " + k.getTelefon();
            id = k.get_ID() + "";
            tr = (TableRow) getLayoutInflater().inflate(R.layout.tablerow_venner, null);
            tv = tr.findViewById(R.id.navn);
            tv.setText(navn);
            tv = tr.findViewById(R.id.telefon);
            tv.setText(tlf);
            btn = tr.findViewById(R.id.vennSlettBtn);
            String finalId = id;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Long lID = Long.parseLong(finalId);
                    slettKontakt(lID);
                }
            });
            tl.addView(tr);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        reloadLayout();
    }

    private void reloadLayout() {
        TableLayout tl = findViewById(R.id.vennerList);
        tl.removeAllViews();
        lastKontakter(db.hentKontakter());

    }

    public void slettKontakt(Long id) {
        db.slettKontakt(id);
        reloadLayout();
    }

    public void redigerKontakt(View view) {
        //TODO ny aktivitet
    }

}
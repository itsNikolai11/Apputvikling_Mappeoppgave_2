package no.nkopperudmoen.mappeoppgave2;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import no.nkopperudmoen.mappeoppgave2.Models.Kontakt;

public class ActivitySelectKontakter extends AppCompatActivity {
    private DBHandler db;
    ArrayList<Kontakt> selectedKontakter = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_kontakter);
        db = new DBHandler(getApplicationContext());
        lastKontakter();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void lastKontakter() {
        ArrayList<Kontakt> kontakter = db.hentKontakter();
        TableLayout tl = findViewById(R.id.vennerSelectList);
        TableRow tr;

        for (Kontakt k : kontakter) {
            tr = (TableRow) getLayoutInflater().inflate(R.layout.tablerow_ordre_add_kontakt, null);
            TextView tv = tr.findViewById(R.id.kontaktNavn);
            tv.setText(k.getFornavn() + " " + k.getEtternavn());
            Button btn = tr.findViewById(R.id.ordreAddKontakt);
            TableRow finalTr = tr;
            setSelectListener(btn, finalTr, false, k);
            tl.addView(tr);
        }
    }

    void setSelectListener(Button btn, TableRow tr, boolean selected, Kontakt k) {
        View.OnClickListener select = view -> {
            btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_24, 0, 0, 0);
            tr.setSelected(true);
            selectedKontakter.add(k);
            tr.setBackgroundColor(getResources().getColor(R.color.positive));
            setSelectListener(btn, tr, true, k);
        };
        View.OnClickListener deselect = view -> {
            btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_add_24, 0, 0, 0);
            tr.setSelected(false);
            selectedKontakter.remove(k);
            tr.setBackgroundColor(getResources().getColor(R.color.primaryElementBG));
            btn.setOnClickListener(select);
            setSelectListener(btn, tr, false, k);
        };
        if (!selected) {
            btn.setOnClickListener(select);
        } else {
            btn.setOnClickListener(deselect);

        }
    }
}

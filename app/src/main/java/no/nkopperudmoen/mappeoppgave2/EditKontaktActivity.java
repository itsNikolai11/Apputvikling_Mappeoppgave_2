package no.nkopperudmoen.mappeoppgave2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import no.nkopperudmoen.mappeoppgave2.Models.Kontakt;

public class EditKontaktActivity extends AppCompatActivity {
    private DBHandler db;
    private Kontakt k;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_kontakt);
        db = new DBHandler(getApplicationContext());
        populateFields();
    }

    public void populateFields() {
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.sharedPrefs), Context.MODE_PRIVATE);
        Long kontaktID = prefs.getLong(getString(R.string.editUser), 1);
        k = db.hentKontakt(kontaktID);
        EditText fornavnET = (EditText) findViewById(R.id.fornavnInputEdit);
        EditText etternavnET = (EditText) findViewById(R.id.etternavnInputEdit);
        EditText telefonET = (EditText) findViewById(R.id.telefonInputEdit);
        fornavnET.setText(k.getFornavn());
        etternavnET.setText(k.getEtternavn());
        telefonET.setText(k.getTelefon());
    }

    public void saveChanges(View view) {
        EditText fornavnET = (EditText) findViewById(R.id.fornavnInputEdit);
        EditText etternavnET = (EditText) findViewById(R.id.etternavnInputEdit);
        EditText telefonET = (EditText) findViewById(R.id.telefonInputEdit);
        k.setFornavn(fornavnET.getText().toString());
        k.setEtternavn(etternavnET.getText().toString());
        k.setTelefon(telefonET.getText().toString());
        db.endreKontakt(k);
        finish();
    }

    public void cancel(View view) {
        finish();
    }
}

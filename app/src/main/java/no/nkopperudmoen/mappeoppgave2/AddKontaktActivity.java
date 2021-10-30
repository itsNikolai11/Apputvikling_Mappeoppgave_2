package no.nkopperudmoen.mappeoppgave2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import no.nkopperudmoen.mappeoppgave2.Fragments.ExitDialogFragment;
import no.nkopperudmoen.mappeoppgave2.Models.DBHandler;
import no.nkopperudmoen.mappeoppgave2.Models.Kontakt;
import no.nkopperudmoen.mappeoppgave2.Tools.Validator;

public class AddKontaktActivity extends AppCompatActivity implements ExitDialogFragment.DialogClickListener {
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addkontakt);
        db = new DBHandler(getApplicationContext());
    }


    public void saveKontakt(View view) {
        EditText fornavn = (EditText) findViewById(R.id.fornavnInput);
        EditText etternavn = (EditText) findViewById(R.id.etternavnInput);
        EditText telefon = (EditText) findViewById(R.id.telefonInput);
        //TODO legg til feilmeldingsfelt
        Kontakt k = new Kontakt();
        k.setFornavn(fornavn.getText().toString());
        k.setEtternavn(etternavn.getText().toString());
        k.setTelefon(telefon.getText().toString());
        if (Validator.validerKontakt(k)) {
            db.leggTilKontakt(k);
            db.close();
            finish();
        }
        //Dialog som forteller om feilmelding

    }

    public void cancel(View v) {

        DialogFragment cancelDialog =new ExitDialogFragment();
        cancelDialog.show(getSupportFragmentManager(), "cancelDialog");
    }
    @Override
    public void onYesClick() {
        finish();
    }

    @Override
    public void onNoClick() {

    }
}

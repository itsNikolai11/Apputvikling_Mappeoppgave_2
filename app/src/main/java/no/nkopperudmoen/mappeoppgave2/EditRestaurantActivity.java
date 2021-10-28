package no.nkopperudmoen.mappeoppgave2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import no.nkopperudmoen.mappeoppgave2.Models.DBHandler;
import no.nkopperudmoen.mappeoppgave2.Models.Restaurant;

public class EditRestaurantActivity extends AppCompatActivity {
    DBHandler db;
    Restaurant r;
    EditText ETNavn;
    EditText ETAdresse;
    EditText ETPostnr;
    EditText ETTelefon;
    Spinner type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant);
        db = new DBHandler(getApplicationContext());
        populateFields();
    }

    public void populateFields() {
        ETNavn = (EditText) findViewById(R.id.inputResNavnEdit);
        ETAdresse = (EditText) findViewById(R.id.inputResAdresseEdit);
        ETPostnr = (EditText) findViewById(R.id.inputResPostnrEdit);
        ETTelefon = (EditText) findViewById(R.id.inputResTlfEdit);
        type = (Spinner) findViewById(R.id.inputResTypeEdit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.restaurant_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.sharedPrefs), Context.MODE_PRIVATE);
        Long resID = prefs.getLong(getString(R.string.editRestaurant), 0);
        r = db.hentRestaurant(resID);
        ETNavn.setText(r.getNavn());
        ETAdresse.setText(r.getAdresse());
        ETPostnr.setText(""+r.getPostNr());
        ETTelefon.setText(r.getTelefon());
        //TODO må legge til typer i en database
        type.setSelection(adapter.getPosition(r.getType()));
    }

    public void saveChanges(View view) {
        r.setNavn(ETNavn.getText().toString());
        r.setAdresse(ETAdresse.getText().toString());
        r.setPostNr(Integer.parseInt(ETPostnr.getText().toString()));
        r.setType(type.getSelectedItem().toString());
        db.endreRestaurant(r);
        finish();
    }

    public void cancel(View v) {
        finish();
    }
}

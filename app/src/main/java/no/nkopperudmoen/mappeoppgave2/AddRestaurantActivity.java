package no.nkopperudmoen.mappeoppgave2;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import no.nkopperudmoen.mappeoppgave2.Models.Restaurant;

public class AddRestaurantActivity extends AppCompatActivity {
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrestaurant);
        db = new DBHandler(getApplicationContext());
        Spinner s = findViewById(R.id.inputResTypeEdit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.restaurant_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
    }

    public void saveRestaurant(View view) {
        EditText txt;
        Spinner s;
        Restaurant r = new Restaurant();
        txt = (EditText) findViewById(R.id.inputResNavnEdit);
        r.setNavn(txt.getText().toString());
        txt = (EditText) findViewById(R.id.inputResAdresseEdit);
        r.setAdresse(txt.getText().toString());
        txt = (EditText) findViewById(R.id.inputResTlfEdit);
        r.setTelefon(txt.getText().toString());
        s = (Spinner) findViewById(R.id.inputResTypeEdit);
        r.setType(s.getSelectedItem().toString());
        db.leggTilRestaurant(r);
        finish();
    }
    public void cancel(View v){
        finish();
    }
}

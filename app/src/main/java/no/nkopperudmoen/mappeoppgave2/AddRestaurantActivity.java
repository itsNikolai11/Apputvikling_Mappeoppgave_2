package no.nkopperudmoen.mappeoppgave2;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import no.nkopperudmoen.mappeoppgave2.Models.Restaurant;

public class AddRestaurantActivity extends AppCompatActivity {
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrestaurant);
        db = new DBHandler(getApplicationContext());
    }

    public void saveRestaurant(View view) {
        EditText txt;
        Restaurant r = new Restaurant();
        txt = (EditText) findViewById(R.id.inpuntResNavn);
        r.setNavn(txt.getText().toString());
        txt = (EditText) findViewById(R.id.inputResAdresse);
        r.setAdresse(txt.getText().toString());
        txt = (EditText) findViewById(R.id.inputResTlf);
        r.setTelefon(txt.getText().toString());
        txt = (EditText) findViewById(R.id.inputResType);
        r.setType(txt.getText().toString());
        db.leggTilRestaurant(r);
        finish();
    }
}

package no.nkopperudmoen.mappeoppgave2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void visBestillinger(View view) {

    }

    public void visVenner(View view) {
        Intent i = new Intent(this, KontakterActivity.class);
        startActivity(i);
    }

    public void visPreferanser(View view) {
    }

    public void visRestauranter(View view) {
        Intent i = new Intent(this, RestauranterActivity.class);
        startActivity(i);
    }
}
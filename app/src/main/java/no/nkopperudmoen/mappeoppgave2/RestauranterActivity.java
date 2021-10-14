package no.nkopperudmoen.mappeoppgave2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RestauranterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restauranter);
        visRestauranter();
        FloatingActionButton fab = findViewById(R.id.addResNav);
        fab.setOnClickListener(view -> {
            Intent i = new Intent(RestauranterActivity.this, AddRestaurantActivity.class);
            startActivity(i);
        });
    }
    private void visRestauranter(){

    }

    public void slettRestaurant(View v) {

    }

    public void redigerRestaurant(View v) {

    }
}

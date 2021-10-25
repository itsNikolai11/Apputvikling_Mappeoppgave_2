package no.nkopperudmoen.mappeoppgave2;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import no.nkopperudmoen.mappeoppgave2.Models.Bestilling;

public class AddBestillingActivity extends AppCompatActivity {
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void lagreBestilling(View v) {
        Bestilling b = new Bestilling();

    }
}

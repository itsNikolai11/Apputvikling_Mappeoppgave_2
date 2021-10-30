package no.nkopperudmoen.mappeoppgave2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import no.nkopperudmoen.mappeoppgave2.Fragments.PreferenceFragment;

public class PreferenceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.preference_view, new PreferenceFragment())
                .commit();
    }
}

package no.nkopperudmoen.mappeoppgave2.Fragments;

import android.os.Bundle;

import no.nkopperudmoen.mappeoppgave2.R;

public class PreferenceFragment extends androidx.preference.PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference_root, rootKey);
    }
}

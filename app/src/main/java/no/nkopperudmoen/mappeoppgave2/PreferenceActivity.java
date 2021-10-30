package no.nkopperudmoen.mappeoppgave2;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.Calendar;

import no.nkopperudmoen.mappeoppgave2.Fragments.PreferenceFragment;

public class PreferenceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        PreferenceFragment preferenceFragment = new PreferenceFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.preference_view, preferenceFragment)
                .commit();
        lastTidsvelger();
    }

    public void lastTidsvelger() {
        Button btn = findViewById(R.id.editSmsTime);
        Calendar cal = Calendar.getInstance();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.MINUTE, prefs.getInt(getString(R.string.smsAlertMin), 0));
        cal.set(Calendar.HOUR_OF_DAY, prefs.getInt(getString(R.string.smsAlertHour), 0));
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        btn.setOnClickListener(listener -> {
            TimePickerDialog timeDialog = new TimePickerDialog(this, R.style.TimePicker, (timePicker, hour1, minute) -> {
                prefs.edit().putInt(getString(R.string.smsAlertMin), minute).apply();
                prefs.edit().putInt(getString(R.string.smsAlertHour), hour1).apply();
            }, hour, min, true);
            timeDialog.show();
        });
    }
}

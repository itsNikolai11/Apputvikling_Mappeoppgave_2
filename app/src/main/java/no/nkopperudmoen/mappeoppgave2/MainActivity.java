package no.nkopperudmoen.mappeoppgave2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import no.nkopperudmoen.mappeoppgave2.Services.PeriodiskService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        startServices();
    }

    public void visBestillinger(View view) {
        Intent i = new Intent(this, BestillingerActivity.class);
        startActivity(i);
    }

    public void visVenner(View view) {
        Intent i = new Intent(this, KontakterActivity.class);
        startActivity(i);
    }

    public void visPreferanser(View view) {
        Intent i = new Intent(this, PreferenceActivity.class);
        startActivity(i);
    }

    public void visRestauranter(View view) {
        Intent i = new Intent(this, RestauranterActivity.class);
        startActivity(i);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.varselKanal);
            String description = "";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("22", name, importance);
            channel.setDescription(description);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    public void startServices() {
        Intent i = new Intent(this, PeriodiskService.class);
        startService(i);
    }
}
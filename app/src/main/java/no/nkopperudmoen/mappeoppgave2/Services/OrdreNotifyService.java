package no.nkopperudmoen.mappeoppgave2.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Calendar;

import no.nkopperudmoen.mappeoppgave2.BestillingerActivity;
import no.nkopperudmoen.mappeoppgave2.Models.Kontakt;
import no.nkopperudmoen.mappeoppgave2.R;

public class OrdreNotifyService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sjekkDagensBestillinger();
        return super.onStartCommand(intent, flags, startId);
    }

    public void sjekkDagensBestillinger() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent i = new Intent(this, BestillingerActivity.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, i, 0);
        Notification notification = new NotificationCompat.Builder(this, "22")
                .setContentTitle("Restaurantbestilling!")
                .setContentText("Du har en kommende restaurant-bestilling!")
                .setSmallIcon(R.drawable.ic_baseline_restaurant_24)
                .setContentIntent(pendingIntent)
                .build();
        notificationManager.notify(0, notification);
    }

    public void notifyKontakter(ArrayList<Kontakt> kontakter) {

    }
}

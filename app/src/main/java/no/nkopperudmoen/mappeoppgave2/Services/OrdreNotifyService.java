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
import java.util.Date;

import no.nkopperudmoen.mappeoppgave2.BestillingerActivity;
import no.nkopperudmoen.mappeoppgave2.Models.Bestilling;
import no.nkopperudmoen.mappeoppgave2.Models.DBHandler;
import no.nkopperudmoen.mappeoppgave2.Models.Kontakt;
import no.nkopperudmoen.mappeoppgave2.R;

public class OrdreNotifyService extends Service {
    DBHandler db;

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
        db = new DBHandler(getApplicationContext());
        ArrayList<Bestilling> alleBestillinger = db.hentBestillinger();
        if (alleBestillinger == null) {
            return;
        }
        for (Bestilling b : alleBestillinger) {
            if (isToday(b.getTid())) {

                sendLokalNotifikasjon(b);
            } else {
                alleBestillinger.remove(b);
            }
        }
        //notifyKontakter(alleBestillinger);
    }

    public boolean isToday(Date d) {
        Date today = new Date();
        return today.getDate() == d.getDate() && today.getMonth() == d.getMonth();
    }

    public void sendLokalNotifikasjon(Bestilling b) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent i = new Intent(this, BestillingerActivity.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, i, 0);
        Notification notification = new NotificationCompat.Builder(this, "22")
                .setContentTitle("Restaurantbestilling")
                .setContentText("Bestilling på " + b.getRestaurantID())
                .setSmallIcon(R.drawable.ic_baseline_restaurant_24)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Du har en bestilling på "
                        + db.hentRestaurant(b.getRestaurantID()).getNavn() + " i dag klokken " + b.getKlokkeslett()))
                .setContentIntent(pendingIntent)
                .build();
        notificationManager.notify(0, notification);
    }

    public void notifyKontakter(ArrayList<Bestilling> bestillinger) {
        for (Bestilling b : bestillinger) {

            if (b.getVenner() == null) {
                return;
            }
            for (Kontakt k : b.getVenner()) {
                sendSMS(k);
            }
        }
    }

    public void sendSMS(Kontakt k) {

    }
}

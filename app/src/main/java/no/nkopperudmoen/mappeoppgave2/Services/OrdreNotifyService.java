package no.nkopperudmoen.mappeoppgave2.Services;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.telephony.SmsManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import no.nkopperudmoen.mappeoppgave2.BestillingerActivity;
import no.nkopperudmoen.mappeoppgave2.Models.Bestilling;
import no.nkopperudmoen.mappeoppgave2.Models.DBHandler;
import no.nkopperudmoen.mappeoppgave2.Models.Kontakt;
import no.nkopperudmoen.mappeoppgave2.R;
import no.nkopperudmoen.mappeoppgave2.VisBestillingerActivity;

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
            }
        }
    }

    public boolean isToday(Date d) {
        Date today = new Date();
        return today.getDate() == d.getDate() && today.getMonth() == d.getMonth();
    }

    public void sendLokalNotifikasjon(Bestilling b) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent i = new Intent(this, VisBestillingerActivity.class);
        i.putExtra("id", b.get_ID());
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(i);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(this, "22")
                .setContentTitle("Restaurantbestilling")
                .setContentText("Bestilling på " + b.getRestaurantID())
                .setSmallIcon(R.drawable.ic_baseline_restaurant_24)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Du har en bestilling på "
                        + db.hentRestaurant(b.getRestaurantID()).getNavn() + " i dag klokken " + b.getKlokkeslett()))
                .setContentIntent(pendingIntent)
                .build();
        notificationManager.notify(0, notification);
        notifyKontakter(b);
    }

    public void notifyKontakter(Bestilling b) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int MY_PERMISSIONS_REQUEST_SEND_SMS = ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int MY_PHONE_STATE_PERM = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (MY_PERMISSIONS_REQUEST_SEND_SMS == PackageManager.PERMISSION_GRANTED
                && MY_PHONE_STATE_PERM == PackageManager.PERMISSION_GRANTED) {
            if (b.getVenner() == null) {
                return;
            }
            if (prefs.getBoolean(getString(R.string.prefSendSMS), false)) {
                for (Kontakt k : b.getVenner()) {
                    sendSMS(k, b);
                }
            }
        }
    }

    public void sendSMS(Kontakt k, Bestilling b) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String msg = prefs.getString(getString(R.string.prefStandardSMS), "");
        msg = msg.replaceAll("%restaurant%", db.hentRestaurant(b.getRestaurantID()).getNavn());
        msg = msg.replaceAll("%fulltid%", b.getTidAsString());
        msg = msg.replaceAll("%klokkeslett%", b.getKlokkeslett());
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(k.getTelefon(), null, msg, null, null);
    }
}

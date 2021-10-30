package no.nkopperudmoen.mappeoppgave2.Services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import java.util.Calendar;

import no.nkopperudmoen.mappeoppgave2.R;

public class PeriodiskService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Calendar cal = Calendar.getInstance();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.MINUTE, prefs.getInt(getString(R.string.smsAlertMin), 0));
        cal.set(Calendar.HOUR, prefs.getInt(getString(R.string.smsAlertHour), 0));
        Intent i = new Intent(this, OrdreNotifyService.class);
        PendingIntent pIntent = PendingIntent.getService(this, 0, i, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pIntent);
        //alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), (60 * 1000) * 30, pIntent);
        return super.onStartCommand(intent, flags, startId);
    }
}

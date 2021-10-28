package no.nkopperudmoen.mappeoppgave2.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, PeriodiskService.class);
        context.startService(i);
    }
}

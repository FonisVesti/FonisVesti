package com.vesti.fonis.fonisvesti;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.vesti.fonis.fonisvesti.model.OnePieceOfNews;

/**
 * Created by Dusan on 2.4.2016..
 */
public class Notifikacija extends Service {
    NotificationManager menadzerNotifikacija;
    @Nullable
    @Override

    public IBinder onBind(Intent intent) {
        return null;
    }

    void napraviNotifikaciju(OnePieceOfNews v){
//        NotificationCompat.Builder notifikacija=v.napraviNotifikaciju(this);
//        Intent vest=new Intent(this,VestActivity.class);
//        TaskStackBuilder stek=TaskStackBuilder.create(this);
//        stek.addParentStack(VestActivity.class);
//        stek.addNextIntent(vest);
//        PendingIntent pendingIntent=stek.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
//        notifikacija.setContentIntent(pendingIntent);
//        menadzerNotifikacija=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }
}

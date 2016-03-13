package vaas.autocurehome.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

import java.util.Calendar;

import vaas.autocurehome.R;
import vaas.autocurehome.VisitActivity;

/**
 * Created by vishal on 10/3/16.
 */
public class AHGCMListenerService extends GcmListenerService  {
    private static final String TAG = "AHGcmListenerService";
    Resources res;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);

        res = getApplicationContext().getResources();

        String title = data.getString("title");
        String body = data.getString("body");
        String path = data.getString("path");

        sendNotification(title, body, path);
    }

    public void sendNotification(String title, String body, String path) {
        Intent intent = new Intent(this, VisitActivity.class);
        intent.putExtra("path", path);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Bitmap imageBitmap;

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setLights(0xFFC107, 500, 3000)
                .setVibrate(new long[]{200, 200, 200, 200})
                .setSound(defaultRingtone)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body));

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int id = (int) Calendar.getInstance().getTimeInMillis();

        notificationManager.notify( id /* ID of notification */, notificationBuilder.build());
        /*try {
            imageBitmap = Picasso.with(getApplicationContext()).load("https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-xtl1/v/t1.0-9/s720x720/12717827_1079182818808498_6987844781615082742_n.jpg?oh=097818aee5121b8a37c03468b3a9cf0b&oe=57618CA9&__gda__=1461890998_31f21230ab4034884448e156f4ddd784").get();
            bigPictureStyle.bigPicture(imageBitmap);
            bigPictureStyle.setSummaryText(body);
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(id, notificationBuilder.build());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }



}

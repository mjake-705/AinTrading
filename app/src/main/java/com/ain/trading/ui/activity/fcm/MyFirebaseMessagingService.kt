package com.ain.trading.ui.activity.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ain.trading.R
import com.ain.trading.ui.activity.home.HomeActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.*

class MyFirebaseMessagingService: FirebaseMessagingService() {

    private val TAG = "MyFirebaseMsgService";

    private val handler = android.os.Handler()

/*

    override fun onNewToken(s: String?) {
        super.onNewToken(s)
        constants.setFcmToken(FirebaseInstanceId.getInstance().token!!)
        Log.d("NEW_TOKEN", s)
    }
*/

    override fun onNewToken(token: String) {

        Log.d("TOKEN", "Refreshed token: $token")

// If you want to send messages to this application instance or
// manage this apps subscriptions on the server side, send the
// Instance ID token to your app server.
        sendRegistrationToServer(token)

    }

    private fun sendRegistrationToServer(token: String?) {
// TODO: Implement this method to send token to your app server.
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        super.onMessageReceived(remoteMessage)
        Log.d(TAG, remoteMessage?.data.get("message"))
        Log.d("push message", remoteMessage?.data.get("message"))

        try {


            if (remoteMessage != null && remoteMessage.data != null) {

                if (remoteMessage.data.isNotEmpty())
                    sendNotification(remoteMessage.notification?.body.toString(), getString(R.string.app_name), remoteMessage.data.get("notification_type").toString(),remoteMessage.data.get("notification_type_id"))
                // }
                //}
            }

        } catch (e: Exception) {
        }

    }

    private fun sendNotification(messageBody: String, title: String, notification_type: String, notification_type_id: String?) {
        var intent = Intent()

        intent = Intent(this, HomeActivity::class.java)
    /*    if(notification_type.equals("1"))
        {
            intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("isfrompush", "isfrompush")
            intent.putExtra("notification_type",notification_type)
            intent.putExtra("notification_type_id",notification_type_id)
        }
        else if(notification_type.equals("3")){
            intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("isfrompush", "isfrompush")
            intent.putExtra("notification_type",notification_type)
            intent.putExtra("notification_type_id",notification_type_id)
        }
        else if(notification_type.equals("4")){
            intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("isfrompush", "isfrompush")
            intent.putExtra("notification_type",notification_type)
            intent.putExtra("notification_type_id",notification_type_id)
        }

        else {
            intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("isfrompush", "isfrompush")
            intent.putExtra("notification_type",notification_type)
            intent.putExtra("notification_type_id",notification_type_id)
        }*/

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 , intent,
            PendingIntent.FLAG_ONE_SHOT)
        val rawBitmap = BitmapFactory.decodeResource(resources,
            R.drawable.ic_launcher_foreground)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        var notificationBuilder: NotificationCompat.Builder? = null
        var notificationManager: NotificationManager? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val CHANNEL_ID = "my_channel_01"// The id of the channel.
            val name = "green_rewards_channel"// The user-visible name of the channel.
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            notificationBuilder =
                NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_plus_circle)
                    .setLargeIcon(rawBitmap)
                    .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setStyle(NotificationCompat.BigTextStyle()
                        .bigText(messageBody))
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }else{
            notificationBuilder =
                NotificationCompat.Builder(this, "")
                    .setSmallIcon(R.drawable.ic_stat_green_logo_vector)
                    .setLargeIcon(rawBitmap)
                    .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setStyle(NotificationCompat.BigTextStyle()
                        .bigText(messageBody))
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }


        notificationManager.notify(Calendar.getInstance().timeInMillis.toInt(), notificationBuilder.build())

        handler.postDelayed({

            val pushIntent = Intent("push_message")
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushIntent)
        }, 3000)


    }


}
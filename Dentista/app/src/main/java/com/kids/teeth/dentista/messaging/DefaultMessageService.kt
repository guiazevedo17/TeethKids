package com.kids.teeth.dentista.messaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.activity.SignInActivity

class DefaultMessageService : FirebaseMessagingService() {

    private lateinit var functions: FirebaseFunctions
    private lateinit var auth: FirebaseAuth

    companion object {
        var docID: String? = null
        var latitude: String? = null
        var longitude: String? = null
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val msgData = remoteMessage.data
        if(msgData["text"] == "emergencyUpdated"){
            val msg = "Nova Emergência!"

            showNotificationEmergenciesList(msg)
        }

        if(msgData["text"] == "acceptedDentist"){
            val msg = "Você foi Escolhido para Realizar uma Emergência!"

            docID = msgData["id"]

            showNotificationEmergencyInProgress(msg)
        }

        if(msgData["text"] == "location"){
            latitude = msgData["latitude"]
            longitude = msgData["longitude"]
            val msg = "Socorrista compartilhou a localização com voce!"
            showNotificationLocation(msg)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        functions = Firebase.functions("southamerica-east1")
        val uid = auth.currentUser?.uid
        val collection = "dentist"
        val newTk = hashMapOf(
            "fcmToken" to token,
            "userId" to uid,
            "colection" to collection,
        )

    }
        private fun showNotificationEmergenciesList(messageBody: String) {
        val intent = Intent(this, SignInActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.action = "OPEN_FRAGMENT_EMERCIES_LIST"
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
            PendingIntent.FLAG_IMMUTABLE)
        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(getString(R.string.fcm_default_title_message))
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Since android Oreo notification channel is needed.
        val channel = NotificationChannel(channelId,
            "Channel human readable title",
            NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

    private fun showNotificationEmergencyInProgress(messageBody: String) {
        val intent = Intent(this, SignInActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.action = "OPEN_FRAGMENT_EMERGENCY_IN_PROGRESS"
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
            PendingIntent.FLAG_IMMUTABLE)
        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(getString(R.string.fcm_default_title_message))
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Since android Oreo notification channel is needed.
        val channel = NotificationChannel(channelId,
            "Channel human readable title",
            NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

    private fun showNotificationLocation(messageBody: String) {
        val intent = Intent(this, SignInActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.action = "OPEN_FRAGMENT_MAPS"
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
            PendingIntent.FLAG_IMMUTABLE)
        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(getString(R.string.fcm_default_title_message))
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Since android Oreo notification channel is needed.
        val channel = NotificationChannel(channelId,
            "Channel human readable title",
            NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

}
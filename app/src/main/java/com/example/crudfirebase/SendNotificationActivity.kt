package com.example.crudfirebase

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build.VERSION_CODES
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crudfirebase.databinding.ActivitySendNotificationBinding
import kotlinx.coroutines.channels.Channel

class SendNotificationActivity : AppCompatActivity() {

    lateinit var notificationBinding = ActivitySendNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        notificationBinding = ActivitySendNotificationBinding.inflate(layoutInflater)
        setContentView(notificationBinding.root)

        notificationBinding.sendNotification.setOnClickListener {
            showNotification()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun showNotification(){
        var builder = NotificationCompat.Builder(this@SendNotificationActivity, CHANNEL_ID))
        if (Build.VERSION.SDK_INT>=VERSION_CODES.0){
            var channel:NotificationChannel = NotificationChannel(CHANNEL_ID, "My Channel", NotificationManager.IMPORTANCE_DEFAULT)
            var manager:NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NoficationService
            manager.createNotificationChannels(channel)
            builder.setSmallIcon(R.drawable.ic_launcher_foreground).setContentTitle("Offer").setContentText("Offer")
        } else {
            builder.setSmallIcon(R.drawable.ic_launcher_background).setContentTitle("Offer").setContentText("Offer")
        }

        var notificationManagerCompat : NotificationManagerCompat = NotificationManagerCompat.from(this@SendNotificationActivity)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManagerCompat.notify(1, builder.build())
    }
}
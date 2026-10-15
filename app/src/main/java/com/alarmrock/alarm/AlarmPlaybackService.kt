package com.alarmrock.alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.IBinder
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import com.alarmrock.R

class AlarmPlaybackService : Service() {
    private var ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
    private var mediaPlayer = android.media.MediaPlayer().apply {
        setAudioStreamType(android.media.AudioManager.STREAM_ALARM)
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        val notification = buildNotification()
        startForeground(101, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        try {
            ringtone?.let {
                mediaPlayer = android.media.MediaPlayer().apply {
                    setAudioStreamType(android.media.AudioManager.STREAM_ALARM)
                    setDataSource(applicationContext, it)
                    isLooping = true
                    prepare()
                    start()
                }
            }
            val vibrator = getSystemService(Vibrator::class.java)
            if (vibrator?.hasVibrator() == true) {
                vibrator.vibrate(longArrayOf(0L, 500L, 250L), 0)
            }
        } catch (_: Exception) {
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            if (mediaPlayer.isPlaying) mediaPlayer.stop()
        } catch (_: Exception) {
        }
        mediaPlayer.release()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "wake_lock_alarm",
                "WakeLock Alarm",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Alarm playback"
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(): Notification {
        return NotificationCompat.Builder(this, "wake_lock_alarm")
            .setContentTitle("WakeLock")
            .setContentText("Wake-up challenge is active")
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
    }
}

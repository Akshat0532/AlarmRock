package com.alarmrock.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val alarmId = intent.getLongExtra("alarm_id", -1L)
        val serviceIntent = Intent(context, AlarmPlaybackService::class.java).apply {
            putExtra("alarm_id", alarmId)
        }
        ContextCompat.startForegroundService(context, serviceIntent)

        val activityIntent = Intent(context, AlarmActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            putExtra("alarm_id", alarmId)
        }
        context.startActivity(activityIntent)
    }
}

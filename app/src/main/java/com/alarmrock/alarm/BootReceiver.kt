package com.alarmrock.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.alarmrock.data.WakeLockDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val database = WakeLockDatabase.getInstance(context)
            val scheduler = AlarmScheduler(context)
            CoroutineScope(Dispatchers.IO).launch {
                database.alarmDao().getEnabledAlarms().forEach { alarm ->
                    scheduler.scheduleAlarm(alarm)
                }
            }
        }
    }
}

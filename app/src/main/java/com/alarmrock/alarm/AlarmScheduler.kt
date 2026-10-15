package com.alarmrock.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.alarmrock.data.AlarmEntity
import java.util.Calendar

class AlarmScheduler(private val context: Context) {
    private val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun scheduleAlarm(alarm: AlarmEntity) {
        val triggerAt = getNextOccurrence(alarm)
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = "com.alarmrock.ALARM_TRIGGER"
            putExtra("alarm_id", alarm.id)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarm.id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAt, pendingIntent)
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAt, pendingIntent)
        }
    }

    fun cancelAlarm(alarmId: Long) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = "com.alarmrock.ALARM_TRIGGER"
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmId.toInt(),
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        pendingIntent?.let(alarmManager::cancel)
    }

    fun getNextOccurrence(alarm: AlarmEntity): Long {
        val now = Calendar.getInstance()
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, alarm.hour)
            set(Calendar.MINUTE, alarm.minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        if (!alarm.repeatDays.isNullOrBlank()) {
            val days = alarm.repeatDays.split(',').map { it.trim() }.filter { it.isNotEmpty() }
            if (days.isNotEmpty()) {
                var next = target.clone() as Calendar
                val list = listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")
                var found = false
                while (!found) {
                    val currentDay = list[next.get(Calendar.DAY_OF_WEEK) - 1]
                    if (currentDay in days) {
                        if (next.timeInMillis > now.timeInMillis) {
                            found = true
                        } else {
                            next.add(Calendar.DAY_OF_YEAR, 1)
                        }
                    } else {
                        next.add(Calendar.DAY_OF_YEAR, 1)
                    }
                }
                return next.timeInMillis
            }
        }

        if (target.timeInMillis <= now.timeInMillis) {
            target.add(Calendar.DAY_OF_YEAR, 1)
        }
        return target.timeInMillis
    }
}

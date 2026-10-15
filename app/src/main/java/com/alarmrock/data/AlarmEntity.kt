package com.alarmrock.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarms")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val label: String = "Wake Up",
    val hour: Int = 7,
    val minute: Int = 0,
    val enabled: Boolean = true,
    val repeatDays: String = "",
    val challengeType: String = "MATH",
    val challengeCount: Int = 3,
    val difficulty: String = "MEDIUM",
    val snoozeMinutes: Int = 5,
    val soundUri: String = "",
    val vibration: Boolean = true
)

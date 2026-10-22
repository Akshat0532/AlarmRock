package com.alarmrock.alarm

import com.alarmrock.data.AlarmEntity
import java.util.Calendar
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AlarmOccurrenceCalculatorTest {
    @Test
    fun oneTimeAlarmThatPassedMovesToTomorrow() {
        val now = calendar(day = Calendar.MONDAY, hour = 10, minute = 0)
        val alarm = AlarmEntity(hour = 9, minute = 30)

        val next = Calendar.getInstance().apply {
            timeInMillis = AlarmOccurrenceCalculator.nextOccurrence(alarm, now)
        }

        assertEquals(Calendar.TUESDAY, next.get(Calendar.DAY_OF_WEEK))
        assertEquals(9, next.get(Calendar.HOUR_OF_DAY))
        assertEquals(30, next.get(Calendar.MINUTE))
    }

    @Test
    fun repeatingAlarmUsesNextConfiguredDay() {
        val now = calendar(day = Calendar.MONDAY, hour = 10, minute = 0)
        val alarm = AlarmEntity(hour = 9, minute = 30, repeatDays = "MON,WED")

        val next = Calendar.getInstance().apply {
            timeInMillis = AlarmOccurrenceCalculator.nextOccurrence(alarm, now)
        }

        assertEquals(Calendar.WEDNESDAY, next.get(Calendar.DAY_OF_WEEK))
        assertTrue(next.timeInMillis > now.timeInMillis)
    }

    private fun calendar(day: Int, hour: Int, minute: Int): Calendar =
        Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, day)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
}

package com.alarmrock.alarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.alarmrock.domain.ChallengeEngine
import com.alarmrock.ui.ActiveAlarmScreen

class AlarmActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val alarmId = intent.getLongExtra("alarm_id", -1L)
        setContent {
            MaterialTheme {
                ActiveAlarmScreen(alarmId = alarmId, onComplete = { finish() })
            }
        }
    }
}

package com.alarmrock.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alarmrock.data.AlarmEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmEditorScreen(
    alarm: AlarmEntity?,
    onSave: (AlarmEntity) -> Unit,
    onCancel: () -> Unit
) {
    val label = remember { mutableStateOf(alarm?.label ?: "Wake Up") }
    val hour = remember { mutableStateOf(alarm?.hour ?: 7) }
    val minute = remember { mutableStateOf(alarm?.minute ?: 0) }
    val challengeType = remember { mutableStateOf(alarm?.challengeType ?: "MATH") }
    val challengeCount = remember { mutableStateOf(alarm?.challengeCount ?: 3) }
    val difficulty = remember { mutableStateOf(alarm?.difficulty ?: "MEDIUM") }
    val repeatDays = remember { mutableStateOf(alarm?.repeatDays ?: "MON,TUE,WED,THU,FRI") }
    val snoozeMinutes = remember { mutableStateOf(alarm?.snoozeMinutes ?: 5) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (alarm == null) "New alarm" else "Edit alarm") },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = label.value,
                onValueChange = { label.value = it },
                label = { Text("Alarm label") },
                modifier = Modifier.fillMaxWidth()
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = hour.value.toString(),
                    onValueChange = { hour.value = it.toIntOrNull()?.coerceIn(0, 23) ?: 7 },
                    label = { Text("Hour") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = minute.value.toString(),
                    onValueChange = { minute.value = it.toIntOrNull()?.coerceIn(0, 59) ?: 0 },
                    label = { Text("Minute") },
                    modifier = Modifier.weight(1f)
                )
            }
            OutlinedTextField(
                value = repeatDays.value,
                onValueChange = { repeatDays.value = it },
                label = { Text("Repeat days (MON,TUE,...)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = challengeType.value,
                onValueChange = { challengeType.value = it },
                label = { Text("Challenge type") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = challengeCount.value.toString(),
                onValueChange = { challengeCount.value = it.toIntOrNull()?.coerceAtLeast(1) ?: 1 },
                label = { Text("Challenge count") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = difficulty.value,
                onValueChange = { difficulty.value = it },
                label = { Text("Difficulty") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = snoozeMinutes.value.toString(),
                onValueChange = { snoozeMinutes.value = it.toIntOrNull()?.coerceAtLeast(0) ?: 5 },
                label = { Text("Snooze minutes") },
                modifier = Modifier.fillMaxWidth()
            )

            Card {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Challenge configured", style = MaterialTheme.typography.titleMedium)
                    Text("${challengeType.value} • ${difficulty.value} • ${challengeCount.value} rounds")
                }
            }

            Button(
                onClick = {
                    val saved = AlarmEntity(
                        id = alarm?.id ?: 0L,
                        label = label.value,
                        hour = hour.value,
                        minute = minute.value,
                        enabled = alarm?.enabled ?: true,
                        repeatDays = repeatDays.value,
                        challengeType = challengeType.value,
                        challengeCount = challengeCount.value,
                        difficulty = difficulty.value,
                        snoozeMinutes = snoozeMinutes.value,
                        soundUri = alarm?.soundUri ?: "",
                        vibration = alarm?.vibration ?: true
                    )
                    onSave(saved)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save alarm")
            }
        }
    }
}

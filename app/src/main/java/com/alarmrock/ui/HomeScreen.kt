package com.alarmrock.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alarmrock.data.AlarmEntity

@Composable
fun HomeScreen(
    viewModel: AlarmViewModel,
    onAddAlarm: () -> Unit,
    onSettings: () -> Unit,
    onStats: () -> Unit,
    onEditAlarm: (AlarmEntity) -> Unit,
    onChallengeSelect: () -> Unit
) {
    val alarms by viewModel.alarms.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddAlarm) {
                Icon(Icons.Default.Add, contentDescription = "Add alarm")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("WakeLock", style = MaterialTheme.typography.headlineMedium)
                    Text("Earn your wake-up", style = MaterialTheme.typography.bodyMedium)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.Analytics, contentDescription = null, modifier = Modifier.clickable { onStats() })
                    Icon(Icons.Default.Settings, contentDescription = null, modifier = Modifier.clickable { onSettings() })
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Next wake-up", style = MaterialTheme.typography.labelLarge)
                    Text("${alarms.firstOrNull()?.hour ?: 7}:${String.format("%02d", alarms.firstOrNull()?.minute ?: 0)}", style = MaterialTheme.typography.displaySmall)
                    Text("${alarms.size} alarms configured")
                }
            }

            if (alarms.isEmpty()) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("No alarms set yet")
                        Text("Create a wake-up challenge to start earning your morning.")
                    }
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(alarms) { alarm ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onEditAlarm(alarm) }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(text = alarm.label, style = MaterialTheme.typography.titleLarge)
                                    Text(text = String.format("%02d:%02d", alarm.hour, alarm.minute))
                                    Text(text = "${alarm.challengeType} • ${alarm.difficulty}")
                                }
                                Switch(
                                    checked = alarm.enabled,
                                    onCheckedChange = { viewModel.toggleAlarm(alarm) }
                                )
                            }
                        }
                    }
                }
            }

            androidx.compose.material3.Button(
                onClick = onChallengeSelect,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Challenge library")
            }
        }
    }
}

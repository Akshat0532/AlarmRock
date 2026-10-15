package com.alarmrock.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.alarmrock.domain.ChallengeDifficulty
import com.alarmrock.domain.ChallengeEngine

@Composable
fun ActiveAlarmScreen(alarmId: Long, onComplete: () -> Unit) {
    val engine = remember { ChallengeEngine() }
    val question = remember { engine.generateMathQuestion(ChallengeDifficulty.MEDIUM) }
    var answerText by remember { mutableStateOf(TextFieldValue()) }
    var completed by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text("WakeLock", style = MaterialTheme.typography.headlineLarge)
        Text("Challenge ${alarmId}: complete the task before dismissing.", style = MaterialTheme.typography.bodyLarge)

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Math challenge", style = MaterialTheme.typography.titleLarge)
                Text("${question.left} ${question.operator} ${question.right} = ?")
                OutlinedTextField(
                    value = answerText,
                    onValueChange = { answerText = it },
                    label = { Text("Your answer") },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = {
                        val answer = answerText.text.toIntOrNull() ?: -1
                        if (engine.validateMathAnswer(question, answer)) {
                            completed = true
                            onComplete()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Submit answer")
                }
            }
        }

        if (completed) {
            Text("Challenge complete — alarm dismissed.")
        }
    }
}

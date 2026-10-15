package com.alarmrock.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

object WakeLockDestinations {
    const val HOME = "home"
    const val EDITOR = "editor"
    const val STATS = "stats"
    const val SETTINGS = "settings"
    const val CHALLENGES = "challenges"
}

@Composable
fun WakeLockNavHost(viewModel: AlarmViewModel) {
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = WakeLockDestinations.HOME) {
        composable(WakeLockDestinations.HOME) {
            HomeScreen(
                viewModel = viewModel,
                onAddAlarm = { navController.navigate(WakeLockDestinations.EDITOR) },
                onSettings = { navController.navigate(WakeLockDestinations.SETTINGS) },
                onStats = { navController.navigate(WakeLockDestinations.STATS) },
                onEditAlarm = { alarm -> navController.navigate("${WakeLockDestinations.EDITOR}/${alarm.id}") },
                onChallengeSelect = { navController.navigate(WakeLockDestinations.CHALLENGES) }
            )
        }
        composable("${WakeLockDestinations.EDITOR}/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
            val current = viewModel.alarms.value.firstOrNull { it.id == id }
            AlarmEditorScreen(
                alarm = current,
                onSave = { alarm ->
                    viewModel.saveAlarm(alarm)
                    navController.navigateUp()
                },
                onCancel = { navController.navigateUp() }
            )
        }
        composable(WakeLockDestinations.EDITOR) {
            AlarmEditorScreen(
                alarm = null,
                onSave = { alarm ->
                    viewModel.saveAlarm(alarm)
                    navController.navigateUp()
                },
                onCancel = { navController.navigateUp() }
            )
        }
        composable(WakeLockDestinations.STATS) {
            StatisticsScreen(onBack = { navController.navigateUp() })
        }
        composable(WakeLockDestinations.SETTINGS) {
            SettingsScreen(onBack = { navController.navigateUp() })
        }
        composable(WakeLockDestinations.CHALLENGES) {
            ChallengeScreen(onBack = { navController.navigateUp() })
        }
    }
}

@Composable
fun DefaultPlaceholder(title: String, description: String) {
    Surface(modifier = androidx.compose.ui.Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = androidx.compose.ui.Modifier.padding(24.dp)) {
            Text(text = title, style = MaterialTheme.typography.headlineMedium)
            Text(text = description, modifier = androidx.compose.ui.Modifier.padding(top = 12.dp))
            Button(onClick = {}, modifier = androidx.compose.ui.Modifier.padding(top = 20.dp)) {
                Text("Continue")
            }
        }
    }
}

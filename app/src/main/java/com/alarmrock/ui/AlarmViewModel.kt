package com.alarmrock.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.alarmrock.alarm.AlarmScheduler
import com.alarmrock.alarm.AlarmScheduling
import com.alarmrock.data.AlarmEntity
import com.alarmrock.data.AlarmDao
import com.alarmrock.data.WakeLockDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlarmViewModel(
    application: Application,
    private val dao: AlarmDao = WakeLockDatabase.getInstance(application).alarmDao(),
    private val scheduler: AlarmScheduling = AlarmScheduler(application)
) : AndroidViewModel(application) {
    private val _alarms = MutableStateFlow<List<AlarmEntity>>(emptyList())
    val alarms: StateFlow<List<AlarmEntity>> = _alarms.asStateFlow()
    private val _operationError = MutableStateFlow<String?>(null)
    val operationError: StateFlow<String?> = _operationError.asStateFlow()

    init {
        viewModelScope.launch {
            dao.getAll().collect { _alarms.value = it }
        }
    }

    fun saveAlarm(alarm: AlarmEntity) = viewModelScope.launch {
        _operationError.value = null
        if (alarm.id == 0L) {
            val id = dao.insert(alarm)
            val persistedAlarm = alarm.copy(id = id)
            if (persistedAlarm.enabled) {
                schedule(persistedAlarm)
            }
        } else {
            scheduler.cancelAlarm(alarm.id)
            dao.update(alarm)
            if (alarm.enabled) {
                schedule(alarm)
            }
        }
    }

    fun deleteAlarm(alarm: AlarmEntity) = viewModelScope.launch {
        _operationError.value = null
        scheduler.cancelAlarm(alarm.id)
        dao.delete(alarm)
    }

    fun toggleAlarm(alarm: AlarmEntity) = viewModelScope.launch {
        _operationError.value = null
        val updatedAlarm = alarm.copy(enabled = !alarm.enabled)
        if (updatedAlarm.enabled) {
            dao.update(updatedAlarm)
            schedule(updatedAlarm)
        } else {
            scheduler.cancelAlarm(alarm.id)
            dao.update(updatedAlarm)
        }
    }

    private fun schedule(alarm: AlarmEntity) {
        try {
            scheduler.scheduleAlarm(alarm)
        } catch (exception: SecurityException) {
            _operationError.value = exception.message ?: "Exact alarm permission is required"
        } catch (exception: RuntimeException) {
            _operationError.value = exception.message ?: "Unable to schedule alarm"
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return AlarmViewModel(application) as T
        }
    }
}

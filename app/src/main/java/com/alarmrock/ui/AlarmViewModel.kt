package com.alarmrock.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.alarmrock.data.AlarmEntity
import com.alarmrock.data.WakeLockDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlarmViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = WakeLockDatabase.getInstance(application).alarmDao()
    private val _alarms = MutableStateFlow<List<AlarmEntity>>(emptyList())
    val alarms: StateFlow<List<AlarmEntity>> = _alarms.asStateFlow()

    init {
        viewModelScope.launch {
            dao.getAll().collect { _alarms.value = it }
        }
    }

    fun saveAlarm(alarm: AlarmEntity) = viewModelScope.launch {
        if (alarm.id == 0L) dao.insert(alarm) else dao.update(alarm)
    }

    fun deleteAlarm(alarm: AlarmEntity) = viewModelScope.launch { dao.delete(alarm) }

    fun toggleAlarm(alarm: AlarmEntity) = viewModelScope.launch {
        dao.update(alarm.copy(enabled = !alarm.enabled))
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return AlarmViewModel(application) as T
        }
    }
}

package com.alarmrock.ui

import android.app.Application
import com.alarmrock.alarm.AlarmScheduling
import com.alarmrock.data.AlarmDao
import com.alarmrock.data.AlarmEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AlarmViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private lateinit var dao: FakeAlarmDao
    private lateinit var scheduler: FakeAlarmScheduler
    private lateinit var viewModel: AlarmViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        dao = FakeAlarmDao()
        scheduler = FakeAlarmScheduler()
        viewModel = AlarmViewModel(Application(), dao, scheduler)
    }

    @After
    fun tearDown() {
        viewModel.clear()
        Dispatchers.resetMain()
    }

    @Test
    fun savingEnabledAlarmPersistsAndSchedulesIt() = runTest(dispatcher) {
        val alarm = AlarmEntity(hour = 8)

        viewModel.saveAlarm(alarm).join()

        assertEquals(1, dao.alarms.size)
        assertEquals(1, scheduler.scheduled)
        assertEquals(dao.alarms.single().id, scheduler.lastScheduled?.id)
    }

    @Test
    fun editingAlarmCancelsOldTriggerAndSchedulesUpdatedAlarm() = runTest(dispatcher) {
        val original = AlarmEntity(id = 4, hour = 8)
        dao.alarms += original
        val edited = original.copy(hour = 9)

        viewModel.saveAlarm(edited).join()

        assertEquals(listOf(4L), scheduler.cancelled)
        assertEquals(edited, scheduler.lastScheduled)
    }

    @Test
    fun enablingAlarmSchedulesIt() = runTest(dispatcher) {
        val disabled = AlarmEntity(id = 5, enabled = false)
        dao.alarms += disabled

        viewModel.toggleAlarm(disabled).join()

        assertEquals(disabled.copy(enabled = true), scheduler.lastScheduled)
    }

    @Test
    fun disablingAlarmCancelsItsTrigger() = runTest(dispatcher) {
        val enabled = AlarmEntity(id = 6)
        dao.alarms += enabled

        viewModel.toggleAlarm(enabled).join()

        assertEquals(listOf(6L), scheduler.cancelled)
        assertTrue(dao.alarms.single().enabled.not())
    }

    @Test
    fun deletingAlarmCancelsBeforeDeletingIt() = runTest(dispatcher) {
        val alarm = AlarmEntity(id = 7)
        dao.alarms += alarm

        viewModel.deleteAlarm(alarm).join()

        assertEquals(listOf(7L), scheduler.cancelled)
        assertTrue(dao.alarms.isEmpty())
    }
}

private class FakeAlarmScheduler : AlarmScheduling {
    var scheduled = 0
    var lastScheduled: AlarmEntity? = null
    val cancelled = mutableListOf<Long>()

    override fun scheduleAlarm(alarm: AlarmEntity) {
        scheduled++
        lastScheduled = alarm
    }

    override fun cancelAlarm(alarmId: Long) {
        cancelled += alarmId
    }
}

private class FakeAlarmDao : AlarmDao {
    val alarms = mutableListOf<AlarmEntity>()
    private val alarmsFlow = MutableStateFlow<List<AlarmEntity>>(emptyList())
    private var nextId = 1L

    override fun getAll(): Flow<List<AlarmEntity>> = alarmsFlow

    override suspend fun getEnabledAlarms(): List<AlarmEntity> =
        alarms.filter { it.enabled }

    override suspend fun insert(alarm: AlarmEntity): Long {
        val id = if (alarm.id == 0L) nextId++ else alarm.id
        alarms += alarm.copy(id = id)
        publish()
        return id
    }

    override suspend fun update(alarm: AlarmEntity) {
        alarms.replaceAll { if (it.id == alarm.id) alarm else it }
        publish()
    }

    override suspend fun delete(alarm: AlarmEntity) {
        alarms.removeAll { it.id == alarm.id }
        publish()
    }

    private fun publish() {
        alarmsFlow.value = alarms.toList()
    }
}

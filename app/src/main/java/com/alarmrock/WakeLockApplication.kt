package com.alarmrock

import android.app.Application
import com.alarmrock.data.WakeLockDatabase

class WakeLockApplication : Application() {
    lateinit var database: WakeLockDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        database = WakeLockDatabase.getInstance(this)
    }
}

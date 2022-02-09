package com.gowtham.repeattimer

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val TAG: String = this.javaClass.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.start_work).setOnClickListener {
            startWork()
        }
        findViewById<Button>(R.id.stop_work).setOnClickListener {
            stopWork()
        }
    }

    private fun startWork() {
        //MyApplication.getAppViewModel()?.startTimerTask()
        //MyApplication.getAppViewModel()?.startThreadPoolExecutor()
        //MyApplication.getAppViewModel()?.startTicker()
        //MyApplication.getAppViewModel()?.startWorkManager()
        MyApplication.getAppViewModel()?.startAlarmManager(this)
    }

    private fun stopWork() {
        //MyApplication.getAppViewModel()?.stopTimerTask()
        //MyApplication.getAppViewModel()?.stopThreadPoolExecutor()
        //MyApplication.getAppViewModel()?.stopTicker()
        //MyApplication.getAppViewModel()?.cancelWorkManager()
        MyApplication.getAppViewModel()?.stopAlarm()
    }
}
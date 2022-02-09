package com.gowtham.repeattimer

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.SystemClock
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.*
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit


public class AppViewModel(var myApplication: Application) : AndroidViewModel(myApplication) {
    private var timer: Timer? = null

    private var scheduledFuture: ScheduledFuture<*>? = null

    private var TIME_INTERVAL: Long = 60000

    private val TAG: String = this.javaClass.name

    private lateinit var tickReceiver: BroadcastReceiver

    private lateinit var pendingIntent: PendingIntent

    private var alarmManager: AlarmManager? = null

    fun startTimerTask() {
        timer?.cancel()
        timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                ApiController.getPost()
            }
        }
        timer?.scheduleAtFixedRate(task, 0, TIME_INTERVAL)
    }

    fun stopTimerTask() {
        timer?.cancel()
    }

    fun cancelWorkManager() {
        MyApplication.getInstance()?.applicationContext?.let {
            WorkManager.getInstance(it).cancelAllWorkByTag("PERIODIC_JOB")
        }
        Log.d(TAG, "cancelWorkManager: PERIODIC_JOB")
    }

    fun startWorkManager() {
        val work: PeriodicWorkRequest =
            PeriodicWorkRequestBuilder<PostDataWorker>(10, TimeUnit.SECONDS)
                .addTag("PERIODIC_JOB")
                .build()
        val workManager = MyApplication.getInstance()?.applicationContext?.let {
            WorkManager.getInstance(
                it
            )
        }
        Log.d(TAG, "startWorkManager: PERIODIC_JOB")
        workManager?.enqueue(work)
    }

    fun startThreadPoolExecutor() {
        val exec = ScheduledThreadPoolExecutor(1)
        val period: Long = 1
        scheduledFuture = exec.scheduleAtFixedRate(MyTask(), 0, period, TimeUnit.MINUTES)
    }

    fun stopThreadPoolExecutor() {
        scheduledFuture?.cancel(true)
    }

    fun startTicker() {
        if (::tickReceiver.isInitialized) {
            myApplication.applicationContext.unregisterReceiver(tickReceiver)
        }
        //Getting Immediately
        ApiController.getPost()
        tickReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, intent: Intent?) {
                if (intent?.action?.compareTo(Intent.ACTION_TIME_TICK) == 0) {
                    //Will be triggering every one minute
                    ApiController.getPost()
                }
            }
        }
        myApplication.applicationContext.registerReceiver(
            tickReceiver,
            IntentFilter(Intent.ACTION_TIME_TICK)
        )
    }

    fun stopTicker() {
        myApplication.applicationContext.unregisterReceiver(tickReceiver)
    }

    fun startAlarmManager(context: Context) {
        try {
            //Create a new PendingIntent and add it to the AlarmManager
            val intent = Intent(context, AlarmReceiver::class.java)
            pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.getActivity(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            } else {
                PendingIntent.getActivity(
                    context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT
                )
            }

            alarmManager = ContextCompat.getSystemService(context, AlarmManager::class.java)
            alarmManager?.setRepeating(
                AlarmManager.ELAPSED_REALTIME, System.currentTimeMillis() + 1000, 60000, pendingIntent
            )
        } catch (e: Exception) {
            Log.d(TAG, "startAlarmManager Exception: ${e.message}")
        }
    }

    fun stopAlarm(){
        alarmManager?.cancel(pendingIntent)
    }


}
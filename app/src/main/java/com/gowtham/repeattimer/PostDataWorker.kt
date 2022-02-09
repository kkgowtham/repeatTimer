package com.gowtham.repeattimer

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class PostDataWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    private val TAG: String = this.javaClass.name

    override fun doWork(): Result {
        Log.d(TAG,"DoWork")
        return Result.success()
    }
}
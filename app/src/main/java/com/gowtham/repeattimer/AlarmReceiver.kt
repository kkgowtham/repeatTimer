package com.gowtham.repeattimer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        ApiController.getPost("Alarm Manager")
    }
}

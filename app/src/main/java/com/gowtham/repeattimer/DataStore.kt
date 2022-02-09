package com.gowtham.repeattimer

import android.util.Log

object DataStore {
    private val TAG: String = this.javaClass.name
    private var data:Post?=null

    fun updateData(post: Post){
        this.data = post
        Log.d(TAG, "updateData: $post")
    }
}
package com.gowtham.repeattimer

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlin.random.Random

class ApiController {

    companion object {
        private val TAG: String = this.javaClass.name
        val api by lazy { ApiHandler.callApi() }

        var disposable: Disposable? = null

        private fun doApiCall(postId: Int = Random.nextInt(100), callback: PostResponseCallBack) {
            disposable = api.getPost(postId.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    callback.invoke(
                        if (result != null) Response.Success(post = result)
                        else Response.Failure("Null Data")
                    )
                }, { error ->
                    callback.invoke(Response.Failure(error.message.toString()))
                    error.printStackTrace()
                })
        }

         fun getPost(source:String?=""){
            doApiCall {
                if (it is Response.Success) {
                    Log.d("$TAG : $source", "run Success: ${it.post}")
                } else if (it is Response.Failure) {
                    Log.d("$TAG : $source", "run Failure: ${it.message}")
                }
            }
        }

    }

}
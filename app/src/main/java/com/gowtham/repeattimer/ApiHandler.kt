package com.gowtham.repeattimer

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiHandler {
    companion object {

        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()
                )
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .baseUrl(Constants.BASE_URL)
                .build()
        }

        fun callApi(): PostApi {
            val retrofitCall = getRetrofit()
            return retrofitCall.create(PostApi::class.java)
        }
    }
}
package com.gowtham.repeattimer

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApi {

    @GET("posts/{postId}")
    fun getPost(@Path("postId")postId:String):Observable<Post>

}
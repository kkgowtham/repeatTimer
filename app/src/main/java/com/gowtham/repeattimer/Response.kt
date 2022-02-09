package com.gowtham.repeattimer

sealed class Response {
    data class Success(var post: Post): Response()
   data class Failure(var message:String?=null):Response()
}
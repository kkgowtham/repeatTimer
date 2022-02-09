package com.gowtham.repeattimer

class MyTask : Runnable {
    override fun run() {
        ApiController.getPost()
    }
}
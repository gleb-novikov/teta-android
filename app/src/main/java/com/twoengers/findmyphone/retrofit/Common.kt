package com.twoengers.findmyphone.retrofit

object Common {
    private val BASE_URL = "http://89.22.187.96/"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}
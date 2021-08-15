package com.twoengers.findmyphone.retrofit

import com.twoengers.findmyphone.retrofit.body.LoginBody
import com.twoengers.findmyphone.retrofit.body.MetricsBody
import com.twoengers.findmyphone.retrofit.body.RegistrationBody
import com.twoengers.findmyphone.retrofit.response.ChildrenResponse
import com.twoengers.findmyphone.retrofit.response.LoginResponse
import com.twoengers.findmyphone.retrofit.response.MetricsResponse
import com.twoengers.findmyphone.retrofit.response.RegistrationResponse
import retrofit2.Call
import retrofit2.http.*


interface RetrofitServices {
    @POST("/auth/login")
    fun login(@Body loginBody: LoginBody?): Call<LoginResponse?>?

    @POST("/auth/registration")
    fun registration(@Body registrationBody: RegistrationBody?): Call<RegistrationResponse?>?

    @GET("/users/children")
    fun getChildren(@Header("Authorization") token: String): Call<ChildrenResponse?>?

    @POST("/metrics")
    fun sendMetrics(@Header("Authorization") token: String, @Body metricsBody: MetricsBody):
            Call<String>?

    @GET("/metrics/user/{id}")
    fun getMetrics(@Path("id") id: Int, @Header("Authorization") token: String):
            Call<MetricsResponse>?
}
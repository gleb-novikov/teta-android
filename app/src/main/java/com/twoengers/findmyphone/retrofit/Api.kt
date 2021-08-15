package com.twoengers.findmyphone.retrofit

import android.text.Editable
import android.util.Log
import com.twoengers.findmyphone.LoginFragment
import com.twoengers.findmyphone.User
import com.twoengers.findmyphone.retrofit.body.LoginBody
import com.twoengers.findmyphone.retrofit.body.MetricsBody
import com.twoengers.findmyphone.retrofit.body.RegistrationBody
import com.twoengers.findmyphone.retrofit.response.ChildrenResponse
import com.twoengers.findmyphone.retrofit.response.LoginResponse
import com.twoengers.findmyphone.retrofit.response.MetricsResponse
import com.twoengers.findmyphone.retrofit.response.RegistrationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Api(val listener: () -> Unit) {
    var mService: RetrofitServices = Common.retrofitService

    fun login(email: String, password: String) {
        mService.login(LoginBody(email, password))?.enqueue(object : Callback<LoginResponse?> {
            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                Log.d("SERVER_ERROR", t.message.toString())
            }
            override fun onResponse(call: Call<LoginResponse?>, response: Response<LoginResponse?>) {
                if (response.code() == 200) {
                    User.TOKEN = response.body()?.token ?: User.TOKEN
                    User.is_parent = response.body()?.is_parent ?: User.is_parent
                    listener()
                }
                Log.d("SERVER", response.body().toString())
            }
        })
    }

    fun registration(email: String, name: String, surname: String, is_parent: Boolean, parent_email: String, password: String) {
        mService.registration(RegistrationBody(email, name, surname, is_parent, parent_email, password))?.enqueue(object : Callback<RegistrationResponse?> {
            override fun onFailure(call: Call<RegistrationResponse?>, t: Throwable) {
                Log.d("SERVER_ERROR", t.message.toString())
            }
            override fun onResponse(call: Call<RegistrationResponse?>, response: Response<RegistrationResponse?>) {
                if (response.code() == 200) {
                    User.TOKEN = response.body()?.token ?: User.TOKEN
                    listener()
                }
                Log.d("SERVER", response.body().toString())
            }
        })
    }

    fun getChildren() {
        mService.getChildren(User.TOKEN)?.enqueue(object : Callback<ChildrenResponse?> {
            override fun onFailure(call: Call<ChildrenResponse?>, t: Throwable) {
                Log.d("SERVER_ERROR", t.message.toString())
            }
            override fun onResponse(call: Call<ChildrenResponse?>, response: Response<ChildrenResponse?>) {
                Log.d("SERVER", response.body().toString())
            }
        })
    }

    fun sendMetrics(metricsBody: MetricsBody) {
        mService.sendMetrics(User.TOKEN, metricsBody)?.enqueue(object : Callback<String?> {
            override fun onFailure(call: Call<String?>, t: Throwable) {
                Log.d("SERVER_ERROR", t.message.toString())
            }
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                Log.d("SERVER", response.code().toString())
            }
        })
    }

    fun getMetrics(userId: Int) {
        mService.getMetrics(userId, User.TOKEN)?.enqueue(object : Callback<MetricsResponse?> {
            override fun onFailure(call: Call<MetricsResponse?>, t: Throwable) {
                Log.d("SERVER_ERROR", t.message.toString())
            }
            override fun onResponse(call: Call<MetricsResponse?>, response: Response<MetricsResponse?>) {
                Log.d("SERVER", response.body().toString())
            }
        })
    }
}
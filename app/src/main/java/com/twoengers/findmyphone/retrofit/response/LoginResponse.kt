package com.twoengers.findmyphone.retrofit.response

data class LoginResponse(
    val token: String,
    val email: String,
    val name: String,
    val surname: String,
    val is_parent: Boolean,
)

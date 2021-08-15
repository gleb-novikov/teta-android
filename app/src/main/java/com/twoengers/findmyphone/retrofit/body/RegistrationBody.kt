package com.twoengers.findmyphone.retrofit.body

data class RegistrationBody(
    val email: String,
    val name: String,
    val surname: String,
    val is_parent: Boolean,
    val parent_email: String,
    val password: String
)

package com.twoengers.findmyphone.retrofit.body

data class MetricsBody(
    val timestamp: String,
    val latitude: Double,
    val longitude: Double,
    val cell_id: Int,
    val device_id: String,
    val lac: Int,
    val rsrp: Int,
    val rsrq: Int,
    val sinr: Int,
    val imsi: String
)

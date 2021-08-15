package com.twoengers.findmyphone

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Context.TELEPHONY_SERVICE
import android.location.LocationListener
import android.location.LocationManager
import android.provider.Settings
import android.telephony.CellInfoLte
import android.telephony.TelephonyManager
import android.telephony.gsm.GsmCellLocation
import android.util.Log

class Metrics(var context: Context) {
    var metrics = mutableMapOf<String, Any>(
            "cell_id" to 0,
            "lac" to 0,
            "android_id" to 0,
            "latitude" to 0.0,
            "longitude" to 0.0,
            "rsrp" to 0,
            "rsrq" to 0,
            "rssnr" to 0,
            "imsi" to ""
    )

    @SuppressLint("MissingPermission", "NewApi", "HardwareIds")
    fun getMetrics() {
        val telephonyManager = context.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        val gsmCellLocation = telephonyManager.cellLocation as GsmCellLocation

        metrics["cell_id"] = gsmCellLocation.cid
        metrics["lac"] = gsmCellLocation.lac
        metrics["android_id"] = Settings.Secure.getString(context.applicationContext.contentResolver,
                Settings.Secure.ANDROID_ID)

        val locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
        val locationListener = LocationListener {
            metrics["latitude"] = it.latitude
            metrics["longitude"] = it.longitude
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000,
                1f, locationListener)

        val cellInfoList = telephonyManager.allCellInfo
        val rsrp = arrayListOf<Int>()
        val rsrq = arrayListOf<Int>()
        val rssnr = arrayListOf<Int>()
        for (cellInfo in cellInfoList) {
            if (cellInfo is CellInfoLte) {
                rsrp.add(cellInfo.cellSignalStrength.rsrp)
                rsrq.add(cellInfo.cellSignalStrength.rsrq)
                rssnr.add(cellInfo.cellSignalStrength.rssnr)
            }
        }
        metrics["rsrp"] = rsrp.average().toInt()
        metrics["rsrq"] = rsrq.average().toInt()
        metrics["rssnr"] = rssnr.average().toInt()

        // metrics["imsi"] = manager.getSubscriberId()

        Log.d("METRICS", metrics.toString())
    }
}
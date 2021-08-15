package com.twoengers.findmyphone

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings.Secure
import android.telephony.CellInfoLte
import android.telephony.TelephonyManager
import android.telephony.gsm.GsmCellLocation
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    var metrics = mutableMapOf(
        "cell_id" to 0,
        "lac" to 0,
        "android_id" to 0,
        "latitude" to 0.0,
        "longitude" to 0.0,
        "rsrp" to mutableListOf<Int>(),
        "rsrq" to mutableListOf<Int>(),
        "rssnr" to mutableListOf<Int>(),
        "imsi" to ""
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getMetrics()
    }

    @SuppressLint("MissingPermission", "NewApi", "HardwareIds")
    fun getMetrics() {
        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val gsmCellLocation = telephonyManager.cellLocation as GsmCellLocation

        metrics["cell_id"] = gsmCellLocation.cid
        metrics["lac"] = gsmCellLocation.lac
        metrics["android_id"] = Secure.getString(applicationContext.contentResolver,
            Secure.ANDROID_ID)

        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val locationListener = LocationListener {
            metrics["latitude"] = it.latitude
            metrics["longitude"] = it.longitude
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000,
            1f, locationListener)

        val cellInfoList = telephonyManager.allCellInfo
        (metrics["rsrp"] as MutableList<*>).clear()
        (metrics["rsrq"] as MutableList<*>).clear()
        (metrics["rssnr"] as MutableList<*>).clear()
        for (cellInfo in cellInfoList) {
            if (cellInfo is CellInfoLte) {
                (metrics["rsrp"] as MutableList<Int>).add(cellInfo.cellSignalStrength.rsrp)
                (metrics["rsrq"] as MutableList<Int>).add(cellInfo.cellSignalStrength.rsrq)
                (metrics["rssnr"] as MutableList<Int>).add(cellInfo.cellSignalStrength.rssnr)
            }
        }

        // metrics["imsi"] = manager.getSubscriberId()

        Log.d("METRICS", metrics.toString())
    }
}

package com.plugin.admob

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdmobImpl(private val activity: Activity) {
    fun initialize() {
        val backgroundScope = CoroutineScope(Dispatchers.IO)
        backgroundScope.launch {
            // Initialize the Google Mobile Ads SDK on a background thread.
            MobileAds.initialize(activity)
        }
    }

    fun pong(value: String): String {
        Log.i("Pong", value)
        return value
    }
}

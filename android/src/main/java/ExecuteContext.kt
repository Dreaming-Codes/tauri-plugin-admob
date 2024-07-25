package com.plugin.admob

import com.plugin.admob.ads.AdBase
import com.plugin.admob.core.Context
import com.plugin.admob.core.Helper.Companion.jsonArray2stringList
import android.app.Activity
import app.tauri.plugin.Invoke
import app.tauri.plugin.JSObject
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.InvocationTargetException

class ExecuteContext internal constructor(private val call: Invoke) : Context {
    fun <T : AdBase?> optAdOrCreate(type: Class<T>): T? {
        var ad = type.cast(optAd())
        if (ad == null) {
            try {
                ad = type.getDeclaredConstructor(ExecuteContext::class.java).newInstance(this)
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
                this.reject("Fail to create ad")
            } catch (e: InstantiationException) {
                e.printStackTrace()
                this.reject("Fail to create ad")
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
                this.reject("Fail to create ad")
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
                this.reject("Fail to create ad")
            }
        }
        return ad
    }

    val activity: Activity
        get() = plugin!!.activity

    private fun getData(): InvokeArgs {
        return call.parseArgs(InvokeArgs::class.java);
    }

    private fun getProperty(args: InvokeArgs, name: String): Any? {
        return when (name) {
            "id" -> args.id
            "cls" -> args.cls
            "adUnitId" -> args.adUnitId
            "appMuted" -> args.appMuted
            "appVolume" -> args.appVolume
            "position" -> args.position
            "contentUrl" -> args.contentUrl
            "npa" -> args.npa
            "maxAdContentRating" -> args.maxAdContentRating
            "tagForChildDirectedTreatment" -> args.tagForChildDirectedTreatment
            "tagForUnderAgeOfConsent" -> args.tagForUnderAgeOfConsent
            "testDeviceIds" -> args.testDeviceIds
            "serverSideVerification" -> args.serverSideVerification
            "customData" -> args.customData
            "userId" -> args.userId
            else -> null
        }
    }

    override fun has(name: String): Boolean {
        println("has: $name, ${getProperty(getData(), name)}")
        return getProperty(getData(), name) != null
    }

    override fun opt(name: String): Any? {
        println("opt: $name, ${getProperty(getData(), name)}")
        return getProperty(getData(), name)
    }

    override fun optBoolean(name: String): Boolean? {
        return opt(name) as Boolean?
    }

    override fun optDouble(name: String): Double? {
        return opt(name) as Double?
    }

    override fun optFloat(name: String): Float? {
        return opt(name) as Float?
    }

    override fun optInt(name: String): Int? {
        return opt(name) as Int?
    }

    override fun optString(name: String): String? {
        return opt(name) as String?
    }

    override fun optStringList(name: String): List<String?> {
        return opt(name) as List<String?>
    }

    override fun optObject(name: String): JSONObject? {
        return opt(name) as JSONObject?
    }

    override fun resolve() {
        call.resolve()
    }

    override fun resolve(data: Boolean) {
        try {
            call.resolve(JSObject("true"))
        } catch (e: JSONException) {
            e.printStackTrace()
            reject()
        }
    }

    override fun reject(msg: String?) {
        call.reject(msg)
    }

    companion object {
        var plugin: AdmobPlugin? = null
    }
}

package com.plugin.admob

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import android.webkit.WebView
import app.tauri.annotation.Command
import app.tauri.annotation.InvokeArg
import app.tauri.annotation.TauriPlugin
import app.tauri.plugin.JSObject
import app.tauri.plugin.Plugin
import app.tauri.plugin.Invoke
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import com.plugin.admob.ads.Banner
import com.plugin.admob.ads.Interstitial
import com.plugin.admob.ads.Rewarded
import com.plugin.admob.ads.RewardedInterstitial
import com.plugin.admob.core.GenericAd
import com.plugin.admob.core.Helper
import org.json.JSONException
import org.json.JSONObject

@InvokeArg
class InvokeArgs {
    var cls: String? = null
    val id: Int? = null
    var adUnitId: String? = null
    var appMuted: Boolean? = null
    var appVolume: Float? = null
    var position: String? = null
    var contentUrl: String? = null
    var npa: String? = null
    var maxAdContentRating: String? = null
    val tagForChildDirectedTreatment: Boolean? = null
    val tagForUnderAgeOfConsent: Boolean? = null
    val testDeviceIds: List<String?>? = null
    val serverSideVerification: JSObject? = null
    val customData: String? = null
    val userId: String? = null
}

@TauriPlugin
class AdmobPlugin(var activity: Activity) : Plugin(activity) {
    private lateinit var consentInformation: ConsentInformation
    val isPrivacyOptionsRequired: Boolean
        get() =
            consentInformation.privacyOptionsRequirementStatus ==
                    ConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED
    private var helper: Helper? = null
    var webView: WebView? = null

    override fun load(webView: WebView) {
        super.load(webView)
        this.webView = webView
        helper = Helper(this)
        ExecuteContext.Companion.plugin = this

        MobileAds.initialize(activity.baseContext) { status: InitializationStatus? ->
            helper!!.configForTestLab()
        }

        val params = ConsentRequestParameters
            .Builder()
            .build()

        consentInformation = UserMessagingPlatform.getConsentInformation(this.activity)
        consentInformation.requestConsentInfoUpdate(
            this.activity,
            params,
            {
                UserMessagingPlatform.loadAndShowConsentFormIfRequired(
                    this.activity
                ) { loadAndShowError ->
                    if (loadAndShowError != null) {
                        Log.w(TAG, "${loadAndShowError.errorCode}: ${loadAndShowError.message}")
                        return@loadAndShowConsentFormIfRequired
                    }
                }
            },
            {
                    requestConsentError ->
                Log.w(TAG, "${requestConsentError.errorCode}: ${requestConsentError.message}")
            }
        )
    }

    @Command
    fun isPrivacyOptionsRequired(call: Invoke) {
        try {
            call.resolve(JSObject("{\"isPrivacyOptionsRequired\": ${isPrivacyOptionsRequired}}"))
        } catch (ex: JSONException) {
            call.reject(ex.toString())
        }
    }

    @Command
    fun showPrivacyOptionsForm(invoke: Invoke) {
        UserMessagingPlatform.showPrivacyOptionsForm(this.activity) { formError ->
            if (formError != null) {
                Log.w(TAG, "${formError.errorCode}: ${formError.message}")
                invoke.reject(formError.message)
                return@showPrivacyOptionsForm
            }
            invoke.resolve()
        }
    }

    @Command
    fun trackingAuthorizationStatus(call: Invoke) {
        try {
            call.resolve(JSObject("{\"status\": false}"))
        } catch (ex: JSONException) {
            call.reject(ex.toString())
        }
    }

    @Command
    fun requestTrackingAuthorization(call: Invoke) {
        try {
            call.resolve(JSObject("{\"status\": false}"))
        } catch (ex: JSONException) {
            call.reject(ex.toString())
        }
    }

    @Command
    fun configure(call: Invoke) {
        val ctx = ExecuteContext(call)
        ctx.configure(helper!!)
    }

    @Command
    fun configRequest(call: Invoke) {
        val ctx = ExecuteContext(call)
        MobileAds.setRequestConfiguration(ctx.optRequestConfiguration())
        helper!!.configForTestLab()
        ctx.resolve()
    }

    @Command
    fun adCreate(invoke: Invoke) {
        val ctx = ExecuteContext(invoke)
        android.os.Handler(activity.mainLooper).post {
            val adClass = ctx.optString("cls")
            if (adClass == null) {
                ctx.reject("ad cls is missing")
            } else {
                when (adClass) {
                    "BannerAd" -> Banner(ctx)
                    "InterstitialAd" -> Interstitial(ctx)
                    "RewardedAd" -> Rewarded(ctx)
                    "RewardedInterstitialAd" -> RewardedInterstitial(ctx)
                    else -> ctx.reject("ad cls is not supported: $adClass")
                }
                ctx.resolve()
            }
        }
    }

    @Command
    fun adIsLoaded(call: Invoke) {
        val ctx = ExecuteContext(call)
        android.os.Handler(activity.mainLooper).post {
            val ad = ctx.optAdOrError() as GenericAd?
            if (ad != null) {
                ctx.resolve(ad.isLoaded)
            }
        }
    }

    @Command
    fun adLoad(call: Invoke) {
        val ctx = ExecuteContext(call)
        android.os.Handler(activity.mainLooper).post {
            val ad = ctx.optAdOrError() as GenericAd?
            ad?.load(ctx)
        }
    }

    @Command
    fun adShow(call: Invoke) {
        val ctx = ExecuteContext(call)

        android.os.Handler(activity.mainLooper).post {
            val ad = ctx.optAdOrError() as GenericAd?
            if (ad != null) {
                if (ad.isLoaded) {
                    ad.show(ctx)
                } else {
                    ctx.reject("ad is not loaded")
                }
            }
        }
    }

    @Command
    fun adHide(call: Invoke) {
        val ctx = ExecuteContext(call)
        android.os.Handler(activity.mainLooper).post {
            val ad = ctx.optAdOrError() as GenericAd?
            ad?.hide(ctx)
        }
    }

    fun emit(eventName: String?, data: Map<String?, Any?>?) {
        try {
            if (eventName != null) {
                trigger(eventName, JSObject.fromJSONObject(JSONObject(data)))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}

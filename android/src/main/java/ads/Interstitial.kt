package com.plugin.admob.ads

import com.plugin.admob.ExecuteContext
import com.plugin.admob.Generated
import com.plugin.admob.core.Context
import com.plugin.admob.core.GenericAd
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class Interstitial(ctx: ExecuteContext?) : AdBase(ctx), GenericAd {
    private var mAd: InterstitialAd? = null
    override fun destroy() {
        clear()
        super.destroy()
    }

    override fun load(ctx: Context?) {
        clear()
        InterstitialAd.load(
            activity,
            adUnitId,
            ctx!!.optAdRequest(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mAd = interstitialAd
                    mAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            clear()
                            emit(Generated.Events.INTERSTITIAL_DISMISS)
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            emit(Generated.Events.INTERSTITIAL_SHOW_FAIL, adError)
                        }

                        override fun onAdShowedFullScreenContent() {
                            emit(Generated.Events.INTERSTITIAL_SHOW)
                        }

                        override fun onAdImpression() {
                            emit(Generated.Events.INTERSTITIAL_IMPRESSION)
                        }
                    }
                    emit(Generated.Events.INTERSTITIAL_LOAD)
                    ctx.resolve()
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    clear()
                    emit(Generated.Events.INTERSTITIAL_LOAD_FAIL, loadAdError)
                    ctx.reject(loadAdError.message)
                }
            })
    }

    override val isLoaded: Boolean
        get() = mAd != null

    override fun show(ctx: Context?) {
        mAd!!.show(activity)
        ctx!!.resolve()
    }

    private fun clear() {
        if (mAd != null) {
            mAd!!.fullScreenContentCallback = null
            mAd = null
        }
    }
}

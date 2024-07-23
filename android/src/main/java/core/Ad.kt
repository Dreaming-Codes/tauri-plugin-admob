package com.plugin.admob.core

import android.R
import android.app.Activity
import android.view.ViewGroup
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.rewarded.RewardItem
import com.plugin.admob.AdmobPlugin
import java.util.Objects

abstract class Ad(val id: Int, val adUnitId: String) {
    init {
        Helper.ads.put(id, this)
    }

    constructor(ctx: Context) : this(
        Objects.requireNonNull<Int?>(ctx.optId()),
        Objects.requireNonNull<String?>(ctx.optAdUnitID())
    )

    open fun destroy() {
        Helper.ads.remove(id)
    }

    protected abstract val plugin: AdmobPlugin
    val activity: Activity
        get() = plugin.activity
    val contentView: ViewGroup?
        get() = activity.findViewById(R.id.content)

    protected fun emit(eventName: String?, error: AdError) {
        this.emit(eventName, object : HashMap<String?, Any?>() {
            init {
                put("code", error.code)
                put("message", error.message)
                put("cause", error.cause)
            }
        })
    }

    protected fun emit(eventName: String?, rewardItem: RewardItem) {
        this.emit(eventName, object : HashMap<String?, Any?>() {
            init {
                put("reward", object : HashMap<String?, Any?>() {
                    init {
                        put("amount", rewardItem.amount)
                        put("type", rewardItem.type)
                    }
                })
            }
        })
    }

    protected fun emit(eventName: String?, data: Map<String?, Any?>? = HashMap()) {
        plugin.emit(eventName, object : HashMap<String?, Any?>(data) {
            init {
                put("adId", id)
            }
        })
    }
}

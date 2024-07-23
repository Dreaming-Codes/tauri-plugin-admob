package com.plugin.admob.ads

import com.plugin.admob.AdmobPlugin
import com.plugin.admob.ExecuteContext
import com.plugin.admob.core.Ad
import com.plugin.admob.core.Helper

open class AdBase(ctx: ExecuteContext?) : Ad(ctx!!) {
    override val plugin: AdmobPlugin
        get() = ExecuteContext.Companion.plugin!!
}

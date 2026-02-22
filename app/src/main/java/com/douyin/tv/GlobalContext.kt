/*
 * GlobalContext - 全局 Context 提供者
 */

package com.douyin.tv

import android.content.Context

object GlobalContext {
    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context.applicationContext
    }

    fun getContext(): Context {
        return context
    }
}

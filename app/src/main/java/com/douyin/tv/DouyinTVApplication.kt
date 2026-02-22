/*
 * DouyinTVApplication - 应用程序入口
 */

package com.douyin.tv

import android.app.Application

class DouyinTVApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalContext.init(this)
    }
}

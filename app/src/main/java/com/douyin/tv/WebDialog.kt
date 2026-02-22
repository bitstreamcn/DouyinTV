/*
 * WebDialog - 全屏 WebView 对话框
 */

package com.douyin.tv

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.KeyEvent
import android.view.ViewGroup
import android.webkit.WebView

class WebDialog(
    context: Context,
    private val webView: WebView
) : Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {

    companion object {
        fun createWebView(context: Context): WebView {
            return WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        }
    }

    init {
        setContentView(webView)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // 处理返回键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack()
                return true
            } else {
                dismiss()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}

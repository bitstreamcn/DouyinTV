/*
 * DefaultRenderersFactoryProvider - Media3 渲染器工厂提供者
 */

package com.douyin.tv

import android.os.Handler
import android.os.Looper
import androidx.media3.exoplayer.DefaultRenderersFactory
import android.content.Context

object DefaultRenderersFactoryProvider {

    fun getRenderersFactory(): DefaultRenderersFactory {
        val context = GlobalContext.getContext()
        return DefaultRenderersFactory(context).apply {
            setEnableDecoderFallback(true)
            setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON)
        }
    }
}

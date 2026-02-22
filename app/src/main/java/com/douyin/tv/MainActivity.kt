/*
 * Douyin TV Player - 抖音电视版播放器
 * 使用 Media3 播放抖音视频
 */

package com.douyin.tv

import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.leanback.widget.BrowseFrameLayout

class MainActivity : ComponentActivity() {

    private lateinit var webView: WebView
    private val videoUrls = mutableListOf<String>()
    private var currentVideoIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color(0xFF000000)
            ) {
                TVMainScreen(
                    onBrowseDouyin = { openDouyinWebView() },
                    onPlayVideo = { url -> playVideo(url) },
                    onBrowseHistory = { showHistory() },
                    videoCount = videoUrls.size
                )
            }
        }
    }

    /**
     * 打开抖音网页 WebView
     * 拦截视频 URL
     */
    private fun openDouyinWebView() {
        webView = WebView(this).apply {
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                mediaPlaybackRequiresUserGesture = false
                loadWithOverviewMode = true
                useWideViewPort = true
                cacheMode = android.webkit.WebSettings.LOAD_NO_CACHE
                userAgentString = "Mozilla/5.0 (Linux; Android 10; TV) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.6099.43 Safari/537.36"
            }

            // 拦截网络请求，获取视频 URL
            webViewClient = object : WebViewClient() {
                override fun shouldInterceptRequest(
                    view: WebView?,
                    request: WebResourceRequest?
                ): WebResourceResponse? {
                    request?.url?.toString()?.let { url ->
                        captureVideoUrl(url)
                    }
                    return super.shouldInterceptRequest(view, request)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    // 注入 JS，监听播放事件
                    injectVideoListener()
                }
            }

            webChromeClient = WebChromeClient()

            // 加载抖音网页版
            loadUrl("https://www.douyin.com")
        }

        // 显示 WebView
        WebDialog(this, webView).show()
    }

    /**
     * 捕获视频 URL
     */
    private fun captureVideoUrl(url: String) {
        // 检测抖音视频 API 请求
        when {
            url.contains("aweme/v1") || url.contains("video") ||
            url.contains(".mp4") || url.contains(".m3u8") -> {
                if (!videoUrls.contains(url)) {
                    videoUrls.add(url)
                    // 提取真实视频 URL（移除签名参数）
                    val cleanUrl = extractCleanVideoUrl(url)
                    if (cleanUrl.isNotEmpty() && !videoUrls.contains(cleanUrl)) {
                        videoUrls.add(cleanUrl)
                    }
                }
            }
        }
    }

    /**
     * 提取纯净视频 URL
     */
    private fun extractCleanVideoUrl(originalUrl: String): String {
        return try {
            val uri = android.net.Uri.parse(originalUrl)
            uri.buildUpon()
                .clearQuery()
                .fragment(null)
                .build()
                .toString()
        } catch (e: Exception) {
            originalUrl
        }
    }

    /**
     * 注入 JavaScript 监听播放事件
     */
    private fun injectVideoListener() {
        val js = """
            (function() {
                const originalFetch = window.fetch;
                window.fetch = function(...args) {
                    const promise = originalFetch.apply(this, args);
                    promise.then(response => {
                        const url = args[0]?.toString() || '';
                        if (url.includes('video') || url.includes('aweme')) {
                            window.AndroidInterface?.onVideoFound?.(url);
                        }
                    });
                    return promise;
                };

                // 监听 video 元素
                document.addEventListener('DOMContentLoaded', () => {
                    const videos = document.querySelectorAll('video');
                    videos.forEach(video => {
                        video.addEventListener('play', () => {
                            window.AndroidInterface?.onVideoPlaying?.(video.src);
                        });
                    });
                });
            })();
        """.trimIndent()

        webView.evaluateJavascript(js, null)
    }

    /**
     * 播放视频
     */
    private fun playVideo(url: String) {
        val intent = Intent(this, VideoPlayerActivity::class.java).apply {
            putExtra(VideoPlayerActivity.EXTRA_VIDEO_URL, url)
        }
        startActivity(intent)
    }

    /**
     * 显示历史记录
     */
    private fun showHistory() {
        // TODO: 显示历史播放记录
    }
}

# Add project specific ProGuard rules here.
-keep class com.douyin.tv.** { *; }

# Media3 / ExoPlayer
-keep class androidx.media3.** { *; }
-keep class androidx.media3.exoplayer.** { *; }
-keep interface androidx.media3.** { *; }

# WebView JavaScript Interface
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Compose
-keep class androidx.compose.** { *; }

# Kotlin
-keepnames class kotlinx.** { *; }

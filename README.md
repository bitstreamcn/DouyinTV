# Douyin TV Player - 抖音电视版播放器

一个使用 Kotlin + Media3 构建的 Android TV 抖音视频播放器应用。

## 功能特性

- 📺 电视大屏显示优化
- 🎬 支持播放抖音网页版视频
- 🔄 WebView 拦截自动获取视频 URL
- ⚡ Media3 (ExoPlayer) 原生播放，性能优秀
- 🎮 完整的遥控器支持（方向键、确认、媒体键）
- 📱 支持 Android 6.0 (API 23) 及以上

## 技术栈

- **语言**: Kotlin
- **UI 框架**: Jetpack Compose
- **媒体播放**: Android Media3 (ExoPlayer)
- **TV UI**: Leanback Library
- **网络**: OkHttp3

## 项目结构

```
DouyinTV/
├── app/
│   └── src/
│       └── main/
│           ├── java/com/douyin/tv/
│           │   ├── MainActivity.kt           # 主界面
│           │   ├── VideoPlayerActivity.kt    # 视频播放页面
│           │   ├── WebDialog.kt              # WebView 全屏对话框
│           │   ├── TVMainScreen.kt           # 主界面 UI
│           │   ├── DefaultRenderersFactoryProvider.kt
│           │   ├── DouyinTVApplication.kt    # Application 类
│           │   └── GlobalContext.kt          # 全局 Context
│           ├── res/
│           │   ├── drawable/
│           │   │   └── app_banner.xml        # TV Banner
│           │   ├── values/
│           │   │   ├── colors.xml
│           │   │   ├── strings.xml
│           │   │   └── themes.xml
│           │   └── layout/
│           ├── AndroidManifest.xml
│           └── proguard-rules.pro
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## 构建说明

### 1. 环境要求

- Android Studio Arctic Fox (2020.3.1) 或更高版本
- JDK 8 或更高版本
- Android SDK API 34
- Gradle 8.x

### 2. 构建步骤

```bash
# 进入项目目录
cd DouyinTV

# 使用 Gradle 构建
./gradlew assembleDebug

# 生成的 APK 位于
app/build/outputs/apk/debug/app-debug.apk
```

### 3. 安装到电视

```bash
# 使用 adb 安装（已连接电视）
adb install app/build/outputs/apk/debug/app-debug.apk

# 或者将 APK 复制到电视后手动安装
```

## 使用方法

1. **启动应用** - 打开抖音电视版
2. **浏览抖音** - 选择「浏览抖音」进入 WebView 界面
3. **自动捕获** - 浏览时应用会自动捕获视频 URL
4. **播放视频** - 选择视频后自动使用 Media3 播放

## 遥控器操作说明

| 按键 | 功能 |
|------|------|
| 方向键上/下/左/右 | 导航 |
| 确认键 | 选择/播放/暂停 |
| 返回键 | 返回上一级 |
| 播放键 | 播放视频 |
| 暂停键 | 暂停视频 |
| 快进 | 快进 10 秒 |
| 快退 | 快退 10 秒 |

## 原理说明

### 视频 URL 获取

使用 WebView 加载抖音网页版后，通过以下方式捕获视频 URL：

1. **网络请求拦截** - 拦截 `.mp4`/`.m3u8` 等视频请求
2. **JavaScript 注入** - 注入 JS 监听 `fetch` API 和 `video` 元素
3. **URL 清理** - 移除签名参数，提取纯 URL

### 播放

- 使用 Android 最新的 Media3 框架
- 支持多种视频格式 (MP4, HLS, DASH)
- 自动选择最佳解码器

## 注意事项

⚠️ **重要提示**：

1. 抖音 API 可能随时变化，如无法获取视频请检查：
   - WebView User-Agent
   - Cookie 设置
   - 签名算法更新

2. 网页版可能需要登录才能访问某些内容

3. 网络请求可能受抖音反爬虫机制限制

## 自定义配置

### 修改 User-Agent

在 `MainActivity.kt` 中：

```kotlin
userAgentString = "Mozilla/5.0 (Linux; Android 10; TV) AppleWebKit/537.36 ..."
```

### 更改视频 URL 匹配规则

在 `captureVideoUrl()` 方法中修改匹配条件。

### 解码器配置

修改 `DefaultRenderersFactoryProvider.kt`：

```kotlin
setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON)
```

## 问题排查

### 无法播放视频
1. 检查网络连接
2. 查看日志确认是否成功捕获视频 URL
3. 尝试直接用 MediaPlayer 播放验证 URL 有效性

### 卡顿问题
1. 降低视频分辨率
2. 检查电视硬件解码支持
3. 调整 Media3 缓冲配置

## License

MIT License

## 致谢

- Android Media3 Team
- JetBrains (Kotlin)
- 抖音网页版

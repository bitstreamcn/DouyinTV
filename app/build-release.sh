#!/bin/bash

# GitHub Release Notes Generator

cat << 'EOF' > release-notes.md
# DouyinTV - 抖音电视版播放器

📺 Android TV 抖音视频播放器，使用 Kotlin + Media3 构建

## 下载说明

### Debug APK (测试版)
- **版本**: 1.0
- **文件**: `app-debug.apk`
- **说明**: 用于测试，可直接安装

### Release APK (正式版)
- **版本**: 1.0
- **文件**: `app-release.apk`
- **说明**: 正式版本，已优化

## 安装方法

### 方法 1: 通过 USB 安装
```bash
adb install app-debug.apk
```

### 方法 2: 直接在电视上安装
1. 将 APK 文件复制到 U 盘或 SD 卡
2. 将 U 盘插入电视
3. 在电视文件管理器中找到 APK 文件
4. 点击安装

## 功能特性

✅ 浏览抖音网页版并自动捕获视频 URL
✅ Media3 原生播放器
✅ 完整的遥控器支持
✅ 支持 Android 6.0 (API 23) 及以上
✅ 大屏 optimized UI

## 使用方法

1. 打开「抖音电视版」应用
2. 选择「浏览抖音」
3. 在网页中浏览视频
4. 应用自动捕获并播放视频

## 遥控器操作

- **方向键**: 导航
- **确认键**: 选择/播放暂停
- **返回键**: 返回
- **播放/暂停键**: 控制播放

## 注意事项

⚠️ 本应用仅供学习交流使用
⚠️ 抖音 API 变化可能导致功能失效
⚠️ 请遵守抖音使用条款

---

**版本**: 1.0  
**构建日期**: $(date +%Y-%m-%d)  
**作者**: DouyinTV Team
EOF

echo "Release notes generated!"

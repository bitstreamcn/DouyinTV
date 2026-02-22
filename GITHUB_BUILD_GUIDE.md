# GitHub Actions 自动构建指南

## 🚀 一键构建 APK（推荐）

由于当前系统是 ARM64 架构，无法直接构建 Android APK。我们使用 GitHub Actions 自动构建。

---

## 📋 操作步骤

### 1️⃣ 推送代码到 GitHub

```bash
# 初始化 Git 仓库（如果还没有）
cd DouyinTV
git init

# 添加所有文件
git add .

# 提交
git commit -m "Initial commit: DouyinTV Player"

# 创建 GitHub 仓库后，推送代码
git branch -M main
git remote add origin https://github.com/你的用户名/DouyinTV.git
git push -u origin main
```

---

### 2️⃣ 自动触发构建

代码推送到 GitHub 后，构建会**自动开始**：

1. 访问你的 GitHub 仓库
2. 点击 **"Actions"** 标签
3. 看到 **"Build DouyinTV APK"** workflow 正在运行
4. 等待约 3-5 分钟构建完成

---

### 3️⃣ 下载 APK

构建完成后：

1. 在 Actions 页面，点击完成的工作流
2. 滚动到底部找到 **"Artifacts"**
3. 下载 **"DouyinTV-Debug-APK"** 或 **"DouyinTV-Release-APK"**
4. 解压后获得 `.apk` 文件

---

## 📱 安装到电视

### 方法 1: 使用 ADB
```bash
adb install app-debug.apk
```

### 方法 2: U 盘安装
1. 将 APK 复制到 U 盘
2. 插入电视
3. 用电视文件管理器找到 APK 并安装

---

## 🔧 手动触发构建

你也可以手动触发构建，无需推送新代码：

1. 访问仓库 **"Actions"** 页面
2. 点击 **"Build DouyinTV APK"**
3. 点击 **"Run workflow"**
4. 选择分支，点击 **"Run workflow"**

---

## 📂 构建文件位置

构建生成的文件位于：
- Debug: `app/build/outputs/apk/debug/app-debug.apk`
- Release: `app/build/outputs/apk/release/app-release.apk`

---

## ⚡ 常见问题

### Q: 构建失败怎么办？
A: 查看 Actions 日志，检查是否有错误信息。常见原因：
- Gradle 依赖问题
- 代码语法错误

### Q: 需要多长时间构建？
A: 通常 3-5 分钟

### Q: 可以自定义构建配置吗？
A: 可以，修改 `.github/workflows/build-apk.yml`

---

## 🎯 下一步

构建完成后：
1. 下载 APK
2. 安装到 Android 6.0 电视
3. 开始使用抖音电视版！

---

**有问题请查看 README.md 或提交 Issue**

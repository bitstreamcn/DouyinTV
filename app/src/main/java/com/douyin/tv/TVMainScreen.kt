/*
 * TVMainScreen - 电视主界面 UI
 */

package com.douyin.tv

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.leanback.widget HorizontalGridView

@Composable
fun TVMainScreen(
    onBrowseDouyin: () -> Unit,
    onPlayVideo: (String) -> Unit,
    onBrowseHistory: () -> Unit,
    videoCount: Int
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1a1a2e),
                        Color(0xFF16213e),
                        Color(0xFF0f3460)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // 标题区域
            TVHeader()

            Spacer(modifier = Modifier.height(40.dp))

            // 主操作区域
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TVMenuCard(
                    title = "浏览抖音",
                    description = "在网页中浏览并选择视频",
                    icon = Icons.Filled.VideoLibrary,
                    onClick = onBrowseDouyin
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    TVMenuCard(
                        title = "播放记录",
                        description = "历史播放视频 ($videoCount)",
                        icon = Icons.Filled.History,
                        onClick = onBrowseHistory,
                        modifier = Modifier.weight(1f)
                    )

                    TVMenuCard(
                        title = "直接播放",
                        description = "输入视频链接播放",
                        icon = Icons.Filled.PlayArrow,
                        onClick = { /* TODO */ },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 底部信息
            TVFooter()
        }
    }
}

@Composable
fun TVHeader() {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "抖音电视版",
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Douyin TV Player",
            fontSize = 18.sp,
            color = Color(0xFFe94560)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TVMenuCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .height(160.dp)
            .background(
                color = if (isFocused) Color(0xFFe94560) else Color(0xFF16213e),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .focusable()
            .onFocusChanged { isFocused = it.isFocused }
            .padding(24.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.width(20.dp))

            Column {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = description,
                    fontSize = 16.sp,
                    color = Color(0xFFa0a0a0)
                )
            }
        }
    }
}

@Composable
fun TVFooter() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "方向键导航 | 确认键选择",
            fontSize = 14.sp,
            color = Color(0xFF808080)
        )
        Text(
            text = "按返回键退出",
            fontSize = 14.sp,
            color = Color(0xFF808080)
        )
    }
}

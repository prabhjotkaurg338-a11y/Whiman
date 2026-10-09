package com.example.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.WearWhimMuted
import com.example.ui.theme.WearWhimPurple
import com.example.ui.theme.WearWhimText

@Composable
fun WearWhimHeader(
  title: String,
  subtitle: String,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(bottom = 16.dp)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(bottom = 8.dp)
    ) {
      Icon(
        imageVector = Icons.Default.AutoAwesome,
        contentDescription = "WearWhim Brand Star",
        tint = WearWhimPurple,
        modifier = Modifier.size(20.dp)
      )
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = "WearWhim AI ✦",
        fontSize = 19.sp,
        fontWeight = FontWeight.ExtraBold,
        color = WearWhimPurple,
        modifier = Modifier.testTag("brand_heading")
      )
    }

    Text(
      text = title,
      fontSize = 26.sp,
      fontWeight = FontWeight.Bold,
      color = WearWhimText,
      lineHeight = 32.sp
    )

    Spacer(modifier = Modifier.height(4.dp))

    Text(
      text = subtitle,
      fontSize = 13.sp,
      color = WearWhimMuted,
      lineHeight = 18.sp
    )
  }
}

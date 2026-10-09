package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.OutfitItem
import com.example.model.PaletteData
import com.example.ui.theme.WearWhimBorder
import com.example.ui.theme.WearWhimMuted
import com.example.ui.theme.WearWhimPale
import com.example.ui.theme.WearWhimPurple
import com.example.ui.theme.WearWhimSurface
import com.example.ui.theme.WearWhimText

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OutfitCard(
  outfit: OutfitItem,
  isSaved: Boolean,
  onToggleSave: () -> Unit,
  modifier: Modifier = Modifier
) {
  val saveBtnColor by animateColorAsState(
    targetValue = if (isSaved) WearWhimPale else WearWhimPurple,
    label = "save_btn_color"
  )
  val saveTextColor by animateColorAsState(
    targetValue = if (isSaved) WearWhimPurple else Color.White,
    label = "save_text_color"
  )

  Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = WearWhimSurface),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    modifier = modifier
      .fillMaxWidth()
      .border(1.dp, WearWhimBorder, RoundedCornerShape(20.dp))
      .testTag("outfit_card_${outfit.id}")
  ) {
    Column(modifier = Modifier.padding(18.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = outfit.name,
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            color = WearWhimText
          )
          Spacer(modifier = Modifier.height(2.dp))
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = outfit.type.label,
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold,
              color = WearWhimPurple
            )
            Text(
              text = " • ${outfit.occasion.emoji} ${outfit.occasion.label}",
              fontSize = 12.sp,
              color = WearWhimMuted
            )
          }
        }

        // Color swatches preview dots
        if (outfit.colorPalettePreview.isNotEmpty()) {
          Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            outfit.colorPalettePreview.forEach { hex ->
              Box(
                modifier = Modifier
                  .size(16.dp)
                  .clip(CircleShape)
                  .background(PaletteData.parseHex(hex))
                  .border(0.5.dp, Color.White, CircleShape)
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Pieces summary
      Text(
        text = outfit.pieces,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = WearWhimText,
        lineHeight = 20.sp
      )

      // Pieces tags list if available
      if (outfit.piecesList.isNotEmpty()) {
        Spacer(modifier = Modifier.height(8.dp))
        FlowRow(
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          outfit.piecesList.forEach { piece ->
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(WearWhimPale.copy(alpha = 0.6f))
                .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Text(
                text = piece,
                fontSize = 11.sp,
                color = WearWhimPurple,
                fontWeight = FontWeight.Medium
              )
            }
          }
        }
      }

      // Why it suits you note
      if (outfit.suitabilityReason.isNotBlank()) {
        Spacer(modifier = Modifier.height(10.dp))
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(WearWhimPale.copy(alpha = 0.4f))
            .padding(10.dp)
        ) {
          Row(verticalAlignment = Alignment.Top) {
            Icon(
              imageVector = Icons.Default.Info,
              contentDescription = "Suitability details",
              tint = WearWhimPurple,
              modifier = Modifier
                .size(16.dp)
                .padding(top = 2.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = outfit.suitabilityReason,
              fontSize = 12.sp,
              color = WearWhimText.copy(alpha = 0.85f),
              lineHeight = 16.sp
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Action Button
      Button(
        onClick = onToggleSave,
        colors = ButtonDefaults.buttonColors(
          containerColor = saveBtnColor,
          contentColor = saveTextColor
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("save_outfit_button_${outfit.id}")
      ) {
        Icon(
          imageVector = if (isSaved) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
          contentDescription = if (isSaved) "Saved look" else "Save look",
          modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = if (isSaved) "Saved to Collection ✓" else "♡ Save look",
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold
        )
      }
    }
  }
}

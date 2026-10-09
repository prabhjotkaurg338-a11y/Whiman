package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BodyShape
import com.example.model.Occasion
import com.example.model.PaletteData
import com.example.ui.WearWhimUiState
import com.example.ui.WearWhimViewModel
import com.example.ui.components.OutfitCard
import com.example.ui.components.WearWhimHeader
import com.example.ui.theme.WearWhimBackground
import com.example.ui.theme.WearWhimBorder
import com.example.ui.theme.WearWhimMuted
import com.example.ui.theme.WearWhimPale
import com.example.ui.theme.WearWhimPurple
import com.example.ui.theme.WearWhimPurpleDark
import com.example.ui.theme.WearWhimRose
import com.example.ui.theme.WearWhimSurface
import com.example.ui.theme.WearWhimTeal
import com.example.ui.theme.WearWhimText

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SuitMeScreen(
  uiState: WearWhimUiState,
  viewModel: WearWhimViewModel,
  modifier: Modifier = Modifier
) {
  val result = uiState.suitMeResult

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(WearWhimBackground)
      .padding(horizontal = 18.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(8.dp))
      WearWhimHeader(
        title = "Clothes that suit you.",
        subtitle = "Personalized AI stylist recommendations tailored to your silhouette & palette."
      )
    }

    // 1. Step: Body Shape Selector
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = WearWhimSurface),
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, WearWhimBorder, RoundedCornerShape(20.dp))
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(WearWhimPurple),
              contentAlignment = Alignment.Center
            ) {
              Text("1", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Select your body silhouette",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = WearWhimText
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Body shapes carousel
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            BodyShape.entries.forEach { shape ->
              val isSelected = uiState.suitMeBodyShape == shape
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(16.dp))
                  .background(if (isSelected) WearWhimPurple else WearWhimPale.copy(alpha = 0.5f))
                  .border(
                    1.dp,
                    if (isSelected) WearWhimPurple else WearWhimBorder,
                    RoundedCornerShape(16.dp)
                  )
                  .clickable { viewModel.updateSuitMeCriteria(bodyShape = shape) }
                  .padding(horizontal = 14.dp, vertical = 10.dp)
                  .testTag("shape_chip_${shape.name.lowercase()}")
              ) {
                Text(
                  text = shape.label,
                  fontSize = 13.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                  color = if (isSelected) Color.White else WearWhimText
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = uiState.suitMeBodyShape.description,
            fontSize = 12.sp,
            color = WearWhimMuted,
            lineHeight = 16.sp
          )
        }
      }
    }

    // 2. Step: Occasion Selector
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = WearWhimSurface),
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, WearWhimBorder, RoundedCornerShape(20.dp))
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(WearWhimPurple),
              contentAlignment = Alignment.Center
            ) {
              Text("2", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "What is the occasion?",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = WearWhimText
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier
              .fillMaxWidth()
              .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Occasion.entries.forEach { occ ->
              val isSelected = uiState.suitMeOccasion == occ
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(16.dp))
                  .background(if (isSelected) WearWhimPurple else WearWhimPale.copy(alpha = 0.5f))
                  .border(
                    1.dp,
                    if (isSelected) WearWhimPurple else WearWhimBorder,
                    RoundedCornerShape(16.dp)
                  )
                  .clickable { viewModel.updateSuitMeCriteria(occasion = occ) }
                  .padding(horizontal = 12.dp, vertical = 9.dp)
                  .testTag("occasion_chip_${occ.name.lowercase()}")
              ) {
                Text(
                  text = "${occ.emoji} ${occ.label}",
                  fontSize = 13.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                  color = if (isSelected) Color.White else WearWhimText
                )
              }
            }
          }
        }
      }
    }

    // 3. Step: Palette Selector
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = WearWhimSurface),
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, WearWhimBorder, RoundedCornerShape(20.dp))
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(WearWhimPurple),
              contentAlignment = Alignment.Center
            ) {
              Text("3", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Your color season",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = WearWhimText
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier
              .fillMaxWidth()
              .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            PaletteData.defaultPalettes.forEach { p ->
              val isSelected = uiState.suitMePalette == p.name
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(16.dp))
                  .background(if (isSelected) WearWhimPurple else WearWhimPale.copy(alpha = 0.5f))
                  .border(
                    1.dp,
                    if (isSelected) WearWhimPurple else WearWhimBorder,
                    RoundedCornerShape(16.dp)
                  )
                  .clickable { viewModel.updateSuitMeCriteria(palette = p.name) }
                  .padding(horizontal = 14.dp, vertical = 9.dp)
                  .testTag("palette_chip_${p.name.lowercase().replace(" ", "_")}")
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Box(
                    modifier = Modifier
                      .size(10.dp)
                      .clip(CircleShape)
                      .background(PaletteData.parseHex(p.colors.first()))
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = p.name,
                    fontSize = 13.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected) Color.White else WearWhimText
                  )
                }
              }
            }
          }
        }
      }
    }

    // AI Suitability Prescription Card
    if (result != null) {
      item {
        Card(
          shape = RoundedCornerShape(22.dp),
          colors = CardDefaults.cardColors(containerColor = WearWhimPale),
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, WearWhimPurple.copy(alpha = 0.3f), RoundedCornerShape(22.dp))
            .testTag("ai_suitability_result_card")
        ) {
          Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = null,
                tint = WearWhimPurple,
                modifier = Modifier.size(20.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "AI Suitability Analysis",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = WearWhimPurpleDark
              )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
              text = result.keyRule,
              fontSize = 14.sp,
              fontWeight = FontWeight.SemiBold,
              color = WearWhimText,
              lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Embraced silhouettes
            Text(
              text = "Silhouettes that flatter you best:",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = WearWhimTeal
            )
            Spacer(modifier = Modifier.height(6.dp))
            result.silhouettesToEmbrace.forEach { item ->
              Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(vertical = 2.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = null,
                  tint = WearWhimTeal,
                  modifier = Modifier.size(16.dp).padding(top = 2.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = item,
                  fontSize = 13.sp,
                  color = WearWhimText,
                  lineHeight = 18.sp
                )
              }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Silhouettes to avoid
            Text(
              text = "Silhouettes to be mindful of:",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = WearWhimRose
            )
            Spacer(modifier = Modifier.height(6.dp))
            result.silhouettesToAvoid.forEach { item ->
              Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(vertical = 2.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.Cancel,
                  contentDescription = null,
                  tint = WearWhimRose,
                  modifier = Modifier.size(16.dp).padding(top = 2.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = item,
                  fontSize = 13.sp,
                  color = WearWhimText.copy(alpha = 0.85f),
                  lineHeight = 18.sp
                )
              }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Color harmony advice
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White.copy(alpha = 0.7f))
                .padding(10.dp)
            ) {
              Text(
                text = "🎨 Palette advice: ${result.colorPairingTip}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = WearWhimPurpleDark,
                lineHeight = 16.sp
              )
            }
          }
        }
      }

      // Recommended Curated Outfits that suit you
      item {
        Text(
          text = "Recommended Outfits for You",
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          color = WearWhimText
        )
      }

      items(result.recommendedOutfits, key = { "suit_${it.id}" }) { outfit ->
        OutfitCard(
          outfit = outfit,
          isSaved = uiState.savedOutfitIds.contains(outfit.id),
          onToggleSave = { viewModel.toggleSavedOutfit(outfit.id) }
        )
      }
    }

    item {
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

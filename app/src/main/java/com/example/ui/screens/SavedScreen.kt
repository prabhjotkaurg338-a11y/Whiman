package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppNavScreen
import com.example.ui.WearWhimUiState
import com.example.ui.WearWhimViewModel
import com.example.ui.components.OutfitCard
import com.example.ui.components.WearWhimHeader
import com.example.ui.theme.WearWhimBackground
import com.example.ui.theme.WearWhimMuted
import com.example.ui.theme.WearWhimPurple
import com.example.ui.theme.WearWhimText

@Composable
fun SavedScreen(
  uiState: WearWhimUiState,
  viewModel: WearWhimViewModel,
  modifier: Modifier = Modifier
) {
  val savedOutfits = uiState.outfits.filter { uiState.savedOutfitIds.contains(it.id) }

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
        title = "Your favourite looks.",
        subtitle = "Outfits and inspirations you have saved to your personal lookbook."
      )
    }

    if (savedOutfits.isEmpty()) {
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 60.dp),
          contentAlignment = Alignment.Center
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.testTag("empty_saved_state")
          ) {
            Icon(
              imageVector = Icons.Default.FavoriteBorder,
              contentDescription = null,
              tint = WearWhimMuted,
              modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(
              text = "No saved outfits yet.",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = WearWhimText
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "Browse looks on Discover or Suit You and tap Save Look to collect them here.",
              fontSize = 13.sp,
              color = WearWhimMuted,
              textAlign = androidx.compose.ui.text.style.TextAlign.Center,
              modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
              onClick = { viewModel.setScreen(AppNavScreen.DISCOVER) },
              colors = ButtonDefaults.buttonColors(containerColor = WearWhimPurple),
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.testTag("explore_looks_button")
            ) {
              Text("Explore Looks", fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    } else {
      items(savedOutfits, key = { "saved_${it.id}" }) { outfit ->
        OutfitCard(
          outfit = outfit,
          isSaved = true,
          onToggleSave = { viewModel.toggleSavedOutfit(outfit.id) }
        )
      }
    }

    item {
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

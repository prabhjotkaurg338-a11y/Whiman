package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.StyleType
import com.example.ui.AppNavScreen
import com.example.ui.WearWhimUiState
import com.example.ui.WearWhimViewModel
import com.example.ui.components.OutfitCard
import com.example.ui.components.WearWhimHeader
import com.example.ui.theme.WearWhimBackground
import com.example.ui.theme.WearWhimBorder
import com.example.ui.theme.WearWhimMuted
import com.example.ui.theme.WearWhimPale
import com.example.ui.theme.WearWhimPurple
import com.example.ui.theme.WearWhimSurface
import com.example.ui.theme.WearWhimText

@Composable
fun DiscoverScreen(
  uiState: WearWhimUiState,
  viewModel: WearWhimViewModel,
  modifier: Modifier = Modifier
) {
  val filteredOutfits = uiState.outfits.filter { outfit ->
    val matchesStyle = uiState.selectedStyle == null || outfit.type == uiState.selectedStyle
    val matchesSearch = uiState.searchQuery.isBlank() ||
      outfit.name.contains(uiState.searchQuery, ignoreCase = true) ||
      outfit.pieces.contains(uiState.searchQuery, ignoreCase = true) ||
      outfit.type.label.contains(uiState.searchQuery, ignoreCase = true)
    matchesStyle && matchesSearch
  }

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
        title = "Find your kind of style.",
        subtitle = "Fashion inspiration made personal for you."
      )
    }

    // Hero Banner Card with visual asset
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(24.dp))
          .background(WearWhimPale)
          .border(1.dp, WearWhimBorder, RoundedCornerShape(24.dp))
          .testTag("hero_banner_card")
      ) {
        Column {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(150.dp)
          ) {
            Image(
              painter = painterResource(id = R.drawable.hero_fashion_style_1791545197496),
              contentDescription = "Fashion aesthetics wardrobe hero",
              contentScale = ContentScale.Crop,
              modifier = Modifier.fillMaxSize()
            )
            // Gradient scrim for smooth transition
            Box(
              modifier = Modifier
                .fillMaxSize()
                .background(
                  Brush.verticalGradient(
                    colors = listOf(Color.Transparent, WearWhimPale.copy(alpha = 0.95f)),
                    startY = 180f
                  )
                )
            )
          }

          Column(modifier = Modifier.padding(20.dp)) {
            Text(
              text = "A little whim.\nA whole new look.",
              fontSize = 24.sp,
              fontWeight = FontWeight.ExtraBold,
              color = WearWhimText,
              lineHeight = 30.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "Explore outfits, color matching, and wardrobe pieces tailored to what suits you best.",
              fontSize = 13.sp,
              color = WearWhimMuted,
              lineHeight = 18.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
              horizontalArrangement = Arrangement.spacedBy(10.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Button(
                onClick = { viewModel.setScreen(AppNavScreen.SUIT_ME) },
                colors = ButtonDefaults.buttonColors(containerColor = WearWhimPurple),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                  .weight(1f)
                  .height(44.dp)
                  .testTag("hero_suit_me_button")
              ) {
                Icon(
                  imageVector = Icons.Default.AutoAwesome,
                  contentDescription = null,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "What Suits Me",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold
                )
              }

              Button(
                onClick = { viewModel.setScreen(AppNavScreen.COLOURS) },
                colors = ButtonDefaults.buttonColors(
                  containerColor = WearWhimSurface,
                  contentColor = WearWhimPurple
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                  .weight(1f)
                  .height(44.dp)
                  .border(1.dp, WearWhimPurple.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                  .testTag("hero_explore_colours_button")
              ) {
                Icon(
                  imageVector = Icons.Default.Palette,
                  contentDescription = null,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "Colour Match",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }
        }
      }
    }

    // Search bar
    item {
      OutlinedTextField(
        value = uiState.searchQuery,
        onValueChange = { viewModel.setSearchQuery(it) },
        placeholder = { Text("Search styles, pieces, or keywords...", color = WearWhimMuted, fontSize = 14.sp) },
        leadingIcon = {
          Icon(Icons.Default.Search, contentDescription = "Search", tint = WearWhimPurple)
        },
        trailingIcon = {
          if (uiState.searchQuery.isNotEmpty()) {
            IconButton(onClick = { viewModel.setSearchQuery("") }) {
              Icon(Icons.Default.Clear, contentDescription = "Clear search", tint = WearWhimMuted)
            }
          }
        },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = WearWhimSurface,
          unfocusedContainerColor = WearWhimSurface,
          focusedBorderColor = WearWhimPurple,
          unfocusedBorderColor = WearWhimBorder
        ),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("search_text_field")
      )
    }

    // Style selector chips
    item {
      Column {
        Text(
          text = "Choose your style",
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          color = WearWhimText
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // "All" chip
          val isAllSelected = uiState.selectedStyle == null
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(20.dp))
              .background(if (isAllSelected) WearWhimPurple else WearWhimSurface)
              .border(
                1.dp,
                if (isAllSelected) WearWhimPurple else WearWhimBorder,
                RoundedCornerShape(20.dp)
              )
              .clickable { viewModel.setStyleFilter(null) }
              .padding(horizontal = 16.dp, vertical = 10.dp)
              .testTag("style_chip_all")
          ) {
            Text(
              text = "All Styles",
              fontSize = 13.sp,
              fontWeight = if (isAllSelected) FontWeight.Bold else FontWeight.Medium,
              color = if (isAllSelected) Color.White else WearWhimText
            )
          }

          StyleType.entries.forEach { style ->
            val isSelected = uiState.selectedStyle == style
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(if (isSelected) WearWhimPurple else WearWhimSurface)
                .border(
                  1.dp,
                  if (isSelected) WearWhimPurple else WearWhimBorder,
                  RoundedCornerShape(20.dp)
                )
                .clickable { viewModel.setStyleFilter(style) }
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .testTag("style_chip_${style.name.lowercase()}")
            ) {
              Text(
                text = style.label,
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color.White else WearWhimText
              )
            }
          }
        }
      }
    }

    // Outfit inspiration section header
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Outfit inspiration",
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          color = WearWhimText
        )
        Text(
          text = "${filteredOutfits.size} looks",
          fontSize = 13.sp,
          fontWeight = FontWeight.SemiBold,
          color = WearWhimPurple
        )
      }
    }

    // Outfits list
    if (filteredOutfits.isEmpty()) {
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
          contentAlignment = Alignment.Center
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
              text = "No outfits found matching this criteria.",
              fontSize = 14.sp,
              color = WearWhimMuted
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
              onClick = {
                viewModel.setStyleFilter(null)
                viewModel.setSearchQuery("")
              },
              colors = ButtonDefaults.buttonColors(containerColor = WearWhimPurple),
              shape = RoundedCornerShape(12.dp)
            ) {
              Text("Clear Filters")
            }
          }
        }
      }
    } else {
      items(filteredOutfits, key = { it.id }) { outfit ->
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

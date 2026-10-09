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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ClothingCategory
import com.example.model.PaletteData
import com.example.model.StyleType
import com.example.ui.WearWhimUiState
import com.example.ui.WearWhimViewModel
import com.example.ui.components.WearWhimHeader
import com.example.ui.theme.WearWhimBackground
import com.example.ui.theme.WearWhimBorder
import com.example.ui.theme.WearWhimMuted
import com.example.ui.theme.WearWhimPale
import com.example.ui.theme.WearWhimPurple
import com.example.ui.theme.WearWhimPurpleDark
import com.example.ui.theme.WearWhimSurface
import com.example.ui.theme.WearWhimText

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WardrobeScreen(
  uiState: WearWhimUiState,
  viewModel: WearWhimViewModel,
  modifier: Modifier = Modifier
) {
  var quickClothingName by remember { mutableStateOf("") }
  var showAddDialog by remember { mutableStateOf(false) }

  // Dialog specific state
  var dialogItemName by remember { mutableStateOf("") }
  var dialogCategory by remember { mutableStateOf(ClothingCategory.TOPS) }
  var dialogStyle by remember { mutableStateOf(StyleType.CASUAL) }
  var dialogColorHex by remember { mutableStateOf("#7650A4") }
  var dialogColorName by remember { mutableStateOf("Lilac Purple") }

  val filteredItems = uiState.wardrobeItems.filter { item ->
    uiState.wardrobeFilterCategory == null || item.category == uiState.wardrobeFilterCategory
  }

  val paletteSwatches = listOf(
    Pair("#7650A4", "Purple"),
    Pair("#292333", "Black/Plum"),
    Pair("#EFE6F8", "Ecru"),
    Pair("#A9C5D9", "Slate Blue"),
    Pair("#C87951", "Terracotta"),
    Pair("#687B4A", "Olive"),
    Pair("#D7265E", "Magenta")
  )

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
        title = "Your digital wardrobe.",
        subtitle = "Catalog pieces and craft cohesive outfits from your closet."
      )
    }

    // Quick Add or Open Detailed Add
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = WearWhimSurface),
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, WearWhimBorder, RoundedCornerShape(20.dp))
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Quick add to closet",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = WearWhimText
          )
          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = quickClothingName,
            onValueChange = { quickClothingName = it },
            placeholder = { Text("e.g., Cream ribbed turtleneck", color = WearWhimMuted, fontSize = 14.sp) },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedContainerColor = WearWhimSurface,
              unfocusedContainerColor = WearWhimSurface,
              focusedBorderColor = WearWhimPurple,
              unfocusedBorderColor = WearWhimBorder
            ),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("add_clothing_input")
          )

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Button(
              onClick = {
                if (quickClothingName.isNotBlank()) {
                  viewModel.addWardrobeItem(
                    name = quickClothingName,
                    category = ClothingCategory.TOPS,
                    colorHex = "#7650A4",
                    colorName = "Soft Lavender",
                    styleType = StyleType.CASUAL
                  )
                  quickClothingName = ""
                }
              },
              enabled = quickClothingName.isNotBlank(),
              colors = ButtonDefaults.buttonColors(containerColor = WearWhimPurple),
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier
                .weight(1f)
                .height(44.dp)
                .testTag("add_clothing_button")
            ) {
              Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Add item", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
              onClick = { showAddDialog = true },
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier
                .weight(1f)
                .height(44.dp)
                .testTag("detailed_add_clothing_button")
            ) {
              Text("More options", fontSize = 13.sp, color = WearWhimPurple, fontWeight = FontWeight.SemiBold)
            }
          }
        }
      }
    }

    // AI Mix & Match Button
    item {
      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = WearWhimPale),
        modifier = Modifier
          .fillMaxWidth()
          .clickable { viewModel.generateMixAndMatch() }
          .testTag("mix_match_button")
      ) {
        Row(
          modifier = Modifier.padding(16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(40.dp)
              .clip(CircleShape)
              .background(WearWhimPurple),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Shuffle,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(20.dp)
            )
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "AI Closet Mix & Match ✦",
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = WearWhimPurpleDark
            )
            Text(
              text = "Combine your clothes into a coordinated look",
              fontSize = 12.sp,
              color = WearWhimText.copy(alpha = 0.8f)
            )
          }
        }
      }
    }

    // Show Mix & Match result if available
    if (uiState.mixMatchSuggestion != null && uiState.mixMatchSuggestion.isNotEmpty()) {
      item {
        Card(
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = WearWhimSurface),
          modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, WearWhimPurple, RoundedCornerShape(18.dp))
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween,
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(
                text = "Assembled Outfit Combo",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = WearWhimPurple
              )
              Text(
                text = "${uiState.mixMatchSuggestion.size} pieces",
                fontSize = 12.sp,
                color = WearWhimMuted
              )
            }
            Spacer(modifier = Modifier.height(10.dp))
            uiState.mixMatchSuggestion.forEach { item ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(text = item.category.iconEmoji, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = item.name,
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Medium,
                  color = WearWhimText
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                  modifier = Modifier
                    .size(14.dp)
                    .clip(CircleShape)
                    .background(PaletteData.parseHex(item.colorHex))
                )
              }
            }
          }
        }
      }
    }

    // Category filter chips
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        val isAllSelected = uiState.wardrobeFilterCategory == null
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isAllSelected) WearWhimPurple else WearWhimSurface)
            .border(1.dp, if (isAllSelected) WearWhimPurple else WearWhimBorder, RoundedCornerShape(16.dp))
            .clickable { viewModel.setWardrobeFilter(null) }
            .padding(horizontal = 14.dp, vertical = 8.dp)
            .testTag("wardrobe_filter_all")
        ) {
          Text(
            text = "All (${uiState.wardrobeItems.size})",
            fontSize = 12.sp,
            fontWeight = if (isAllSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isAllSelected) Color.White else WearWhimText
          )
        }

        ClothingCategory.entries.forEach { cat ->
          val isSelected = uiState.wardrobeFilterCategory == cat
          val count = uiState.wardrobeItems.count { it.category == cat }
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(16.dp))
              .background(if (isSelected) WearWhimPurple else WearWhimSurface)
              .border(1.dp, if (isSelected) WearWhimPurple else WearWhimBorder, RoundedCornerShape(16.dp))
              .clickable { viewModel.setWardrobeFilter(cat) }
              .padding(horizontal = 14.dp, vertical = 8.dp)
              .testTag("wardrobe_filter_${cat.name.lowercase()}")
          ) {
            Text(
              text = "${cat.iconEmoji} ${cat.label} ($count)",
              fontSize = 12.sp,
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
              color = if (isSelected) Color.White else WearWhimText
            )
          }
        }
      }
    }

    // Wardrobe Items List
    if (filteredItems.isEmpty()) {
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp),
          contentAlignment = Alignment.Center
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
              text = "No clothing items in this category yet.",
              fontSize = 14.sp,
              color = WearWhimMuted
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Add some pieces above to start organizing!",
              fontSize = 12.sp,
              color = WearWhimMuted
            )
          }
        }
      }
    } else {
      items(filteredItems, key = { it.id }) { item ->
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = WearWhimSurface),
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, WearWhimBorder, RoundedCornerShape(16.dp))
            .testTag("wardrobe_item_${item.id}")
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(PaletteData.parseHex(item.colorHex).copy(alpha = 0.2f)),
              contentAlignment = Alignment.Center
            ) {
              Text(text = item.category.iconEmoji, fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = item.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = WearWhimText
              )
              Spacer(modifier = Modifier.height(2.dp))
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(PaletteData.parseHex(item.colorHex))
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "${item.category.label} • ${item.styleType.label}",
                  fontSize = 12.sp,
                  color = WearWhimMuted
                )
              }
            }

            IconButton(
              onClick = { viewModel.removeWardrobeItem(item.id) },
              modifier = Modifier.testTag("remove_wardrobe_item_${item.id}")
            ) {
              Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Remove clothing item",
                tint = WearWhimMuted.copy(alpha = 0.7f),
                modifier = Modifier.size(20.dp)
              )
            }
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(24.dp))
    }
  }

  // Detailed Add Item Dialog
  if (showAddDialog) {
    AlertDialog(
      onDismissRequest = { showAddDialog = false },
      title = {
        Text("Add Clothing Item", fontWeight = FontWeight.Bold, color = WearWhimText)
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
          OutlinedTextField(
            value = dialogItemName,
            onValueChange = { dialogItemName = it },
            label = { Text("Item Name") },
            placeholder = { Text("e.g. Silk floral midi skirt") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )

          // Category Picker
          Text("Category", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = WearWhimMuted)
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            ClothingCategory.entries.forEach { cat ->
              val isSel = dialogCategory == cat
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(12.dp))
                  .background(if (isSel) WearWhimPurple else WearWhimPale)
                  .clickable { dialogCategory = cat }
                  .padding(horizontal = 10.dp, vertical = 6.dp)
              ) {
                Text(
                  text = "${cat.iconEmoji} ${cat.label}",
                  fontSize = 11.sp,
                  color = if (isSel) Color.White else WearWhimText
                )
              }
            }
          }

          // Style Picker
          Text("Style Vibe", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = WearWhimMuted)
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            StyleType.entries.take(5).forEach { st ->
              val isSel = dialogStyle == st
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(12.dp))
                  .background(if (isSel) WearWhimPurple else WearWhimPale)
                  .clickable { dialogStyle = st }
                  .padding(horizontal = 10.dp, vertical = 6.dp)
              ) {
                Text(
                  text = st.label,
                  fontSize = 11.sp,
                  color = if (isSel) Color.White else WearWhimText
                )
              }
            }
          }

          // Color swatch picker
          Text("Color Shade", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = WearWhimMuted)
          Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            paletteSwatches.forEach { (hex, name) ->
              val isSel = dialogColorHex == hex
              Box(
                modifier = Modifier
                  .size(32.dp)
                  .clip(CircleShape)
                  .background(PaletteData.parseHex(hex))
                  .border(if (isSel) 2.5.dp else 1.dp, if (isSel) WearWhimPurpleDark else WearWhimBorder, CircleShape)
                  .clickable {
                    dialogColorHex = hex
                    dialogColorName = name
                  }
              )
            }
          }
        }
      },
      confirmButton = {
        Button(
          onClick = {
            if (dialogItemName.isNotBlank()) {
              viewModel.addWardrobeItem(
                name = dialogItemName,
                category = dialogCategory,
                colorHex = dialogColorHex,
                colorName = dialogColorName,
                styleType = dialogStyle
              )
              dialogItemName = ""
              showAddDialog = false
            }
          },
          enabled = dialogItemName.isNotBlank(),
          colors = ButtonDefaults.buttonColors(containerColor = WearWhimPurple)
        ) {
          Text("Add to Closet")
        }
      },
      dismissButton = {
        TextButton(onClick = { showAddDialog = false }) {
          Text("Cancel", color = WearWhimMuted)
        }
      }
    )
  }
}

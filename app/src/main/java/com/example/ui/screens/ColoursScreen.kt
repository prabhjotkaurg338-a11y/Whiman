package com.example.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.ColorPalette
import com.example.model.PaletteData
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
fun ColoursScreen(
  uiState: WearWhimUiState,
  viewModel: WearWhimViewModel,
  modifier: Modifier = Modifier
) {
  val photoPickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickVisualMedia()
  ) { uri ->
    if (uri != null) {
      viewModel.setUserPhotoUri(uri)
    }
  }

  var selectedSwatchHex by remember { mutableStateOf<String?>(null) }

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
        title = "Your colour edit.",
        subtitle = "Explore shades, undertones and flattering colour combinations."
      )
    }

    // Photo selection section
    item {
      Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = WearWhimSurface),
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, WearWhimBorder, RoundedCornerShape(22.dp))
      ) {
        Column(
          modifier = Modifier.padding(18.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          if (uiState.userPhotoUri != null) {
            Box(
              modifier = Modifier
                .width(180.dp)
                .height(230.dp)
                .clip(RoundedCornerShape(20.dp))
                .border(2.dp, WearWhimPurple, RoundedCornerShape(20.dp))
            ) {
              AsyncImage(
                model = uiState.userPhotoUri,
                contentDescription = "Your selected photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
              )
              Box(
                modifier = Modifier
                  .align(Alignment.BottomCenter)
                  .fillMaxWidth()
                  .background(Color.Black.copy(alpha = 0.5f))
                  .padding(vertical = 4.dp),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = "Photo Selected",
                  color = Color.White,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.SemiBold
                )
              }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
              Button(
                onClick = {
                  photoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                  )
                },
                colors = ButtonDefaults.buttonColors(containerColor = WearWhimPurple),
                shape = RoundedCornerShape(12.dp)
              ) {
                Text("Change photo", fontSize = 13.sp)
              }

              OutlinedButton(
                onClick = { viewModel.setUserPhotoUri(null) },
                shape = RoundedCornerShape(12.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.Delete,
                  contentDescription = "Remove photo",
                  tint = WearWhimMuted,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Remove", fontSize = 13.sp, color = WearWhimMuted)
              }
            }
          } else {
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              modifier = Modifier.padding(vertical = 8.dp)
            ) {
              Icon(
                imageVector = Icons.Default.AddPhotoAlternate,
                contentDescription = null,
                tint = WearWhimPurple,
                modifier = Modifier.size(36.dp)
              )
              Spacer(modifier = Modifier.height(8.dp))
              Text(
                text = "Add a photo for colour matching",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = WearWhimText
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "Compare your portrait against flattering palettes",
                fontSize = 12.sp,
                color = WearWhimMuted
              )
              Spacer(modifier = Modifier.height(14.dp))
              Button(
                onClick = {
                  photoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                  )
                },
                colors = ButtonDefaults.buttonColors(containerColor = WearWhimPurple),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                  .fillMaxWidth()
                  .height(46.dp)
                  .testTag("choose_photo_button")
              ) {
                Text(
                  text = "Choose your photo",
                  fontWeight = FontWeight.Bold,
                  fontSize = 14.sp
                )
              }
            }
          }
        }
      }
    }

    // Palette Selection Section
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Select a palette",
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          color = WearWhimText
        )
        Text(
          text = "Active: ${uiState.selectedPalette}",
          fontSize = 13.sp,
          fontWeight = FontWeight.SemiBold,
          color = WearWhimPurple
        )
      }
    }

    // Palette Cards
    items(PaletteData.defaultPalettes, key = { it.name }) { palette ->
      val isSelected = uiState.selectedPalette == palette.name

      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
          containerColor = if (isSelected) WearWhimPale.copy(alpha = 0.5f) else WearWhimSurface
        ),
        modifier = Modifier
          .fillMaxWidth()
          .border(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) WearWhimPurple else WearWhimBorder,
            shape = RoundedCornerShape(20.dp)
          )
          .clickable { viewModel.selectPalette(palette.name) }
          .testTag("palette_card_${palette.name.lowercase().replace(" ", "_")}")
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = palette.name,
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold,
                color = WearWhimText
              )
              Text(
                text = palette.subtitle,
                fontSize = 12.sp,
                color = WearWhimMuted
              )
            }

            if (isSelected) {
              Box(
                modifier = Modifier
                  .size(24.dp)
                  .clip(CircleShape)
                  .background(WearWhimPurple),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Check,
                  contentDescription = "Selected",
                  tint = Color.White,
                  modifier = Modifier.size(16.dp)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Swatches row
          Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            palette.colors.forEach { hex ->
              val isSwatchActive = selectedSwatchHex == hex
              Box(
                modifier = Modifier
                  .size(48.dp)
                  .clip(RoundedCornerShape(14.dp))
                  .background(PaletteData.parseHex(hex))
                  .border(
                    width = if (isSwatchActive) 2.5.dp else 1.dp,
                    color = if (isSwatchActive) WearWhimPurpleDark else WearWhimBorder,
                    shape = RoundedCornerShape(14.dp)
                  )
                  .clickable {
                    selectedSwatchHex = if (selectedSwatchHex == hex) null else hex
                  }
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          Text(
            text = palette.contrastAdvice,
            fontSize = 12.sp,
            color = WearWhimText.copy(alpha = 0.85f),
            lineHeight = 16.sp
          )

          // If selected, display clothing suggestions for this palette
          AnimatedVisibility(visible = isSelected) {
            Column(modifier = Modifier.padding(top = 12.dp)) {
              Text(
                text = "Flattering clothing pieces in this edit:",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = WearWhimPurpleDark
              )
              Spacer(modifier = Modifier.height(6.dp))
              FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                palette.recommendedClothes.forEach { piece ->
                  Box(
                    modifier = Modifier
                      .clip(RoundedCornerShape(8.dp))
                      .background(WearWhimSurface)
                      .border(1.dp, WearWhimBorder, RoundedCornerShape(8.dp))
                      .padding(horizontal = 8.dp, vertical = 4.dp)
                  ) {
                    Text(
                      text = piece,
                      fontSize = 11.sp,
                      color = WearWhimText,
                      fontWeight = FontWeight.Medium
                    )
                  }
                }
              }
            }
          }
        }
      }
    }

    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(14.dp))
          .background(WearWhimPale.copy(alpha = 0.4f))
          .padding(14.dp)
      ) {
        Text(
          text = "Note: These are curated seasonal palettes designed to inspire personal color harmony.",
          fontSize = 12.sp,
          color = WearWhimMuted,
          lineHeight = 16.sp
        )
      }
    }

    item {
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

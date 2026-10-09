package com.example.ui.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BodyShape
import com.example.model.Occasion
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
fun ProfileScreen(
  uiState: WearWhimUiState,
  viewModel: WearWhimViewModel,
  modifier: Modifier = Modifier
) {
  var name by remember { mutableStateOf(uiState.userProfile.name) }
  var selectedStyle by remember { mutableStateOf(uiState.userProfile.preferredStyle) }
  var selectedBodyShape by remember { mutableStateOf(uiState.userProfile.bodyShape) }
  var selectedPalette by remember { mutableStateOf(uiState.userProfile.preferredPalette) }
  var selectedUndertone by remember { mutableStateOf(uiState.userProfile.undertone) }

  val undertones = listOf("Cool Rose", "Warm Golden", "Neutral", "Olive / Deep")

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
        title = "Make it yours.",
        subtitle = "Personalise your fashion experience and styling algorithm."
      )
    }

    // Profile Summary Card
    item {
      Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = WearWhimPale),
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, WearWhimPurple.copy(alpha = 0.3f), RoundedCornerShape(22.dp))
      ) {
        Row(
          modifier = Modifier.padding(18.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(56.dp)
              .clip(CircleShape)
              .background(WearWhimPurple),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Person,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(30.dp)
            )
          }

          Spacer(modifier = Modifier.width(14.dp))

          Column {
            Text(
              text = if (name.isNotBlank()) name else "Fashion Lover",
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold,
              color = WearWhimText
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "${selectedStyle.label} • ${selectedPalette} • ${selectedBodyShape.label}",
              fontSize = 12.sp,
              color = WearWhimPurpleDark,
              fontWeight = FontWeight.Medium
            )
          }
        }
      }
    }

    // Stats Row
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = WearWhimSurface),
          modifier = Modifier
            .weight(1f)
            .border(1.dp, WearWhimBorder, RoundedCornerShape(16.dp))
        ) {
          Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "${uiState.wardrobeItems.size}", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = WearWhimPurple)
            Text(text = "Closet Pieces", fontSize = 11.sp, color = WearWhimMuted)
          }
        }

        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = WearWhimSurface),
          modifier = Modifier
            .weight(1f)
            .border(1.dp, WearWhimBorder, RoundedCornerShape(16.dp))
        ) {
          Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "${uiState.savedOutfitIds.size}", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = WearWhimPurple)
            Text(text = "Saved Looks", fontSize = 11.sp, color = WearWhimMuted)
          }
        }

        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = WearWhimSurface),
          modifier = Modifier
            .weight(1f)
            .border(1.dp, WearWhimBorder, RoundedCornerShape(16.dp))
        ) {
          Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "${PaletteData.defaultPalettes.size}", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = WearWhimPurple)
            Text(text = "Color Edits", fontSize = 11.sp, color = WearWhimMuted)
          }
        }
      }
    }

    // Name Input
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = WearWhimSurface),
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, WearWhimBorder, RoundedCornerShape(20.dp))
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(text = "Your Name", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = WearWhimText)
          Spacer(modifier = Modifier.height(8.dp))
          OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("Your name (optional)") },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedContainerColor = WearWhimSurface,
              unfocusedContainerColor = WearWhimSurface,
              focusedBorderColor = WearWhimPurple,
              unfocusedBorderColor = WearWhimBorder
            ),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("profile_name_input")
          )
        }
      }
    }

    // Preferred Style
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = WearWhimSurface),
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, WearWhimBorder, RoundedCornerShape(20.dp))
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(text = "Preferred Style: ${selectedStyle.label}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = WearWhimText)
          Spacer(modifier = Modifier.height(10.dp))
          FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            StyleType.entries.forEach { st ->
              val isSel = selectedStyle == st
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(14.dp))
                  .background(if (isSel) WearWhimPurple else WearWhimPale.copy(alpha = 0.5f))
                  .border(1.dp, if (isSel) WearWhimPurple else WearWhimBorder, RoundedCornerShape(14.dp))
                  .clickable { selectedStyle = st }
                  .padding(horizontal = 12.dp, vertical = 8.dp)
              ) {
                Text(
                  text = st.label,
                  fontSize = 12.sp,
                  fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium,
                  color = if (isSel) Color.White else WearWhimText
                )
              }
            }
          }
        }
      }
    }

    // Preferred Body Shape
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = WearWhimSurface),
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, WearWhimBorder, RoundedCornerShape(20.dp))
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(text = "Body Silhouette Preference", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = WearWhimText)
          Spacer(modifier = Modifier.height(10.dp))
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            BodyShape.entries.forEach { bs ->
              val isSel = selectedBodyShape == bs
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(14.dp))
                  .background(if (isSel) WearWhimPurple else WearWhimPale.copy(alpha = 0.5f))
                  .border(1.dp, if (isSel) WearWhimPurple else WearWhimBorder, RoundedCornerShape(14.dp))
                  .clickable { selectedBodyShape = bs }
                  .padding(horizontal = 12.dp, vertical = 8.dp)
              ) {
                Text(
                  text = bs.label,
                  fontSize = 12.sp,
                  fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium,
                  color = if (isSel) Color.White else WearWhimText
                )
              }
            }
          }
        }
      }
    }

    // Undertone selection
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = WearWhimSurface),
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, WearWhimBorder, RoundedCornerShape(20.dp))
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(text = "Skin Undertone", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = WearWhimText)
          Spacer(modifier = Modifier.height(10.dp))
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            undertones.forEach { ut ->
              val isSel = selectedUndertone == ut
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(14.dp))
                  .background(if (isSel) WearWhimPurple else WearWhimPale.copy(alpha = 0.5f))
                  .border(1.dp, if (isSel) WearWhimPurple else WearWhimBorder, RoundedCornerShape(14.dp))
                  .clickable { selectedUndertone = ut }
                  .padding(horizontal = 12.dp, vertical = 8.dp)
              ) {
                Text(
                  text = ut,
                  fontSize = 12.sp,
                  fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium,
                  color = if (isSel) Color.White else WearWhimText
                )
              }
            }
          }
        }
      }
    }

    // Save profile button
    item {
      Button(
        onClick = {
          viewModel.updateUserProfile(
            name = name,
            style = selectedStyle,
            palette = selectedPalette,
            bodyShape = selectedBodyShape,
            occasion = Occasion.CASUAL_DAILY,
            undertone = selectedUndertone
          )
        },
        colors = ButtonDefaults.buttonColors(containerColor = WearWhimPurple),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(50.dp)
          .testTag("save_profile_button")
      ) {
        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Save Profile Preferences", fontWeight = FontWeight.Bold, fontSize = 14.sp)
      }
    }

    item {
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

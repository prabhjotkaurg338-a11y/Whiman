package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.AppNavScreen
import com.example.ui.WearWhimViewModel
import com.example.ui.screens.ColoursScreen
import com.example.ui.screens.DiscoverScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.SavedScreen
import com.example.ui.screens.SuitMeScreen
import com.example.ui.screens.WardrobeScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.WearWhimBackground
import com.example.ui.theme.WearWhimBorder
import com.example.ui.theme.WearWhimMuted
import com.example.ui.theme.WearWhimPale
import com.example.ui.theme.WearWhimPurple
import com.example.ui.theme.WearWhimSurface
import com.example.ui.theme.WearWhimText
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        WearWhimApp()
      }
    }
  }
}

@Composable
fun WearWhimApp(viewModel: WearWhimViewModel = viewModel()) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  // Handle back button: if not on Discover, return to Discover
  BackHandler(enabled = uiState.currentScreen != AppNavScreen.DISCOVER) {
    viewModel.setScreen(AppNavScreen.DISCOVER)
  }

  // Auto-dismiss user banner after 3 seconds
  LaunchedEffect(uiState.userMessageBanner) {
    if (uiState.userMessageBanner != null) {
      delay(3000)
      viewModel.dismissBanner()
    }
  }

  val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

  Scaffold(
    containerColor = WearWhimBackground,
    bottomBar = {
      WearWhimBottomNav(
        currentScreen = uiState.currentScreen,
        onScreenSelected = { viewModel.setScreen(it) },
        savedCount = uiState.savedOutfitIds.size
      )
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(
          top = statusBarPadding,
          bottom = innerPadding.calculateBottomPadding()
        )
    ) {
      // Main Screen Content
      when (uiState.currentScreen) {
        AppNavScreen.DISCOVER -> DiscoverScreen(uiState = uiState, viewModel = viewModel)
        AppNavScreen.SUIT_ME -> SuitMeScreen(uiState = uiState, viewModel = viewModel)
        AppNavScreen.COLOURS -> ColoursScreen(uiState = uiState, viewModel = viewModel)
        AppNavScreen.WARDROBE -> WardrobeScreen(uiState = uiState, viewModel = viewModel)
        AppNavScreen.SAVED -> SavedScreen(uiState = uiState, viewModel = viewModel)
        AppNavScreen.PROFILE -> ProfileScreen(uiState = uiState, viewModel = viewModel)
      }

      // Banner message notification
      AnimatedVisibility(
        visible = uiState.userMessageBanner != null,
        enter = slideInVertically { -it } + fadeIn(),
        exit = slideOutVertically { -it } + fadeOut(),
        modifier = Modifier
          .align(Alignment.TopCenter)
          .padding(top = 10.dp, start = 16.dp, end = 16.dp)
      ) {
        uiState.userMessageBanner?.let { message ->
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = WearWhimPurple,
            shadowElevation = 6.dp,
            modifier = Modifier.border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = message,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.weight(1f, fill = false)
              )
              Spacer(modifier = Modifier.width(6.dp))
              IconButton(
                onClick = { viewModel.dismissBanner() },
                modifier = Modifier.size(24.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.Close,
                  contentDescription = "Dismiss",
                  tint = Color.White,
                  modifier = Modifier.size(14.dp)
                )
              }
            }
          }
        }
      }
    }
  }
}

data class NavItem(
  val screen: AppNavScreen,
  val label: String,
  val icon: ImageVector,
  val testTag: String
)

@Composable
fun WearWhimBottomNav(
  currentScreen: AppNavScreen,
  onScreenSelected: (AppNavScreen) -> Unit,
  savedCount: Int,
  modifier: Modifier = Modifier
) {
  val items = listOf(
    NavItem(AppNavScreen.DISCOVER, "Discover", Icons.Default.Explore, "nav_discover"),
    NavItem(AppNavScreen.SUIT_ME, "Suit You", Icons.Default.AutoAwesome, "nav_suit_me"),
    NavItem(AppNavScreen.COLOURS, "Colours", Icons.Default.Palette, "nav_colours"),
    NavItem(AppNavScreen.WARDROBE, "Wardrobe", Icons.Default.Checkroom, "nav_wardrobe"),
    NavItem(AppNavScreen.SAVED, "Saved", Icons.Default.Favorite, "nav_saved"),
    NavItem(AppNavScreen.PROFILE, "Profile", Icons.Default.Person, "nav_profile")
  )

  Surface(
    color = WearWhimSurface,
    tonalElevation = 4.dp,
    shadowElevation = 8.dp,
    modifier = modifier
      .fillMaxWidth()
      .border(0.5.dp, WearWhimBorder)
      .navigationBarsPadding()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 4.dp, vertical = 8.dp),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      items.forEach { item ->
        val isSelected = currentScreen == item.screen
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onScreenSelected(item.screen) }
            .padding(horizontal = 6.dp, vertical = 6.dp)
            .testTag(item.testTag)
        ) {
          Box {
            Icon(
              imageVector = item.icon,
              contentDescription = item.label,
              tint = if (isSelected) WearWhimPurple else WearWhimMuted,
              modifier = Modifier.size(22.dp)
            )

            // Show badge on Saved if items are saved
            if (item.screen == AppNavScreen.SAVED && savedCount > 0) {
              Box(
                modifier = Modifier
                  .align(Alignment.TopEnd)
                  .size(8.dp)
                  .clip(RoundedCornerShape(4.dp))
                  .background(WearWhimPurple)
              )
            }
          }

          Spacer(modifier = Modifier.height(3.dp))

          Text(
            text = item.label,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) WearWhimPurple else WearWhimMuted
          )
        }
      }
    }
  }
}

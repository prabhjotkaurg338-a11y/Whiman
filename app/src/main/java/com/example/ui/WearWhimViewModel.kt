package com.example.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.data.OutfitRepository
import com.example.model.BodyShape
import com.example.model.ClothingCategory
import com.example.model.Occasion
import com.example.model.OutfitItem
import com.example.model.PaletteData
import com.example.model.StyleType
import com.example.model.UserProfile
import com.example.model.WardrobeItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

enum class AppNavScreen(val title: String) {
  DISCOVER("Discover"),
  SUIT_ME("Suit You"),
  COLOURS("Colours"),
  WARDROBE("Wardrobe"),
  SAVED("Saved"),
  PROFILE("Profile")
}

data class SuitabilityAnalysisResult(
  val bodyShape: BodyShape,
  val paletteName: String,
  val occasion: Occasion,
  val keyRule: String,
  val silhouettesToEmbrace: List<String>,
  val silhouettesToAvoid: List<String>,
  val recommendedOutfits: List<OutfitItem>,
  val colorPairingTip: String
)

data class WearWhimUiState(
  val currentScreen: AppNavScreen = AppNavScreen.DISCOVER,
  val selectedStyle: StyleType? = null, // null means "All" or "Any"
  val searchQuery: String = "",
  val outfits: List<OutfitItem> = OutfitRepository.defaultOutfits,
  val savedOutfitIds: Set<String> = setOf("outfit_1"),
  val selectedPalette: String = "Soft & Cool",
  val userPhotoUri: Uri? = null,
  val wardrobeItems: List<WardrobeItem> = OutfitRepository.sampleWardrobe,
  val userProfile: UserProfile = UserProfile(),
  val wardrobeFilterCategory: ClothingCategory? = null,
  // Suit Me interactive state
  val suitMeBodyShape: BodyShape = BodyShape.HOURGLASS,
  val suitMeOccasion: Occasion = Occasion.CASUAL_DAILY,
  val suitMePalette: String = "Soft & Cool",
  val suitMeResult: SuitabilityAnalysisResult? = null,
  // Mix & match result
  val mixMatchSuggestion: List<WardrobeItem>? = null,
  val userMessageBanner: String? = null
)

class WearWhimViewModel : ViewModel() {

  private val _uiState = MutableStateFlow(WearWhimUiState())
  val uiState: StateFlow<WearWhimUiState> = _uiState.asStateFlow()

  init {
    calculateSuitability()
  }

  fun setScreen(screen: AppNavScreen) {
    _uiState.update { it.copy(currentScreen = screen, userMessageBanner = null) }
  }

  fun setStyleFilter(style: StyleType?) {
    _uiState.update {
      if (it.selectedStyle == style) it.copy(selectedStyle = null)
      else it.copy(selectedStyle = style)
    }
  }

  fun setSearchQuery(query: String) {
    _uiState.update { it.copy(searchQuery = query) }
  }

  fun toggleSavedOutfit(outfitId: String) {
    _uiState.update { state ->
      val newSaved = state.savedOutfitIds.toMutableSet()
      val isNowSaved = if (newSaved.contains(outfitId)) {
        newSaved.remove(outfitId)
        false
      } else {
        newSaved.add(outfitId)
        true
      }
      val bannerMsg = if (isNowSaved) "Saved look to your collection ♡" else "Removed look from collection"
      state.copy(savedOutfitIds = newSaved, userMessageBanner = bannerMsg)
    }
  }

  fun selectPalette(paletteName: String) {
    _uiState.update {
      it.copy(
        selectedPalette = paletteName,
        suitMePalette = paletteName,
        userMessageBanner = "Palette updated to $paletteName"
      )
    }
    calculateSuitability()
  }

  fun setUserPhotoUri(uri: Uri?) {
    _uiState.update {
      it.copy(
        userPhotoUri = uri,
        userProfile = it.userProfile.copy(photoUri = uri?.toString()),
        userMessageBanner = if (uri != null) "Photo added for color & style check!" else null
      )
    }
  }

  fun addWardrobeItem(
    name: String,
    category: ClothingCategory,
    colorHex: String,
    colorName: String,
    styleType: StyleType,
    photoUri: Uri? = null
  ) {
    val newItem = WardrobeItem(
      id = "w_${UUID.randomUUID()}",
      name = name.trim(),
      category = category,
      colorHex = colorHex,
      colorName = colorName,
      styleType = styleType,
      photoUri = photoUri?.toString()
    )
    _uiState.update {
      it.copy(
        wardrobeItems = listOf(newItem) + it.wardrobeItems,
        userMessageBanner = "Added \"${newItem.name}\" to digital wardrobe ✨"
      )
    }
  }

  fun removeWardrobeItem(id: String) {
    _uiState.update { state ->
      val item = state.wardrobeItems.firstOrNull { it.id == id }
      state.copy(
        wardrobeItems = state.wardrobeItems.filterNot { it.id == id },
        userMessageBanner = item?.let { "Removed ${it.name} from wardrobe" }
      )
    }
  }

  fun setWardrobeFilter(category: ClothingCategory?) {
    _uiState.update {
      if (it.wardrobeFilterCategory == category) it.copy(wardrobeFilterCategory = null)
      else it.copy(wardrobeFilterCategory = category)
    }
  }

  fun updateUserProfile(
    name: String,
    style: StyleType,
    palette: String,
    bodyShape: BodyShape,
    occasion: Occasion,
    undertone: String
  ) {
    val updated = UserProfile(
      name = name.ifBlank { "Fashion Lover" },
      preferredStyle = style,
      preferredPalette = palette,
      bodyShape = bodyShape,
      occasionFocus = occasion,
      undertone = undertone,
      photoUri = _uiState.value.userPhotoUri?.toString()
    )
    _uiState.update {
      it.copy(
        userProfile = updated,
        selectedPalette = palette,
        suitMeBodyShape = bodyShape,
        suitMeOccasion = occasion,
        suitMePalette = palette,
        userMessageBanner = "Style profile updated!"
      )
    }
    calculateSuitability()
  }

  fun updateSuitMeCriteria(
    bodyShape: BodyShape? = null,
    occasion: Occasion? = null,
    palette: String? = null
  ) {
    _uiState.update {
      it.copy(
        suitMeBodyShape = bodyShape ?: it.suitMeBodyShape,
        suitMeOccasion = occasion ?: it.suitMeOccasion,
        suitMePalette = palette ?: it.suitMePalette
      )
    }
    calculateSuitability()
  }

  fun calculateSuitability() {
    val current = _uiState.value
    val shape = current.suitMeBodyShape
    val occasion = current.suitMeOccasion
    val palette = current.suitMePalette

    val embrace = when (shape) {
      BodyShape.HOURGLASS -> listOf("Wrap tops and dresses", "High-waisted trousers with tailored belt", "V-neck and sweetheart necklines", "Fitted pencil skirts")
      BodyShape.RECTANGLE -> listOf("Belted shirt dresses", "Wide-leg pleated trousers", "Ruffle and puffy statement tops", "A-line flared skirts")
      BodyShape.PEAR -> listOf("Boatneck & off-shoulder tops", "Structured blazers with padded shoulders", "Dark wash wide-leg denim", "A-line midi silhouettes")
      BodyShape.INVERTED_TRIANGLE -> listOf("Flared or wide-leg cargo pants", "V-neck fluid knitwear", "A-line skirts with pockets", "Peplum tops")
      BodyShape.APPLE -> listOf("Empire waist tunics", "Fluid vertical open dusters & cardigans", "Straight leg ankle pants", "V-neck silk blouses")
      BodyShape.PETITE -> listOf("High-rise cropped pants", "Monochromatic tonal sets", "Cropped boxy blazers", "Pointed toe footwear")
      BodyShape.TALL -> listOf("Floor-skimming maxi coats", "Bold wide-cuff trousers", "Oversized chunky layering", "Color-blocked combinations")
    }

    val avoid = when (shape) {
      BodyShape.HOURGLASS -> listOf("Shapeless boxy sack dresses", "Bulky drop-waist silhouettes")
      BodyShape.RECTANGLE -> listOf("Straight uncinched tube cuts without focal details")
      BodyShape.PEAR -> listOf("Skinny bottoms with horizontal light-colored hip detailing")
      BodyShape.INVERTED_TRIANGLE -> listOf("Heavy shoulder pads or boatneck stripes")
      BodyShape.APPLE -> listOf("Tight horizontal belly belts and clingy bodycons")
      BodyShape.PETITE -> listOf("Overly long pooling hems and overwhelming gigantic patterns")
      BodyShape.TALL -> listOf("Excessively cropped three-quarter lengths that look accidental")
    }

    // Filter matching outfits based on shape, occasion, or palette
    val matching = current.outfits.filter { outfit ->
      val shapeMatches = outfit.bestForBodyShapes.isEmpty() || outfit.bestForBodyShapes.contains(shape)
      val occasionMatches = outfit.occasion == occasion || occasion == Occasion.CASUAL_DAILY
      val paletteMatches = outfit.bestForPalettes.isEmpty() || outfit.bestForPalettes.contains(palette)
      shapeMatches || (occasionMatches && paletteMatches)
    }.take(4).ifEmpty { current.outfits.take(3) }

    val colorAdvice = when (palette) {
      "Soft & Cool" -> "Pair muted lavender, powdery blue, and soft sage against crisp chalk-white."
      "Warm & Earthy" -> "Combine terracotta and olive green with warm camel or cognac leather accents."
      "Bright & Clear" -> "Use high-voltage cobalt or ruby red as the hero piece, balanced by clean monochrome."
      "Deep & Rich" -> "Layer opulent jewel tones: burgundy velvet with midnight navy and brushed gold jewelry."
      else -> "Embrace soft pastels like peach blush and buttercream in breezy, light-catching fabrics."
    }

    val result = SuitabilityAnalysisResult(
      bodyShape = shape,
      paletteName = palette,
      occasion = occasion,
      keyRule = shape.stylingTip,
      silhouettesToEmbrace = embrace,
      silhouettesToAvoid = avoid,
      recommendedOutfits = matching,
      colorPairingTip = colorAdvice
    )

    _uiState.update { it.copy(suitMeResult = result) }
  }

  fun generateMixAndMatch() {
    val items = _uiState.value.wardrobeItems
    if (items.isEmpty()) {
      _uiState.update { it.copy(userMessageBanner = "Add clothes to your wardrobe first to mix & match!") }
      return
    }

    val top = items.firstOrNull { it.category == ClothingCategory.TOPS }
      ?: items.firstOrNull { it.category == ClothingCategory.DRESSES }
      ?: items.firstOrNull()

    val bottom = items.firstOrNull { it.category == ClothingCategory.BOTTOMS }
      ?: items.drop(1).firstOrNull()

    val outer = items.firstOrNull { it.category == ClothingCategory.OUTERWEAR }
    val shoes = items.firstOrNull { it.category == ClothingCategory.SHOES }

    val combo = listOfNotNull(top, bottom, outer, shoes).distinctBy { it.id }
    _uiState.update {
      it.copy(
        mixMatchSuggestion = combo,
        userMessageBanner = "AI assembled a stylish look from your closet! ✨"
      )
    }
  }

  fun dismissBanner() {
    _uiState.update { it.copy(userMessageBanner = null) }
  }
}

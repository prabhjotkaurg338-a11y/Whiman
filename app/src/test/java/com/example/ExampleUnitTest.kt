package com.example

import com.example.model.BodyShape
import com.example.model.ClothingCategory
import com.example.model.Occasion
import com.example.model.StyleType
import com.example.ui.AppNavScreen
import com.example.ui.WearWhimViewModel
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun testInitialViewModelState() {
    val viewModel = WearWhimViewModel()
    val state = viewModel.uiState.value

    assertEquals(AppNavScreen.DISCOVER, state.currentScreen)
    assertFalse(state.outfits.isEmpty())
    assertNotNull(state.suitMeResult)
  }

  @Test
  fun testToggleSavedOutfit() {
    val viewModel = WearWhimViewModel()
    val testOutfitId = "outfit_2"

    assertFalse(viewModel.uiState.value.savedOutfitIds.contains(testOutfitId))
    viewModel.toggleSavedOutfit(testOutfitId)
    assertTrue(viewModel.uiState.value.savedOutfitIds.contains(testOutfitId))

    viewModel.toggleSavedOutfit(testOutfitId)
    assertFalse(viewModel.uiState.value.savedOutfitIds.contains(testOutfitId))
  }

  @Test
  fun testAddAndRemoveWardrobeItem() {
    val viewModel = WearWhimViewModel()
    val initialSize = viewModel.uiState.value.wardrobeItems.size

    viewModel.addWardrobeItem(
      name = "Cashmere Scarf",
      category = ClothingCategory.ACCESSORIES,
      colorHex = "#7650A4",
      colorName = "Purple",
      styleType = StyleType.FORMAL
    )

    val updatedItems = viewModel.uiState.value.wardrobeItems
    assertEquals(initialSize + 1, updatedItems.size)
    assertEquals("Cashmere Scarf", updatedItems.first().name)

    val addedId = updatedItems.first().id
    viewModel.removeWardrobeItem(addedId)
    assertEquals(initialSize, viewModel.uiState.value.wardrobeItems.size)
  }

  @Test
  fun testSuitabilityCalculation() {
    val viewModel = WearWhimViewModel()
    viewModel.updateSuitMeCriteria(
      bodyShape = BodyShape.PEAR,
      occasion = Occasion.WORK_OFFICE,
      palette = "Warm & Earthy"
    )

    val result = viewModel.uiState.value.suitMeResult
    assertNotNull(result)
    assertEquals(BodyShape.PEAR, result!!.bodyShape)
    assertEquals(Occasion.WORK_OFFICE, result.occasion)
    assertEquals("Warm & Earthy", result.paletteName)
    assertFalse(result.silhouettesToEmbrace.isEmpty())
    assertFalse(result.silhouettesToAvoid.isEmpty())
  }
}

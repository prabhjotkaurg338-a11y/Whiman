package com.example.model

enum class ClothingCategory(val label: String, val iconEmoji: String) {
  TOPS("Tops", "👕"),
  BOTTOMS("Bottoms", "👖"),
  OUTERWEAR("Outerwear", "🧥"),
  DRESSES("Dresses / Sets", "👗"),
  SHOES("Footwear", "👟"),
  ACCESSORIES("Accessories", "👜");

  companion object {
    fun fromLabel(label: String): ClothingCategory =
      entries.firstOrNull { it.label.equals(label, ignoreCase = true) } ?: TOPS
  }
}

data class WardrobeItem(
  val id: String,
  val name: String,
  val category: ClothingCategory,
  val colorHex: String = "#7650A4",
  val colorName: String = "Purple",
  val styleType: StyleType = StyleType.CASUAL,
  val photoUri: String? = null,
  val isFavorite: Boolean = false,
  val dateAddedMillis: Long = System.currentTimeMillis()
)

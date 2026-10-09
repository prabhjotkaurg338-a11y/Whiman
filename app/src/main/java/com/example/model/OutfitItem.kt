package com.example.model

data class OutfitItem(
  val id: String,
  val name: String,
  val type: StyleType,
  val pieces: String,
  val piecesList: List<String> = emptyList(),
  val occasion: Occasion = Occasion.CASUAL_DAILY,
  val bestForBodyShapes: List<BodyShape> = emptyList(),
  val bestForPalettes: List<String> = emptyList(),
  val suitabilityReason: String = "",
  val colorPalettePreview: List<String> = emptyList(),
  val vibeTag: String = "Effortless"
)

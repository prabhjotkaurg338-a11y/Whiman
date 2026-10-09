package com.example.model

import androidx.compose.ui.graphics.Color

data class ColorPalette(
  val name: String,
  val subtitle: String,
  val colors: List<String>,
  val contrastAdvice: String,
  val recommendedClothes: List<String>
)

object PaletteData {
  val defaultPalettes = listOf(
    ColorPalette(
      name = "Soft & Cool",
      subtitle = "Muted lilac, slate blue, sage & dusty rose",
      colors = listOf("#D8C8E8", "#A9C5D9", "#B8CBBF", "#F0D6DE"),
      contrastAdvice = "Ideal for cool, muted undertones. Pair with soft neutrals like heather grey and chalk white.",
      recommendedClothes = listOf("Pastel lavender knit sweater", "Light denim wide-leg jeans", "Chalk white linen shirt", "Dusty rose trench coat")
    ),
    ColorPalette(
      name = "Warm & Earthy",
      subtitle = "Terracotta, mustard amber, moss green & cognac",
      colors = listOf("#C87951", "#D8B77A", "#687B4A", "#9E6B53"),
      contrastAdvice = "Perfect for golden warm undertones. Pairs exceptionally with cream, beige, and warm leathers.",
      recommendedClothes = listOf("Rust-orange ribbed crop top", "Olive utility pants", "Camel wool coat", "Cognac leather boots")
    ),
    ColorPalette(
      name = "Bright & Clear",
      subtitle = "Ruby magenta, deep teal, marigold & emerald",
      colors = listOf("#D7265E", "#176B87", "#F2B632", "#28734D"),
      contrastAdvice = "High contrast pop that makes vibrant features shine. Balance with crisp black and stark white.",
      recommendedClothes = listOf("Emerald satin slip dress", "Teal structured blazer", "Magenta silk scarf", "Marigold knit cardigan")
    ),
    ColorPalette(
      name = "Deep & Rich",
      subtitle = "Burgundy plum, forest pine, midnight navy & royal violet",
      colors = listOf("#5B2035", "#173F35", "#263A63", "#754B83"),
      contrastAdvice = "Dramatic intensity suited for high-contrast deep coloring. Gives an instantly luxurious, regal mood.",
      recommendedClothes = listOf("Burgundy tailored trousers", "Midnight navy turtleneck", "Forest green velvet overshirt", "Deep violet evening dress")
    ),
    ColorPalette(
      name = "Pastel Spring",
      subtitle = "Peach blush, buttercream, sky cyan & mint",
      colors = listOf("#FFD3B6", "#FFAAA6", "#D5ECC2", "#A8E6CF"),
      contrastAdvice = "Fresh airy shades that flatter radiant light warm complexions with youthful glow.",
      recommendedClothes = listOf("Buttercream button-down", "Mint pleated midi skirt", "Peach relaxed polo", "Sky blue sneakers")
    )
  )

  fun parseHex(hex: String): Color {
    val clean = hex.removePrefix("#")
    val colorInt = clean.toLongOrNull(16) ?: 0xFF7650A4
    return if (clean.length == 6) {
      Color(0xFF000000 or colorInt)
    } else {
      Color(colorInt)
    }
  }
}

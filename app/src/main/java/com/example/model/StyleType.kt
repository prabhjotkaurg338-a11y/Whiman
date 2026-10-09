package com.example.model

enum class StyleType(val label: String, val iconName: String) {
  CASUAL("Casual", "wb_sunny"),
  STREETWEAR("Streetwear", "skateboarding"),
  FORMAL("Formal", "work"),
  PARTY("Party", "nightlife"),
  TRADITIONAL("Traditional", "festival"),
  ATHLEISURE("Athleisure", "fitness_center"),
  MINIMALIST("Minimalist", "check_circle"),
  VINTAGE("Vintage", "history");

  companion object {
    fun fromLabel(label: String): StyleType =
      entries.firstOrNull { it.label.equals(label, ignoreCase = true) } ?: CASUAL
  }
}

enum class BodyShape(val label: String, val description: String, val stylingTip: String) {
  HOURGLASS(
    "Hourglass",
    "Balanced bust and hips with a defined waist",
    "Fitted silhouettes, wrap dresses, and belted high-waist pants accentuate your natural balance."
  ),
  RECTANGLE(
    "Rectangle",
    "Similar bust, waist, and hip proportions",
    "Belts, ruffles, statement necklines, and A-line cuts create visual curves and definition."
  ),
  PEAR(
    "Pear / Triangle",
    "Hips wider than shoulders and bust",
    "Statement tops, boat necklines, structured shoulders, and dark A-line bottoms draw eyes upward."
  ),
  INVERTED_TRIANGLE(
    "Inverted Triangle",
    "Shoulders/chest broader than hips",
    "V-necks, wide-leg trousers, flared skirts, and cargo pants balance your silhouette beautifully."
  ),
  APPLE(
    "Apple / Round",
    "Fuller midsection with slender limbs",
    "Empire waists, flowy tunics, monochromatic outfits, and V-neck tunics showcase legs and décolletage."
  ),
  PETITE(
    "Petite (< 5'4\")",
    "Shorter vertical proportions",
    "High-waisted cuts, vertical stripes, cropped jackets, and pointed shoes create long vertical lines."
  ),
  TALL(
    "Tall (> 5'8\")",
    "Longer vertical frame and limbs",
    "Maxi coats, wide-leg trousers, color-blocking, and bold oversized layers harmonize with your height."
  );

  companion object {
    fun fromLabel(label: String): BodyShape =
      entries.firstOrNull { it.label.equals(label, ignoreCase = true) } ?: HOURGLASS
  }
}

enum class Occasion(val label: String, val emoji: String) {
  CASUAL_DAILY("Everyday Casual", "☕"),
  WORK_OFFICE("Work / Office", "💼"),
  DATE_NIGHT("Date Night", "🕯️"),
  PARTY_EVENING("Party & Events", "✨"),
  FESTIVE_TRADITIONAL("Festive / Wedding", "🎉"),
  WEEKEND_OUTING("Weekend Brunch", "🥑"),
  GYM_ACTIVE("Active & Gym", "👟");

  companion object {
    fun fromLabel(label: String): Occasion =
      entries.firstOrNull { it.label.equals(label, ignoreCase = true) } ?: CASUAL_DAILY
  }
}

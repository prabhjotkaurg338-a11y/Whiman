package com.example.model

data class UserProfile(
  val name: String = "Fashion Lover",
  val preferredStyle: StyleType = StyleType.CASUAL,
  val preferredPalette: String = "Soft & Cool",
  val bodyShape: BodyShape = BodyShape.HOURGLASS,
  val occasionFocus: Occasion = Occasion.CASUAL_DAILY,
  val undertone: String = "Cool Neutral",
  val photoUri: String? = null
)

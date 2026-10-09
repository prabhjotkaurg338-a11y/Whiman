package com.example.data

import com.example.model.BodyShape
import com.example.model.ClothingCategory
import com.example.model.Occasion
import com.example.model.OutfitItem
import com.example.model.StyleType
import com.example.model.WardrobeItem

object OutfitRepository {
  val defaultOutfits = listOf(
    OutfitItem(
      id = "outfit_1",
      name = "Everyday Ease",
      type = StyleType.CASUAL,
      pieces = "Relaxed tee, jeans, sneakers",
      piecesList = listOf("Relaxed cotton crewneck tee", "High-rise straight denim jeans", "White minimalist leather sneakers", "Canvas tote bag"),
      occasion = Occasion.CASUAL_DAILY,
      bestForBodyShapes = listOf(BodyShape.HOURGLASS, BodyShape.RECTANGLE, BodyShape.PETITE, BodyShape.APPLE),
      bestForPalettes = listOf("Soft & Cool", "Warm & Earthy"),
      suitabilityReason = "Straight-leg cut with a tucked relaxed tee balances upper and lower proportions effortlessly without clinging.",
      colorPalettePreview = listOf("#D8C8E8", "#A9C5D9", "#292333"),
      vibeTag = "Laid-back & Clean"
    ),
    OutfitItem(
      id = "outfit_2",
      name = "Modern Minimal",
      type = StyleType.FORMAL,
      pieces = "Structured shirt, trousers, simple accessories",
      piecesList = listOf("Crisp poplin structured button-up", "Pleated high-waisted tailored trousers", "Pointed leather mules", "Minimal gold geometric watch"),
      occasion = Occasion.WORK_OFFICE,
      bestForBodyShapes = listOf(BodyShape.HOURGLASS, BodyShape.PEAR, BodyShape.INVERTED_TRIANGLE, BodyShape.TALL),
      bestForPalettes = listOf("Soft & Cool", "Deep & Rich"),
      suitabilityReason = "Clean vertical lines of pressed pleats elongate legs while structured collars frame the jawline sharply.",
      colorPalettePreview = listOf("#FAF7FC", "#754B83", "#263A63"),
      vibeTag = "Executive Chic"
    ),
    OutfitItem(
      id = "outfit_3",
      name = "Weekend Style",
      type = StyleType.STREETWEAR,
      pieces = "Statement top, relaxed bottoms, trainers",
      piecesList = listOf("Graphic heavyweight boxy tee", "Relaxed cargo parachute pants", "Retro chunky platform trainers", "Crossbody utility pouch"),
      occasion = Occasion.WEEKEND_OUTING,
      bestForBodyShapes = listOf(BodyShape.RECTANGLE, BodyShape.INVERTED_TRIANGLE, BodyShape.TALL),
      bestForPalettes = listOf("Warm & Earthy", "Bright & Clear"),
      suitabilityReason = "Volume at the lower half balances broader shoulders and adds effortless urban dimension to straighter silhouettes.",
      colorPalettePreview = listOf("#C87951", "#687B4A", "#9E6B53"),
      vibeTag = "Urban Edge"
    ),
    OutfitItem(
      id = "outfit_4",
      name = "Evening Glow",
      type = StyleType.PARTY,
      pieces = "Elegant outfit, accessories, dress shoes",
      piecesList = listOf("Bias-cut satin slip midi dress", "Cropped velvet tuxedo blazer", "Strappy heeled sandals", "Glittering crystal drop earrings"),
      occasion = Occasion.PARTY_EVENING,
      bestForBodyShapes = listOf(BodyShape.HOURGLASS, BodyShape.PEAR, BodyShape.PETITE),
      bestForPalettes = listOf("Deep & Rich", "Bright & Clear"),
      suitabilityReason = "Bias-cut fluid fabric drapes along the waist and hips naturally, celebrating curves with luminous luster.",
      colorPalettePreview = listOf("#5B2035", "#754B83", "#D7265E"),
      vibeTag = "Glamorous & Radiant"
    ),
    OutfitItem(
      id = "outfit_5",
      name = "Festive Fusion",
      type = StyleType.TRADITIONAL,
      pieces = "Traditional outfit, matching accessories",
      piecesList = listOf("Embroidered raw silk Anarkali / Kurta", "Churidar / tailored cigarette pants", "Zari woven dupatta scarf", "Jhumka earrings & Mojari flats"),
      occasion = Occasion.FESTIVE_TRADITIONAL,
      bestForBodyShapes = listOf(BodyShape.HOURGLASS, BodyShape.PEAR, BodyShape.APPLE, BodyShape.RECTANGLE),
      bestForPalettes = listOf("Bright & Clear", "Deep & Rich", "Warm & Earthy"),
      suitabilityReason = "The flare of traditional panels creates graceful motion and accommodates all body shapes with regal elegance.",
      colorPalettePreview = listOf("#D7265E", "#F2B632", "#28734D"),
      vibeTag = "Opulent Heritage"
    ),
    OutfitItem(
      id = "outfit_6",
      name = "Studio Parisian",
      type = StyleType.MINIMALIST,
      pieces = "Breton striped knit, ecru denim, loafers",
      piecesList = listOf("Fine-gauge boatneck striped knit", "Straight ecru denim trousers", "Polished black penny loafers", "Structured leather bucket bag"),
      occasion = Occasion.CASUAL_DAILY,
      bestForBodyShapes = listOf(BodyShape.PEAR, BodyShape.RECTANGLE, BodyShape.PETITE),
      bestForPalettes = listOf("Soft & Cool", "Deep & Rich"),
      suitabilityReason = "Horizontal nautical stripe subtly widens narrow shoulders to achieve harmonious golden-ratio balance with hips.",
      colorPalettePreview = listOf("#B8CBBF", "#263A63", "#FAF7FC"),
      vibeTag = "Timeless Parisian"
    ),
    OutfitItem(
      id = "outfit_7",
      name = "Sunset Serenade",
      type = StyleType.CASUAL,
      pieces = "Wrap floral blouse, wide linen pants, woven espadrilles",
      piecesList = listOf("Surplice wrap flutter-sleeve blouse", "High-waist wide-leg linen pants", "Jute sole wedge espadrilles", "Straw woven tote"),
      occasion = Occasion.DATE_NIGHT,
      bestForBodyShapes = listOf(BodyShape.HOURGLASS, BodyShape.APPLE, BodyShape.TALL),
      bestForPalettes = listOf("Warm & Earthy", "Pastel Spring"),
      suitabilityReason = "A true wrap neckline creates an alluring diagonal focal point that cinches the waist comfortably.",
      colorPalettePreview = listOf("#FFAAA6", "#D8B77A", "#FFD3B6"),
      vibeTag = "Romantic Breezy"
    ),
    OutfitItem(
      id = "outfit_8",
      name = "Power Motion",
      type = StyleType.ATHLEISURE,
      pieces = "Seamless ribbed set, oversized bomber, dad sneakers",
      piecesList = listOf("Sculpting ribbed high-neck bra tank", "Seamless 7/8 compression leggings", "Oversized nylon bomber jacket", "Retro runner trainers"),
      occasion = Occasion.GYM_ACTIVE,
      bestForBodyShapes = listOf(BodyShape.HOURGLASS, BodyShape.INVERTED_TRIANGLE, BodyShape.RECTANGLE),
      bestForPalettes = listOf("Soft & Cool", "Deep & Rich"),
      suitabilityReason = "Contouring compression fabric hugs natural body lines while the outerwear provides chic street contrast.",
      colorPalettePreview = listOf("#D8C8E8", "#292333", "#777080"),
      vibeTag = "Sporty Luxe"
    )
  )

  val sampleWardrobe = listOf(
    WardrobeItem(
      id = "w_1",
      name = "Lavender Oversized Blazer",
      category = ClothingCategory.OUTERWEAR,
      colorHex = "#7650A4",
      colorName = "Soft Lavender",
      styleType = StyleType.FORMAL
    ),
    WardrobeItem(
      id = "w_2",
      name = "Ecru Wide-Leg Linen Pants",
      category = ClothingCategory.BOTTOMS,
      colorHex = "#EFE6F8",
      colorName = "Ecru Cream",
      styleType = StyleType.CASUAL
    ),
    WardrobeItem(
      id = "w_3",
      name = "Ribbed Silk Knit Tee",
      category = ClothingCategory.TOPS,
      colorHex = "#D8C8E8",
      colorName = "Lilac Mist",
      styleType = StyleType.CASUAL
    ),
    WardrobeItem(
      id = "w_4",
      name = "Pointed Kitten Heel Mules",
      category = ClothingCategory.SHOES,
      colorHex = "#292333",
      colorName = "Midnight Plum",
      styleType = StyleType.PARTY
    )
  )
}

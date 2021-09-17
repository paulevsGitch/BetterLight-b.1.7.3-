[![](https://jitpack.io/v/paulevsGitch/BetterLight-b.1.7.3-.svg)](https://jitpack.io/#paulevsGitch/BetterLight-b.1.7.3-)
# BetterLight mod for Minecraft beta 1.7.3 (client side)

This is a small mod that improves lighting in Beta.

**Content list:**
- Color lights (for any light sources including modded);
- API for light sources registration;
- Custom AO calculation.


Requires **[Fabric Legacy](https://github.com/calmilamsy/Cursed-Fabric-MultiMC)** and **[Station API](https://github.com/ModificationStation/StationAPI)** (>= 1.3)

## How to install (MultiMC):
- Download [MultiMC instance](https://github.com/calmilamsy/Cursed-Fabric-MultiMC) and import it as zip
- Download [Station API](https://github.com/ModificationStation/StationAPI/releases) and import it as a mod in MultiMC
- Import this mod

## Setup
- **Dev:** "gradlew eclipse" or "gradlew rebuildLVT genSources eclipse"
- **Build:** "gradlew build"

## Lights API
You can add custom light sources inside special registration event.

Note: Light source shouldn't have vanilla emission, it can be any block (like, for example, wool).

**Event registration:**
```java
LightRegistry.EVENT.register(new LightRegistry() {
	// Register light for specified block ID & meta
	LightColors.setBlockLight(your_block_id, your_block_meta, color);
	
	// Register light for specified block ID
	// Will be applied to all meta values
	// (Except meta that was already defined by function above)
	LightColors.setBlockLight(your_block_id, color);
	
	// Same as the first function, but takes a block instead of integer
	LightColors.setBlockLight(your_block, your_block_meta, color);
	
	// Same as the second function, but takes a block instead of integer
	LightColors.setBlockLight(your_block, color);
}
```
Color is an integer color representation (6 bites), there are some helpful functions to calculate this value from RGB or from hex string:
```java
int rgbColor = LightColors.getColor(r, g, b); // Color from RRB ints [0-255]
int hexColor = LightColors.getColor("ffffff"); // White color
	
// Will calculate average color of bright texture pixels
// Should be called only inside LightRegistry event
int texColor = LightColors.getTextureColor(textureID);
    
// Will return vanilla (tweaked) color
// Same meta order as dyes have
// based on brighten wool colors
int dyeColor = LightColors.getVanillaLight(meta);
```
You can also use Java AWT color RGB value as a color:
```java
LightRegistry.EVENT.register(new LightRegistry() {
	LightColors.setBlockLight(BlockBase.WOOD, Color.RED.getRGB());
}
```
package paulevs.betterlight.mixin;

import net.minecraft.block.BlockBase;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.betterlight.LightColors;
import paulevs.betterlight.LightRegistry;

import java.awt.Color;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Inject(method = "init", at = @At("TAIL"))
	private void light_init(CallbackInfo info) {
		light_registerLights();
		LightRegistry.EVENT.getInvoker().registerLightSources();
	}
	
	private void light_registerLights() {
		float[] hsv = new float[3];
		for (BlockBase block: BlockBase.BY_ID) {
			if (block != null && BlockBase.EMITTANCE[block.id] > 0) {
				int rgb = LightColors.getTextureColor(block.getTextureForSide(0));
				Color.RGBtoHSB((rgb >> 16) & 255, (rgb >> 8) & 255, rgb & 255, hsv);
				hsv[2] = 1 - hsv[2];
				hsv[2] = 1 - hsv[2] * hsv[2];
				rgb = Color.getHSBColor(hsv[0], hsv[1], hsv[2]).getRGB();
				LightColors.setBlockLight(block, rgb);
			}
		}
		
		LightColors.setBlockLight(BlockBase.FIRE, LightColors.getColor("ffd249"));
	}
}

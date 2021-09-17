package paulevs.betterlight.mixin;

import net.minecraft.block.BlockBase;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TileRenderer;
import net.minecraft.level.TileView;
import net.minecraft.util.maths.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import paulevs.betterlight.AOCalculator;
import paulevs.betterlight.LightColors;

import java.util.Arrays;

@Mixin(TileRenderer.class)
public class TileRendererMixin {
	@Shadow
	private TileView field_82;
	@Shadow
	private float field_56;
	@Shadow
	private float field_57;
	@Shadow
	private float field_58;
	@Shadow
	private float field_59;
	@Shadow
	private float field_60;
	@Shadow
	private float field_61;
	@Shadow
	private float field_62;
	@Shadow
	private float field_63;
	@Shadow
	private float field_64;
	@Shadow
	private float field_65;
	@Shadow
	private float field_66;
	@Shadow
	private float field_68;
	@Shadow
	private int field_88;
	@Shadow
	private int field_89;
	@Shadow
	private int field_86;
	@Shadow
	private int field_87;
	@Shadow
	public boolean field_81;
	@Shadow
	private float field_102;
	@Shadow
	private float field_101;
	@Shadow
	private float field_104;
	@Shadow
	private float field_95;
	
	private float[] lightColor = new float[3];
	
	@Inject(method = "method_46", at = @At("HEAD"), cancellable = true)
	private void light_renderBottom(BlockBase block, double x, double y, double z, int texture, CallbackInfo info) {
		if (field_82 == null) {
			return;
		}
		
		double x1 = x + block.minX;
		double x2 = x + block.maxX;
		double y1 = y + block.minY;
		double z1 = z + block.minZ;
		double z2 = z + block.maxZ;
		
		light_getColor(block, x1, y1, z2, x, y, z, 0, texture);
		this.field_56 = lightColor[0]; this.field_60 = lightColor[1]; this.field_64 = lightColor[2];
		light_getColor(block, x1, y1, z1, x, y, z, 0, texture);
		this.field_57 = lightColor[0]; this.field_61 = lightColor[1]; this.field_65 = lightColor[2];
		light_getColor(block, x2, y1, z1, x, y, z, 0, texture);
		this.field_58 = lightColor[0]; this.field_62 = lightColor[1]; this.field_66 = lightColor[2];
		light_getColor(block, x2, y1, z2, x, y, z, 0, texture);
		this.field_59 = lightColor[0]; this.field_63 = lightColor[1]; this.field_68 = lightColor[2];
	}
	
	@Inject(method = "method_55", at = @At("HEAD"), cancellable = true)
	private void light_renderTop(BlockBase block, double x, double y, double z, int texture, CallbackInfo info) {
		if (field_82 == null) {
			return;
		}
		
		double x1 = x + block.minX;
		double x2 = x + block.maxX;
		double y2 = y + block.maxY;
		double z1 = z + block.minZ;
		double z2 = z + block.maxZ;
		
		light_getColor(block, x2, y2, z2, x, y, z, 1, texture);
		this.field_56 = lightColor[0]; this.field_60 = lightColor[1]; this.field_64 = lightColor[2];
		light_getColor(block, x2, y2, z1, x, y, z, 1, texture);
		this.field_57 = lightColor[0]; this.field_61 = lightColor[1]; this.field_65 = lightColor[2];
		light_getColor(block, x1, y2, z1, x, y, z, 1, texture);
		this.field_58 = lightColor[0]; this.field_62 = lightColor[1]; this.field_66 = lightColor[2];
		light_getColor(block, x1, y2, z2, x, y, z, 1, texture);
		this.field_59 = lightColor[0]; this.field_63 = lightColor[1]; this.field_68 = lightColor[2];
	}
	
	@Inject(method = "method_61", at = @At("HEAD"), cancellable = true)
	private void light_renderSideNZ(BlockBase block, double x, double y, double z, int texture, CallbackInfo info) {
		if (field_82 == null) {
			return;
		}
		
		double x1 = x + block.minX;
		double x2 = x + block.maxX;
		double y1 = y + block.minY;
		double y2 = y + block.maxY;
		double z1 = z + block.minZ;
		
		light_getColor(block, x1, y2, z1, x, y, z, 2, texture);
		this.field_56 = lightColor[0]; this.field_60 = lightColor[1]; this.field_64 = lightColor[2];
		light_getColor(block, x2, y2, z1, x, y, z, 2, texture);
		this.field_57 = lightColor[0]; this.field_61 = lightColor[1]; this.field_65 = lightColor[2];
		light_getColor(block, x2, y1, z1, x, y, z, 2, texture);
		this.field_58 = lightColor[0]; this.field_62 = lightColor[1]; this.field_66 = lightColor[2];
		light_getColor(block, x1, y1, z1, x, y, z, 2, texture);
		this.field_59 = lightColor[0]; this.field_63 = lightColor[1]; this.field_68 = lightColor[2];
	}
	
	@Inject(method = "method_65", at = @At("HEAD"), cancellable = true)
	private void light_renderSidePZ(BlockBase block, double x, double y, double z, int texture, CallbackInfo info) {
		if (field_82 == null) {
			return;
		}
		
		double x1 = x + block.minX;
		double x2 = x + block.maxX;
		double y1 = y + block.minY;
		double y2 = y + block.maxY;
		double z2 = z + block.maxZ;
		
		light_getColor(block, x1, y2, z2, x, y, z, 3, texture);
		this.field_56 = lightColor[0]; this.field_60 = lightColor[1]; this.field_64 = lightColor[2];
		light_getColor(block, x1, y1, z2, x, y, z, 3, texture);
		this.field_57 = lightColor[0]; this.field_61 = lightColor[1]; this.field_65 = lightColor[2];
		light_getColor(block, x2, y1, z2, x, y, z, 3, texture);
		this.field_58 = lightColor[0]; this.field_62 = lightColor[1]; this.field_66 = lightColor[2];
		light_getColor(block, x2, y2, z2, x, y, z, 3, texture);
		this.field_59 = lightColor[0]; this.field_63 = lightColor[1]; this.field_68 = lightColor[2];
	}
	
	@Inject(method = "method_67", at = @At("HEAD"), cancellable = true)
	private void light_renderSideNX(BlockBase block, double x, double y, double z, int texture, CallbackInfo info) {
		if (field_82 == null) {
			return;
		}
		
		double x1 = x + block.minX;
		double y1 = y + block.minY;
		double x2 = y + block.maxY;
		double z1 = z + block.minZ;
		double z2 = z + block.maxZ;
		
		light_getColor(block, x1, x2, z2, x, y, z, 4, texture);
		this.field_56 = lightColor[0]; this.field_60 = lightColor[1]; this.field_64 = lightColor[2];
		light_getColor(block, x1, x2, z1, x, y, z, 4, texture);
		this.field_57 = lightColor[0]; this.field_61 = lightColor[1]; this.field_65 = lightColor[2];
		light_getColor(block, x1, y1, z1, x, y, z, 4, texture);
		this.field_58 = lightColor[0]; this.field_62 = lightColor[1]; this.field_66 = lightColor[2];
		light_getColor(block, x1, y1, z2, x, y, z, 4, texture);
		this.field_59 = lightColor[0]; this.field_63 = lightColor[1]; this.field_68 = lightColor[2];
	}
	
	@Inject(method = "method_69", at = @At("HEAD"), cancellable = true)
	private void light_renderSidePX(BlockBase block, double x, double y, double z, int texture, CallbackInfo info) {
		if (field_82 == null) {
			return;
		}
		
		double x2 = x + block.maxX;
		double y1 = y + block.minY;
		double y2 = y + block.maxY;
		double z1 = z + block.minZ;
		double z2 = z + block.maxZ;
		
		light_getColor(block, x2, y1, z2, x, y, z, 5, texture);
		this.field_56 = lightColor[0]; this.field_60 = lightColor[1]; this.field_64 = lightColor[2];
		light_getColor(block, x2, y1, z1, x, y, z, 5, texture);
		this.field_57 = lightColor[0]; this.field_61 = lightColor[1]; this.field_65 = lightColor[2];
		light_getColor(block, x2, y2, z1, x, y, z, 5, texture);
		this.field_58 = lightColor[0]; this.field_62 = lightColor[1]; this.field_66 = lightColor[2];
		light_getColor(block, x2, y2, z2, x, y, z, 5, texture);
		this.field_59 = lightColor[0]; this.field_63 = lightColor[1]; this.field_68 = lightColor[2];
	}
	
	@Inject(
		method = "method_46",
		cancellable = true,
		locals = LocalCapture.CAPTURE_FAILSOFT,
		require = 0,
		expect = 0,
		at = @At(
			target = "Lnet/minecraft/client/render/Tessellator;colour(FFF)V",
			shift = Shift.BEFORE,
			value = "INVOKE"
		)
	)
	private void light_swapBottom(BlockBase block, double x, double y, double z, int i, CallbackInfo info, Tessellator var9, int var10, int var11, double var12, double var14, double var16, double var18, double var20, double var22, double var24, double var26, double var28, double var30, double var32, double var34, double var36) {
		float a = Math.max(this.field_56, Math.max(this.field_60, this.field_64));
		float b = Math.max(this.field_58, Math.max(this.field_62, this.field_66));
		float c = Math.max(this.field_57, Math.max(this.field_61, this.field_65));
		float d = Math.max(this.field_59, Math.max(this.field_63, this.field_68));
		a = (a + b) * 0.5F;
		b = (c + d) * 0.5F;
		
		if (a > b) {
			var9.colour(this.field_57, this.field_61, this.field_65);
			var9.vertex(var28, var32, var34, var12, var16);
			var9.colour(this.field_58, this.field_62, this.field_66);
			var9.vertex(var30, var32, var34, var20, var24);
			var9.colour(this.field_59, this.field_63, this.field_68);
			var9.vertex(var30, var32, var36, var14, var18);
			var9.colour(this.field_56, this.field_60, this.field_64);
			var9.vertex(var28, var32, var36, var22, var26);
			info.cancel();
		}
	}
	
	@Inject(
		method = "method_55",
		cancellable = true,
		locals = LocalCapture.CAPTURE_FAILSOFT,
		require = 0,
		expect = 0,
		at = @At(
			target = "Lnet/minecraft/client/render/Tessellator;colour(FFF)V",
			shift = Shift.BEFORE,
			value = "INVOKE"
		)
	)
	private void light_swapTop(BlockBase block, double x, double y, double z, int i, CallbackInfo info, Tessellator var9, int var10, int var11, double var12, double var14, double var16, double var18, double var20, double var22, double var24, double var26, double var28, double var30, double var32, double var34, double var36) {
		float a = Math.max(this.field_56, Math.max(this.field_60, this.field_64));
		float b = Math.max(this.field_58, Math.max(this.field_62, this.field_66));
		float c = Math.max(this.field_57, Math.max(this.field_61, this.field_65));
		float d = Math.max(this.field_59, Math.max(this.field_63, this.field_68));
		a = (a + b) * 0.5F;
		b = (c + d) * 0.5F;
		
		if (a > b) {
			var9.colour(this.field_57, this.field_61, this.field_65);
			var9.vertex(var30, var32, var34, var20, var24);
			var9.colour(this.field_58, this.field_62, this.field_66);
			var9.vertex(var28, var32, var34, var12, var16);
			var9.colour(this.field_59, this.field_63, this.field_68);
			var9.vertex(var28, var32, var36, var22, var26);
			var9.colour(this.field_56, this.field_60, this.field_64);
			var9.vertex(var30, var32, var36, var14, var18);
			info.cancel();
		}
	}
	
	@Inject(
		method = "method_67",
		cancellable = true,
		locals = LocalCapture.CAPTURE_FAILSOFT,
		require = 0,
		expect = 0,
		at = @At(
			target = "Lnet/minecraft/client/render/Tessellator;colour(FFF)V",
			shift = Shift.BEFORE,
			value = "INVOKE"
		)
	)
	private void light_swapSideNX(BlockBase block, double x, double y, double z, int i, CallbackInfo info, Tessellator var9, int var10, int var11, double var12, double var14, double var16, double var18, double var20, double var22, double var24, double var26, double var28, double var30, double var32, double var34, double var36) {
		float a = Math.max(this.field_56, Math.max(this.field_60, this.field_64));
		float b = Math.max(this.field_58, Math.max(this.field_62, this.field_66));
		float c = Math.max(this.field_57, Math.max(this.field_61, this.field_65));
		float d = Math.max(this.field_59, Math.max(this.field_63, this.field_68));
		a = (a + b) * 0.5F;
		b = (c + d) * 0.5F;
		
		if (a > b) {
			var9.colour(this.field_57, this.field_61, this.field_65);
			var9.vertex(var28, var32, var34, var12, var16);
			var9.colour(this.field_58, this.field_62, this.field_66);
			var9.vertex(var28, var30, var34, var22, var26);
			var9.colour(this.field_59, this.field_63, this.field_68);
			var9.vertex(var28, var30, var36, var14, var18);
			var9.colour(this.field_56, this.field_60, this.field_64);
			var9.vertex(var28, var32, var36, this.field_89 == 1 ? var12 : var14, var24);
			info.cancel();
		}
	}
	
	@Inject(
		method = "method_69",
		cancellable = true,
		locals = LocalCapture.CAPTURE_FAILSOFT,
		require = 0,
		expect = 0,
		at = @At(
			target = "Lnet/minecraft/client/render/Tessellator;colour(FFF)V",
			shift = Shift.BEFORE,
			value = "INVOKE"
		)
	)
	private void light_swapSidePX(BlockBase block, double x, double y, double z, int i, CallbackInfo info, Tessellator var9, int var10, int var11, double var12, double var14, double var16, double var18, double var20, double var22, double var24, double var26, double var28, double var30, double var32, double var34, double var36) {
		float a = Math.max(this.field_56, Math.max(this.field_60, this.field_64));
		float b = Math.max(this.field_58, Math.max(this.field_62, this.field_66));
		float c = Math.max(this.field_57, Math.max(this.field_61, this.field_65));
		float d = Math.max(this.field_59, Math.max(this.field_63, this.field_68));
		a = (a + b) * 0.5F;
		b = (c + d) * 0.5F;
		
		if (a > b) {
			var9.colour(this.field_57, this.field_61, this.field_65);
			var9.vertex(var28, var30, var34, var14, var18);
			var9.colour(this.field_58, this.field_62, this.field_66);
			var9.vertex(var28, var32, var34, field_88 == 2 ? var12 : var14, var24);
			var9.colour(this.field_59, this.field_63, this.field_68);
			var9.vertex(var28, var32, var36, var12, var16);
			var9.colour(this.field_56, this.field_60, this.field_64);
			var9.vertex(var28, var30, var36, var22, var26);
			info.cancel();
		}
	}
	
	@Inject(
		method = "method_61",
		cancellable = true,
		locals = LocalCapture.CAPTURE_FAILSOFT,
		require = 0,
		expect = 0,
		at = @At(
			target = "Lnet/minecraft/client/render/Tessellator;colour(FFF)V",
			shift = Shift.BEFORE,
			value = "INVOKE"
		)
	)
	private void light_swapSideNZ(BlockBase block, double x, double y, double z, int i, CallbackInfo info, Tessellator var9, int var10, int var11, double var12, double var14, double var16, double var18, double var20, double var22, double var24, double var26, double var28, double var30, double var32, double var34, double var36) {
		float a = Math.max(this.field_56, Math.max(this.field_60, this.field_64));
		float b = Math.max(this.field_58, Math.max(this.field_62, this.field_66));
		float c = Math.max(this.field_57, Math.max(this.field_61, this.field_65));
		float d = Math.max(this.field_59, Math.max(this.field_63, this.field_68));
		a = (a + b) * 0.5F;
		b = (c + d) * 0.5F;
		
		if (a > b) {
			var9.colour(this.field_57, this.field_61, this.field_65);
			var9.vertex(var30, var34, var36, var12, var16);
			var9.colour(this.field_58, this.field_62, this.field_66);
			var9.vertex(var30, var32, var36, var22, var26);
			var9.colour(this.field_59, this.field_63, this.field_68);
			var9.vertex(var28, var32, var36, var14, var18);
			var9.colour(this.field_56, this.field_60, this.field_64);
			var9.vertex(var28, var34, var36, this.field_86 == 2 ? var12 : var14, var24);
			info.cancel();
		}
	}
	
	@Inject(
		method = "method_65",
		cancellable = true,
		locals = LocalCapture.CAPTURE_FAILSOFT,
		require = 0,
		expect = 0,
		at = @At(
			target = "Lnet/minecraft/client/render/Tessellator;colour(FFF)V",
			shift = Shift.BEFORE,
			value = "INVOKE"
		)
	)
	private void light_swapSidePZ(BlockBase block, double x, double y, double z, int i, CallbackInfo info, Tessellator var9, int var10, int var11, double var12, double var14, double var16, double var18, double var20, double var22, double var24, double var26, double var28, double var30, double var32, double var34, double var36) {
		float a = Math.max(this.field_56, Math.max(this.field_60, this.field_64));
		float b = Math.max(this.field_58, Math.max(this.field_62, this.field_66));
		float c = Math.max(this.field_57, Math.max(this.field_61, this.field_65));
		float d = Math.max(this.field_59, Math.max(this.field_63, this.field_68));
		a = (a + b) * 0.5F;
		b = (c + d) * 0.5F;
		
		if (a > b) {
			var9.colour(this.field_56, this.field_60, this.field_64);
			var9.vertex(var28, var34, var36, var12, var16);
			var9.colour(this.field_57, this.field_61, this.field_65);
			var9.vertex(var28, var32, var36, var22, var26);
			var9.colour(this.field_58, this.field_62, this.field_66);
			var9.vertex(var30, var32, var36, var14, var18);
			var9.colour(this.field_59, this.field_63, this.field_68);
			var9.vertex(var30, var34, var36, this.field_87 == 1 ? var12 : var14, var24);
			info.cancel();
		}
	}
	
	private void light_getColor(BlockBase block, double x, double y, double z, double bx, double by, double bz, int side, int texture) {
		Arrays.fill(lightColor, 0F);
		for (int i = 0; i < 2; i++) {
			int px = MathHelper.floor(i + x - 0.5);
			for (int j = 0; j < 2; j++) {
				int py = MathHelper.floor(j + y - 0.5);
				for (int k = 0; k < 2; k++) {
					int pz = MathHelper.floor(k + z - 0.5);
					int id = field_82.getTileId(px, py, pz);
					int meta = field_82.getTileMeta(px, py, pz);
					
					int light = LightColors.getLight(id, meta);
					if (light == 0) {
						float power = BlockBase.EMITTANCE[id] / 15F;
						lightColor[0] += power;
						lightColor[1] += power;
						lightColor[2] += power;
					}
					else {
						lightColor[0] += ((light >> 16) & 255) / 255F;
						lightColor[1] += ((light >> 8) & 255) / 255F;
						lightColor[2] += (light & 255) / 255F;
					}
				}
			}
		}
		
		if (lightColor[0] > 0 && lightColor[1] > 1 && lightColor[2] > 0) {
			float power = MathHelper.sqrt(lightColor[0] * lightColor[0] + lightColor[1] * lightColor[1] + lightColor[2] * lightColor[2]);
			if (power > 1) {
				power = (power - 1) * 0.3F + 1.0F;
			}
			lightColor[0] /= power;
			lightColor[1] /= power;
			lightColor[2] /= power;
		}
		
		float light = AOCalculator.calculateAO(field_82, x, y, z, bx, by, bz, side);
		
		float colR = light;
		float colG = light;
		float colB = light;
		
		if (block.id != BlockBase.GRASS.id || texture != 3) {
			int color = block.getColor(field_82, MathHelper.floor(bx), MathHelper.floor(by), MathHelper.floor(bz));
			colR *= ((color >> 16) & 255) / 255F;
			colG *= ((color >> 8) & 255) / 255F;
			colB *= (color & 255) / 255F;
		}
		
		float saturation = light_saturation();
		float dr = lightColor[0] * 2;
		if (dr > 1) {
			dr = 1;
		}
		float dg = lightColor[0] * 2;
		if (dg > 1) {
			dg = 1;
		}
		float db = lightColor[0] * 2;
		if (db > 1) {
			db = 1;
		}
		
		dr = Math.max(saturation, dr);
		dg = Math.max(saturation, dg);
		db = Math.max(saturation, db);
		
		//lightColor[0] = light_lerp(colR, light_lerp(colR, lightColor[0], saturation), dr);
		//lightColor[1] = light_lerp(colG, light_lerp(colG, lightColor[1], saturation), dg);
		//lightColor[2] = light_lerp(colB, light_lerp(colB, lightColor[2], saturation), db);
		lightColor[0] = light_lerp(colR, lightColor[0], dr);
		lightColor[1] = light_lerp(colG, lightColor[1], dg);
		lightColor[2] = light_lerp(colB, lightColor[2], db);
	}
	
	private float light_lerp(float start, float end, float delta) {
		return start + delta * (end - start);
	}
	
	private float light_saturation() {
		float a = MathHelper.abs(lightColor[0] - lightColor[1]);
		float b = MathHelper.abs(lightColor[0] - lightColor[2]);
		float c = MathHelper.abs(lightColor[1] - lightColor[2]);
		return Math.max(a, Math.max(b, c));
		//a = Math.max(a, Math.max(b, c)) * 5;
		//return a > 1 ? 1 : a;
	}
}

package paulevs.betterlight;

import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockBase;
import net.minecraft.client.Minecraft;
import net.modificationstation.stationloader.api.client.texture.TextureRegistry;
import net.modificationstation.stationloader.api.client.texture.TextureRegistry.Vanilla;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.awt.Point;
import java.nio.ByteBuffer;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class LightColors {
	private static final Map<Integer, Integer> BLOCK_COLORS_NO_META = Maps.newHashMap();
	private static final Map<Point, Integer> BLOCK_COLORS = Maps.newHashMap();
	private static final Point SELECTOR = new Point();
	private static final int[] VANILLA_COLORS;
	
	public static void setBlockLight(BlockBase block, int color) {
		setBlockLight(block.id, color);
	}
	
	public static void setBlockLight(BlockBase block, int meta, int color) {
		setBlockLight(block.id, meta, color);
	}
	
	public static void setBlockLight(int tileID, int color) {
		BLOCK_COLORS_NO_META.put(tileID, color);
	}
	
	public static void setBlockLight(int tileID, int meta, int color) {
		BLOCK_COLORS.put(new Point(tileID, meta), color);
	}
	
	public static int getLight(int tileID, int meta) {
		SELECTOR.setLocation(tileID, meta);
		return BLOCK_COLORS.getOrDefault(SELECTOR, BLOCK_COLORS_NO_META.getOrDefault(tileID, 0));
	}
	
	public static int getColor(int r, int g, int b) {
		return r << 16 | g << 8 | b;
	}
	
	public static int getColor(String hexCode) {
		int r = Integer.parseInt(hexCode.substring(0, 2), 16);
		int g = Integer.parseInt(hexCode.substring(2, 4), 16);
		int b = Integer.parseInt(hexCode.substring(4, 6), 16);
		return r << 16 | g << 8 | b;
	}
	
	public static int getVanillaLight(int meta) {
		return VANILLA_COLORS[meta];
	}
	
	public static int getTextureColor(int textureID) {
		@Deprecated
		Minecraft instance = (Minecraft) FabricLoader.getInstance().getGameInstance();
		TextureRegistry registry = TextureRegistry.getRegistry(Vanilla.TERRAIN);
		ByteBuffer buffer = BufferUtils.createByteBuffer(256 * 256 * 4);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, registry.getAtlasTexture(instance.textureManager, textureID >> 8));
		GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
		
		int cr = 0;
		int cg = 0;
		int cb = 0;
		int count = 0;
		textureID &= 255;
		int x = (textureID & 15) << 4;
		int y = (textureID >> 4) << 4;
		for (int i = 0; i < 16; i++) {
			int px = x + i;
			for (int j = 0; j < 16; j++) {
				int py = y + i;
				int index = (py << 8 | px) << 2;
				int a = buffer.get(index | 3) & 255;
				if (a > 127) {
					int r = buffer.get(index) & 255;
					int g = buffer.get(index | 1) & 255;
					int b = buffer.get(index | 2) & 255;
					int max = Math.max(r, Math.max(g, b));
					if (max > 50) {
						cr += r;
						cg += g;
						cb += b;
						count++;
					}
				}
			}
		}
		
		if (count == 0) {
			return 0;
		}
		
		cr /= count;
		cg /= count;
		cb /= count;
		
		return cr << 16 | cg << 8 | cb;
	}
	
	static {
		VANILLA_COLORS = new int[] {
			0,
			getColor("e4342e"),
			getColor("466d18"),
			getColor("803e1d"),
			getColor("2b3edd"),
			getColor("c543f3"),
			getColor("2bb6d9"),
			getColor("e0e5e5"),
			getColor("585858"),
			getColor("f9c7dd"),
			getColor("4bf139"),
			getColor("f2ed1d"),
			getColor("a2d0f8"),
			getColor("f169f5"),
			getColor("fdc344"),
			16777215
		};
	}
}

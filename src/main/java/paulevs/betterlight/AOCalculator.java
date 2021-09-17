package paulevs.betterlight;

import net.minecraft.block.BlockBase;
import net.minecraft.level.TileView;
import net.minecraft.util.maths.MathHelper;

public class AOCalculator {
	private static final float[] BRIGHTNESS_MULTIPLIER;
	private static final Vec3[] NORMALS;
	private static final Vec3[] OFFSETS;
	private static final boolean[] NEAR;
	private static final Vec3[] CROSS;
	
	// Sides
	// 0 - BOTTOM
	// 1 - TOP
	// 2 - NEGATIVE Z
	// 3 - POSITIVE Z
	// 4 - NEGATIVE X
	// 5 - POSITIVE X
	public static float calculateAO(TileView world, double x, double y, double z, double bx, double by, double bz, int side) {
		float light = BlockBase.EMITTANCE[world.getTileId(MathHelper.floor(bx), MathHelper.floor(by), MathHelper.floor(bz))];
		if (light > 0.8) {
			return 1.0F;
		}
		
		float brightness = lerp(BRIGHTNESS_MULTIPLIER[side], 2.0F, light);
		Vec3 normal = NORMALS[side];
		
		if (hasCorners(world, x, y, z, bx, by, bz, normal)) {
			int px = MathHelper.floor(bx + normal.x);
			int py = MathHelper.floor(by + normal.y);
			int pz = MathHelper.floor(bz + normal.z);
			return world.getBrightness(px, py, pz) * 0.25F * brightness;
		}
		
		light = 0.0F;
		int start = (side >> 1) << 2;
		
		for (int i = 0; i < 4; i++) {
			Vec3 offset = OFFSETS[i + start];
			int px = MathHelper.floor(x + normal.x * 0.5F + offset.x);
			int py = MathHelper.floor(y + normal.y * 0.5F + offset.y);
			int pz = MathHelper.floor(z + normal.z * 0.5F + offset.z);
			int id = world.getTileId(px, py, pz);
			if (!hasAO(id)) {
				light += world.getBrightness(px, py, pz);
			}
		}
		
		return light * 0.25F * brightness;
	}
	
	private static float lerp(float start, float end, float delta) {
		return start + delta * (end - start);
	}
	
	private static boolean hasAO(int tileID) {
		return BlockBase.FULL_OPAQUE[tileID] && BlockBase.EMITTANCE[tileID] == 0;
	}
	
	private static void fillNearBlocks(TileView world, Vec3 normal, int start, double bx, double by, double bz) {
		for (int i = 0; i < 4; i++) {
			Vec3 offset = CROSS[i + start];
			int px = MathHelper.floor(bx + normal.x + offset.x);
			int py = MathHelper.floor(by + normal.y + offset.y);
			int pz = MathHelper.floor(bz + normal.z + offset.z);
			int id = world.getTileId(px, py, pz);
			NEAR[i] = hasAO(id);
		}
	}
	
	private static boolean hasCorners(TileView world, double x, double y, double z, double bx, double by, double bz, Vec3 normal) {
		int cx = MathHelper.floor(bx + normal.x);
		int cy = MathHelper.floor(by + normal.y);
		int cz = MathHelper.floor(bz + normal.z);
		
		int dx = normal.x != 0.0F ? cx : MathHelper.floor((x - bx - 0.5F) * 2.0F + cx);
		int dy = normal.y != 0.0F ? cy : MathHelper.floor((y - by - 0.5F) * 2.0F + cy);
		int dz = normal.z != 0.0F ? cz : MathHelper.floor((z - bz - 0.5F) * 2.0F + cz);
		
		if (normal.x != 0.0F) {
			int id = world.getTileId(dx, dy, cz);
			if (hasAO(id)) {
				id = world.getTileId(dx, cy, dz);
				if (hasAO(id)) {
					return true;
				}
			}
		}
		else if (normal.y != 0.0F) {
			int id = world.getTileId(dx, dy, cz);
			if (hasAO(id)) {
				id = world.getTileId(cx, dy, dz);
				if (hasAO(id)) {
					return true;
				}
			}
		}
		else {
			int id = world.getTileId(dx, cy, dz);
			if (hasAO(id)) {
				id = world.getTileId(cx, dy, dz);
				if (hasAO(id)) {
					return true;
				}
			}
		}
		return false;
	}
	
	static {
		BRIGHTNESS_MULTIPLIER = new float[] { 0.47F, 1.0F, 0.8F, 0.8F, 0.6F, 0.6F };
		NEAR = new boolean[4];
		
		NORMALS = new Vec3[] {
			new Vec3( 0.0F, -1.0F,  0.0F),
			new Vec3( 0.0F,  1.0F,  0.0F),
			new Vec3( 0.0F,  0.0F, -1.0F),
			new Vec3( 0.0F,  0.0F,  1.0F),
			new Vec3(-1.0F,  0.0F,  0.0F),
			new Vec3( 1.0F,  0.0F,  0.0F)
		};
		
		OFFSETS = new Vec3[] {
			// BOTTOM & TOP
			new Vec3(-0.5F, 0.0F, -0.5F),
			new Vec3( 0.5F, 0.0F, -0.5F),
			new Vec3(-0.5F, 0.0F,  0.5F),
			new Vec3( 0.5F, 0.0F,  0.5F),
			
			// Z SIDE
			new Vec3(-0.5F, -0.5F, 0.0F),
			new Vec3( 0.5F, -0.5F, 0.0F),
			new Vec3(-0.5F,  0.5F, 0.0F),
			new Vec3( 0.5F,  0.5F, 0.0F),
			
			// X SIDE
			new Vec3(0.0F, -0.5F, -0.5F),
			new Vec3(0.0F,  0.5F, -0.5F),
			new Vec3(0.0F, -0.5F,  0.5F),
			new Vec3(0.0F,  0.5F,  0.5F)
		};
		
		CROSS = new Vec3[] {
			// BOTTOM & TOP
			new Vec3(-0.5F, 0.0F,  0.0F),
			new Vec3( 0.5F, 0.0F,  0.0F),
			new Vec3( 0.0F, 0.0F, -0.5F),
			new Vec3( 0.0F, 0.0F,  0.5F),
			
			// Z SIDE
			new Vec3(-0.5F,  0.0F, 0.0F),
			new Vec3( 0.5F,  0.0F, 0.0F),
			new Vec3( 0.0F, -0.5F, 0.0F),
			new Vec3( 0.0F,  0.5F, 0.0F),
			
			// X SIDE
			new Vec3(0.0F, -0.5F,  0.0F),
			new Vec3(0.0F,  0.5F,  0.0F),
			new Vec3(0.0F,  0.0F, -0.5F),
			new Vec3(0.0F,  0.0F,  0.5F)
		};
	}
}

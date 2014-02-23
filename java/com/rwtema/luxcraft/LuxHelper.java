package com.rwtema.luxcraft;

import net.minecraft.block.BlockFurnace;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.DamageSource;

import com.rwtema.luxcraft.luxapi.ILuxContainer;
import com.rwtema.luxcraft.luxapi.LuxColor;
import com.rwtema.luxcraft.luxapi.LuxStack;

public class LuxHelper {

	public final static String[] color_str = { "\u00a7o", "\u00a74", "\u00a72", "\u00a71", "\u00a73", "\u00a76", "\u00a75", "\u00a78" };
	//public final static int[] color_int = { 0xffffff, 0xff0000, 0x00ff00, 0x0000ff, 0x00ffff, 0xffff00, 0xff00ff, 0x101010 };
	public final static double[] r = { 1, 1, 0, 0, 0, 1, 1, 10.0 / 255 };
	public final static double[] g = { 1, 0, 1, 0, 1, 1, 0, 10 / 255.0 };
	public final static double[] b = { 1, 0, 0, 1, 1, 0, 1, 10.0 / 255 };
	private final static int[] dx = { 0, 0, 0, 0, 1, -1 };
	private final static int[] dy = { 1, -1, 0, 0, 0, 0 };
	private final static int[] dz = { 0, 0, 1, -1, 0, 0 };

	public static DamageSource[] luxDamage = { DamageSource.cactus, DamageSource.inFire, DamageSource.cactus, DamageSource.cactus, DamageSource.cactus, DamageSource.inFire, DamageSource.wither };

	public final static int[][] convRate = { { 1, 3, 3, 3, 3, 3, 3 }, { 3, 1, 3, 3, 2, 2, 3 }, { 3, 3, 1, 3, 3, 2, 2 }, { 3, 3, 3, 1, 2, 3, 2 }, { 3, 2, 3, 3, 1, 3, 3 }, { 3, 3, 2, 3, 3, 1, 3 },
			{ 3, 3, 3, 2, 3, 3, 1 } };

	public final static byte[][] convOrder = { { 1, 2, 3, 4, 5, 6, 7 }, { 2, 1, 5, 6, 3, 4, 7 }, { 3, 1, 6, 7, 2, 4, 5 }, { 4, 1, 5, 7, 2, 3, 6 }, { 5, 2, 1, 3, 4, 6, 7 }, { 6, 3, 1, 2, 4, 5, 7 },
			{ 7, 4, 1, 2, 3, 5, 6 } };

	public static String display(int amount) {
		if (amount % 12 == 0) {
			return Integer.toString(amount / 12);
		} else {
			if (amount % 6 == 0)
				return String.format("%.1f", ((float) amount) / 12);
			else if (amount % 3 == 0)
				return String.format("%.2f", ((float) amount) / 12);
			else
				return String.format("%.3f", ((float) amount) / 12);
		}

	}

}

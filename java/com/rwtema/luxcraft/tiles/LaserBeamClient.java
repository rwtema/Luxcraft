package com.rwtema.luxcraft.tiles;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.rwtema.luxcraft.block.LaserType;
import com.rwtema.luxcraft.luxapi.LuxColor;

public class LaserBeamClient extends LaserBeam {

	public float alpha, noise_size, noise_momentum;
	public int[] maxLengths;

	private static int getMax(int[] m) {
		int n = 0;
		for (int i : m)
			if (n < i)
				n = i;

		return n;
	}

	private LuxColor col;

	public LaserBeamClient startProjection() {
		super.startProjection();
		return this;
	}

	public LaserBeamClient(World world, int xstart, int ystart, int zstart, ForgeDirection direction, LaserType type, int[] maxLength) {
		super(world, xstart, ystart, zstart, direction, type);

		this.maxLength = Math.min(this.maxLength, getMax(maxLength)+1);
		this.maxLengths = maxLength;
		this.alpha = type.alpha;
		this.noise_size = type.noise;
		this.noise_momentum = type.noise_momentum;
	}
}

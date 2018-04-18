package com.rwtema.luxcraft.tiles;

import com.rwtema.luxcraft.luxapi.LuxColor;
import net.minecraft.world.World;
import net.minecraft.util.EnumFacing;

public class LaserBeamClient extends LaserBeam {

    public float alpha, noise_size, noise_momentum;
    public int[] maxLengths;
    private LuxColor col;

    public LaserBeamClient(World world, int xstart, int ystart, int zstart, EnumFacing direction, LaserType type, int[] maxLength) {

        super(world, xstart, ystart, zstart, direction, type);

        this.maxLength = Math.min(this.maxLength, getMax(maxLength) + 1);
        this.maxLengths = maxLength;
        this.alpha = type.alpha;
        this.noise_size = type.noise;
        this.noise_momentum = type.noise_momentum;
    }

    private static int getMax(int[] m) {
        int n = 0;
        for (int i : m)
            if (n < i)
                n = i;

        return n;
    }

    public LaserBeamClient startProjection() {
        super.startProjection();
        return this;
    }
}

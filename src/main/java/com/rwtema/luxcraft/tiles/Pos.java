package com.rwtema.luxcraft.tiles;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class Pos {
    public int x, y, z;

    public Pos() {
        this(0, 0, 0);
    }

    public Pos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Pos(BlockPos pos) {
        this(pos.getX(),pos.getY(),pos.getZ());
    }

    public Pos advance(EnumFacing dir) {
        this.x += dir.getFrontOffsetX();
        this.y += dir.getFrontOffsetY();
        this.z += dir.getFrontOffsetZ();
        return this;
    }

    public Pos copy() {
        return new Pos(x, y, z);
    }

    public boolean equals(Pos t) {
        return t != null && x == t.x && y == t.y && z == t.z;
    }
}
package com.rwtema.luxcraft.tiles;

import net.minecraftforge.common.util.ForgeDirection;

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

    public Pos advance(ForgeDirection dir) {
        this.x += dir.offsetX;
        this.y += dir.offsetY;
        this.z += dir.offsetZ;
        return this;
    }

    public Pos copy() {
        return new Pos(x, y, z);
    }

    public boolean equals(Pos t) {
        return t != null && x == t.x && y == t.y && z == t.z;
    }
}
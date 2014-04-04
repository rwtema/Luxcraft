package com.rwtema.luxcraft.block_base;

import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

public class Box {
    public float minX;
    public float minY;
    public float minZ;
    public float maxX;
    public float maxY;
    public float maxZ;
    public int offsetx = 0;
    public int offsety = 0;
    public int offsetz = 0;
    public String label = "";
    public IIcon texture = null;
    public IIcon[] textureSide = new IIcon[6];
    public boolean invisible = false;
    public boolean renderAsNormalBlock = false;
    public boolean[] invisibleSide = new boolean[6];
    public int uvRotateEast = 0;
    public int uvRotateWest = 0;
    public int uvRotateSouth = 0;
    public int uvRotateNorth = 0;
    public int uvRotateTop = 0;
    public int uvRotateBottom = 0;
    public int color = 0xffffff;
    public int[] rotAdd = new int[]{0, 1, 2, 3};

    public Box(String l, float par1, float par3, float par5, float par7, float par9, float par11) {
        this.label = l;
        this.minX = Math.min(par1, par7);
        this.minY = Math.min(par3, par9);
        this.minZ = Math.min(par5, par11);
        this.maxX = Math.max(par1, par7);
        this.maxY = Math.max(par3, par9);
        this.maxZ = Math.max(par5, par11);
    }

    public Box(float par1, float par3, float par5, float par7, float par9, float par11) {
        this("", par1, par3, par5, par7, par9, par11);
    }

    public Box setTextures(IIcon[] icons) {
        for (int i = 0; i < 6 && i < icons.length; i++) {
            this.textureSide[i] = icons[i];
        }

        return this;
    }

    public Box setTextureSides(Object... tex) {
        textureSide = new IIcon[6];
        int s = 0;

        for (int i = 0; i < tex.length; i++) {
            if (tex[i] instanceof Integer) {
                s = (Integer) tex[i];
            } else if (tex[i] instanceof IIcon) {
                if (s < 6 && s >= 0) {
                    textureSide[s] = (IIcon) tex[i];
                    s++;
                }
            }
        }

        return this;
    }

    public Box setColor(int col) {
        this.color = col;
        return this;
    }

    public Box setAllSideInvisible() {
        for (int i = 0; i < 6; i++) {
            this.invisibleSide[i] = true;
        }

        return this;
    }

    public Box setSideInvisible(Object... tex) {
        int s = 0;

        for (int i = 0; i < tex.length; i++) {
            if (tex[i] instanceof Integer) {
                s = (Integer) tex[i];
                this.invisibleSide[s] = true;
            } else if (tex[i] instanceof Boolean) {
                this.invisibleSide[s] = (Boolean) tex[i];
                s++;
            }
        }

        return this;
    }

    public Box setTexture(IIcon l) {
        this.texture = l;
        return this;
    }

    public Box setLabel(String l) {
        this.label = l;
        return this;
    }

    public Box copy() {
        return new Box(label, minX, minY, minZ, maxX, maxY, maxZ);
    }

    public void setBounds(float par1, float par3, float par5, float par7, float par9, float par11) {
        this.minX = Math.min(par1, par7);
        this.minY = Math.min(par3, par9);
        this.minZ = Math.min(par5, par11);
        this.maxX = Math.max(par1, par7);
        this.maxY = Math.max(par3, par9);
        this.maxZ = Math.max(par5, par11);
    }

    public Box offset(float x, float y, float z) {
        this.minX += x;
        this.minY += y;
        this.minZ += z;
        this.maxX += x;
        this.maxY += y;
        this.maxZ += z;
        return this;
    }

    public Box rotateY(int numRotations) {
        if (numRotations == 0) {
            return this;
        }

        if (numRotations < 0) {
            numRotations += 4;
        }

        for (int i = 0; i < numRotations; i++) {
            Box prev = this.copy();
            this.minZ = prev.minX;
            this.maxZ = prev.maxX;
            this.minX = 1 - prev.maxZ;
            this.maxX = 1 - prev.minZ;
            IIcon temp = this.textureSide[2];
            this.textureSide[2] = this.textureSide[4];
            this.textureSide[4] = this.textureSide[3];
            this.textureSide[3] = this.textureSide[5];
            this.textureSide[5] = temp;
        }

        this.uvRotateTop = (this.uvRotateTop + rotAdd[numRotations]) % 2;
        this.uvRotateBottom = (this.uvRotateBottom + rotAdd[numRotations]) % 2;
        return this;
    }

    public Box rotateToSide(ForgeDirection dir) {
        Box prev = this.copy();

        switch (dir) {
            case DOWN:// (0, -1, 0),
                break;

            case UP:// (0, 1, 0)
                minY = 1 - prev.maxY;
                maxY = 1 - prev.minY;
                break;

            case NORTH:// (0, 0, -1),
                minZ = prev.minY;
                maxZ = prev.maxY;
                minY = prev.minX;
                maxY = prev.maxX;
                minX = prev.minZ;
                maxX = prev.maxZ;
                break;

            case SOUTH:// (0, 0, 1),
                minZ = 1 - prev.maxY;
                maxZ = 1 - prev.minY;
                minY = prev.minX;
                maxY = prev.maxX;
                minX = 1 - prev.maxZ;
                maxX = 1 - prev.minZ;
                break;

            case WEST:// (-1, 0, 0),
                minX = prev.minY;
                maxX = prev.maxY;
                minY = prev.minX;
                maxY = prev.maxX;
                minZ = 1 - prev.maxZ;
                maxZ = 1 - prev.minZ;
                break;

            case EAST:// (1, 0, 0),
                minX = 1 - prev.maxY;
                maxX = 1 - prev.minY;
                minY = prev.minX;
                maxY = prev.maxX;
                break;

            default:
                break;
        }

        return this;
    }
}

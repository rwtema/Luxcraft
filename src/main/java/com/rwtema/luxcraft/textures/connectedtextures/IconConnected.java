package com.rwtema.luxcraft.textures.connectedtextures;

import net.minecraft.block.Block;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class IconConnected implements IIcon {
    public ForgeDirection[] up = new ForgeDirection[]{ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.UP, ForgeDirection.UP, ForgeDirection.UP, ForgeDirection.UP};
    public ForgeDirection[] left = new ForgeDirection[]{ForgeDirection.WEST, ForgeDirection.WEST, ForgeDirection.WEST, ForgeDirection.WEST, ForgeDirection.NORTH, ForgeDirection.NORTH};
    public String texture;
    IIcon[] icons = new IIcon[47];

    protected IconConnected(String texture) {
        this.texture = texture;
    }

    protected void setTexture(int id, IIcon texture) {
        icons[id] = texture;
    }

    private boolean matches(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        Block b = world.getBlock(x, y, z);
        IIcon icon = b.getIcon(side.ordinal(), world.getBlockMetadata(x, y, z));
        return icon != null && this.getIconName().equals(icon.getIconName()) && b.shouldSideBeRendered(world, x + side.offsetX, y + side.offsetY, z + side.offsetZ, side.ordinal());
    }

    public IIcon getWorldIcon(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        if(side == ForgeDirection.UNKNOWN)
            return null;

        ForgeDirection u = up[side.ordinal()];
        ForgeDirection l = left[side.ordinal()];
        ForgeDirection r = l.getOpposite();
        ForgeDirection d = u.getOpposite();
        ForgeDirection k = ForgeDirection.UNKNOWN;

        int t = 3736880;


        int ar = 0;
        ar += matches(world, x, y, z, side, u, k);
        ar += matches(world, x, y, z, side, k, r) * 2;
        ar += matches(world, x, y, z, side, d, k) * 4;
        ar += matches(world, x, y, z, side, k, l) * 8;

        if (!ConnectedTexturesHelper.isAdvancedArrangement[ar])
            return icons[ConnectedTexturesHelper.textureFromArrangement[ar]];


        ar += matches(world, x, y, z, side, u, r) * 16;
        ar += matches(world, x, y, z, side, d, r) * 32;
        ar += matches(world, x, y, z, side, d, l) * 64;
        ar += matches(world, x, y, z, side, u, l) * 128;

        return icons[ConnectedTexturesHelper.textureFromArrangement[ar]];
    }

    private int matches(IBlockAccess world, int x, int y, int z, ForgeDirection side, ForgeDirection up, ForgeDirection left) {
        return matches(world, x + up.offsetX + left.offsetX, y + up.offsetY + left.offsetY, z + up.offsetZ + left.offsetZ, side) ? 0 : 1;
    }

    @Override
    public int getIconWidth() {
        return icons[15].getIconWidth();
    }

    @Override
    public int getIconHeight() {
        return icons[15].getIconHeight();
    }

    @Override
    public float getMinU() {
        return icons[15].getMinU();
    }

    @Override
    public float getMaxU() {
        return icons[15].getMaxU();
    }

    @Override
    public float getInterpolatedU(double var1) {
        return icons[15].getInterpolatedU(var1);
    }

    @Override
    public float getMinV() {
        return icons[15].getMinV();
    }

    @Override
    public float getMaxV() {
        return icons[15].getMinV();
    }

    @Override
    public float getInterpolatedV(double var1) {
        return icons[15].getInterpolatedV(var1);
    }

    @Override
    public String getIconName() {
        return texture;
    }
}

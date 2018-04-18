package com.rwtema.luxcraft.block.fluid;

import net.minecraft.util.IIcon;


public class IconCorrecting implements IIcon {
    private final IIcon base;

    public IconCorrecting(IIcon base) {
        this.base = base;
    }

    public int getIconWidth() {
        return base.getIconWidth();
    }

    public int getIconHeight() {
        return base.getIconHeight();
    }

    public float getMinU() {
        return base.getMinU();
    }

    public float getMaxU() {
        return base.getMaxU() + (base.getMaxU() - base.getMinU());
    }

    public float getInterpolatedU(double var1) {
        float f = this.getMaxU() - this.getMinU();
        return this.getMinU() + f * (float) var1 / 16.0F;
    }

    public float getMinV() {
        return base.getMinV();
    }

    public float getMaxV() {
        return base.getMaxV() + (base.getMaxV() - base.getMinV());
    }

    public float getInterpolatedV(double var1) {
        float f = this.getMaxV() - this.getMinV();
        return this.getMinV() + f * (float) var1 / 16.0F;
    }

    public String getIconName() {
        return base.getIconName();
    }

}

package com.rwtema.luxcraft.render;

import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SubIcon implements IIcon {

	private float dx = 0, dy = 0, dw = 1, dh = 1;
	private final IIcon base;

	public SubIcon(IIcon base, int dx, int dy, int dw, int dh) {
		super();
		this.dx = dx / 16F;
		this.dy = dy / 16F;
		this.dw = dw / 16F;
		this.dh = dh / 16F;
		this.base = base;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getIconWidth() {
		return (int) Math.ceil(base.getIconWidth() * dw);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getIconHeight() {
		return (int) Math.ceil(base.getIconWidth() * dh);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getMinU() {
		return base.getInterpolatedU(dx);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getMaxU() {
		return base.getInterpolatedU(dx + dw);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getInterpolatedU(double var1) {
		float f = this.getMaxU() - this.getMinU();
		return this.getMinU() + f * (float) var1 / 16.0F;

	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getMinV() {
		return base.getInterpolatedV(dx);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getMaxV() {
		return base.getInterpolatedV(dx + dw);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getInterpolatedV(double var1) {
		float f = this.getMaxV() - this.getMinV();
		return this.getMinV() + f * (float) var1 / 16.0F;

	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getIconName() {
		return base.getIconName();
	}

}

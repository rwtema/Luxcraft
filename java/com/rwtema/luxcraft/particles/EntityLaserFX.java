package com.rwtema.luxcraft.particles;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import com.rwtema.luxcraft.block.BlockLuxLaser;
import com.rwtema.luxcraft.luxapi.LuxColor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityLaserFX extends EntityFX {
	public EntityLaserFX(World world, double x, double y, double z, int speed, LuxColor col) {
		this(world, x, y, z, col);
		this.motionX *= speed;
		this.motionY *= speed;
		this.motionZ *= speed;
	}

	public EntityLaserFX(World par1World, double x, double y, double z, LuxColor col) {
		super(par1World, x, y, z, 0.0D, 0.0D, 0.0D);
		this.setParticleIcon(BlockLuxLaser.cols[col.ordinal()]);
		// this.particleRed = col.r;
		// this.particleGreen = col.g;
		// this.particleBlue = col.b;

		this.particleRed = t(col.r);
		this.particleGreen = t(col.g);
		this.particleBlue = t(col.b);

		this.particleAlpha = 0.7F;

		// this.particleGravity = Blocks.snow.blockParticleGravity;
		this.particleGravity = 0;
		this.particleScale /= 3.0F;
		this.motionX *= 0.080000000149011612D;
		this.motionY *= 0.080000000149011612D;
		this.motionZ *= 0.080000000149011612D;

		this.particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
		this.noClip = true;
	}

	private float t(float r) {
		return r;
	}

	// public EntityLaserFX(World par1World, double par2, double par4, double
	// par6, double par8, double par10, double par12, LuxColor col) {
	// this(par1World, par2, par4, par6, col);
	// this.motionX *= 0.10000000149011612D;
	// this.motionY *= 0.10000000149011612D;
	// this.motionZ *= 0.10000000149011612D;
	// this.motionX += par8;
	// this.motionY += par10;
	// this.motionZ += par12;
	// }

	public int getFXLayer() {
		return 1;
	}

	// public void renderParticle(Tessellator par1Tessellator, float par2, float
	// par3, float par4, float par5, float par6, float par7) {
	// float f6 = ((float) this.particleTextureIndexX +
	// this.particleTextureJitterX / 4.0F) / 16.0F;
	// float f7 = f6 + 0.015609375F;
	// float f8 = ((float) this.particleTextureIndexY +
	// this.particleTextureJitterY / 4.0F) / 16.0F;
	// float f9 = f8 + 0.015609375F;
	// float f10 = 0.1F * this.particleScale;
	//
	// if (this.particleIcon != null) {
	// f6 = this.particleIcon.getInterpolatedU((double)
	// (this.particleTextureJitterX / 4.0F * 16.0F));
	// f7 = this.particleIcon.getInterpolatedU((double)
	// ((this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F));
	// f8 = this.particleIcon.getInterpolatedV((double)
	// (this.particleTextureJitterY / 4.0F * 16.0F));
	// f9 = this.particleIcon.getInterpolatedV((double)
	// ((this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F));
	// }
	//
	// float f11 = (float) (this.prevPosX + (this.posX - this.prevPosX) *
	// (double) par2 - interpPosX);
	// float f12 = (float) (this.prevPosY + (this.posY - this.prevPosY) *
	// (double) par2 - interpPosY);
	// float f13 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) *
	// (double) par2 - interpPosZ);
	// par1Tessellator.setColorOpaque_F(this.particleRed, this.particleGreen,
	// this.particleBlue);
	// par1Tessellator.addVertexWithUV((double) (f11 - par3 * f10 - par6 * f10),
	// (double) (f12 - par4 * f10), (double) (f13 - par5 * f10 - par7 * f10),
	// (double) f6, (double) f9);
	// par1Tessellator.addVertexWithUV((double) (f11 - par3 * f10 + par6 * f10),
	// (double) (f12 + par4 * f10), (double) (f13 - par5 * f10 + par7 * f10),
	// (double) f6, (double) f8);
	// par1Tessellator.addVertexWithUV((double) (f11 + par3 * f10 + par6 * f10),
	// (double) (f12 + par4 * f10), (double) (f13 + par5 * f10 + par7 * f10),
	// (double) f7, (double) f8);
	// par1Tessellator.addVertexWithUV((double) (f11 + par3 * f10 - par6 * f10),
	// (double) (f12 - par4 * f10), (double) (f13 + par5 * f10 - par7 * f10),
	// (double) f7, (double) f9);
	// super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6,
	// par7);
	// }
}

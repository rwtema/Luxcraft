package com.rwtema.luxcraft.particles;

import com.rwtema.luxcraft.block.BlockLuxLaser;
import com.rwtema.luxcraft.luxapi.LuxColor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

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

        this.particleRed = col.r;
        this.particleGreen = col.g;
        this.particleBlue = col.b;

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

    public int getFXLayer() {
        return 1;
    }

}

package com.rwtema.luxcraft.particles;

import com.rwtema.luxcraft.block.fluid.BlockVoidGas;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class EntityVoidFX extends EntityFX {
    public EntityVoidFX(World par1World, double x, double y, double z) {
        super(par1World, x, y, z, 0.0D, 0.0D, 0.0D);
        this.setParticleIcon(BlockVoidGas.voidGas_particle);

        this.particleAlpha = 0.7F;

        this.particleGravity = -0.05F;
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

package com.rwtema.luxcraft.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;

public class ParticleHandler {
	public static void spawnParticle(EntityFX ent) {
		Minecraft.getMinecraft().effectRenderer.addEffect(ent);
	}
}

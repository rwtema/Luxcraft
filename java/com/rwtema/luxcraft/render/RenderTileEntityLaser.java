package com.rwtema.luxcraft.render;

import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.rwtema.luxcraft.luxapi.LuxColor;
import com.rwtema.luxcraft.particles.EntityLaserFX;
import com.rwtema.luxcraft.particles.ParticleHandler;
import com.rwtema.luxcraft.tiles.LaserBeamClient;
import com.rwtema.luxcraft.tiles.Pos;
import com.rwtema.luxcraft.tiles.TileEntityLuxLaserClient;

public class RenderTileEntityLaser extends TileEntitySpecialRenderer {
	static Random rand = new Random();
	private static final ResourceLocation texture = new ResourceLocation("luxcraft", "textures/beam.png");

	private static float curPartialTicks = 0;
	private static boolean newTick = false;

	@Override
	public void renderTileEntityAt(TileEntity var1, double x, double y, double z, float f) {
		if (curPartialTicks != f) {
			newTick = curPartialTicks > f;
			curPartialTicks = f;
		}

		TileEntityLuxLaserClient laser = (TileEntityLuxLaserClient) var1;
		LaserBeamClient laserBeam = laser.getClientPath();

		if (laserBeam == null || laserBeam.getLength() == 0)
			return;

		Tessellator var2 = Tessellator.instance;
		
		GL11.glDepthFunc(GL11.GL_LEQUAL);

		// .glAlphaFunc(GL11.GL_GREATER, 0.1F);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDepthMask(false);

		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);
	
		// GL11.glDisable(GL11.GL_CULL_FACE);

		this.bindTexture(texture);

		List<Pos> path = laserBeam.getPath();

		GL11.glPushMatrix();

		GL11.glTranslated(x - laser.xCoord, y - laser.yCoord, z - laser.zCoord);

		double dx = 0.5;
		double dy = 0.5;
		double dz = 0.5;

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

		for (LuxColor col : LuxColor.values()) {

			double t1 = (col.ordinal() / (double) col.n + (float) ((Minecraft.getSystemTime()) % 3000L) / 3000.0F) * Math.PI * 2;
			double t2 = (col.ordinal() / (double) col.n + (float) ((Minecraft.getSystemTime()) % 4000L) / 4000.0F) * Math.PI * 2;

			double ddx = 0.5 + Math.cos(t1) * Math.cos(t2) * 0.75F * laserBeam.beamsize;
			double ddy = 0.5 + Math.cos(t1) * Math.sin(t2) * 0.75F * laserBeam.beamsize;
			double ddz = 0.5 + Math.sin(t1) * 0.75F * laserBeam.beamsize;

//			ddx = ddy = ddz = 0.5;

			GL11.glColor4f(col.r, col.g, col.b, laserBeam.alpha);

			if (col == LuxColor.Black) {
				GL11.glColor4f(col.r, col.g, col.b, Math.min(1, 4 * laserBeam.alpha));
			}

			for (int j = 0; j < Math.min(path.size() - 1, laserBeam.maxLengths[col.index]); j++) {

				double ax = path.get(j).x + dx, ay = path.get(j).y + dy, az = path.get(j).z + dz;

				dx = dx * laserBeam.noise_momentum + ddx * (1 - laserBeam.noise_momentum);
				dy = dy * laserBeam.noise_momentum + ddy * (1 - laserBeam.noise_momentum);
				dz = dz * laserBeam.noise_momentum + ddz * (1 - laserBeam.noise_momentum);

				if (path.get(j + 1) == null)
					break;

				if (newTick && rand.nextInt(4) == 0) {
					double k = rand.nextDouble();

					ParticleHandler.spawnParticle(new EntityLaserFX(laser.getWorldObj(), ax * k + (1 - k) * (path.get(j + 1).x + dx), ay * k + (1 - k) * (path.get(j + 1).y + dy), az * k + (1 - k)
							* (path.get(j + 1).z + dz), col));
				}

				drawLine(ax, ay, az, laserBeam.beamsize, path.get(j + 1).x + dx, path.get(j + 1).y + dy, path.get(j + 1).z + dz);
			}
		}

		GL11.glColor4f(1, 1, 1, 1);

		var2.setTranslation(0, 0, 0);

		GL11.glPopMatrix();
		
		GL11.glDepthFunc(GL11.GL_LEQUAL);

		GL11.glEnable(GL11.GL_LIGHTING);

		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(true);

		GL11.glDisable(GL11.GL_BLEND);

		GL11.glAlphaFunc(GL11.GL_GREATER, 0.5F);
	}

	public void drawLine(double x1, double y1, double z1, double w, double x2, double y2, double z2) {
		double dx = x2 - x1, dy = y2 - y1, dz = z2 - z1;
		double d = Math.sqrt(dx * dx + dy * dy + dz * dz);
		if (d == 0)
			return;
		if (w == 0)
			return;
		Tessellator tes = Tessellator.instance;

		// tes.startDrawing(1);
		// tes.addVertex(x1, y1, z1);
		// tes.addVertex(x2, y2, z2);
		// tes.draw();

		GL11.glPushMatrix();

		GL11.glTranslated(x1, y1, z1);

		tes.setBrightness(15);
		tes.setColorOpaque_F(1, 1, 1);

		GL11.glRotatef((float) -(Math.atan2(dz, dx) / Math.PI * 180), 0, 1, 0);
		GL11.glRotatef((float) (Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)) / Math.PI * 180), 0, 0, 1);
		GL11.glScaled(d, w, w);

		tes.startDrawingQuads();
		GL11.glNormal3f(0.0F, -1.0F, 0.0F);
		tes.addVertexWithUV(0, -1, -1, 1, 0);
		tes.addVertexWithUV(1, -1, -1, 1, 1);
		tes.addVertexWithUV(1, -1, 1, 0, 1);
		tes.addVertexWithUV(0, -1, 1, 0, 0);

		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		tes.addVertexWithUV(0, 1, 1, 0, 0);
		tes.addVertexWithUV(1, 1, 1, 0, 1);
		tes.addVertexWithUV(1, 1, -1, 1, 1);
		tes.addVertexWithUV(0, 1, -1, 1, 0);

		GL11.glNormal3f(0.0F, 0.0F, -1.0F);
		tes.addVertexWithUV(0, 1, -1, 0, 0);
		tes.addVertexWithUV(1, 1, -1, 0, 1);
		tes.addVertexWithUV(1, -1, -1, 1, 1);
		tes.addVertexWithUV(0, -1, -1, 1, 0);

		GL11.glNormal3f(0.0F, 0.0F, 1.0F);
		tes.addVertexWithUV(0, -1, 1, 1, 0);
		tes.addVertexWithUV(1, -1, 1, 1, 1);
		tes.addVertexWithUV(1, 1, 1, 0, 1);
		tes.addVertexWithUV(0, 1, 1, 0, 0);

		tes.draw();

		GL11.glPopMatrix();

		// GL11.glScaled(1 / d, 1 / w, 1 / w);
		// GL11.glRotatef((float) -(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz))
		// / Math.PI * 180), 0, 0, 1);
		// GL11.glRotatef((float) (Math.atan2(dz, dx) / Math.PI * 180), 0, 1,
		// 0);
		// GL11.glTranslated(-x1, -y1, -z1);
	}

}

package com.rwtema.luxcraft.render;

import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.rwtema.luxcraft.luxapi.LuxColor;
import com.rwtema.luxcraft.tiles.LaserBeamClient;
import com.rwtema.luxcraft.tiles.Pos;
import com.rwtema.luxcraft.tiles.TileEntityLuxLaserClient;

public class RenderTileEntityLaser extends TileEntitySpecialRenderer {
	static Random rand = new Random();
	private static final ResourceLocation texture = new ResourceLocation("luxcraft", "textures/beam.png");

	@Override
	public void renderTileEntityAt(TileEntity var1, double x, double y, double z, float f) {

		TileEntityLuxLaserClient laser = (TileEntityLuxLaserClient) var1;
		LaserBeamClient laserBeam = laser.getClientPath();

		if (laserBeam == null || laserBeam.getLength() == 0)
			return;

		Tessellator var2 = Tessellator.instance;

		// .glAlphaFunc(GL11.GL_GREATER, 0.1F);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDepthMask(true);

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

		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);

		for (LuxColor col : LuxColor.values()) {
			GL11.glColor4f(col.r, col.g, col.b, laserBeam.alpha);

			if (col == LuxColor.Black) {
				GL11.glColor4f(col.r, col.g, col.b, Math.min(1, 4 * laserBeam.alpha));
			}

			for (int j = 0; j < Math.min(path.size() - 1, laserBeam.maxLengths[col.index]); j++) {

				double ax = path.get(j).x + dx, ay = path.get(j).y + dy, az = path.get(j).z + dz;

				dx = dx * laserBeam.noise_momentum + (0.5 + rand.nextGaussian() / 2 * laserBeam.noise_size) * (1 - laserBeam.noise_momentum);
				dy = dy * laserBeam.noise_momentum + (0.5 + rand.nextGaussian() / 2 * laserBeam.noise_size) * (1 - laserBeam.noise_momentum);
				dz = dz * laserBeam.noise_momentum + (0.5 + rand.nextGaussian() / 2 * laserBeam.noise_size) * (1 - laserBeam.noise_momentum);

				if (path.get(j + 1) == null)
					break;
				drawLine(ax, ay, az, laserBeam.beamsize, path.get(j + 1).x + dx, path.get(j + 1).y + dy, path.get(j + 1).z + dz);
			}
		}

		GL11.glColor4f(1, 1, 1, 1);

		var2.setTranslation(0, 0, 0);

		GL11.glPopMatrix();

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

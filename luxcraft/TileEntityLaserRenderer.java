package luxcraft;

import java.util.Random;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.world.ChunkPosition;

import org.lwjgl.opengl.GL11;

public class TileEntityLaserRenderer extends TileEntitySpecialRenderer
{
	static Random rand = new Random();

	@Override
	public void renderTileEntityAt(TileEntity var1, double x, double y,
			double z, float f)
	{
		TileEntityLaser laser = (TileEntityLaser) var1;

		Tessellator var2 = Tessellator.instance;
		
		//var2.setBrightness(15);
		
		GL11.glEnable(GL11.GL_BLEND);
//		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDepthMask(true);
		//GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
		this.bindTextureByName("/misc/beam.png");
		
		
		
		for(int i = 0;i < laser.getBeams().size(); i++){
			LaserBeam t = laser.getBeams().get(i);

			if(t.started){
				GL11.glLineWidth(t.beamsize);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

				//var2.startDrawing(1);
				var2.setColorRGBA_F(t.r, t.g, t.b, t.alpha);
				//var2.setTranslation(x - laser.xCoord, y - laser.yCoord, z-laser.zCoord);
				GL11.glTranslated(x - laser.xCoord, y - laser.yCoord, z-laser.zCoord);

				double dx = 0;
				double dy = 0;
				double dz = 0;

				for(int j = 0; j<t.pathx.size()-1;j++){
					double ax=t.pathx.get(j)+dx, ay= t.pathy.get(j)+dy, az=t.pathz.get(j)+dz;
					
					//var2.addVertex(t.pathx.get(j)+dx,t.pathy.get(j)+dy,t.pathz.get(j)+dz);
					
					dx=dx*t.noise_momentum+rand.nextGaussian()/2*t.noise_size*(1-t.noise_momentum);
					dy=dy*t.noise_momentum+rand.nextGaussian()/2*t.noise_size*(1-t.noise_momentum);
					dz=dz*t.noise_momentum+rand.nextGaussian()/2*t.noise_size*(1-t.noise_momentum);
					
					
					drawLine(ax,ay,az, t.beamsize/40, t.pathx.get(j+1)+dx,t.pathy.get(j+1)+dy,t.pathz.get(j+1)+dz, t.r, t.g, t.b, t.alpha);;

					//var2.addVertex(t.pathx.get(j+1)+dx,t.pathy.get(j+1)+dy,t.pathz.get(j+1)+dz);
				}

				GL11.glTranslated(-x + laser.xCoord, -y + laser.yCoord, -z+laser.zCoord);
				//var2.draw();

			}
			
			GL11.glColor4f(1, 1, 1, 1);
			var2.setTranslation(0, 0, 0);
		}

//		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	public void drawLine(double x1, double y1, double z1,double w, double x2, double y2, double z2, float r, float g, float b, float alpha){
		double dx = x2 -x1, dy = y2 - y1, dz = z2 -z1;
		double d = Math.sqrt(dx*dx+dy*dy+dz*dz);
		if(d==0) return;
		if(w==0) return;
		Tessellator tes = Tessellator.instance;

		GL11.glTranslated(x1, y1, z1);
		GL11.glColor4f(r, g, b, alpha);
		
		tes.setBrightness(15);
		tes.setColorOpaque_F(1, 1, 1);

		GL11.glRotatef((float) -(Math.atan2(dz, dx)/Math.PI*180), 0, 1, 0);
		GL11.glRotatef((float) (Math.atan2(dy, Math.sqrt(dx*dx+dz*dz))/Math.PI*180), 0, 0, 1);
		GL11.glScaled(d, w, w);
//		tes.startDrawing(1);
//		tes.addVertex(0,0,0);
//		tes.addVertex(1,0,0);
//		tes.draw();
		
		tes.startDrawingQuads();
//		tes.addVertexWithUV(0, -1, -1, 0, 0);
//		tes.addVertexWithUV(0,  1, -1, 1, 0);
//		tes.addVertexWithUV(0,  1,  1, 1, 1);
//		tes.addVertexWithUV(0, -1,  1, 0, 1);
//		
//		tes.addVertexWithUV(1, -1, -1, 0, 0);
//		tes.addVertexWithUV(1,  1, -1, 1, 0);
//		tes.addVertexWithUV(1,  1,  1, 1, 1);
//		tes.addVertexWithUV(1, -1,  1, 0, 1);
		
		
		 GL11.glNormal3f(0.0F, -1.0F, 0.0F);
		tes.addVertexWithUV(0, -1, -1, 1, 0);
		tes.addVertexWithUV(1, -1, -1, 1, 1);
		tes.addVertexWithUV(1, -1,  1, 0, 1);
		tes.addVertexWithUV(0, -1,  1, 0, 0);
		
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		tes.addVertexWithUV( 0, 1,  1, 0, 0);
		tes.addVertexWithUV( 1, 1,  1, 0, 1);
		tes.addVertexWithUV( 1, 1, -1, 1, 1);
		tes.addVertexWithUV( 0, 1, -1, 1, 0);
		
		GL11.glNormal3f(0.0F, 0.0F, -1.0F);
		tes.addVertexWithUV(0,  1, -1, 0, 0);
		tes.addVertexWithUV(1,  1, -1, 0, 1);
		tes.addVertexWithUV(1, -1, -1, 1, 1);
		tes.addVertexWithUV(0, -1, -1, 1, 0);
		
		
		GL11.glNormal3f(0.0F, 0.0F, 1.0F);
		tes.addVertexWithUV( 0, -1, 1, 1, 0);
		tes.addVertexWithUV( 1, -1, 1, 1, 1);
		tes.addVertexWithUV( 1,  1, 1, 0, 1);
		tes.addVertexWithUV( 0,  1, 1, 0, 0);	
		
		
		tes.draw();
		
		
		GL11.glScaled(1/d, 1/w, 1/w);
		GL11.glRotatef((float) -(Math.atan2(dy, Math.sqrt(dx*dx+dz*dz))/Math.PI*180), 0, 0, 1);
		GL11.glRotatef((float) (Math.atan2(dz, dx)/Math.PI*180), 0, 1, 0);
		GL11.glTranslated(-x1, -y1, -z1);
	}
		
}

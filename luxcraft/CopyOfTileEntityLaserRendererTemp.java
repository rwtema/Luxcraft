package luxcraft;

import java.util.Random;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.world.ChunkPosition;

import org.lwjgl.opengl.GL11;

public class CopyOfTileEntityLaserRendererTemp extends TileEntitySpecialRenderer
{
	static Random rand = new Random();

	@Override
	public void renderTileEntityAt(TileEntity var1, double x, double y,
			double z, float f)
	{
		TileEntityLaser laser = (TileEntityLaser) var1;

		Tessellator var2 = Tessellator.instance;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		for(int i = 0;i < laser.getBeams().size(); i++){
			LaserBeam t = laser.getBeams().get(i);

			if(t.started){
				GL11.glLineWidth(t.beamsize);

				var2.startDrawing(1);
				var2.setColorRGBA_F(t.r, t.g, t.b, t.alpha);			
				var2.setTranslation(x - laser.xCoord, y - laser.yCoord, z-laser.zCoord);

				double dx = 0;
				double dy = 0;
				double dz = 0;

				for(int j = 0; j<t.pathx.size()-1;j++){
					double ax=t.pathx.get(j)+dx, ay= t.pathy.get(j)+dy, az=t.pathz.get(j)+dz;
					
					var2.addVertex(t.pathx.get(j)+dx,t.pathy.get(j)+dy,t.pathz.get(j)+dz);
					
					dx=dx*t.noise_momentum+rand.nextGaussian()/2*t.noise_size*(1-t.noise_momentum);
					dy=dy*t.noise_momentum+rand.nextGaussian()/2*t.noise_size*(1-t.noise_momentum);
					dz=dz*t.noise_momentum+rand.nextGaussian()/2*t.noise_size*(1-t.noise_momentum);

					var2.addVertex(t.pathx.get(j+1)+dx,t.pathy.get(j+1)+dy,t.pathz.get(j+1)+dz);
				}
				
				var2.draw();

			}
			var2.setTranslation(0, 0, 0);
		}

		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}
		
}

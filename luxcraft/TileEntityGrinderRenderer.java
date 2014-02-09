package luxcraft;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

public class TileEntityGrinderRenderer extends TileEntitySpecialRenderer
{
	public TileEntityGrinderRenderer()
	{
	}

	@Override
	public void renderTileEntityAt(TileEntity var1, double x, double y,
			double z, float f)
	{
		Block block = var1.getBlockType();
		int var6 = block.blockIndexInTexture;
		var6 = 134;
		int var7 = (var6 & 15) << 4;
		int var8 = var6 & 240;
		float var9 = 0.015625F;
		double var10 = (double)((float)var7 / 256.0F);
		double var11 = (double)(((float)var7 + 15.99F / 2) / 256.0F);
		double var12 = (double)(((float)var7 + 15.99F) / 256.0F);
		double var14 = (double)((float)var8 / 256.0F);
		double var16 = (double)(((float)var8 + 15.99F) / 256.0F);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(32826);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);

		if(var1.worldObj != null){
			int var223 = var1.worldObj.getLightBrightnessForSkyBlocks(var1.xCoord, var1.yCoord, var1.zCoord, 0);
			int var224 = var223 % 65536;
			int var225 = var223 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var224 / 1.0F, (float)var225 / 1.0F);
		}

		this.bindTextureByName("/terrain.png");
		Tessellator var5 = Tessellator.instance;
		//var5.startDrawingQuads();
		var5.setNormal(0.0F, -1.0F, 0.0F);
		//var5.setColorOpaque_F(255, 255, 255);
		GL11.glDisable(2896);
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		float ang = ((TileEntityGrinder)var1).prevspin_angle + (((TileEntityGrinder)var1).spin_angle - ((TileEntityGrinder)var1).prevspin_angle) * f;
		ang *= 3;
		GL11.glRotatef(ang, 0, 1, 0);

		//		var5.setColorOpaque_I(0xffffff);

		for (int i = 0; i < 360; i += 45)
		{
			var5.startDrawingQuads();
			if(var1.worldObj != null){
				int var223 = var1.worldObj.getLightBrightnessForSkyBlocks(var1.xCoord, var1.yCoord, var1.zCoord, 0);
				int var224 = var223 % 65536;
				int var225 = var223 / 65536;
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var224 / 1.0F, (float)var225 / 1.0F);
			}
			GL11.glRotatef(45, 0, 1, 0);
			RenderMiniSpike(var5, 0.25, 0.5, 0.25, 0.1, var10, var11, var12, var14, var16);
			var5.draw();
		}

		//var5.draw();
		GL11.glRotatef(-ang, 0, 1, 0);
		GL11.glTranslated(-x - 0.5, -y, -z - 0.5);
		GL11.glDisable(32826);
		GL11.glEnable(2896);
	}

	public void RenderMiniSpike(Tessellator var5, double x, double y, double z, double size, double var10, double var11, double var12, double var14, double var16)
	{
		var5.addVertexWithUV(x, y + size, z, var11, var14);
		var5.addVertexWithUV(x, y + size, z, var11, var14);
		var5.addVertexWithUV(x + size / 2, y, z - size / 2, var12, var16);
		var5.addVertexWithUV(x - size / 2, y, z - size / 2, var10, var16);
		var5.addVertexWithUV(x - size / 2, y, z + size / 2, var10, var16);
		var5.addVertexWithUV(x + size / 2, y, z + size / 2, var12, var16);
		var5.addVertexWithUV(x, y + size, z, var11, var14);
		var5.addVertexWithUV(x, y + size, z, var11, var14);
		var5.addVertexWithUV(x - size / 2, y, z - size / 2, var10, var16);
		var5.addVertexWithUV(x - size / 2, y, z + size / 2, var12, var16);
		var5.addVertexWithUV(x, y + size, z, var11, var14);
		var5.addVertexWithUV(x, y + size, z, var11, var14);
		var5.addVertexWithUV(x, y + size, z, var11, var14);
		var5.addVertexWithUV(x, y + size, z, var11, var14);
		var5.addVertexWithUV(x + size / 2, y, z + size / 2, var12, var16);
		var5.addVertexWithUV(x + size / 2, y, z - size / 2, var10, var16);
		var5.addVertexWithUV(x - size / 2, y, z - size / 2, var10, var16);
		var5.addVertexWithUV(x + size / 2, y, z - size / 2, var12, var16);
		var5.addVertexWithUV(x + size / 2, y, z + size / 2, var12, var14);
		var5.addVertexWithUV(x - size / 2, y, z + size / 2, var10, var14);
	}
}

package luxcraft;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StringUtils;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityLinkingChestRenderer extends TileEntitySpecialRenderer
{
	/** The normal small chest model. */
	private ModelChest chestModel = new ModelChest();

	/**
	 * Renders the TileEntity for the chest at a position.
	 */
	public void renderTileEntityChestAt(
			TileEntityLinkingChest par1TileEntityChest, double par2,
			double par4, double par6, float par8)
	{
		int var9=0;

		ModelChest var14;
		var14 = this.chestModel;
		this.bindTextureByName("/item/chest.png");
		GL11.glPushMatrix();
//		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		//		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float) par2, (float) par4 + 1.0F, (float) par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		short var11 = 0;

		GL11.glRotatef((float) var11, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		float var12 = par1TileEntityChest.prevLidAngle
				+ (par1TileEntityChest.lidAngle - par1TileEntityChest.prevLidAngle)
				* par8;
		float var13;
		var12 = 1.0F - var12;
		var12 = 1.0F - var12 * var12 * var12;
		var14.chestLid.rotateAngleX = -(var12 * (float) Math.PI / 2.0F);
		GL11.glColor4f(1.0F, 0.5F, 1.0F, 1.0F);
		var14.renderAll();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		

		if (par1TileEntityChest.owner != null
				&& par1TileEntityChest.owner.length() > 0)
		{
			String vStr = "http://skins.minecraft.net/MinecraftSkins/"
					+ StringUtils.stripControlCodes(par1TileEntityChest.owner)
					+ ".png";

			if (!this.tileEntityRenderer.renderEngine.hasImageData(vStr))
			{
				this.tileEntityRenderer.renderEngine.obtainImageData(vStr,
						new ImageBufferDownload());
			}

			this.bindTextureByURL(vStr, "/mob/char.png");
		}
		else
		{
			this.bindTextureByName("/mob/char.png");
		}

		GL11.glDisable(GL11.GL_CULL_FACE);
		float var10 = 0.0625F;
		Tessellator var5 = Tessellator.instance;
		if(par1TileEntityChest.worldObj != null){
			int var223 = par1TileEntityChest.worldObj
					.getLightBrightnessForSkyBlocks(par1TileEntityChest.xCoord,
							par1TileEntityChest.yCoord, par1TileEntityChest.zCoord,
							0);
			int var224 = var223 % 65536;
			int var225 = var223 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,
					(float) var224 / 1.0F, (float) var225 / 1.0F);
		}
		float border = 0.2F;
		GL11.glScalef(1.0F, 1.0F, 1.0F);
		GL11.glTranslatef(0, 6.995F / 16, 1 - 0.995F / 16);
		GL11.glScalef(1.0F, 1.0F, -1.0F);
		GL11.glPushMatrix();
		GL11.glRotatef(var12 * 90, 1, 0, 0);
		float y = -5.0F / 16.0F;
		var5.startDrawingQuads();
		var5.addVertexWithUV(border + 1.0F / 16, y, border, 0.125D, 0.25D);
		var5.addVertexWithUV(1 - border - 1.0F / 16, y, border, 0.249999D,
				0.25D);
		var5.addVertexWithUV(1 - border - 1.0F / 16, y, 1 - 2.0F / 16 - border,
				0.249999D, 0.49999D);
		var5.addVertexWithUV(border + 1.0F / 16, y, 1 - 2.0F / 16 - border,
				0.125D, 0.49999D);
		var5.draw();
		GL11.glPopMatrix();
		GL11.glTranslatef(0, -0.005F, -0.005F);
		border = (1 - 2 / 16 - (1 - 2 / 16 - 2 * border) * 9.0F / 8.0F) / 2;
		GL11.glPushMatrix();
		GL11.glRotatef(var12 * 90, 1, 0, 0);
		var5.startDrawingQuads();
		var5.addVertexWithUV(border + 1.0F / 16, y, border, 0.625D, 0.25D);
		var5.addVertexWithUV(1 - border - 1.0F / 16, y, border, 0.749999D, 0.25D);
		var5.addVertexWithUV(1 - border - 1.0F / 16, y, 1 - 2.0F / 16 - border, 0.749999D, 0.49999D);
		var5.addVertexWithUV(border + 1.0F / 16, y, 1 - 2.0F / 16 - border, 0.625D, 0.49999D);
		var5.draw();
		GL11.glPopMatrix();
		GL11.glScalef(1.0F, 1.0F, 1.0F);
		GL11.glTranslatef(0, -16.0F / 16.0F, 0);
//		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glTranslatef(-0.5F, -0.8F, -0.5F);
		GL11.glPopMatrix();
	}

	public void renderTileEntityAt(TileEntity par1TileEntity, double par2,
			double par4, double par6, float par8)
	{
		this.renderTileEntityChestAt((TileEntityLinkingChest) par1TileEntity,
				par2, par4, par6, par8);
	}
}

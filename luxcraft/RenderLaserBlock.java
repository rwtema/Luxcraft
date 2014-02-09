package luxcraft;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderLaserBlock implements ISimpleBlockRenderingHandler
{
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		Tessellator tes = Tessellator.instance;
		block.setBlockBoundsForItemRender();
		tes.startDrawingQuads();
		renderer.renderStandardBlock(block, 0, 0, 0);
		tes.draw();
		//renderer.func_83018_a(block);

//		int bx1=0,by1=0,bz1=0;
//		int bx2=0,by2=0,bz2=0;
//		int bx3=0,by3=0,bz3=0;
//		int tx1=0,ty1=0,tz1=0;
//		int tx2=0,ty2=0,tz2=0;
//		int tx3=0,ty3=0,tz3=0;		
//
//		bx1 = 0; by1 = 0; bz1 = 0;
//		bx2 = 1; by2 = 0; bz2 = 0;
//		bx3 = 0; by3 = 0; bz3 = 1;
//		tx1 = 0; ty1 = 1; tz1 = 0;
//		tx2 = 1; ty2 = 1; tz2 = 0;
//		tx3 = 0; ty3 = 1; tz3 = 1;
//
//		GL11.glRotatef(90.0F, 0.0F, -1.0F, 0.0F);
//		GL11.glRotatef(90.0F, -1.0F, 0.0F, 0.0F);
//		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);



	}

	public boolean shouldRender3DInInventory()
	{
		// This is where it asks if you want the renderInventory part called or not.
		return false; // Change to 'true' if you want the Inventory render to be called.
	}

	public int getRenderId()
	{
		// This is one place we need that renderId from earlier.
		return LuxcraftClient.laserBlockRenderingID;
	}

	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		Tessellator tes = Tessellator.instance;
		int texture = block.blockIndexInTexture;
		int tx = (texture & 15) << 4;
		int ty = texture & 240;

		double var10 = (double)((float)tx / 256.0F);
		double var12 = (double)(((float)tx + 15.99F) / 256.0F);
		double var14 = (double)((float)ty / 256.0F);
		double var16 = (double)(((float)ty + 15.99F) / 256.0F);
		
		int b = block.getMixedBrightnessForBlock(world, x, y, z);

		tes.setColorOpaque_I(0xffffff);
		tes.setBrightness(b);
		
		renderer.setRenderBoundsFromBlock(block);
		renderer.clearOverrideBlockTexture();
		return renderer.renderStandardBlock(block, x, y, z);
	}
}
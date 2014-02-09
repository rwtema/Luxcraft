package luxcraft;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderLinkingChest implements ISimpleBlockRenderingHandler
{
	private TileEntityLinkingChest theChest = new TileEntityLinkingChest();

	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		TileEntityRenderer.instance.renderTileEntityAt(this.theChest, 0, 0, 0, 0);
		GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);		
		return;
	}

	public boolean renderWorldBlock(IBlockAccess world, int par2, int par3, int par4, Block par1Block, int modelId, RenderBlocks renderer)
	{
		return true;
	}

	public boolean shouldRender3DInInventory()
	{
		// This is where it asks if you want the renderInventory part called or not.
		return true; // Change to 'true' if you want the Inventory render to be called.
	}

	public int getRenderId()
	{
		// This is one place we need that renderId from earlier.
		return LuxcraftClient.linkingChestID;
	}
}
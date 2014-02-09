package luxcraft;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderPrism implements ISimpleBlockRenderingHandler
{
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		Tessellator tes = Tessellator.instance;
		block.setBlockBoundsForItemRender();
		//renderer.func_83018_a(block);

		int bx1=0,by1=0,bz1=0;
		int bx2=0,by2=0,bz2=0;
		int bx3=0,by3=0,bz3=0;
		int tx1=0,ty1=0,tz1=0;
		int tx2=0,ty2=0,tz2=0;
		int tx3=0,ty3=0,tz3=0;		

		bx1 = 0; by1 = 0; bz1 = 0;
		bx2 = 1; by2 = 0; bz2 = 0;
		bx3 = 0; by3 = 0; bz3 = 1;
		tx1 = 0; ty1 = 1; tz1 = 0;
		tx2 = 1; ty2 = 1; tz2 = 0;
		tx3 = 0; ty3 = 1; tz3 = 1;

		GL11.glRotatef(90.0F, 0.0F, -1.0F, 0.0F);
		GL11.glRotatef(90.0F, -1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);


		tes.startDrawingQuads();
		tes.setNormal(0, 0, 1);
		tes.addVertexWithUV(tx1, ty1, tz1, tx1(6), ty1(6));
		tes.addVertexWithUV(tx2, ty2, tz2, tx2(6), ty1(6));
		tes.addVertexWithUV(bx2, by2, bz2, tx2(6), ty2(6));
		tes.addVertexWithUV(bx1, by1, bz1, tx1(6), ty2(6));
		tes.draw();

		tes.startDrawingQuads();
		tes.setNormal(1, 0, 0);		
		tes.addVertexWithUV(bx3, by3, bz3, tx2(6), ty2(6));
		tes.addVertexWithUV(tx3, ty3, tz3, tx2(6), ty1(6));
		tes.addVertexWithUV(tx1, ty1, tz1, tx1(6), ty1(6));
		tes.addVertexWithUV(bx1, by1, bz1, tx1(6), ty2(6));
		tes.draw();

		tes.startDrawingQuads();
		tes.setNormal(0, 1, 0);		
		tes.addVertexWithUV(tx3, ty3, tz3, tx2(7), ty2(7));
		tes.addVertexWithUV(tx2, ty2, tz2, tx1(7), ty1(7));
		tes.addVertexWithUV(tx2, ty2, tz2, tx1(7), ty1(7));
		tes.addVertexWithUV(tx1, ty1, tz1, tx1(7), ty2(7));
		tes.draw();

		tes.startDrawingQuads();
		tes.setNormal(0, -1, 0);		
		tes.addVertexWithUV(bx1, by1, bz1, tx1(7), ty2(7));
		tes.addVertexWithUV(bx2, by2, bz2, tx1(7), ty1(7));
		tes.addVertexWithUV(bx2, by2, bz2, tx1(7), ty1(7));
		tes.addVertexWithUV(bx3, by3, bz3, tx2(7), ty2(7));
		tes.draw();

		tes.startDrawingQuads();
		tes.setNormal(1, 0, 1);
		tes.addVertexWithUV(bx2, by2, bz2, tx1(8), ty2(8));
		tes.addVertexWithUV(tx2, ty2, tz2, tx1(8), ty1(8));
		tes.addVertexWithUV(tx3, ty3, tz3, tx2(8), ty1(8));
		tes.addVertexWithUV(bx3, by3, bz3, tx2(8), ty2(8));
		tes.draw();

	}

	public boolean shouldRender3DInInventory()
	{
		// This is where it asks if you want the renderInventory part called or not.
		return true; // Change to 'true' if you want the Inventory render to be called.
	}

	public int getRenderId()
	{
		// This is one place we need that renderId from earlier.
		return LuxcraftClient.prismRenderingID;
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

		//renderer.renderStandardBlock(block, x, y, z);

		//        int var223 = world.getLightBrightnessForSkyBlocks(x, y, z, 0);
		//        int var224 = var223 % 65536;
		//        int var225 = var223 / 65536;
		//        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var224 / 1.0F, (float)var225 / 1.0F);
		//        
		int b = block.getMixedBrightnessForBlock(world, x, y, z);

		tes.setColorOpaque_I(0xffffff);
		tes.setBrightness(b);

		int bx1=0,by1=0,bz1=0;
		int bx2=0,by2=0,bz2=0;
		int bx3=0,by3=0,bz3=0;
		int tx1=0,ty1=0,tz1=0;
		int tx2=0,ty2=0,tz2=0;
		int tx3=0,ty3=0,tz3=0;

		tes.addTranslation(x, y, z);

		int data = world.getBlockMetadata(x, y, z);

		int side1 = ((BlockPrism)Luxcraft.prism).getOutSide1(data);
		int side2 = ((BlockPrism)Luxcraft.prism).getOutSide2(data);

		int dx1 = Facing.offsetsXForSide[side1], dy1 = Facing.offsetsYForSide[side1], dz1 = Facing.offsetsZForSide[side1];
		int dx2 = Facing.offsetsXForSide[side2], dy2 = Facing.offsetsYForSide[side2], dz2 = Facing.offsetsZForSide[side2];

		switch(data){
		case 0:
			bx1 = 1; by1 = 0; bz1 = 1;
			bx2 = 0; by2 = 0; bz2 = 1;
			bx3 = 1; by3 = 0; bz3 = 0;
			tx1 = 1; ty1 = 1; tz1 = 1;
			tx2 = 0; ty2 = 1; tz2 = 1;
			tx3 = 1; ty3 = 1; tz3 = 0;
			break;

		case 1:
			bx1 = 0; by1 = 0; bz1 = 0;
			bx2 = 1; by2 = 0; bz2 = 0;
			bx3 = 0; by3 = 0; bz3 = 1;
			tx1 = 0; ty1 = 1; tz1 = 0;
			tx2 = 1; ty2 = 1; tz2 = 0;
			tx3 = 0; ty3 = 1; tz3 = 1;
			break;

		case 2:
			bx1 = 1; by1 = 1; bz1 = 0;
			bx2 = 0; by2 = 1; bz2 = 0;
			bx3 = 1; by3 = 1; bz3 = 1;
			tx1 = 1; ty1 = 0; tz1 = 0;
			tx2 = 0; ty2 = 0; tz2 = 0;
			tx3 = 1; ty3 = 0; tz3 = 1;
			break;

		case 3:
			bx1 = 0; by1 = 1; bz1 = 1;
			bx2 = 1; by2 = 1; bz2 = 1;
			bx3 = 0; by3 = 1; bz3 = 0;
			tx1 = 0; ty1 = 0; tz1 = 1;
			tx2 = 1; ty2 = 0; tz2 = 1;
			tx3 = 0; ty3 = 0; tz3 = 0;
			break;

		case 10:
			bx1 = 1; bz1 = 1; by1 = 1;
			bx2 = 0; bz2 = 1; by2 = 1;
			bx3 = 1; bz3 = 1; by3 = 0;
			tx1 = 1; tz1 = 0; ty1 = 1;
			tx2 = 0; tz2 = 0; ty2 = 1;
			tx3 = 1; tz3 = 0; ty3 = 0;
			break;

		case 7:
			bx1 = 0; bz1 = 1; by1 = 0;
			bx2 = 1; bz2 = 1; by2 = 0;
			bx3 = 0; bz3 = 1; by3 = 1;
			tx1 = 0; tz1 = 0; ty1 = 0;
			tx2 = 1; tz2 = 0; ty2 = 0;
			tx3 = 0; tz3 = 0; ty3 = 1;
			break;

		case 6:
			bx1 = 1; bz1 = 0; by1 = 0;
			bx2 = 0; bz2 = 0; by2 = 0;
			bx3 = 1; bz3 = 0; by3 = 1;
			tx1 = 1; tz1 = 1; ty1 = 0;
			tx2 = 0; tz2 = 1; ty2 = 0;
			tx3 = 1; tz3 = 1; ty3 = 1;
			break;

		case 11:
			bx1 = 0; bz1 = 0; by1 = 1;
			bx2 = 1; bz2 = 0; by2 = 1;
			bx3 = 0; bz3 = 0; by3 = 0;
			tx1 = 0; tz1 = 1; ty1 = 1;
			tx2 = 1; tz2 = 1; ty2 = 1;
			tx3 = 0; tz3 = 1; ty3 = 0;
			break;

		case 8:
			by1 = 1; bx1 = 1; bz1 = 1;
			by2 = 0; bx2 = 1; bz2 = 1;
			by3 = 1; bx3 = 1; bz3 = 0;
			ty1 = 1; tx1 = 0; tz1 = 1;
			ty2 = 0; tx2 = 0; tz2 = 1;
			ty3 = 1; tx3 = 0; tz3 = 0;
			break;

		case 5:
			by1 = 0; bx1 = 1; bz1 = 0;
			by2 = 1; bx2 = 1; bz2 = 0;
			by3 = 0; bx3 = 1; bz3 = 1;
			ty1 = 0; tx1 = 0; tz1 = 0;
			ty2 = 1; tx2 = 0; tz2 = 0;
			ty3 = 0; tx3 = 0; tz3 = 1;
			break;

		case 9:
			by1 = 1; bx1 = 0; bz1 = 0;
			by2 = 0; bx2 = 0; bz2 = 0;
			by3 = 1; bx3 = 0; bz3 = 1;
			ty1 = 1; tx1 = 1; tz1 = 0;
			ty2 = 0; tx2 = 1; tz2 = 0;
			ty3 = 1; tx3 = 1; tz3 = 1;
			break;

		case 4:
			by1 = 0; bx1 = 0; bz1 = 1;
			by2 = 1; bx2 = 0; bz2 = 1;
			by3 = 0; bx3 = 0; bz3 = 0;
			ty1 = 0; tx1 = 1; tz1 = 1;
			ty2 = 1; tx2 = 1; tz2 = 1;
			ty3 = 0; tx3 = 1; tz3 = 0;
			break;


		default:
		}





		tes.addVertexWithUV(tx1, ty1, tz1, tx1(6), ty1(6));
		tes.addVertexWithUV(tx2, ty2, tz2, tx2(6), ty1(6));
		tes.addVertexWithUV(bx2, by2, bz2, tx2(6), ty2(6));
		tes.addVertexWithUV(bx1, by1, bz1, tx1(6), ty2(6));

		tes.addVertexWithUV(bx3, by3, bz3, tx2(6), ty2(6));
		tes.addVertexWithUV(tx3, ty3, tz3, tx2(6), ty1(6));
		tes.addVertexWithUV(tx1, ty1, tz1, tx1(6), ty1(6));
		tes.addVertexWithUV(bx1, by1, bz1, tx1(6), ty2(6));

		tes.addVertexWithUV(tx3, ty3, tz3, tx2(7), ty2(7));
		tes.addVertexWithUV(tx2, ty2, tz2, tx1(7), ty1(7));
		tes.addVertexWithUV(tx2, ty2, tz2, tx1(7), ty1(7));
		tes.addVertexWithUV(tx1, ty1, tz1, tx1(7), ty2(7));

		tes.addVertexWithUV(bx1, by1, bz1, tx1(7), ty2(7));
		tes.addVertexWithUV(bx2, by2, bz2, tx1(7), ty1(7));
		tes.addVertexWithUV(bx2, by2, bz2, tx1(7), ty1(7));
		tes.addVertexWithUV(bx3, by3, bz3, tx2(7), ty2(7));

		tes.addVertexWithUV(bx2, by2, bz2, tx1(8), ty2(8));
		tes.addVertexWithUV(tx2, ty2, tz2, tx1(8), ty1(8));
		tes.addVertexWithUV(tx3, ty3, tz3, tx2(8), ty1(8));
		tes.addVertexWithUV(bx3, by3, bz3, tx2(8), ty2(8));

		tes.addTranslation(-x, -y, -z);

		//renderPrism();

		return true;
	}

	public void renderPrism(){
		Tessellator tes = Tessellator.instance;

		tes.addVertexWithUV(1, 0, 0, tx1(6), ty1(6));
		tes.addVertexWithUV(1, 0, 1, tx1(6), ty2(6));
		tes.addVertexWithUV(0, 0, 1, tx2(6), ty2(6));
		tes.addVertexWithUV(0, 0, 0, tx2(6), ty1(6));

		tes.addVertexWithUV(1, 0, 0, tx1(1), ty1(1));
		tes.addVertexWithUV(1, 1, 0, tx1(1), ty2(1));
		tes.addVertexWithUV(0, 1, 0, tx2(1), ty2(1));
		tes.addVertexWithUV(0, 0, 0, tx2(1), ty1(1));    	

	}

	public double tx1(int texture){return (double)((float)((texture & 15) << 4) / 256.0F);}
	public double tx2(int texture){return (double)(((float)((texture & 15) << 4) + 15.99F) / 256.0F);}
	public double ty1(int texture){return (double)((float)(texture & 240) / 256.0F);}
	public double ty2(int texture){return (double)(((float)(texture & 240) + 15.99F) / 256.0F);}    

//	public void renderTiledFace(double x1, double x2, double x3, double x4, double y1, double y2, double y3, double y4, double z1, double z2, double z3, double z4, Block block, int texture, RenderBlocks renderer)
//	{
//		Tessellator var9 = Tessellator.instance;
//		int var10 = (texture & 15) << 4;
//		int var11 = texture & 240;
//		double var12 = ((double)var10 + renderer.field_83021_g * 16.0D) / 256.0D;
//		double var14 = ((double)var10 + renderer.field_83026_h * 16.0D - 0.01D) / 256.0D;
//		double var16 = ((double)var11 + renderer.field_83025_k * 16.0D) / 256.0D;
//		double var18 = ((double)var11 + renderer.field_83022_l * 16.0D - 0.01D) / 256.0D;
//		double mx = (x1 + x2 + x3 + x4) / 4;
//		double my = (y1 + y2 + y3 + y4) / 4;
//		double mz = (z1 + z2 + z3 + z4) / 4;
//		double dmx = (var12 + var14) / 2;
//		double dmy = (var16 + var18) / 2;
//		double dx1 = var12;
//		double dy1 = var16;
//		double dx2 = var14;
//		double dy2 = var16;
//		var9.addVertexWithUV(x1, y1, z1, dx1, dy1);
//		var9.addVertexWithUV((x1 + x2) / 2, (y1 + y2) / 2, (z1 + z2) / 2, (dx1 + dx2) / 2, (dy1 + dy2) / 2);
//		var9.addVertexWithUV((x1 + x2) / 2, (y1 + y2) / 2, (z1 + z2) / 2, (dx1 + dx2) / 2, (dy1 + dy2) / 2);
//		var9.addVertexWithUV(mx, my, mz, dmx, dmy);
//		var9.addVertexWithUV((x1 + x2) / 2, (y1 + y2) / 2, (z1 + z2) / 2, (dx1 + dx2) / 2, (dy1 + dy2) / 2);
//		var9.addVertexWithUV((x1 + x2) / 2, (y1 + y2) / 2, (z1 + z2) / 2, (dx1 + dx2) / 2, (dy1 + dy2) / 2);
//		var9.addVertexWithUV(x2, y2, z2, dx1, dy1);
//		var9.addVertexWithUV(mx, my, mz, dmx, dmy);
//		var9.addVertexWithUV((x3 + x2) / 2, (y3 + y2) / 2, (z3 + z2) / 2, (dx1 + dx2) / 2, (dy1 + dy2) / 2);
//		var9.addVertexWithUV((x3 + x2) / 2, (y3 + y2) / 2, (z3 + z2) / 2, (dx1 + dx2) / 2, (dy1 + dy2) / 2);
//		var9.addVertexWithUV(x3, y3, z3, dx1, dy1);
//		var9.addVertexWithUV(mx, my, mz, dmx, dmy);
//		var9.addVertexWithUV(x2, y2, z2, dx1, dy1);
//		var9.addVertexWithUV((x3 + x2) / 2, (y3 + y2) / 2, (z3 + z2) / 2, (dx1 + dx2) / 2, (dy1 + dy2) / 2);
//		var9.addVertexWithUV((x3 + x2) / 2, (y3 + y2) / 2, (z3 + z2) / 2, (dx1 + dx2) / 2, (dy1 + dy2) / 2);
//		var9.addVertexWithUV(mx, my, mz, dmx, dmy);
//		var9.addVertexWithUV((x3 + x4) / 2, (y3 + y4) / 2, (z3 + z4) / 2, (dx1 + dx2) / 2, (dy1 + dy2) / 2);
//		var9.addVertexWithUV((x3 + x4) / 2, (y3 + y4) / 2, (z3 + z4) / 2, (dx1 + dx2) / 2, (dy1 + dy2) / 2);
//		var9.addVertexWithUV(x4, y4, z4, dx1, dy1);
//		var9.addVertexWithUV(mx, my, mz, dmx, dmy);
//		var9.addVertexWithUV(x3, y3, z3, dx1, dy1);
//		var9.addVertexWithUV((x3 + x4) / 2, (y3 + y4) / 2, (z3 + z4) / 2, (dx1 + dx2) / 2, (dy1 + dy2) / 2);
//		var9.addVertexWithUV((x3 + x4) / 2, (y3 + y4) / 2, (z3 + z4) / 2, (dx1 + dx2) / 2, (dy1 + dy2) / 2);
//		var9.addVertexWithUV(mx, my, mz, dmx, dmy);
//		var9.addVertexWithUV((x1 + x4) / 2, (y1 + y4) / 2, (z1 + z4) / 2, (dx1 + dx2) / 2, (dy1 + dy2) / 2);
//		var9.addVertexWithUV((x1 + x4) / 2, (y1 + y4) / 2, (z1 + z4) / 2, (dx1 + dx2) / 2, (dy1 + dy2) / 2);
//		var9.addVertexWithUV(x1, y1, z1, dx1, dy1);
//		var9.addVertexWithUV(mx, my, mz, dmx, dmy);
//		var9.addVertexWithUV(x4, y4, z4, dx1, dy1);
//		var9.addVertexWithUV((x1 + x4) / 2, (y1 + y4) / 2, (z1 + z4) / 2, (dx1 + dx2) / 2, (dy1 + dy2) / 2);
//		var9.addVertexWithUV((x1 + x4) / 2, (y1 + y4) / 2, (z1 + z4) / 2, (dx1 + dx2) / 2, (dy1 + dy2) / 2);
//		var9.addVertexWithUV(mx, my, mz, dmx, dmy);
//	}
}
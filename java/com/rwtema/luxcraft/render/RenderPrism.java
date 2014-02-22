package com.rwtema.luxcraft.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import com.rwtema.luxcraft.Luxcraft;
import com.rwtema.luxcraft.LuxcraftClient;
import com.rwtema.luxcraft.block.BlockLuxReflector;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderPrism implements ISimpleBlockRenderingHandler {

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		Tessellator tes = Tessellator.instance;
		block.setBlockBoundsForItemRender();

		int bx1 = 0;
		int by1 = 0;
		int bz1 = 0;
		int bx2 = 1;
		int by2 = 0;
		int bz2 = 0;
		int bx3 = 0;
		int by3 = 0;
		int bz3 = 1;
		int tx1 = 0;
		int ty1 = 1;
		int tz1 = 0;
		int tx2 = 1;
		int ty2 = 1;
		int tz2 = 0;
		int tx3 = 0;
		int ty3 = 1;
		int tz3 = 1;

		GL11.glRotatef(90.0F, 0.0F, -1.0F, 0.0F);
		GL11.glRotatef(90.0F, -1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		tes.startDrawingQuads();
		tes.setNormal(0, 0, 1);
		tes.addVertexWithUV(tx1, ty1, tz1, tx1(Luxcraft.luxReflector.icons[0]), ty1(Luxcraft.luxReflector.icons[0]));
		tes.addVertexWithUV(tx2, ty2, tz2, tx2(Luxcraft.luxReflector.icons[0]), ty1(Luxcraft.luxReflector.icons[0]));
		tes.addVertexWithUV(bx2, by2, bz2, tx2(Luxcraft.luxReflector.icons[0]), ty2(Luxcraft.luxReflector.icons[0]));
		tes.addVertexWithUV(bx1, by1, bz1, tx1(Luxcraft.luxReflector.icons[0]), ty2(Luxcraft.luxReflector.icons[0]));
		tes.draw();

		tes.startDrawingQuads();
		tes.setNormal(1, 0, 0);
		tes.addVertexWithUV(bx3, by3, bz3, tx2(Luxcraft.luxReflector.icons[0]), ty2(Luxcraft.luxReflector.icons[0]));
		tes.addVertexWithUV(tx3, ty3, tz3, tx2(Luxcraft.luxReflector.icons[0]), ty1(Luxcraft.luxReflector.icons[0]));
		tes.addVertexWithUV(tx1, ty1, tz1, tx1(Luxcraft.luxReflector.icons[0]), ty1(Luxcraft.luxReflector.icons[0]));
		tes.addVertexWithUV(bx1, by1, bz1, tx1(Luxcraft.luxReflector.icons[0]), ty2(Luxcraft.luxReflector.icons[0]));
		tes.draw();

		tes.startDrawingQuads();
		tes.setNormal(0, 1, 0);
		tes.addVertexWithUV(tx3, ty3, tz3, tx2(Luxcraft.luxReflector.icons[1]), ty2(Luxcraft.luxReflector.icons[1]));
		tes.addVertexWithUV(tx2, ty2, tz2, tx1(Luxcraft.luxReflector.icons[1]), ty1(Luxcraft.luxReflector.icons[1]));
		tes.addVertexWithUV(tx2, ty2, tz2, tx1(Luxcraft.luxReflector.icons[1]), ty1(Luxcraft.luxReflector.icons[1]));
		tes.addVertexWithUV(tx1, ty1, tz1, tx1(Luxcraft.luxReflector.icons[1]), ty2(Luxcraft.luxReflector.icons[1]));
		tes.draw();

		tes.startDrawingQuads();
		tes.setNormal(0, -1, 0);
		tes.addVertexWithUV(bx1, by1, bz1, tx1(Luxcraft.luxReflector.icons[1]), ty2(Luxcraft.luxReflector.icons[1]));
		tes.addVertexWithUV(bx2, by2, bz2, tx1(Luxcraft.luxReflector.icons[1]), ty1(Luxcraft.luxReflector.icons[1]));
		tes.addVertexWithUV(bx2, by2, bz2, tx1(Luxcraft.luxReflector.icons[1]), ty1(Luxcraft.luxReflector.icons[1]));
		tes.addVertexWithUV(bx3, by3, bz3, tx2(Luxcraft.luxReflector.icons[1]), ty2(Luxcraft.luxReflector.icons[1]));
		tes.draw();

		tes.startDrawingQuads();
		tes.setNormal(1, 0, 1);
		tes.addVertexWithUV(bx2, by2, bz2, tx1(Luxcraft.luxReflector.icons[2]), ty2(Luxcraft.luxReflector.icons[2]));
		tes.addVertexWithUV(tx2, ty2, tz2, tx1(Luxcraft.luxReflector.icons[2]), ty1(Luxcraft.luxReflector.icons[2]));
		tes.addVertexWithUV(tx3, ty3, tz3, tx2(Luxcraft.luxReflector.icons[2]), ty1(Luxcraft.luxReflector.icons[2]));
		tes.addVertexWithUV(bx3, by3, bz3, tx2(Luxcraft.luxReflector.icons[2]), ty2(Luxcraft.luxReflector.icons[2]));
		tes.draw();

	}

	public int getRenderId() {
		// This is one place we need that renderId from earlier.
		return LuxcraftClient.prismRenderingID;
	}

	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		Tessellator tes = Tessellator.instance;
		int texture = 0;
		int tx = (texture & 15) << 4;
		int ty = texture & 240;

		int b = block.getMixedBrightnessForBlock(world, x, y, z);

		tes.setColorOpaque_I(0xffffff);
		tes.setBrightness(b);

		int bx1 = 0, by1 = 0, bz1 = 0;
		int bx2 = 0, by2 = 0, bz2 = 0;
		int bx3 = 0, by3 = 0, bz3 = 0;
		int tx1 = 0, ty1 = 0, tz1 = 0;
		int tx2 = 0, ty2 = 0, tz2 = 0;
		int tx3 = 0, ty3 = 0, tz3 = 0;

		tes.addTranslation(x, y, z);

		int data = world.getBlockMetadata(x, y, z);

		int side1 = BlockLuxReflector.getSide1(data);
		int side2 = BlockLuxReflector.getSide2(data);

		int dx1 = Facing.offsetsXForSide[side1], dy1 = Facing.offsetsYForSide[side1], dz1 = Facing.offsetsZForSide[side1];
		int dx2 = Facing.offsetsXForSide[side2], dy2 = Facing.offsetsYForSide[side2], dz2 = Facing.offsetsZForSide[side2];

		switch (data) {
		case 0:
			bx1 = 1;
			by1 = 0;
			bz1 = 1;
			bx2 = 0;
			by2 = 0;
			bz2 = 1;
			bx3 = 1;
			by3 = 0;
			bz3 = 0;
			tx1 = 1;
			ty1 = 1;
			tz1 = 1;
			tx2 = 0;
			ty2 = 1;
			tz2 = 1;
			tx3 = 1;
			ty3 = 1;
			tz3 = 0;
			break;

		case 1:
			bx1 = 0;
			by1 = 0;
			bz1 = 0;
			bx2 = 1;
			by2 = 0;
			bz2 = 0;
			bx3 = 0;
			by3 = 0;
			bz3 = 1;
			tx1 = 0;
			ty1 = 1;
			tz1 = 0;
			tx2 = 1;
			ty2 = 1;
			tz2 = 0;
			tx3 = 0;
			ty3 = 1;
			tz3 = 1;
			break;

		case 2:
			bx1 = 1;
			by1 = 1;
			bz1 = 0;
			bx2 = 0;
			by2 = 1;
			bz2 = 0;
			bx3 = 1;
			by3 = 1;
			bz3 = 1;
			tx1 = 1;
			ty1 = 0;
			tz1 = 0;
			tx2 = 0;
			ty2 = 0;
			tz2 = 0;
			tx3 = 1;
			ty3 = 0;
			tz3 = 1;
			break;

		case 3:
			bx1 = 0;
			by1 = 1;
			bz1 = 1;
			bx2 = 1;
			by2 = 1;
			bz2 = 1;
			bx3 = 0;
			by3 = 1;
			bz3 = 0;
			tx1 = 0;
			ty1 = 0;
			tz1 = 1;
			tx2 = 1;
			ty2 = 0;
			tz2 = 1;
			tx3 = 0;
			ty3 = 0;
			tz3 = 0;
			break;

		case 10:
			bx1 = 1;
			bz1 = 1;
			by1 = 1;
			bx2 = 0;
			bz2 = 1;
			by2 = 1;
			bx3 = 1;
			bz3 = 1;
			by3 = 0;
			tx1 = 1;
			tz1 = 0;
			ty1 = 1;
			tx2 = 0;
			tz2 = 0;
			ty2 = 1;
			tx3 = 1;
			tz3 = 0;
			ty3 = 0;
			break;

		case 7:
			bx1 = 0;
			bz1 = 1;
			by1 = 0;
			bx2 = 1;
			bz2 = 1;
			by2 = 0;
			bx3 = 0;
			bz3 = 1;
			by3 = 1;
			tx1 = 0;
			tz1 = 0;
			ty1 = 0;
			tx2 = 1;
			tz2 = 0;
			ty2 = 0;
			tx3 = 0;
			tz3 = 0;
			ty3 = 1;
			break;

		case 6:
			bx1 = 1;
			bz1 = 0;
			by1 = 0;
			bx2 = 0;
			bz2 = 0;
			by2 = 0;
			bx3 = 1;
			bz3 = 0;
			by3 = 1;
			tx1 = 1;
			tz1 = 1;
			ty1 = 0;
			tx2 = 0;
			tz2 = 1;
			ty2 = 0;
			tx3 = 1;
			tz3 = 1;
			ty3 = 1;
			break;

		case 11:
			bx1 = 0;
			bz1 = 0;
			by1 = 1;
			bx2 = 1;
			bz2 = 0;
			by2 = 1;
			bx3 = 0;
			bz3 = 0;
			by3 = 0;
			tx1 = 0;
			tz1 = 1;
			ty1 = 1;
			tx2 = 1;
			tz2 = 1;
			ty2 = 1;
			tx3 = 0;
			tz3 = 1;
			ty3 = 0;
			break;

		case 8:
			by1 = 1;
			bx1 = 1;
			bz1 = 1;
			by2 = 0;
			bx2 = 1;
			bz2 = 1;
			by3 = 1;
			bx3 = 1;
			bz3 = 0;
			ty1 = 1;
			tx1 = 0;
			tz1 = 1;
			ty2 = 0;
			tx2 = 0;
			tz2 = 1;
			ty3 = 1;
			tx3 = 0;
			tz3 = 0;
			break;

		case 5:
			by1 = 0;
			bx1 = 1;
			bz1 = 0;
			by2 = 1;
			bx2 = 1;
			bz2 = 0;
			by3 = 0;
			bx3 = 1;
			bz3 = 1;
			ty1 = 0;
			tx1 = 0;
			tz1 = 0;
			ty2 = 1;
			tx2 = 0;
			tz2 = 0;
			ty3 = 0;
			tx3 = 0;
			tz3 = 1;
			break;

		case 9:
			by1 = 1;
			bx1 = 0;
			bz1 = 0;
			by2 = 0;
			bx2 = 0;
			bz2 = 0;
			by3 = 1;
			bx3 = 0;
			bz3 = 1;
			ty1 = 1;
			tx1 = 1;
			tz1 = 0;
			ty2 = 0;
			tx2 = 1;
			tz2 = 0;
			ty3 = 1;
			tx3 = 1;
			tz3 = 1;
			break;

		case 4:
			by1 = 0;
			bx1 = 0;
			bz1 = 1;
			by2 = 1;
			bx2 = 0;
			bz2 = 1;
			by3 = 0;
			bx3 = 0;
			bz3 = 0;
			ty1 = 0;
			tx1 = 1;
			tz1 = 1;
			ty2 = 1;
			tx2 = 1;
			tz2 = 1;
			ty3 = 0;
			tx3 = 1;
			tz3 = 0;
			break;

		default:
		}

		tes.addVertexWithUV(tx1, ty1, tz1, tx1(Luxcraft.luxReflector.icons[0]), ty1(Luxcraft.luxReflector.icons[0]));
		tes.addVertexWithUV(tx2, ty2, tz2, tx2(Luxcraft.luxReflector.icons[0]), ty1(Luxcraft.luxReflector.icons[0]));
		tes.addVertexWithUV(bx2, by2, bz2, tx2(Luxcraft.luxReflector.icons[0]), ty2(Luxcraft.luxReflector.icons[0]));
		tes.addVertexWithUV(bx1, by1, bz1, tx1(Luxcraft.luxReflector.icons[0]), ty2(Luxcraft.luxReflector.icons[0]));

		tes.addVertexWithUV(bx3, by3, bz3, tx2(Luxcraft.luxReflector.icons[0]), ty2(Luxcraft.luxReflector.icons[0]));
		tes.addVertexWithUV(tx3, ty3, tz3, tx2(Luxcraft.luxReflector.icons[0]), ty1(Luxcraft.luxReflector.icons[0]));
		tes.addVertexWithUV(tx1, ty1, tz1, tx1(Luxcraft.luxReflector.icons[0]), ty1(Luxcraft.luxReflector.icons[0]));
		tes.addVertexWithUV(bx1, by1, bz1, tx1(Luxcraft.luxReflector.icons[0]), ty2(Luxcraft.luxReflector.icons[0]));

		tes.addVertexWithUV(tx3, ty3, tz3, tx2(Luxcraft.luxReflector.icons[1]), ty2(Luxcraft.luxReflector.icons[1]));
		tes.addVertexWithUV(tx2, ty2, tz2, tx1(Luxcraft.luxReflector.icons[1]), ty1(Luxcraft.luxReflector.icons[1]));
		tes.addVertexWithUV(tx2, ty2, tz2, tx1(Luxcraft.luxReflector.icons[1]), ty1(Luxcraft.luxReflector.icons[1]));
		tes.addVertexWithUV(tx1, ty1, tz1, tx1(Luxcraft.luxReflector.icons[1]), ty2(Luxcraft.luxReflector.icons[1]));

		tes.addVertexWithUV(bx1, by1, bz1, tx1(Luxcraft.luxReflector.icons[1]), ty2(Luxcraft.luxReflector.icons[1]));
		tes.addVertexWithUV(bx2, by2, bz2, tx1(Luxcraft.luxReflector.icons[1]), ty1(Luxcraft.luxReflector.icons[1]));
		tes.addVertexWithUV(bx2, by2, bz2, tx1(Luxcraft.luxReflector.icons[1]), ty1(Luxcraft.luxReflector.icons[1]));
		tes.addVertexWithUV(bx3, by3, bz3, tx2(Luxcraft.luxReflector.icons[1]), ty2(Luxcraft.luxReflector.icons[1]));

		tes.addVertexWithUV(bx2, by2, bz2, tx1(Luxcraft.luxReflector.icons[2]), ty2(Luxcraft.luxReflector.icons[2]));
		tes.addVertexWithUV(tx2, ty2, tz2, tx1(Luxcraft.luxReflector.icons[2]), ty1(Luxcraft.luxReflector.icons[2]));
		tes.addVertexWithUV(tx3, ty3, tz3, tx2(Luxcraft.luxReflector.icons[2]), ty1(Luxcraft.luxReflector.icons[2]));
		tes.addVertexWithUV(bx3, by3, bz3, tx2(Luxcraft.luxReflector.icons[2]), ty2(Luxcraft.luxReflector.icons[2]));

		tes.addTranslation(-x, -y, -z);

		// renderPrism();

		return true;
	}

	public double tx1(IIcon texture) {
		return texture.getMinU();
	}

	public double tx2(IIcon texture) {
		return texture.getMaxU();
	}

	public double ty1(IIcon texture) {
		return texture.getMinV();
	}

	public double ty2(IIcon texture) {
		return texture.getMaxV();
	}
}

package com.rwtema.luxcraft.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import com.rwtema.luxcraft.item.ItemLuxSaber;
import com.rwtema.luxcraft.luxapi.LuxColor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRenderLuxSaber implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;

	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		double offset = -0.5;

		if (!(item.getItem() instanceof ItemLuxSaber))
			return;
		
		IIcon texture = item.getIconIndex();

		if (texture == null) {
			return;
		}

		if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			offset = 0;
		} else if (type == ItemRenderType.ENTITY) {
			GL11.glScalef(0.5F, 0.5F, 0.5F);
		}

		GL11.glTranslated(offset, offset, offset);

		if (type == ItemRenderType.EQUIPPED) {
			GL11.glTranslated(0.5F, 0.5F, 0.5F);
			GL11.glRotated(40, -1, 0, 1);
			GL11.glTranslated(-0.5F, -0.5F, -0.5F);
		}

		if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON)
			GL11.glDisable(GL11.GL_LIGHTING);

		RenderBlocks renderer = (RenderBlocks) data[0];

		Tessellator tessellator = RenderHelper.tessellator();
		Block block = Blocks.stone;

		renderer.overrideBlockBounds(0.375, 0, 0.375, 0.625, 1, 0.625);

		tessellator.startDrawingQuads();

		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, new SubIcon(texture, 0, 0, 2, 2));

		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, new SubIcon(texture, 0, 2, 2, 2));

		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, texture);

		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, texture);

		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, texture);

		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, texture);

		tessellator.draw();

		if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glTranslated(0, 1, 0);
			GL11.glScaled(1, 3, 1);

			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDepthMask(true);

			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glDisable(GL11.GL_LIGHTING);

			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);

			texture = ItemLuxSaber.laserBeam;

			GL11.glColor4d(1, 1, 1, 0.1);

			texture = ItemLuxSaber.laserBeam;

			renderer.overrideBlockBounds(0.4375, 0, 0.4375, 0.5625, 1 - 1 / 16 / 3, 0.5625);
			drawCube(renderer, block, texture);
			renderer.unlockBlockBounds();

			LuxColor c = ItemLuxSaber.getColor(item);

			GL11.glColor4d(c.r, c.g, c.b, 0.1);

			renderer.overrideBlockBounds(0.375, 0, 0.375, 0.625, 1, 0.625);
			drawCube(renderer, block, texture);
			renderer.unlockBlockBounds();

			GL11.glEnable(GL11.GL_LIGHTING);

			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glDepthMask(true);

			GL11.glDisable(GL11.GL_BLEND);
		}

		renderer.unlockBlockBounds();
		renderer.setRenderBounds(0, 0, 0, 1, 1, 1);

	}

	public void drawCube(RenderBlocks renderer, Block block, IIcon texture) {
		Tessellator tessellator = Tessellator.instance;

		tessellator.startDrawingQuads();

		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, texture);

		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, texture);

		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, texture);

		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, texture);

		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, texture);

		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, texture);

		tessellator.draw();

	}

}

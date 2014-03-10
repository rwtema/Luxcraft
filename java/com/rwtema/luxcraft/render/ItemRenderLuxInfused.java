package com.rwtema.luxcraft.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.rwtema.luxcraft.debug.CommandClientDebug;
import com.rwtema.luxcraft.item.ItemInfusedItems;

public class ItemRenderLuxInfused implements IItemRenderer {

	public static RenderItem itemRenderer = new RenderItem();

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
		ItemStack render = ItemInfusedItems.getRenderItem(item);

		double offset = -0.5;

		if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			offset = 0;
		} else if (type == ItemRenderType.ENTITY) {
			GL11.glScalef(0.5F, 0.5F, 0.5F);
		}

		if (render == null) {
			RenderHelper.renderItemAsBlock((RenderBlocks) data[0], item, offset, offset, offset);
		} else {
			double t = (float) (Minecraft.getSystemTime() % 6000L) / 6000.0F;

			GL11.glTranslated(offset, offset, offset);

			GL11.glPushMatrix();
			{
				double dt = 0.1;

				GL11.glTranslated(dt, dt, dt);
				GL11.glRotated(t * 360, 0, 1, 0);
				GL11.glScaled(0.8F, 0.8F, 0.8F);

				if (!(render.getItem() instanceof ItemBlock))
					return;

				Block block = ((ItemBlock) render.getItem()).field_150939_a;

				RenderHelper.setBlockTextureSheet();

				((RenderBlocks) data[0]).renderBlockAsItem(block, render.getItemDamage(), 1.0F);
			}
			GL11.glPopMatrix();

			for (int ang = 0; ang < 360; ang += 45) {
				GL11.glPushMatrix();
				{

					GL11.glRotatef(ang, 1, 0, 0);
					GL11.glRotatef(ang + (float) t * 720, 0, 1, 0);

					GL11.glTranslatef(0F, 0F, 0.8F);

					GL11.glScalef(0.1F, 0.1F, 0.1F);

					RenderHelper.setItemTextureSheet();
					RenderHelper.renderTextureAsBlock((RenderBlocks) data[0], ItemInfusedItems.glow, -0.5F, -0.5F, -0.5F);
				}
				GL11.glPopMatrix();
			}
		}

	}

}

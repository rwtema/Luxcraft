package com.rwtema.luxcraft.render;

import com.rwtema.luxcraft.StackHelper;
import com.rwtema.luxcraft.item.ItemInfusedItems;
import com.rwtema.luxcraft.luxapi.LuxColor;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import scala.util.Random;

public class ItemRenderLuxInfused implements IItemRenderer {

    public static final int[] ang = new int[]{0, 45, 90, 135, 180, 225, 270, 315};
    public static final LuxColor[] cols = new LuxColor[]{LuxColor.White, LuxColor.Red, LuxColor.Green, LuxColor.Blue, LuxColor.Black, LuxColor.Cyan, LuxColor.Violet, LuxColor.Yellow};
    public static RenderItem itemRenderer = new RenderItem();
    Random rand = new Random();

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

        if (StackHelper.isNull(render)) {
            RenderHelper.renderItemAsBlock((RenderBlocks) data[0], item, offset, offset, offset);
        } else {
            rand.setSeed(item.getTagCompound().hashCode());

            double t = (float) ((Minecraft.getSystemTime()) % 6000L) / 6000.0F;

            GL11.glTranslated(0.5F, 0.5F, 0.5F);
            GL11.glTranslated(offset, offset, offset);

            if (!(render.getItem() instanceof ItemBlock))
                return;

            GL11.glPushMatrix();
            {


                GL11.glRotated(t * 360, 0, 1, 0);

                GL11.glScaled(0.8F, 0.8F, 0.8F);

                Block block = ((ItemBlock) render.getItem()).field_150939_a;

                RenderHelper.setBlockTextureSheet();

                ((RenderBlocks) data[0]).renderBlockAsItem(block, render.getItemDamage(), 1.0F);
            }
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            {
                boolean isblending = GL11.glIsEnabled(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glColor4f(1F, 1, 1F, 0.1F);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_SRC_COLOR);

                GL11.glRotated(t * 360, 0, 1, 0);

                // GL11.glScaled(0.9F, 0.9F, 0.9F);

                GL11.glPushMatrix();
                {
                    // GL11.glScalef(0.9F, 0.9F, 0.9F);

                    Block block = ((ItemBlock) render.getItem()).field_150939_a;
                    ((RenderBlocks) data[0]).renderBlockAsItem(block, render.getItemDamage(), 1.0F);
                }
                GL11.glPopMatrix();

                RenderHelper.setItemTextureSheet();
                // RenderHelper.renderTextureAsBlock((RenderBlocks) data[0],
                // ItemInfusedItems.glow, -0.5F, -0.5F, -0.5F);

                if (!isblending)
                    GL11.glDisable(GL11.GL_BLEND);

                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

                GL11.glColor4f(1, 1, 1, 1);
            }
            GL11.glPopMatrix();

            int z = 1;

            int k = rand.nextInt(8);

            for (int i = 0; i < 8; i++) {
                GL11.glPushMatrix();
                {
                    z *= -1;
                    GL11.glRotatef(ang[i], 1, 0, 0);
                    GL11.glRotatef(rand.nextInt(360) + z * (float) t * 720, 0, 1, 0);

                    LuxColor col = cols[(i + k) % 8];
                    GL11.glColor4f(col.r, col.g, col.b, 1);

                    GL11.glTranslatef(0F, 0F, 0.8F);

                    GL11.glScalef(0.1F, 0.1F, 0.1F);

                    RenderHelper.renderTextureAsBlock((RenderBlocks) data[0], ItemInfusedItems.glow, -0.5F, -0.5F, -0.5F);
                }
                GL11.glPopMatrix();
            }
        }

    }

}

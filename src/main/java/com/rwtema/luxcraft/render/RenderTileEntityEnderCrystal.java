package com.rwtema.luxcraft.render;

import com.rwtema.luxcraft.tiles.TileEntityEnderCrystal;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderTileEntityEnderCrystal extends TileEntitySpecialRenderer {

    private static final ResourceLocation enderCrystalTextures = new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
    private static final RenderBlocks render = new RenderBlocks();
    private ModelEnderCrystal model;

    public RenderTileEntityEnderCrystal() {
        this.model = new ModelEnderCrystal(0.0F, true);
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float var8) {
        // float f2 = (float) par1EntityEnderCrystal.innerRotation + par9;

        float f2 = (((TileEntityEnderCrystal) tile).loop + var8) / 2;

        boolean rescale = GL11.glIsEnabled(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        // GL11.glTranslated(x - tile.xCoord, y - tile.yCoord, z - tile.zCoord);

        GL11.glPushMatrix();
        {
            GL11.glTranslated(x, y, z);

            GL11.glPushMatrix();
            {

                GL11.glTranslated(0.5, 0, 0.5);
                GL11.glScaled(0.25, 0.25, 0.25);
                GL11.glTranslated(-0.5, 0, -0.5);

                this.bindTexture(RenderHelper.MC_BLOCK_SHEET);

                RenderHelper.renderTextureAsBlock(render, Blocks.bedrock.getIcon(0, 0), 0, 0, 0, 1);
                GL11.glTranslated(0, 1, 0);

                RenderHelper.renderTextureAsBlock(render, Blocks.fire.getIcon(0, 0), 0, 0, 0, 3);

            }
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            {
                GL11.glTranslated(0.5, 0, 0.5);
                GL11.glTranslated(0, 0.25, 0);
                GL11.glScaled(0.25, 0.25, 0.25);

                this.bindTexture(enderCrystalTextures);
                float f3 = MathHelper.sin(f2 * 0.2F) / 2.0F + 0.5F;
                f3 += f3 * f3;
                this.model.render(null, 0.0F, f2 * 3.0F, f3 * 0.2F, 0.0F, 0.0F, 0.0625F);

                if (!rescale)
                    GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            }
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}

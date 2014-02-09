package luxcraft;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderSpike implements ISimpleBlockRenderingHandler
{
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        Tessellator var5 = Tessellator.instance;
        block.setBlockBoundsForItemRender();

        renderer.setRenderBoundsFromBlock(block);
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        int var6 = block.blockIndexInTexture;
        var6 = 22;
        int var7 = (var6 & 15) << 4;
        int var8 = var6 & 240;
        float var9 = 0.015625F;
        double var10 = (double)((float)var7 / 256.0F);
        double var11 = (double)(((float)var7 + 15.99F / 2) / 256.0F);
        double var12 = (double)(((float)var7 + 15.99F) / 256.0F);
        double var14 = (double)((float)var8 / 256.0F);
        double var16 = (double)(((float)var8 + 15.99F) / 256.0F);

        var5.setNormal(0.0F, 1.0F, 0.0F);
        double par2 = 0;
        double par3 = 0;
        double par4 = 0;
        int b = 527;
        int b2 = b;
        var5.startDrawingQuads();
        var5.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(1, metadata));
        var5.draw();
        var5.startDrawingQuads();
        var5.setNormal(0.0F, 0.9F, -0.45F);
        var5.addVertexWithUV(par2 + 0.5, par3 + 1, par4 + 0.5, var11, var14);
        var5.addVertexWithUV(par2 + 0.5, par3 + 1, par4 + 0.5, var11, var14);
        var5.addVertexWithUV(par2 + 1, par3, par4, var12, var16);
        var5.addVertexWithUV(par2, par3, par4, var10, var16);
        var5.draw();
        var5.startDrawingQuads();
        var5.setNormal(0.0F, 0.9F, 0.45F);
        var5.addVertexWithUV(par2, par3, par4 + 1, var10, var16);
        var5.addVertexWithUV(par2 + 1, par3, par4 + 1, var12, var16);
        var5.addVertexWithUV(par2 + 0.5, par3 + 1, par4 + 0.5, var11, var14);
        var5.addVertexWithUV(par2 + 0.5, par3 + 1, par4 + 0.5, var11, var14);
        var5.draw();
        var5.startDrawingQuads();
        var5.setNormal(-0.45F, 0.9F, 0.0F);
        var5.addVertexWithUV(par2, par3, par4, var10, var16);
        var5.addVertexWithUV(par2, par3, par4 + 1, var12, var16);
        var5.addVertexWithUV(par2 + 0.5, par3 + 1, par4 + 0.5, var11, var14);
        var5.addVertexWithUV(par2 + 0.5, par3 + 1, par4 + 0.5, var11, var14);
        var5.draw();
        var5.startDrawingQuads();
        var5.setNormal(0.45F, 0.9F, 0.0F);
        var5.addVertexWithUV(par2 + 0.5, par3 + 1, par4 + 0.5, var11, var14);
        var5.addVertexWithUV(par2 + 0.5, par3 + 1, par4 + 0.5, var11, var14);
        var5.addVertexWithUV(par2 + 1, par3, par4 + 1, var12, var16);
        var5.addVertexWithUV(par2 + 1, par3, par4, var10, var16);
        var5.draw();
        var5.startDrawingQuads();
        var5.setNormal(0.0F, -1.0F, 0.0F);
        var5.addVertexWithUV(par2, par3, par4, var10, var16);
        var5.addVertexWithUV(par2 + 1, par3, par4, var12, var16);
        var5.addVertexWithUV(par2 + 1, par3, par4 + 1, var12, var14);
        var5.addVertexWithUV(par2, par3, par4 + 1, var10, var14);
        var5.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
//        GL11.glDisable(32826);
//        GL11.glEnable(2896);
    }

    public boolean renderWorldBlock(IBlockAccess world, int par2, int par3, int par4, Block par1Block, int modelId, RenderBlocks renderer)
    {
        Tessellator var5 = Tessellator.instance;
        int var6 = par1Block.blockIndexInTexture;
        int var7 = (var6 & 15) << 4;
        int var8 = var6 & 240;
        float var9 = 0.015625F;
        double var10 = (double)((float)var7 / 256.0F);
        double var11 = (double)(((float)var7 + 15.99F / 2) / 256.0F);
        double var12 = (double)(((float)var7 + 15.99F) / 256.0F);
        double var14 = (double)((float)var8 / 256.0F);
        double var16 = (double)(((float)var8 + 15.99F) / 256.0F);
                
        int b = par1Block.getMixedBrightnessForBlock(world, par2, par3, par4);
        int b2 = (int)((double)b * 0.75);

        int var223 = world.getLightBrightnessForSkyBlocks(par2, par3, par4, 0);
        int var224 = var223 % 65536;
        int var225 = var223 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var224 / 1.0F, (float)var225 / 1.0F);
        
        double tip_x, tip_y, tip_z;
        double base1_x, base1_y, base1_z;
        double base2_x, base2_y, base2_z;
        double base3_x, base3_y, base3_z;
        double base4_x, base4_y, base4_z;
        tip_x = par2 + 0.5;
        tip_y = par3 + 1;
        tip_z = par4 + 0.5;
        base1_x = par2;
        base1_y = par3;
        base1_z = par4;
        base2_x = par2 + 1;
        base2_y = par3;
        base2_z = par4;
        base3_x = par2 + 1;
        base3_y = par3;
        base3_z = par4 + 1;
        base4_x = par2;
        base4_y = par3;
        base4_z = par4 + 1;
        int ang = world.getBlockMetadata(par2, par3, par4);
        int bloodcol = par1Block.getBlockColor();
        int basecol = par1Block.getBlockColor();
        basecol = 0xbbbbbb;
        b2 = b;

        if (ang >= 6)
        {
            ang = ang % 6;
        }

        if (ang == 0)
        {
            tip_x = par2 + 0.5;
            tip_y = par3;
            tip_z = par4 + 0.5;
            base4_x = par2;
            base4_y = par3 + 1;
            base4_z = par4;
            base3_x = par2 + 1;
            base3_y = par3 + 1;
            base3_z = par4;
            base2_x = par2 + 1;
            base2_y = par3 + 1;
            base2_z = par4 + 1;
            base1_x = par2;
            base1_y = par3 + 1;
            base1_z = par4 + 1;
        }
        else if (ang == 1)
        {
            tip_x = par2 + 0.5;
            tip_y = par3 + 1;
            tip_z = par4 + 0.5;
            base1_x = par2;
            base1_y = par3;
            base1_z = par4;
            base2_x = par2 + 1;
            base2_y = par3;
            base2_z = par4;
            base3_x = par2 + 1;
            base3_y = par3;
            base3_z = par4 + 1;
            base4_x = par2;
            base4_y = par3;
            base4_z = par4 + 1;
        }
        else if (ang == 2)
        {
            tip_x = par2 + 0.5;
            tip_y = par3 + 0.5;
            tip_z = par4;
            base1_x = par2;
            base1_y = par3;
            base1_z = par4 + 1;
            base2_x = par2 + 1;
            base2_y = par3;
            base2_z = par4 + 1;
            base3_x = par2 + 1;
            base3_y = par3 + 1;
            base3_z = par4 + 1;
            base4_x = par2;
            base4_y = par3 + 1;
            base4_z = par4 + 1;
        }
        else if (ang == 3)
        {
            tip_x = par2 + 0.5;
            tip_y = par3 + 0.5;
            tip_z = par4 + 1;
            base1_x = par2;
            base1_y = par3 + 1;
            base1_z = par4;
            base2_x = par2 + 1;
            base2_y = par3 + 1;
            base2_z = par4;
            base3_x = par2 + 1;
            base3_y = par3;
            base3_z = par4;
            base4_x = par2;
            base4_y = par3;
            base4_z = par4;
        }
        else if (ang == 4)
        {
            tip_x = par2;
            tip_y = par3 + 0.5;
            tip_z = par4 + 0.5;
            base1_x = par2 + 1;
            base1_y = par3 + 1;
            base1_z = par4;
            base2_x = par2 + 1;
            base2_y = par3 + 1;
            base2_z = par4 + 1;
            base3_x = par2 + 1;
            base3_y = par3;
            base3_z = par4 + 1;
            base4_x = par2 + 1;
            base4_y = par3;
            base4_z = par4;
        }
        else if (ang == 5)
        {
            tip_x = par2 + 1;
            tip_y = par3 + 0.5;
            tip_z = par4 + 0.5;
            base1_x = par2;
            base1_y = par3;
            base1_z = par4;
            base2_x = par2;
            base2_y = par3;
            base2_z = par4 + 1;
            base3_x = par2;
            base3_y = par3 + 1;
            base3_z = par4 + 1;
            base4_x = par2;
            base4_y = par3 + 1;
            base4_z = par4;
        }

        var5.setColorOpaque_I(par1Block.getBlockColor());
        var5.setColorOpaque_I(bloodcol);

        if (renderer.enableAO)
        {
            var5.setBrightness(b);
        }

        var5.addVertexWithUV(tip_x, tip_y, tip_z, var11, var14);
        var5.addVertexWithUV(tip_x, tip_y, tip_z, var11, var14);
        var5.setColorOpaque_I(basecol);

        if (renderer.enableAO)
        {
            var5.setBrightness(b2);
        }

        var5.addVertexWithUV(base2_x, base2_y, base2_z, var12, var16);
        var5.addVertexWithUV(base1_x, base1_y, base1_z, var10, var16);
        var5.setColorOpaque_I(basecol);

        if (renderer.enableAO)
        {
            var5.setBrightness(b2);
        }

        var5.addVertexWithUV(base4_x, base4_y, base4_z, var10, var16);
        var5.addVertexWithUV(base3_x, base3_y, base3_z, var12, var16);
        var5.setColorOpaque_I(bloodcol);

        if (renderer.enableAO)
        {
            var5.setBrightness(b);
        }

        var5.addVertexWithUV(tip_x, tip_y, tip_z, var11, var14);
        var5.addVertexWithUV(tip_x, tip_y, tip_z, var11, var14);
        var5.setColorOpaque_I(basecol);

        if (renderer.enableAO)
        {
            var5.setBrightness(b2);
        }

        var5.addVertexWithUV(base1_x, base1_y, base1_z, var10, var16);
        var5.addVertexWithUV(base4_x, base4_y, base4_z, var12, var16);
        var5.setColorOpaque_I(bloodcol);

        if (renderer.enableAO)
        {
            var5.setBrightness(b);
        }

        var5.addVertexWithUV(tip_x, tip_y, tip_z, var11, var14);
        var5.addVertexWithUV(tip_x, tip_y, tip_z, var11, var14);
        var5.setColorOpaque_I(bloodcol);

        if (renderer.enableAO)
        {
            var5.setBrightness(b);
        }

        var5.addVertexWithUV(tip_x, tip_y, tip_z, var11, var14);
        var5.addVertexWithUV(tip_x, tip_y, tip_z, var11, var14);
        var5.setColorOpaque_I(basecol);

        if (renderer.enableAO)
        {
            var5.setBrightness(b2);
        }

        var5.addVertexWithUV(base3_x, base3_y, base3_z, var12, var16);
        var5.addVertexWithUV(base2_x, base2_y, base2_z, var10, var16);
        var5.setColorOpaque_I(basecol);

        if (renderer.enableAO)
        {
            var5.setBrightness(b2);
        }

        var5.setColorOpaque_I(basecol);
        var5.addVertexWithUV(base1_x, base1_y, base1_z, var10, var16);
        var5.addVertexWithUV(base2_x, base2_y, base2_z, var12, var16);
        var5.addVertexWithUV(base3_x, base3_y, base3_z, var12, var14);
        var5.addVertexWithUV(base4_x, base4_y, base4_z, var10, var14);
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
        return LuxcraftClient.spikeRendererID;
    }
}
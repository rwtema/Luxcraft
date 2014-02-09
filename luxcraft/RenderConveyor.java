package luxcraft;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderConveyor implements ISimpleBlockRenderingHandler
{
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        Tessellator var4 = Tessellator.instance;
        block.setBlockBoundsForItemRender();
        renderer.setRenderBoundsFromBlock(block);
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F * 0, -0.5F);
        var4.startDrawingQuads();
        var4.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderBottomFace(block, 0.0D, -0.5D, 0.0D, block.getBlockTextureFromSideAndMetadata(0, metadata));
        var4.draw();
//        if (var5 && this.useInventoryTint)
//        {
//            var14 = block.getRenderColor(metadata);
//            var8 = (float)(var14 >> 16 & 255) / 255.0F;
//            var9 = (float)(var14 >> 8 & 255) / 255.0F;
//            float var10 = (float)(var14 & 255) / 255.0F;
//            GL11.glColor4f(var8 * par3, var9 * par3, var10 * par3, 1.0F);
//        }
        var4.startDrawingQuads();
        var4.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderTopFace(block, 0.0D, -0.5D, 0.0D, block.getBlockTextureFromSideAndMetadata(1, metadata));
        var4.draw();
        var4.startDrawingQuads();
        var4.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderTopFace(block, 0.0D, 0.01D - 0.5D, 0.0D, 128);
        var4.draw();
//        if (var5 && renderer.useInventoryTint)
//        {
//            GL11.glColor4f(par3, par3, par3, 1.0F);
//        }
        var4.startDrawingQuads();
        var4.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderEastFace(block, 0.0D, -0.5D, 0.0D, block.getBlockTextureFromSideAndMetadata(2, metadata));
        var4.draw();
        var4.startDrawingQuads();
        var4.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderWestFace(block, 0.0D, -0.5D, 0.0D, block.getBlockTextureFromSideAndMetadata(3, metadata));
        var4.draw();
        var4.startDrawingQuads();
        var4.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderNorthFace(block, 0.0D, -0.5D, 0.0D, block.getBlockTextureFromSideAndMetadata(4, metadata));
        var4.draw();
        var4.startDrawingQuads();
        var4.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderSouthFace(block, 0.0D, -0.5D, 0.0D, block.getBlockTextureFromSideAndMetadata(5, metadata));
        var4.draw();
        GL11.glTranslatef(0.5F, 0.5F * 0, 0.5F);
        return;
    }

    public boolean renderWorldBlock(IBlockAccess world, int par2, int par3, int par4, Block par1Block, int modelId, RenderBlocks renderer)
    {
        Tessellator var8 = Tessellator.instance;
        //renderer.renderBlockAllFaces(par1Block, par2, par3, par4);
        int temp = renderer.uvRotateTop;
        renderer.uvRotateTop = (int)(world.getBlockMetadata(par2, par3, par4) % 2);
        float var12 = renderer.aoLightValueYPos;
        float var11 = renderer.aoLightValueYPos;
        float var10 = renderer.aoLightValueYPos;
        float var9 = renderer.aoLightValueYPos;
        renderer.renderStandardBlock(par1Block, par2, par3, par4);
        int var24 = par1Block.getMixedBrightnessForBlock(world, par2, par3 + 1, par4);
        renderer.brightnessTopLeft = renderer.brightnessBottomLeft = renderer.brightnessBottomRight = renderer.brightnessTopRight = var24;
        var8.setBrightness(var24);
        var8.setColorOpaque_F(255, 255, 255);
        renderer.renderTopFace(par1Block, par2, par3 + 0.01, par4, 128);
        renderer.uvRotateTop = temp;
        /*
         int var7 = (var6 & 15) << 4;
         int var8 = var6 & 240;
         float var9 = 0.015625F;
         double var10 = (double)((float)var7 / 256.0F);
         double var11 = (double)(((float)var7 + 15.99F/2) / 256.0F);
         double var12 = (double)(((float)var7 + 15.99F) / 256.0F);
         double var14 = (double)((float)var8 / 256.0F);
         double var16 = (double)(((float)var8 + 15.99F) / 256.0F);
         long var18 = (long)(par2 * 3129871) ^ (long)par4 * 116129781L ^ (long)par3;
         var18 = var18 * var18 * 42317861L + var18 * 11L;
         int var20 = (int)(var18 >> 16 & 3L);
         int b=par1Block.getMixedBrightnessForBlock(world, par2, par3, par4);
        // b=(int)par1Block.getBlockBrightness(world, par2, par3, par4);

         int b2=(int)((double)b*0.75);

         float var21 = (float)par2 + 0.5F;
         float var22 = (float)par4 + 0.5F;
         float var23 = (float)(var20 & 1) * 0.5F * (float)(1 - var20 / 2 % 2 * 2);
         float var24 = (float)(var20 + 1 & 1) * 0.5F * (float)(1 - (var20 + 1) / 2 % 2 * 2);

         double tip_x,tip_y,tip_z;
         double base1_x, base1_y, base1_z;
         double base2_x, base2_y, base2_z;
         double base3_x, base3_y, base3_z;
         double base4_x, base4_y, base4_z;

         tip_x=par2+0.5;tip_y=par3+1;tip_z=par4+0.5;
         base1_x=par2;base1_y=par3;base1_z=par4;
         base2_x=par2+1;base2_y=par3;base2_z=par4;
         base3_x=par2+1;base3_y=par3;base3_z=par4+1;
         base4_x=par2;base4_y=par3;base4_z=par4+1;

         int ang = world.getBlockMetadata(par2, par3,par4);

         int bloodcol=par1Block.getBlockColor();
         int basecol=par1Block.getBlockColor();
         basecol=0xbbbbbb;
         b2=b;

         if(ang>=6){
        	 ang=ang%6;
         }

         if(ang==0){
        	 tip_x=par2+0.5;tip_y=par3;tip_z=par4+0.5;
             base4_x=par2;base4_y=par3+1;base4_z=par4;
             base3_x=par2+1;base3_y=par3+1;base3_z=par4;
             base2_x=par2+1;base2_y=par3+1;base2_z=par4+1;
             base1_x=par2;base1_y=par3+1;base1_z=par4+1;
         }else if(ang==1){
        	 tip_x=par2+0.5;tip_y=par3+1;tip_z=par4+0.5;
             base1_x=par2;base1_y=par3;base1_z=par4;
             base2_x=par2+1;base2_y=par3;base2_z=par4;
             base3_x=par2+1;base3_y=par3;base3_z=par4+1;
             base4_x=par2;base4_y=par3;base4_z=par4+1;
         }
         else if(ang==2){
        	 tip_x=par2+0.5;tip_y=par3+0.5;tip_z=par4;
             base1_x=par2;base1_y=par3;base1_z=par4+1;
             base2_x=par2+1;base2_y=par3;base2_z=par4+1;
             base3_x=par2+1;base3_y=par3+1;base3_z=par4+1;
             base4_x=par2;base4_y=par3+1;base4_z=par4+1;
         }else if(ang==3){
        	 tip_x=par2+0.5;tip_y=par3+0.5;tip_z=par4+1;
             base1_x=par2;base1_y=par3+1;base1_z=par4;
             base2_x=par2+1;base2_y=par3+1;base2_z=par4;
             base3_x=par2+1;base3_y=par3;base3_z=par4;
             base4_x=par2;base4_y=par3;base4_z=par4;
         }else if(ang==4){
        	 tip_x=par2;tip_y=par3+0.5;tip_z=par4+0.5;
             base1_x=par2+1;base1_y=par3+1;base1_z=par4;
             base2_x=par2+1;base2_y=par3+1;base2_z=par4+1;
             base3_x=par2+1;base3_y=par3;base3_z=par4+1;
             base4_x=par2+1;base4_y=par3;base4_z=par4;
         }else if(ang==5){
        	 tip_x=par2+1;tip_y=par3+0.5;tip_z=par4+0.5;
             base1_x=par2;base1_y=par3;base1_z=par4;
             base2_x=par2;base2_y=par3;base2_z=par4+1;
             base3_x=par2;base3_y=par3+1;base3_z=par4+1;
             base4_x=par2;base4_y=par3+1;base4_z=par4;
         }

         var5.setColorOpaque_I(par1Block.getBlockColor());

         var5.setColorOpaque_I(bloodcol);var5.setColorOpaque_I(bloodcol);var5.setBrightness(b);
         var5.addVertexWithUV(tip_x,tip_y,tip_z, var11, var14);
         var5.addVertexWithUV(tip_x,tip_y,tip_z, var11, var14);
         var5.setColorOpaque_I(basecol);
         var5.setColorOpaque_I(basecol);var5.setBrightness(b2);
         var5.addVertexWithUV(base2_x, base2_y, base2_z, var12, var16);
         var5.addVertexWithUV(base1_x, base1_y, base1_z, var10, var16);

         var5.setColorOpaque_I(basecol);var5.setBrightness(b2);
         var5.addVertexWithUV(base4_x, base4_y, base4_z, var10, var16);
         var5.addVertexWithUV(base3_x, base3_y, base3_z, var12, var16);
         var5.setColorOpaque_I(bloodcol);var5.setBrightness(b);
         var5.addVertexWithUV(tip_x,tip_y,tip_z, var11, var14);
         var5.addVertexWithUV(tip_x,tip_y,tip_z, var11, var14);

         var5.setColorOpaque_I(basecol);var5.setBrightness(b2);
         var5.addVertexWithUV(base1_x, base1_y, base1_z, var10, var16);
         var5.addVertexWithUV(base4_x, base4_y, base4_z, var12, var16);
         var5.setColorOpaque_I(bloodcol);var5.setBrightness(b);
         var5.addVertexWithUV(tip_x,tip_y,tip_z, var11, var14);
         var5.addVertexWithUV(tip_x,tip_y,tip_z, var11, var14);

         var5.setColorOpaque_I(bloodcol);var5.setBrightness(b);
         var5.addVertexWithUV(tip_x,tip_y,tip_z, var11, var14);
         var5.addVertexWithUV(tip_x,tip_y,tip_z, var11, var14);
         var5.setColorOpaque_I(basecol);var5.setBrightness(b2);
         var5.addVertexWithUV(base3_x, base3_y, base3_z, var12, var16);
         var5.addVertexWithUV(base2_x, base2_y, base2_z, var10, var16);

         var5.setColorOpaque_I(basecol);var5.setBrightness(b2);
         var5.setColorOpaque_I(basecol);
         var5.addVertexWithUV(base1_x, base1_y, base1_z, var10, var16);
         var5.addVertexWithUV(base2_x, base2_y, base2_z, var12, var16);
         var5.addVertexWithUV(base3_x, base3_y, base3_z, var12, var14);
         var5.addVertexWithUV(base4_x, base4_y, base4_z, var10, var14);
         */
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
        return LuxcraftClient.conveyorRendererID;
    }
}
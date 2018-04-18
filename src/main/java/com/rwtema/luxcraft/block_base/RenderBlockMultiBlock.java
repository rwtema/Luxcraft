package com.rwtema.luxcraft.block_base;

import com.rwtema.luxcraft.LuxcraftProxy;
import net.minecraftforge.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class RenderBlockMultiBlock implements ISimpleBlockRenderingHandler {
    static boolean rendering = true;

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        if (!(block instanceof IMultiBoxBlock)) {
            return;
        }

        BoxModel boxes = ((IMultiBoxBlock) block).getInventoryModel(metadata);

        if (boxes == null) {
            return;
        }

        if (boxes.size() == 0) {
            return;
        }

        ((IMultiBoxBlock) block).prepareForRender(boxes.label);
        Box union = boxes.boundingBox();
        float dx = (union.maxX + union.minX) / 2 - 0.5F;
        float dy = (union.maxY + union.minY) / 2 - 0.5F;
        float dz = (union.maxZ + union.minZ) / 2 - 0.5F;
        GL11.glTranslatef(-dx, -dy, -dz);
        GL11.glRotatef(boxes.invModelRotate, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        for (int i = 0; i < boxes.size(); i++) {
            Box b = boxes.get(i);
            block.setBlockBounds(b.minX, b.minY, b.minZ, b.maxX, b.maxY, b.maxZ);
            ((IMultiBoxBlock) block).prepareForRender(b.label);
            Tessellator tessellator = Tessellator.instance;
            renderer.setRenderBoundsFromBlock(block);
            GL11.glColor3f((b.color >> 16 & 255) / 255.0F, (b.color >> 8 & 255) / 255.0F, (b.color & 255) / 255.0F);
            renderer.uvRotateEast = b.uvRotateEast;
            renderer.uvRotateWest = b.uvRotateWest;
            renderer.uvRotateSouth = b.uvRotateSouth;
            renderer.uvRotateNorth = b.uvRotateNorth;
            renderer.uvRotateTop = b.uvRotateTop;
            renderer.uvRotateBottom = b.uvRotateBottom;

            if (!b.invisibleSide[0]) {
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, -1.0F, 0.0F);
                renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, getTexture(null, 0, 0, 0, renderer, b, block, 0, metadata));
                tessellator.draw();
            }

            if (!b.invisibleSide[1]) {
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 1.0F, 0.0F);
                renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, getTexture(null, 0, 0, 0, renderer, b, block, 1, metadata));
                tessellator.draw();
            }

            if (!b.invisibleSide[2]) {
                renderer.flipTexture = true;
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 0.0F, -1.0F);
                renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, getTexture(null, 0, 0, 0, renderer, b, block, 2, metadata));
                tessellator.draw();
            }

            if (!b.invisibleSide[3]) {
                renderer.flipTexture = false;
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 0.0F, 1.0F);
                renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, getTexture(null, 0, 0, 0, renderer, b, block, 3, metadata));
                tessellator.draw();
            }

            if (!b.invisibleSide[4]) {
                renderer.flipTexture = false;
                tessellator.startDrawingQuads();
                tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, getTexture(null, 0, 0, 0, renderer, b, block, 4, metadata));
                tessellator.draw();
            }

            if (!b.invisibleSide[5]) {
                renderer.flipTexture = true;
                tessellator.startDrawingQuads();
                tessellator.setNormal(1.0F, 0.0F, 0.0F);
                renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, getTexture(null, 0, 0, 0, renderer, b, block, 5, metadata));
                tessellator.draw();
            }

            renderer.uvRotateEast = 0;
            renderer.uvRotateWest = 0;
            renderer.uvRotateSouth = 0;
            renderer.uvRotateNorth = 0;
            renderer.uvRotateTop = 0;
            renderer.uvRotateBottom = 0;
            renderer.flipTexture = false;
        }

        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        GL11.glTranslatef(dx, dy, dz);
    }

    public IIcon getTexture(IBlockAccess world, int x, int y, int z, RenderBlocks renderer, Box box, Block block, int side, int metadata) {
        if (box.textureSide[side] != null) {
            return box.textureSide[side];
        }

        if (box.texture != null) {
            return box.texture;
        }

        if (world == null) {
            return renderer.getBlockIconFromSideAndMetadata(block, side, metadata);
        } else {
            return renderer.getBlockIcon(block, world, x, y, z, side);
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        int metadata = world.getBlockMetadata(x, y, z);

        if (!(block instanceof IMultiBoxBlock)) {
            return false;
        }

        BoxModel boxes = ((IMultiBoxBlock) block).getWorldModel(world, x, y, z);

        if (boxes == null || boxes.size() == 0) {
            return false;
        }

        ((IMultiBoxBlock) block).prepareForRender(boxes.label);

        for (int i = 0; i < boxes.size(); i++) {
            Box b1 = boxes.get(i);
            float r = (b1.color >> 16 & 255) / 255.0F;
            float g = (b1.color >> 8 & 255) / 255.0F;
            float b = (b1.color & 255) / 255.0F;
            ((IMultiBoxBlock) block).prepareForRender(b1.label);
            block.setBlockBounds(b1.minX, b1.minY, b1.minZ, b1.maxX, b1.maxY, b1.maxZ);
            renderer.uvRotateEast = b1.uvRotateEast;
            renderer.uvRotateWest = b1.uvRotateWest;
            renderer.uvRotateSouth = b1.uvRotateSouth;
            renderer.uvRotateNorth = b1.uvRotateNorth;
            renderer.uvRotateTop = b1.uvRotateTop;
            renderer.uvRotateBottom = b1.uvRotateBottom;
            renderer.setRenderBoundsFromBlock(block);

            if (b1.renderAsNormalBlock) {
                renderer.renderStandardBlock(block, x + b1.offsetx, y + b1.offsety, z + b1.offsetz);
            } else {
                renderer.enableAO = false;
                Tessellator tessellator = Tessellator.instance;
                tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x + b1.offsetx, y + b1.offsety, z + b1.offsetz));
                tessellator.setColorOpaque_F(r * 0.5F, g * 0.5F, b * 0.5F);

                if (!b1.invisibleSide[0]) {
                    renderer.renderFaceYNeg(block, x + b1.offsetx, y + b1.offsety, z + b1.offsetz, getTexture(world, x, y, z, renderer, b1, block, 0, metadata));
                }

                tessellator.setColorOpaque_F(r, g, b);

                if (!b1.invisibleSide[1]) {
                    renderer.renderFaceYPos(block, x + b1.offsetx, y + b1.offsety, z + b1.offsetz, getTexture(world, x, y, z, renderer, b1, block, 1, metadata));
                }

                renderer.flipTexture = true;
                tessellator.setColorOpaque_F(r * 0.8F, g * 0.8F, b * 0.8F);

                if (!b1.invisibleSide[2]) {
                    renderer.renderFaceZNeg(block, x + b1.offsetx, y + b1.offsety, z + b1.offsetz, getTexture(world, x, y, z, renderer, b1, block, 2, metadata));
                }

                renderer.flipTexture = false;

                if (!b1.invisibleSide[3]) {
                    renderer.renderFaceZPos(block, x + b1.offsetx, y + b1.offsety, z + b1.offsetz, getTexture(world, x, y, z, renderer, b1, block, 3, metadata));
                }

                tessellator.setColorOpaque_F(r * 0.6F, g * 0.6F, b * 0.6F);
                renderer.flipTexture = false;

                if (!b1.invisibleSide[4]) {
                    renderer.renderFaceXNeg(block, x + b1.offsetx, y + b1.offsety, z + b1.offsetz, getTexture(world, x, y, z, renderer, b1, block, 4, metadata));
                }

                renderer.flipTexture = true;

                if (!b1.invisibleSide[5]) {
                    renderer.renderFaceXPos(block, x + b1.offsetx, y + b1.offsety, z + b1.offsetz, getTexture(world, x, y, z, renderer, b1, block, 5, metadata));
                }
            }

            renderer.uvRotateBottom = 0;
            renderer.uvRotateTop = 0;
            renderer.uvRotateSouth = 0;
            renderer.uvRotateNorth = 0;
            renderer.uvRotateWest = 0;
            renderer.uvRotateEast = 0;
            renderer.flipTexture = false;
        }

        block.setBlockBoundsBasedOnState(world, x, y, z);
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return LuxcraftProxy.multiBoxRenderingID;
    }
}

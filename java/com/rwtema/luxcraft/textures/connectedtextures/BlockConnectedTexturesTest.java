package com.rwtema.luxcraft.textures.connectedtextures;


import com.rwtema.luxcraft.textures.connectedtextures.IconConnected;
import com.rwtema.luxcraft.textures.connectedtextures.ConnectedTexturesHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockConnectedTexturesTest extends Block {
    public BlockConnectedTexturesTest() {
        super(Material.rock);
        this.setBlockTextureName("luxcraft:test");
        this.setBlockName("luxcraft:test");
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        this.blockIcon = ConnectedTexturesHelper.registerIcon(this.getTextureName(), register);
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        IIcon icon = super.getIcon(world, x, y, z, side);
        if (icon instanceof IconConnected)
            return ((IconConnected) icon).getWorldIcon(world, x, y, z, ForgeDirection.getOrientation(side));
        return icon;
    }
}

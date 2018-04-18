package com.rwtema.luxcraft.block;

import com.rwtema.luxcraft.Luxcraft;
import com.rwtema.luxcraft.LuxcraftCreativeTab;
import com.rwtema.luxcraft.tiles.TileEntityLuxStorage;
import com.rwtema.luxcraft.tiles.TileEntityLuxTransmitterBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

public class BlockLuxStorage extends BlockLuxContainer {
    private static Random random = new Random();
    public IIcon sideIcon = null;

    public BlockLuxStorage() {
        super(Material.rock);
        this.setCreativeTab(LuxcraftCreativeTab.instance);
        this.setBlockName("luxcraft:luxStorage");
        this.setBlockTextureName("luxcraft:luxStorageTop");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register) {
        this.blockIcon = register.registerIcon("luxcraft:luxStorageTop");
        this.sideIcon = register.registerIcon("luxcraft:luxStorageSide");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (!world.isRemote)
            player.openGui(Luxcraft.instance, 0, world, x, y, z);
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        if (side >= 2)
            return sideIcon;
        else
            return super.getIcon(side, meta);
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntityLuxTransmitterBase createTileEntity(World var1, int meta) {
        return new TileEntityLuxStorage();
    }

}

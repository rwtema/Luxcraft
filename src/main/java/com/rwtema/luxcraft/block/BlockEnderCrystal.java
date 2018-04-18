package com.rwtema.luxcraft.block;

import com.rwtema.luxcraft.LuxcraftCreativeTab;
import com.rwtema.luxcraft.tiles.TileEntityEnderCrystal;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class BlockEnderCrystal extends Block {

    public BlockEnderCrystal() {
        super(Material.rock);
        this.setCreativeTab(LuxcraftCreativeTab.instance);
        this.setHardness(40);
        this.setBlockName("luxcraft:enderCrystal");
        this.setLightLevel(1);
        this.setBlockUnbreakable();
        this.setBlockBounds(4 / 16F, 0, 4 / 16F, 12 / 16F, 12 / 16F, 12 / 16F);
    }

    @Override
    public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_) {
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        return world.isRemote || world.func_147480_a(x, y, z, true);

    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityEnderCrystal();
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

}

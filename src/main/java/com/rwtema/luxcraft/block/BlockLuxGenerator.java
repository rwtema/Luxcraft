package com.rwtema.luxcraft.block;

import com.rwtema.luxcraft.Luxcraft;
import com.rwtema.luxcraft.LuxcraftCreativeTab;
import com.rwtema.luxcraft.StackHelper;
import com.rwtema.luxcraft.tiles.TileEntityLuxGenerator;
import com.rwtema.luxcraft.tiles.TileEntityLuxTransmitterBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockLuxGenerator extends BlockLuxContainer {
    private static Random random = new Random();
    public IIcon[] icons = new IIcon[8];

    public BlockLuxGenerator() {
        super(Material.rock);
        this.setCreativeTab(LuxcraftCreativeTab.instance);
        this.setBlockName("luxcraft:luxGenerator");
        this.setBlockTextureName("luxcraft:generator_base");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (!world.isRemote)
            player.openGui(Luxcraft.instance, 0, world, x, y, z);
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register) {
        this.blockIcon = register.registerIcon("luxcraft:generator_base");
        for (int i = 0; i < 8; i++)
            this.icons[i] = register.registerIcon("luxcraft:generator_top" + i);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == 1)
            return icons[meta % 8];
        else
            return super.getIcon(side, meta);
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntityLuxTransmitterBase createTileEntity(World var1, int meta) {
        return new TileEntityLuxGenerator();
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an
     * update, as appropriate
     */
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6) {
        TileEntityLuxGenerator var7 = (TileEntityLuxGenerator) par1World.getTileEntity(par2, par3, par4);

        if (var7 != null) {
            for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8) {
                ItemStack var9 = var7.getStackInSlot(var8);

                if (var9 != null) {
                    float var10 = random.nextFloat() * 0.8F + 0.1F;
                    float var11 = random.nextFloat() * 0.8F + 0.1F;
                    EntityItem var14;

                    for (float var12 = random.nextFloat() * 0.8F + 0.1F; StackHelper.getStackSize(var9) > 0; par1World.spawnEntityInWorld(var14)) {
                        int var13 = random.nextInt(21) + 10;

                        if (var13 > StackHelper.getStackSize(var9)) {
                            var13 = StackHelper.getStackSize(var9);
                        }

                        var9.stackSize -= var13;
                        var14 = new EntityItem(par1World, (double) ((float) par2 + var10), (double) ((float) par3 + var11), (double) ((float) par4 + var12), new ItemStack(var9.getItem(), var13,
                                var9.getItemDamage()));
                        float var15 = 0.05F;
                        var14.motionX = (double) ((float) random.nextGaussian() * var15);
                        var14.motionY = (double) ((float) random.nextGaussian() * var15 + 0.2F);
                        var14.motionZ = (double) ((float) random.nextGaussian() * var15);

                        if (var9.hasTagCompound()) {
                            var14.getEntityItem().setTagCompound((NBTTagCompound) var9.getTagCompound().copy());
                        }
                    }
                }
            }
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

    @Override
    public int damageDropped(int par1) {
        return par1;
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (int var4 = 0; var4 < 8; ++var4) {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }

}

package com.rwtema.luxcraft.block;

import com.rwtema.luxcraft.LuxcraftClient;
import com.rwtema.luxcraft.LuxcraftCreativeTab;
import com.rwtema.luxcraft.luxapi.ILaser;
import com.rwtema.luxcraft.luxapi.IReflector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class BlockLuxReflector extends Block implements IReflector {

    public IIcon[] icons = new IIcon[3];

    public BlockLuxReflector() {
        super(Material.glass);
        this.setBlockName("luxcraft:luxReflector");
        this.setBlockTextureName("luxcraft:luxReflector");
        this.setLightOpacity(0);
        this.setCreativeTab(LuxcraftCreativeTab.instance);
    }

    public static int determineOrientation(World par0World, int par1, int par2, int par3, EntityLivingBase par4EntityPlayer) {
        int var7 = MathHelper.floor_double((double) (par4EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        return var7 == 0 ? 0 : var7 == 1 ? 3 : var7 == 2 ? 1 : 2;
    }

    public static int getSide1(int metadata) {
        return 2 + metadata % 4;
    }

    public static int getSide2(int metadata) {
        if (metadata >= 8)
            return 0;
        else if (metadata >= 4)
            return 1;
        else {
            switch (2 + metadata % 4) {
                case 2:
                    return 4;
                case 3:
                    return 5;
                case 4:
                    return 3;
                case 5:
                    return 2;
            }
            return 2 + (metadata + 1) % 4;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register) {
        icons[0] = this.blockIcon = register.registerIcon("luxcraft:reflector");
        icons[1] = register.registerIcon("luxcraft:reflector_side");
        icons[2] = register.registerIcon("luxcraft:reflector_mirror");
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack p_149689_6_) {
        int var6 = determineOrientation(world, x, y, z, player);
        world.setBlockMetadataWithNotify(x, y, z, var6, 0);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {

        if ((player.getCurrentEquippedItem() == null) || (player.getCurrentEquippedItem().getItem() == Items.stick)) {
            int data = world.getBlockMetadata(x, y, z);
            if (!player.isSneaking()) {
                world.setBlockMetadataWithNotify(x, y, z, (data + 1) % 4 + (data / 4) * 4, 2);
            } else {
                world.setBlockMetadataWithNotify(x, y, z, (data + 8) % 12, 2);
            }
        }

        return false;
    }

    public int newDir(int side, int metadata) {
        if (side == Facing.oppositeSide[getSide1(metadata)])
            return getSide2(metadata);
        else if (side == Facing.oppositeSide[getSide2(metadata)])
            return getSide1(metadata);
        return side;
    }

    @Override
    public int getRenderType() {
        return LuxcraftClient.prismRenderingID;

    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
    }

    @Override
    public ForgeDirection newDir(ForgeDirection dir, World world, int x, int y, int z, ILaser beam) {
        return ForgeDirection.getOrientation(newDir(dir.ordinal(), world.getBlockMetadata(x, y, z)));
    }

}

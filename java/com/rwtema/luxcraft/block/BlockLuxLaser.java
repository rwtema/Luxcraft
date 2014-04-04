package com.rwtema.luxcraft.block;

import com.rwtema.luxcraft.Luxcraft;
import com.rwtema.luxcraft.LuxcraftCreativeTab;
import com.rwtema.luxcraft.block_base.BlockMultiBlock;
import com.rwtema.luxcraft.block_base.BoxModel;
import com.rwtema.luxcraft.luxapi.LuxColor;
import com.rwtema.luxcraft.tiles.LaserType;
import com.rwtema.luxcraft.tiles.TileEntityLuxLaser;
import com.rwtema.luxcraft.tiles.TileEntityLuxLaserClient;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class BlockLuxLaser extends BlockMultiBlock {
    public static IIcon[] cols = new IIcon[LuxColor.n];

    public BlockLuxLaser() {
        super(Material.rock);
        this.setCreativeTab(LuxcraftCreativeTab.instance);
        this.setBlockName("luxcraft:luxLaser");
        this.setBlockTextureName("luxcraft:luxLaser");
        this.setLightOpacity(0);
    }

    public static ForgeDirection getDirection(int metadata) {
        return ForgeDirection.getOrientation(metadata % 6);
    }

    public static LaserType getLaser(int metadata) {
        if (metadata < 6)
            return LaserType.Standard;
        else
            return LaserType.Advanced;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        super.registerBlockIcons(register);

        for (int i = 0; i < LuxColor.n; i++) {
            if (Luxcraft.colorBlind)
                cols[i] = register.registerIcon("luxcraft:lux_" + LuxColor.col(i).shortname);
            else
                cols[i] = register.registerIcon("luxcraft:lux");

        }
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this
     * box can change after the pool has been cleared to be reused)
     */
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }

    @SideOnly(Side.CLIENT)
    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
    }

    @Override
    public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9) {
        return par5;
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        if (world.isRemote)
            return new TileEntityLuxLaserClient();
        else
            return new TileEntityLuxLaser();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {

        if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.stick) {
            if (world.isRemote)
                return true;
            int data = world.getBlockMetadata(x, y, z);
            world.setBlockMetadataWithNotify(x, y, z, (data + 1) % 6, 0);
            return true;
        }

        if (player instanceof EntityPlayerMP) {
            TileEntityLuxLaser laser = (TileEntityLuxLaser) world.getTileEntity(x, y, z);
            NBTTagCompound tag = new NBTTagCompound();
            laser.writeToNBT(tag);

            ((EntityPlayerMP) player).addChatMessage(new ChatComponentText("" + tag.toString()));
        }

        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        par3List.add(new ItemStack(par1, 1, 3));
    }

    @Override
    public void prepareForRender(String label) {

    }

    @Override
    public BoxModel getWorldModel(IBlockAccess world, int x, int y, int z) {
        int metadata = world.getBlockMetadata(x, y, z);

        BoxModel model = this.getInventoryModel(0);
        model.rotateToSide(ForgeDirection.getOrientation(metadata % 6).getOpposite());

        return model;
    }

    @Override
    public BoxModel getInventoryModel(int metadata) {
        BoxModel model = new BoxModel();
        model.addBoxI(0, 0, 0, 16, 2, 16);
        model.addBoxI(3, 2, 3, 16 - 3, 4, 16 - 3);
        model.addBoxI(5, 4, 5, 16 - 5, 6, 16 - 5);
        model.addBoxI(3, 4, 3, 4, 10, 4);
        model.addBoxI(3, 4, 3, 4, 10, 4).rotateY(1);
        model.addBoxI(3, 4, 3, 4, 10, 4).rotateY(2);
        model.addBoxI(3, 4, 3, 4, 10, 4).rotateY(3);
        model.hollowBoxI(3, 10, 3, 5, 5, 11, 11, 13, 11, 13);
        return model;
    }
}

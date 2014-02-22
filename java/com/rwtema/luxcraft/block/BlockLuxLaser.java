package com.rwtema.luxcraft.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.rwtema.luxcraft.LuxcraftCreativeTab;
import com.rwtema.luxcraft.tiles.TileEntityLuxLaser;
import com.rwtema.luxcraft.tiles.TileEntityLuxLaserClient;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLuxLaser extends Block {
	public BlockLuxLaser() {
		super(Material.rock);
		this.setCreativeTab(LuxcraftCreativeTab.instance);
		this.setBlockName("luxcraft:luxLaser");
		this.setBlockTextureName("luxcraft:luxLaser");
		this.setLightOpacity(0);
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

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y,
	 * z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		this.setBounds(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
	}

	public static ForgeDirection getDirection(int metadata) {
		return ForgeDirection.getOrientation(metadata % 6);
	}

	public void setBounds(int metadata) {
		float h = 0.125F;

		if (metadata == 0) {
			this.setBlockBounds(0.0F, 1.0F - h, 0.0F, 1.0F, 1.0F, 1.0F);
		}

		if (metadata == 1) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, h, 1.0F);
		}

		if (metadata == 2) {
			this.setBlockBounds(0.0F, 0.0F, 1.0F - h, 1.0F, 1.0F, 1.0F);
		}

		if (metadata == 3) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, h);
		}

		if (metadata == 4) {
			this.setBlockBounds(1.0F - h, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}

		if (metadata == 5) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, h, 1.0F, 1.0F);
		}
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether
	 * or not to render the shared face of two adjacent blocks and also whether
	 * the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False
	 * (examples: signs, buttons, stairs, etc)
	 */

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	/**
	 * Sets the block's bounds for rendering it as an item
	 */
	public void setBlockBoundsForItemRender() {
		float var3 = 0.125F;
		this.setBlockBounds(0.0F, 1.0F - var3, 0.0F, 1.0F, 1.0F, 1.0F);
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

	public static LaserType getLaser(int metadata) {
		if (metadata < 6)
			return LaserType.Standard;
		else
			return LaserType.Advanced;
	}

}

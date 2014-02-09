package luxcraft;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLuxGenerator extends BlockContainer {
	private static Random random = new Random();

	protected BlockLuxGenerator(int par1, Material par2Material) {
		super(par1, par2Material);
		this.blockIndexInTexture = 1;
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setTextureFile("/luxcraft/terrain.png");
	}


	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public int getBlockTextureFromSideAndMetadata(int par1, int par2)
	{
		return (par1 == 1) ? 16+par2 : 1;
	}

	/**
	 * Returns the block texture based on the side being looked at.  Args: side
	 */
	public int getBlockTextureFromSide(int par1)
	{
		return (par1 == 1) ? 0 : 1;
	}


	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityLuxGenerator();
	}

	/**
	 * ejects contained items into the world, and notifies neighbours of an update, as appropriate
	 */
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		TileEntityLuxGenerator var7 = (TileEntityLuxGenerator)par1World.getBlockTileEntity(par2, par3, par4);

		if (var7 != null)
		{
			for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8)
			{
				ItemStack var9 = var7.getStackInSlot(var8);

				if (var9 != null)
				{
					float var10 = this.random.nextFloat() * 0.8F + 0.1F;
					float var11 = this.random.nextFloat() * 0.8F + 0.1F;
					EntityItem var14;

					for (float var12 = this.random.nextFloat() * 0.8F + 0.1F; var9.stackSize > 0; par1World.spawnEntityInWorld(var14))
					{
						int var13 = this.random.nextInt(21) + 10;

						if (var13 > var9.stackSize)
						{
							var13 = var9.stackSize;
						}

						var9.stackSize -= var13;
						var14 = new EntityItem(par1World, (double)((float)par2 + var10), (double)((float)par3 + var11), (double)((float)par4 + var12), new ItemStack(var9.itemID, var13, var9.getItemDamage()));
						float var15 = 0.05F;
						var14.motionX = (double)((float)this.random.nextGaussian() * var15);
						var14.motionY = (double)((float)this.random.nextGaussian() * var15 + 0.2F);
						var14.motionZ = (double)((float)this.random.nextGaussian() * var15);

						if (var9.hasTagCompound())
						{
							var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
						}
					}
				}
			}
		}

		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}


	/**
	 * Called upon block activation (right click on the block.)
	 */
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if(par5EntityPlayer.isSneaking())
			return false;

		if(par5EntityPlayer.getCurrentEquippedItem() != null)
			if (par5EntityPlayer.getCurrentEquippedItem().itemID == Luxcraft.luxLaser.blockID | 
			par5EntityPlayer.getCurrentEquippedItem().itemID == Luxcraft.luxLaser2.blockID)
				return false;

		if (par1World.isRemote)
		{
			TileEntity var10 = par1World.getBlockTileEntity(par2, par3, par4);
			if(var10 != null){
//				((TileEntityLuxGenerator) var10).updateAllUsers();
				PacketDispatcher.sendPacketToPlayer(var10.getDescriptionPacket(), (Player)par5EntityPlayer); 
			}
			return true;
		}
		else
		{
			TileEntity var10 = par1World.getBlockTileEntity(par2, par3, par4);

			if (var10 != null)
			{
				PacketDispatcher.sendPacketToPlayer(var10.getDescriptionPacket(), (Player)par5EntityPlayer);
				par5EntityPlayer.openGui(Luxcraft.instance, 0, par1World, par2, par3, par4);
			}

			return true;
		}
	}

	/**
	 * Determines the damage on the item the block drops. Used in cloth and wood.
	 */
	public int damageDropped(int par1)
	{
		return par1;
	}


	@SideOnly(Side.CLIENT)

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int var4 = 0; var4 < 7; ++var4)
		{
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}

}

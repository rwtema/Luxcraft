package luxcraft;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLuxRitualTable extends BlockContainer {
	private Random random = new Random();
	
	public BlockLuxRitualTable(int par1) {
		super(par1, 11, Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setTextureFile("/luxcraft/terrain.png");
	}

	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityLuxRitualTable();
	}

	/**
	 * Returns the block texture based on the side being looked at.  Args: side
	 */
	public int getBlockTextureFromSide(int par1)
	{
		if(par1 == 1)
			return 14;
		else
			return 15;
	}



	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if(par5EntityPlayer.isSneaking())
			return false;

		if (!par1World.isRemote){
			TileEntity var10 = par1World.getBlockTileEntity(par2, par3, par4);

			if (var10 != null)
			{
				((TileEntityLuxRitualTable)var10).calcRitualStrength();
				par5EntityPlayer.openGui(Luxcraft.instance, 0, par1World, par2, par3, par4);
			}
		}
		return true;
	}
	
    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
    	if (!par1World.isRemote){
			TileEntity var10 = par1World.getBlockTileEntity(par2, par3, par4);

			if (var10 != null)
			{
				((TileEntityLuxRitualTable)var10).calcRitualStrength();
			}
		}
    }

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		super.onBlockAdded(par1World, par2, par3, par4);
		if (!par1World.isRemote){
			TileEntity var10 = par1World.getBlockTileEntity(par2, par3, par4);
			
			if(var10 != null)
				((TileEntityLuxRitualTable)var10).calcRitualStrength();
		}
	}

	/**
	 * Can this block provide power. Only wire currently seems to have this change based on its state.
	 */
	public boolean canProvidePower()
	{
		return true;
	}

	/**
	 * Returns true if the block is emitting indirect/weak redstone power on the specified side. If isBlockNormalCube
	 * returns true, standard redstone propagation rules will apply instead and this will not be called. Args: World, X,
	 * Y, Z, side
	 */
	public boolean isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		TileEntity t = par1IBlockAccess.getBlockTileEntity(par2, par3, par4);
		if(t != null){
			if (t instanceof TileEntityLuxRitualTable){
				return ((TileEntityLuxRitualTable)t).isPowered();
			}
		}
		return false;
	}

	/**
	 * Return true if the block is a normal, solid cube.  This
	 * determines indirect power state, entity ejection from blocks, and a few
	 * others.
	 * 
	 * @param world The current world
	 * @param x X Position
	 * @param y Y position
	 * @param z Z position
	 * @return True if the block is a full cube
	 */
	public boolean isBlockNormalCube(World world, int x, int y, int z) 
	{
		return false;
	}
	
	/**
	 * ejects contained items into the world, and notifies neighbours of an update, as appropriate
	 */
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		TileEntityLuxRitualTable var7 = (TileEntityLuxRitualTable)par1World.getBlockTileEntity(par2, par3, par4);

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

}

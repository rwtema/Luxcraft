
package luxcraft;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLaserDetector extends BlockContainer
{
	public BlockLaserDetector(int par1)
	{
		super(par1, Material.iron);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setLightOpacity(0);
		this.setTextureFile("/luxcraft/terrain.png");
		this.blockIndexInTexture=29;
		float v = 3.0F/16.0F;
		this.setBlockBounds(v, v, v, 1-v, 1-v, 1-v);
		
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	public boolean isOpaqueCube()
	{
		return false;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	public boolean renderAsNormalBlock()
	{
		return false;
	}


	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityLaserDetector();
	}

	/**
	 * Returns true if the block is emitting indirect/weak redstone power on the specified side. If isBlockNormalCube
	 * returns true, standard redstone propagation rules will apply instead and this will not be called. Args: World, X,
	 * Y, Z, side
	 */
	public boolean isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		if(par1IBlockAccess.getBlockTileEntity(par2, par3, par4)!= null){
			if(par1IBlockAccess.getBlockMetadata(par2, par3, par4) % 2==0){
				return ((TileEntityLaserDetector)par1IBlockAccess.getBlockTileEntity(par2, par3, par4)).luxDetectedTimer>0;
			}else{
				return ((TileEntityLaserDetector)par1IBlockAccess.getBlockTileEntity(par2, par3, par4)).luxDetectedTimer==0;
			}

		}
		return false;
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public int getBlockTextureFromSideAndMetadata(int par1, int par2)
	{
		return this.blockIndexInTexture+par2;
	}

	/**
	 * Can this block provide power. Only wire currently seems to have this change based on its state.
	 */
	public boolean canProvidePower()
	{
		return true;
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
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
	}
	
	   /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!par1World.isRemote)
        {
        	par1World.notifyBlocksOfNeighborChange(par2, par3, par4, this.blockID);
        }
    }

}




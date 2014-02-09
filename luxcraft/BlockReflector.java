
package luxcraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockReflector extends Block implements IReflector
{
	public BlockReflector(int par1)
	{
		super(par1, Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setLightOpacity(0);
		this.setTextureFile("/luxcraft/terrain.png");
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public int getBlockTextureFromSideAndMetadata(int par1, int par2)
	{
		return par1 == par2 ? 8 : 6;
	}


	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	public boolean isOpaqueCube()
	{
		return false;
	}

	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
	{
		int var6 = determineOrientation(par1World, par2, par3, par4, (EntityPlayer)par5EntityLiving);
		par1World.setBlockMetadataWithNotify(par2, par3, par4, var6);
	}

	public static int determineOrientation(World par0World, int par1, int par2, int par3, EntityPlayer par4EntityPlayer)
	{
		if (MathHelper.abs((float)par4EntityPlayer.posX - (float)par1) < 2.0F && MathHelper.abs((float)par4EntityPlayer.posZ - (float)par3) < 2.0F)
		{
			double var5 = par4EntityPlayer.posY + 1.82D - (double)par4EntityPlayer.yOffset;

			if (var5 - (double)par2 > 2.0D)
			{
				return 1;
			}

			if ((double)par2 - var5 > 0.0D)
			{
				return 0;
			}
		}

		int var7 = MathHelper.floor_double((double)(par4EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		return var7 == 0 ? 2 : (var7 == 1 ? 5 : (var7 == 2 ? 3 : (var7 == 3 ? 4 : 0)));
	}

	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if (par5EntityPlayer.getCurrentEquippedItem().itemID == Item.stick.itemID){
			int data = par1World.getBlockMetadata(par2, par3,par4);
			par1World.setBlockMetadataWithNotify(par2, par3,par4,(data+1) % 6);
		}

		return false;
	}


	public int newDir(int side, int metadata) {
		if(side==metadata)
			return Facing.faceToSide[side];
		return side;

	}
}



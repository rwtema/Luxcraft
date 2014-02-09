package luxcraft;

import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLuxLaser extends BlockContainer
{
	public int type;
	public BlockLuxLaser(int par1, int par2)
	{
		super(par1, Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setLightOpacity(0);
		this.setTextureFile("/luxcraft/terrain.png");
		switch(par2){
		case 0:
			this.blockIndexInTexture=4;
			break;
		case 1:
			this.blockIndexInTexture=36;
			break;
		}
		this.type=par2;
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public int getBlockTextureFromSideAndMetadata(int par1, int par2)
	{
		return par1 == par2 ? blockIndexInTexture : par1 == Facing.faceToSide[par2] ? 2 : type==0 ? 3 : 3;
	}

	/**
	 * Returns the block texture based on the side being looked at.  Args: side
	 */
	public int getBlockTextureFromSide(int par1)
	{
		return par1 == 0 ? blockIndexInTexture : par1 == 1 ? 2 : 3;
	}

	@SideOnly(Side.CLIENT)

	/**
	 * Returns the bounding box of the wired rectangular prism to render.
	 */
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y, z
	 */
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		this.func_85107_d(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
	}

	public void func_85107_d(int par1)
	{
		float var3 = 0.125F;

		if (par1 == 0)
		{
			this.setBlockBounds(0.0F, 1.0F - var3, 0.0F, 1.0F, 1.0F, 1.0F);
		}

		if (par1 == 1)
		{
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var3, 1.0F);
		}

		if (par1 == 2)
		{
			this.setBlockBounds(0.0F, 0.0F, 1.0F - var3, 1.0F, 1.0F, 1.0F);
		}

		if (par1 == 3)
		{
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var3);
		}

		if (par1 == 4)
		{
			this.setBlockBounds(1.0F - var3, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}

		if (par1 == 5)
		{
			this.setBlockBounds(0.0F, 0.0F, 0.0F, var3, 1.0F, 1.0F);
		}
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

	/**
	 * Sets the block's bounds for rendering it as an item
	 */
	public void setBlockBoundsForItemRender()
	{
		float var3 = 0.125F;
		this.setBlockBounds(0.0F, 1.0F - var3, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	//	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
	//	{
	//		int var6 = determineOrientation(par1World, par2, par3, par4, (EntityPlayer)par5EntityLiving);
	//		par1World.setBlockMetadataWithNotify(par2, par3, par4, var6);
	//	}

	public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
	{
		return par5;
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

	public TileEntity createNewTileEntity(World var1)
	{
		return new TileEntityLuxLaser((byte) type);
	}

	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if (par5EntityPlayer.getCurrentEquippedItem() != null & par5EntityPlayer.getCurrentEquippedItem().itemID == Item.stick.itemID){
			int data = par1World.getBlockMetadata(par2, par3,par4);
			par1World.setBlockMetadataWithNotify(par2, par3,par4,(data+1) % 6);
		}

		return false;
	}
	
	@SideOnly(Side.CLIENT)

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 3));
	}
	
//	public int getRenderType(){
//		return LuxcraftClient.laserBlockRenderingID;
//	}

}

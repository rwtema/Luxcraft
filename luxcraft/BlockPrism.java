
package luxcraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPrism extends Block implements IReflector
{
	public BlockPrism(int par1)
	{
		super(par1, Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setLightOpacity(0);
		this.setTextureFile("/luxcraft/terrain.png");
		this.blockIndexInTexture=6;
	}

	//	/**
	//	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	//	 * cleared to be reused)
	//	 */
	//	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	//	{
	//		this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
	//		return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
	//	}

//	/**
//	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
//	 */
//	public int getBlockTextureFromSideAndMetadata(int par1, int par2)
//	{
//		return par1 == this.getOutSide1(par2) ? 213 : par1 == this.getOutSide2(par2) ? 214 : 54;
//	}
//
//	/**
//	 * Returns the block texture based on the side being looked at.  Args: side
//	 */
//	public int getBlockTextureFromSide(int par1)
//	{
//		return (par1 % 2 == 0) ? 213 : 54;
//	}

	//	@SideOnly(Side.CLIENT)
	//
	//	/**
	//	 * Returns the bounding box of the wired rectangular prism to render.
	//	 */
	//	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	//	{
	//		this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
	//		return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
	//	}


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

	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
	{
		int var6 = determineOrientation(par1World, par2, par3, par4, (EntityPlayer)par5EntityLiving);
		par1World.setBlockMetadataWithNotify(par2, par3, par4, var6);
	}

	public static int determineOrientation(World par0World, int par1, int par2, int par3, EntityPlayer par4EntityPlayer)
	{
		int var7 = MathHelper.floor_double((double)(par4EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		return var7 == 0 ? 0 : var7 == 1 ? 3 : var7 == 2 ? 1 : 2;
	}

	public int getOutSide1(int metadata){
		return 2 + metadata % 4;
	}    

	public int getOutSide2(int metadata){
		if (metadata >= 8)
			return 0;
		else if (metadata >= 4)
			return 1;
		else{
			switch(2 + metadata % 4){
			case 2:return 4;
			case 3:return 5;
			case 4:return 3;
			case 5:return 2;
			}
			return 2 + (metadata + 1) % 4;
		}
	}

	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{

		if(par5EntityPlayer.getCurrentEquippedItem() != null)
			if (par5EntityPlayer.getCurrentEquippedItem().itemID == Item.stick.itemID){
				int data = par1World.getBlockMetadata(par2, par3,par4);
				if (!par5EntityPlayer.isSneaking()){
					par1World.setBlockMetadataWithNotify(par2, par3,par4,(data+1) % 4 + ((int)data / 4)*4);
				}else{
					par1World.setBlockMetadataWithNotify(par2, par3,par4,(data+8) % 12);
				}
			}

//		if(par5EntityPlayer.getCurrentEquippedItem() != null)
//			if (par5EntityPlayer.getCurrentEquippedItem().itemID == Item.stick.itemID){
//				int data = par1World.getBlockMetadata(par2, par3,par4);
//					par1World.setBlockMetadataWithNotify(par2, par3,par4,(data+1) % 12);
//			}

		return false;
	}


	public int newDir(int side, int metadata) {
		if (side == Facing.faceToSide[this.getOutSide1(metadata)])
			return this.getOutSide2(metadata);
		else if (side == Facing.faceToSide[this.getOutSide2(metadata)])
			return this.getOutSide1(metadata);
		return side;
	}

	public int getRenderType(){
		return LuxcraftClient.prismRenderingID;
	}
}



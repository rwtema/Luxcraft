package luxcraft;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringTranslate;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCrate extends Item {

	public ItemCrate(int par1) {
		super(par1);
		this.setIconIndex(56);
		this.setMaxStackSize(1);
		//this.setCreativeTab(CreativeTabs.tabBlock);
		this.setTextureFile("/luxcraft/terrain.png");		
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		return par1ItemStack;
	}

	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
	 */
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		if(par1ItemStack == null)
			return false;

		if(!par1ItemStack.hasTagCompound())
			return false;

		if(!par1ItemStack.getTagCompound().hasKey("block_id"))
			return false;

		if(!par1ItemStack.getTagCompound().hasKey("block_metadata"))
			return false;		

		int blockID = par1ItemStack.getTagCompound().getInteger("block_id");
		int metadata = par1ItemStack.getTagCompound().getInteger("block_metadata");

		int var11 = par3World.getBlockId(par4, par5, par6);

		if (var11 == Block.snow.blockID)
		{
			par7 = 1;
		}
		else if (var11 != Block.vine.blockID && var11 != Block.tallGrass.blockID && var11 != Block.deadBush.blockID
				&& (Block.blocksList[var11] == null || !Block.blocksList[var11].isBlockReplaceable(par3World, par4, par5, par6)))
		{
			if (par7 == 0)
			{
				--par5;
			}

			if (par7 == 1)
			{
				++par5;
			}

			if (par7 == 2)
			{
				--par6;
			}

			if (par7 == 3)
			{
				++par6;
			}

			if (par7 == 4)
			{
				--par4;
			}

			if (par7 == 5)
			{
				++par4;
			}
		}

		if (par1ItemStack.stackSize == 0)
		{
			return false;
		}
		else if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
		{
			return false;
		}
		else if (par5 == 255 && Block.blocksList[blockID].blockMaterial.isSolid())
		{
			return false;
		}
		else if (par3World.canPlaceEntityOnSide(blockID, par4, par5, par6, false, par7, par2EntityPlayer))
		{
			Block var12 = Block.blocksList[blockID];
			//int var13 = this.getMetadata(par1ItemStack.getItemDamage());
			//int var14 = Block.blocksList[blockID].onBlockPlaced(par3World, par4, par5, par6, par7, par8, par9, par10, var13);

			if (par3World.setBlockAndMetadataWithNotify(par4, par5, par6, blockID, metadata))
			{
				if(par1ItemStack.getTagCompound().hasKey("tileEntityData")){
					NBTTagCompound tiledata = par1ItemStack.getTagCompound().getCompoundTag("tileEntityData");
					
					TileEntity tile = par3World.getBlockTileEntity(par4, par5, par6);
					if(tile != null & tiledata != null){
						tiledata.setBoolean("Wrangler", true);
						tiledata.setInteger("x", par4);
						tiledata.setInteger("y", par5);
						tiledata.setInteger("z", par6);
						tile.readFromNBT(tiledata);
					}
				}	
				par3World.playSoundEffect((double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), (double)((float)par6 + 0.5F), var12.stepSound.getPlaceSound(), (var12.stepSound.getVolume() + 1.0F) / 2.0F, var12.stepSound.getPitch() * 0.8F);
				par2EntityPlayer.destroyCurrentEquippedItem();

				//--par1ItemStack.stackSize;
			}

			return true;
		}
		else
		{
			return false;
		}
	}

	@SideOnly(Side.CLIENT)

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		if(par1ItemStack == null)
			return;

		if(!par1ItemStack.hasTagCompound())
			return;

		if(!par1ItemStack.getTagCompound().hasKey("block_id"))
			return;

		if(!par1ItemStack.getTagCompound().hasKey("block_metadata"))
			return;

		int id = par1ItemStack.getTagCompound().getInteger("block_id");

		if(Block.blocksList[id] == null)
			return;
		if(Block.blocksList[id].getBlockName() == null)
			return;

		ItemStack tis = new ItemStack(Block.blocksList[id], 1, par1ItemStack.getTagCompound().getInteger("block_metadata"));

		String t = ("" + StringTranslate.getInstance().translateNamedKey(tis.getItem().getItemNameIS(tis))).trim();

		par3List.add(t);

		//		 if(par1ItemStack.getTagCompound().hasKey("tileEntityData")){
		//			  //par1ItemStack.getTagCompound().getCompoundTag("tileEntityData");
		//			 
		//			 par3List.add(par1ItemStack.getTagCompound().getCompoundTag("tileEntityData").toString());
		//		 }
	}
}

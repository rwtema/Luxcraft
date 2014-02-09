package luxcraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemWrangler  extends Item {

	public ItemWrangler(int par1) {
		super(par1);
		this.setIconIndex(55);
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setTextureFile("/luxcraft/terrain.png");		
	}
	
    /**
     * Returns if the item (tool) can harvest results from the block type.
     */
    public boolean canHarvestBlock(Block par1Block)
    {
    	return Item.pickaxeSteel.canHarvestBlock(par1Block)
    			| Item.axeSteel.canHarvestBlock(par1Block)
    			| Item.shovelSteel.canHarvestBlock(par1Block);
    			
        //return par1Block == Block.obsidian ? this.toolMaterial.getHarvestLevel() == 3 : (par1Block != Block.blockDiamond && par1Block != Block.oreDiamond ? (par1Block != Block.oreEmerald && par1Block != Block.blockEmerald ? (par1Block != Block.blockGold && par1Block != Block.oreGold ? (par1Block != Block.blockSteel && par1Block != Block.oreIron ? (par1Block != Block.blockLapis && par1Block != Block.oreLapis ? (par1Block != Block.oreRedstone && par1Block != Block.oreRedstoneGlowing ? (par1Block.blockMaterial == Material.rock ? true : (par1Block.blockMaterial == Material.iron ? true : par1Block.blockMaterial == Material.anvil)) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2);
    }

	/**
	 * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
	 * sword
	 */
	public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block, int metadata)
	{
		float t = super.getStrVsBlock(par1ItemStack, par2Block, metadata);
		//if(par2Block.hasTileEntity(0)){
			t = Math.max(t,Item.pickaxeSteel.getStrVsBlock(par1ItemStack, par2Block, metadata));
			t = Math.max(t,Item.axeSteel.getStrVsBlock(par1ItemStack, par2Block, metadata));
			t = Math.max(t,Item.shovelSteel.getStrVsBlock(par1ItemStack, par2Block, metadata));
		//	t= 4;
		//}
		return t;
		//        return par2Block != null && (par2Block.blockMaterial == Material.iron || par2Block.blockMaterial == Material.anvil || par2Block.blockMaterial == Material.rock) ? this.efficiencyOnProperMaterial : super.getStrVsBlock(par1ItemStack, par2Block);
	}

	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack)
	{
		return false;
	}

	/**
	 * Called before a block is broken.  Return true to prevent default block harvesting.
	 *
	 * Note: In SMP, this is called on both client and server sides!
	 *
	 * @param itemstack The current ItemStack
	 * @param X The X Position
	 * @param Y The X Position
	 * @param Z The X Position
	 * @param player The Player that is wielding the item
	 * @return True to prevent harvesting, false to continue as normal
	 */
	public boolean onBlockStartBreak(ItemStack itemstack, int par4, int par5, int par6, EntityPlayer player)
	{
		World par3World = player.worldObj;
		TileEntity var11 = par3World.getBlockTileEntity(par4, par5, par6);

		if(par3World.getBlockTileEntity(par4, par5, par6) != null){
			NBTTagCompound tileData = new NBTTagCompound();
			tileData.setBoolean("Wrangler", true);
			var11.writeToNBT(tileData);
			if(!tileData.hasKey("Wrangler") | !tileData.getBoolean("Wrangler")){
				return false;
			}

			if(par3World.isRemote)
				return true;
			
			ItemStack newCrate = new ItemStack(Luxcraft.itemCrate, 1);
			NBTTagCompound var3 = newCrate.getTagCompound();

			if (var3 == null)
			{
				var3 = new NBTTagCompound();
				newCrate.setTagCompound(var3);
			}

			var3.setInteger("block_id", par3World.getBlockId(par4, par5, par6));
			var3.setInteger("block_metadata", par3World.getBlockMetadata(par4, par5, par6));
			var3.setCompoundTag("tileEntityData", tileData);

			newCrate.setTagCompound(var3);

			par3World.removeBlockTileEntity(par4, par5, par6);
			par3World.setBlockWithNotify(par4, par5, par6, 0);
			EntityItem var14 = new EntityItem(par3World, par4+0.5, par5+0.5, par6+0.5, newCrate);
			par3World.spawnEntityInWorld(var14);

			return true;
		}

		return false;
	}

}

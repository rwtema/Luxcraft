package luxcraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class ItemAntiPickaxe extends ItemTool
{
	/** an array of the blocks this pickaxe is effective against */
	public static final Block[] blocksEffectiveAgainst = new Block[] {Block.cobblestone, Block.stoneDoubleSlab, Block.stoneSingleSlab, Block.stone, Block.sandStone, Block.cobblestoneMossy, Block.oreIron, Block.blockSteel, Block.oreCoal, Block.blockGold, Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice, Block.netherrack, Block.oreLapis, Block.blockLapis, Block.oreRedstone, Block.oreRedstoneGlowing, Block.rail, Block.railDetector, Block.railPowered};

	public ItemAntiPickaxe(int par1)
	{
		super(par1, 2, EnumToolMaterial.EMERALD, blocksEffectiveAgainst);
		this.setCreativeTab(CreativeTabs.tabTools);
		this.setTextureFile("/luxcraft/terrain.png");
		this.setIconIndex(64+16);		
	}

	/**
	 * Returns the damage against a given entity.
	 */
	public int getDamageVsEntity(Entity par1Entity)
	{
		return this.damageVsEntity/2;
	}

	/**
	 * Returns if the item (tool) can harvest results from the block type.
	 */
	public boolean canHarvestBlock(Block par1Block)
	{
		return par1Block == Block.obsidian ? this.toolMaterial.getHarvestLevel() == 3 : (par1Block != Block.blockDiamond && par1Block != Block.oreDiamond ? (par1Block != Block.oreEmerald && par1Block != Block.blockEmerald ? (par1Block != Block.blockGold && par1Block != Block.oreGold ? (par1Block != Block.blockSteel && par1Block != Block.oreIron ? (par1Block != Block.blockLapis && par1Block != Block.oreLapis ? (par1Block != Block.oreRedstone && par1Block != Block.oreRedstoneGlowing ? (par1Block.blockMaterial == Material.rock ? true : (par1Block.blockMaterial == Material.iron ? true : par1Block.blockMaterial == Material.anvil)) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2);
	}

	/**
	 * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
	 * sword
	 */
	public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block, int metadata)
	{
		float t = super.getStrVsBlock(par1ItemStack, par2Block, metadata);
		t = Math.max(t,Item.pickaxeDiamond.getStrVsBlock(par1ItemStack, par2Block, metadata));
		t = Math.max(t,Item.axeDiamond.getStrVsBlock(par1ItemStack, par2Block, metadata)/2);
		t = Math.max(t,Item.shovelDiamond.getStrVsBlock(par1ItemStack, par2Block, metadata)/2);
		return t*6;
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
	public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player)
	{

		int id = player.worldObj.getBlockId(X, Y, Z);
		if(id>0){
			if (player.worldObj.setBlockAndMetadataWithUpdate(X, Y, Z, 0, 0, player.worldObj.isRemote))
			{
				player.worldObj.playAuxSFX(2001, X, Y, Z, id + (player.worldObj.getBlockMetadata(X, Y, Z) << 12));
				player.worldObj.notifyBlocksOfNeighborChange(X, Y, Z, 0);
			}

			Block.blocksList[id].onBlockDestroyedByExplosion(player.worldObj, X, Y, Z);
		}
		return true;
	}
}

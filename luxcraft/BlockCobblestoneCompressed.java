package luxcraft;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCobblestoneCompressed extends Block {
	public static final int maxIterations = 8;

	protected BlockCobblestoneCompressed(int par1, Material par2Material) {
		super(par1, par2Material);
		this.blockIndexInTexture = 64;
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setTextureFile("/luxcraft/terrain.png");
	}
	
	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public int getBlockTextureFromSideAndMetadata(int par1, int par2)
	{
		if(par2>maxIterations) return 32;
		return 64+par2;
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
		for (int var4 = 0; var4 < maxIterations; ++var4)
		{
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}	
}

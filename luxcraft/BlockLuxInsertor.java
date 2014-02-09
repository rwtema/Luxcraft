
package luxcraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockLuxInsertor extends Block
{
	public BlockLuxInsertor(int par1)
	{
		super(par1, Material.iron);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setLightOpacity(0);
		this.setTextureFile("/luxcraft/terrain.png");
		this.blockIndexInTexture=5;
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
}




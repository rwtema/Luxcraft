package luxcraft;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockLuxAssembler extends BlockContainer {

	public BlockLuxAssembler(int par1) {
		super(par1, 26, Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setTextureFile("/luxcraft/terrain.png");
	}

	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityLuxSynthesizer();
	}
	
	
    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int par1)
    {
    	if(par1 == 1) return 25;
        return this.blockIndexInTexture;
    }
	
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if(par5EntityPlayer.isSneaking())
			return false;
		
		if (par1World.isRemote)
		{
			return true;
		}
		else
		{
			TileEntity var10 = par1World.getBlockTileEntity(par2, par3, par4);

			if (var10 != null)
			{
				par5EntityPlayer.openGui(Luxcraft.instance, 0, par1World, par2, par3, par4);
			}

			return true;
		}
	}

}

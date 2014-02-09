package luxcraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCurtain extends Block
{
    public BlockCurtain(int id, int texture)
    {
        super(id, texture, Material.cloth);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setLightOpacity(12);
        float h = 0.025F;
        this.setBlockBounds(0.0F, 0.0F, 0.5F - h, 1.0F, 1.0F, 0.5F + h);
    }

    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int var6 = ((MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) + 2) % 4;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var6);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var6 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        float h = 0.025F;

        if (var6 % 2 == 0)
        {
            this.setBlockBounds(0.0F, 0.0F, 0.5F - h, 1.0F, 1.0F, 0.5F + h);
        }
        else
        {
            this.setBlockBounds(0.5F - h, 0.0F, 0.0F, 0.5F + h, 1.0F, 1.0F);
        }
    }
//    public int getRenderType()
//    {
//        return 302;
//    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        float h = 0.025F;
        this.setBlockBounds(0.0F, 0.0F, 0.5F - h, 1.0F, 1.0F, 0.5F + h);
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

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        float var5 = 0.125F;

        //return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)par2 + var5), (double)par3, (double)((float)par4 + var5), (double)((float)(par2 + 1) - var5), (double)(par3 + 1), (double)((float)(par4 + 1) - var5));
        if (par1World.getBlockMetadata(par2, par3, par4) % 2 == 0)
        {
            return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(par2, par3, par4 + 0.5 - var5, par2 + 1, par3 + 1, par4 + 0.5 + var5);
        }

        return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(par2 + 0.5 - var5, par3, par4, par2 + 0.5 + var5, par3 + 1, par4 + 1);
    }

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

    /*	@SideOnly(Side.CLIENT)

    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
    	//     float var5 = 0.0625F;
    	//     return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)par2 + var5), (double)par3, (double)((float)par4 + var5), (double)((float)(par2 + 1) - var5), (double)(par3 + 1), (double)((float)(par4 + 1) - var5));
    //		return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)par2), (double)par3, (double)((float)par4), (double)((float)(par2 + 1)), (double)(par3 + 1), (double)((float)(par4 + 1)));
    }*/

}

package luxcraft;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBUD extends Block
{
    protected BlockBUD(int par1)
    {
        //super(par1, myMaterial);
        //super(par1, Material.anvil);
        super(par1, Material.anvil);
        this.blockIndexInTexture = 62;
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setTextureFile("/luxcraft/terrain.png");
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

//    public int getRenderType()
//    {
//        return TutorialClient.tilingRendererID;
//        // return 256;
//    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return par2 == 1 ? blockIndexInTexture + 1: blockIndexInTexture;
    }


    /**
     * Is this block powering the block on the specified side
     */
    public boolean isProvidingStrongPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return par1IBlockAccess.getBlockMetadata(par2, par3, par4) == 1;
        //return (((id % 6) + 1 - 2 * (id % 2)) % 6) == par5;
        //return false;
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean canProvidePower()
    {
        return true;
    }
    /**
     * Is this block indirectly powering the block on the specified side
     */
    public boolean isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return this.isProvidingStrongPower(par1IBlockAccess, par2, par3, par4, par5);
    }

    public boolean canConnectRedstone(IBlockAccess iba, int i, int j, int k, int dir)
    {
        return true;
    }

    public int tickRate()
    {
        return 2;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        if (par5 != this.blockID & par5 != Block.redstoneRepeaterIdle.blockID)
        {
            int data = par1World.getBlockMetadata(par2, par3, par4);

            if (data == 0)
            {
                par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate());
            }
        }
    }

    public void updateRedstone(World par1World, int par2, int par3, int par4)
    {
        //par1World.notifyBlocksOfNeighborChange(par2, par3, par4,this.blockID);
        par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
        par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, this.blockID);
        par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this.blockID);
        par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this.blockID);
        par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this.blockID);
        par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this.blockID);
    }

    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par1World.getBlockMetadata(par2, par3, par4) == 0)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 1);
            par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate());
        }
        else if (par1World.getBlockMetadata(par2, par3, par4) == 1)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2);
            par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate() * 3);
        }
        else
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 0);
        }

        this.updateRedstone(par1World, par2, par3, par4);
    }
    
    /**
     * Returns Returns true if the given side of this block type should be rendered (if it's solid or not), if the
     * adjacent block is at the given coordinates. Args: blockAccess, x, y, z, side
     */
    public boolean isBlockSolid(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return true;
    }

    /**
     * Checks if the block is a solid face on the given side, used by placement logic.
     *
     * @param world The current world
     * @param x X Position
     * @param y Y position
     * @param z Z position
     * @param side The side to check
     * @return True if the block is solid on the specified side.
     */
    public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side)
    {
        return true;
    }
}

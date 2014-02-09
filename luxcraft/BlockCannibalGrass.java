package luxcraft;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCannibalGrass extends Block
{
    protected BlockCannibalGrass(int par1)
    {
        super(par1, Material.grass);
        this.blockIndexInTexture = 77;
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return par1 == 1 ? 78 : (par1 == 0 ? 2 : 77);
    }

    @SideOnly(Side.CLIENT)

    /**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
    public int getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        if (par5 == 1)
        {
            return 78;
        }
        else if (par5 == 0)
        {
            return 2;
        }
        else
        {
            Material var6 = par1IBlockAccess.getBlockMaterial(par2, par3 + 1, par4);
            return var6 != Material.snow && var6 != Material.craftedSnow ? 77 : 68;
        }
    }

    @SideOnly(Side.CLIENT)
    public int getBlockColor()
    {
        return 0x505050;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    public int getRenderColor(int par1)
    {
        return this.getBlockColor();
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        if (par1IBlockAccess.getBlockMetadata(par2, par3, par4) == 15)
        {
            return 0x303030;
        }

        return getBlockColor();
    }

    public void onEntityWalking(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        if (par5Entity.isEntityAlive())
        {
            par5Entity.attackEntityFrom(DamageSource.generic, 1);
        }
    }

    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par5EntityPlayer.getCurrentEquippedItem() != null)
        {
            return false;
        }

        int myId = par1World.getBlockId(par2, par3, par4);

        for (int x = par2 - 3; x <= par2 + 3; x += 1)
        {
            for (int y = par3 - 3; y <= par3 + 3; y += 1)
            {
                for (int z = par4 - 3; z <= par4 + 3; z += 1)
                {
                    if (par1World.getBlockId(x, y, z) == myId)
                    {
                        par1World.setBlockWithNotify(x, y, z, Block.dirt.blockID);
                    }
                }
            }
        }

        return true;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!par1World.isRemote)
        {
            boolean destroy = true;

            if (par1World.getBlockLightValue(par2, par3 + 1, par4) < 4 && par1World.getBlockLightOpacity(par2, par3 + 1, par4) > 2)
            {
                par1World.setBlockWithNotify(par2, par3, par4, Block.dirt.blockID);
            }
            else
            {
                int t = par1World.getBlockMetadata(par2, par3, par4);

                if (t != 15)
                {
                    for (int var6 = 0; var6 < 12; ++var6)
                    {
                        int var7 = par2 + par5Random.nextInt(3) - 1;
                        int var8 = par3 + par5Random.nextInt(5) - 3;
                        int var9 = par4 + par5Random.nextInt(3) - 1;

                        if (par1World.getBlockId(var7, var8, var9) == Block.grass.blockID)
                        {
                            if (t != 15)
                            {
                                if (par5Random.nextInt(2) == 0)
                                {
                                    t = t + 1;
                                }
                            }

                            //t=t+(int)Math.floor(par5Random.nextInt(5)/2);
                            par1World.setBlockAndMetadataWithNotify(var7, var8, var9,  par1World.getBlockId(par2, par3, par4), t);
                            destroy = false;
                        }
                    }
                }

                if (destroy)
                {
                    for (int x = par2 - 1; x <= par2 + 1; x += 1)
                    {
                        for (int y = par3 - 3; y <= par3 + 5 - 2; y += 1)
                        {
                            for (int z = par4 - 1; z <= par4 + 1; z += 1)
                            {
                                if (par1World.getBlockId(x, y, z) == Block.grass.blockID)
                                {
                                    destroy = false;
                                    return;
                                }
                            }
                        }
                    }
                }

                if (destroy)
                {
                    par1World.setBlockWithNotify(par2, par3, par4, Block.dirt.blockID);
                }
            }
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Block.dirt.idDropped(0, par2Random, par3);
    }
}

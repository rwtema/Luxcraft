package luxcraft;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSpike extends Block
{
    public BlockSpike(int id, int texture)
    {
        super(id, texture, Material.iron);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public int getRenderType()
    {
        return LuxcraftClient.spikeRendererID;
    }

    public int getBlockColor()
    {
        return 0xffffff;
    }

    public int getBloodColor()
    {
        return 0xff7777;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

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

    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        if (par5Entity.isEntityAlive())
        {
            int var6 = par1World.getBlockMetadata(par2, par3, par4) % 6;

            if (var6 < 6)
            {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 + 6);
            }

            this.checkForWater(par1World, par2, par3, par4);
        }

        par5Entity.attackEntityFrom(DamageSource.generic, 4);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        float var5 = 0.0625F;
        return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)par2 + var5), (double)par3, (double)((float)par4 + var5), (double)((float)(par2 + 1) - var5), (double)((float)(par3 + 1) - var5), (double)((float)(par4 + 1) - var5));
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
//     float var5 = 0.0625F;
//     return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)par2 + var5), (double)par3, (double)((float)par4 + var5), (double)((float)(par2 + 1) - var5), (double)(par3 + 1), (double)((float)(par4 + 1) - var5));
        return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)par2), (double)par3, (double)((float)par4), (double)((float)(par2 + 1)), (double)(par3 + 1), (double)((float)(par4 + 1)));
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        this.checkForWater(par1World, par2, par3, par4);
    }

    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        this.checkForWater(par1World, par2, par3, par4);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        this.checkForWater(par1World, par2, par3, par4);
    }

    private void checkForWater(World par1World, int par2, int par3, int par4)
    {
        int var6 = par1World.getBlockMetadata(par2, par3, par4);

        if (var6 >= 6 && par1World.getBlockId(par2, par3, par4) == this.blockID)
        {
            boolean var5 = false;

            for (int dx = -1; dx <= 1; dx += 1)
                for (int dy = -1; dy <= 1; dy += 1)
                    for (int dz = -1; dz <= 1; dz += 1)
                        if (var5 || par1World.getBlockId(par2 + dx, par3 + dy, par4 + dz) == Block.waterMoving.blockID
                                || par1World.getBlockId(par2 + dx, par3 + dy, par4 + dz) == Block.waterStill.blockID)
                        {
                            var5 = true;
                        }

            if (var5)
            {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 % 6);
            }
        }
    }
}

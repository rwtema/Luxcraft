package luxcraft;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCursedGround extends Block
{
	public static boolean powered=false;
	
    protected BlockCursedGround(int par1)
    {
        super(par1, Material.grass);
        this.blockIndexInTexture = 3;
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setStepSound(Block.soundGrassFootstep);
        this.setTextureFile("/luxcraft/terrain.png");
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return par1 == 1 ? 88 : (par1 == 0 ? 86 : 85);
    }



//    @SideOnly(Side.CLIENT)
//    public int getBlockColor()
//    {
//        return 0x884488;
//        //return 0xffffff;
//        //        double var1 = 0.5D;
//        //      double var3 = 1.0D;
//        //    return ColorizerGrass.getGrassColor(var1, var3);
//    }
//
//    @SideOnly(Side.CLIENT)
//
//    /**
//     * Returns the color this block should be rendered. Used by leaves.
//     */
//    public int getRenderColor(int par1)
//    {
//        return this.getBlockColor();
//    }
//
//    @SideOnly(Side.CLIENT)
//
//    /**
//     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
//     * when first determining what to render.
//     */
//    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
//    {
//        int var5 = 0;
//        int var6 = 0;
//        int var7 = 0;
//
//        for (int var8 = -1; var8 <= 1; ++var8)
//        {
//            for (int var9 = -1; var9 <= 1; ++var9)
//            {
//                int var10 = par1IBlockAccess.getBiomeGenForCoords(par2 + var9, par4 + var8).getBiomeGrassColor();
//                var5 += (var10 & 16711680) >> 16;
//                var6 += (var10 & 65280) >> 8;
//                var7 += var10 & 255;
//            }
//        }
//
//        //return (var5 / 9 & 255) << 16 | (var6 / 9 & 255) << 8 | var7 / 9 & 255;
//        return 0x664466;
//    }

//	@SideOnly(Side.CLIENT)
//
//	/**
//	 * A randomly called display update to be able to add particles or other items for display
//	 */
//	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
//	{
//		super.randomDisplayTick(par1World, par2, par3, par4, par5Random);
//
//		if (par5Random.nextInt(10) == 0)
//		{
//			par1World.spawnParticle("townaura", (double)((float)par2 + par5Random.nextFloat()), (double)((float)par3 + 1.1F), (double)((float)par4 + par5Random.nextFloat()), 0.0D, 0.0D, 0.0D);
//		}
//	}

    public void onEntityWalking(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
    	if(par1World.isRemote)
    		return;
        if (par5Entity.isEntityAlive())
        {
            if (par1World.getBlockLightValue(par2, par3 + 1, par4) > 9)
            {
                par5Entity.attackEntityFrom(DamageSource.cactus, 1);

                if (par1World.rand.nextInt(3) == 0)
                    if (par1World.getBlockId(par2, par3 + 1, par4) == 0)
                    {
                        par1World.setBlockWithNotify(par2, par3 + 1, par4, Block.fire.blockID);
                    }

                par1World.setBlockWithNotify(par2, par3, par4, Block.dirt.blockID);

                for (int var6 = 0; var6 < 20; ++var6)
                {
                    int var7 = par2 + par1World.rand.nextInt(9) - 4;
                    int var8 = par3 + par1World.rand.nextInt(5) - 3;
                    int var9 = par4 + par1World.rand.nextInt(9) - 4;
                    int var10 = par1World.getBlockId(var7, var8 + 1, var9);

                    if (par1World.getBlockId(var7, var8, var9) == this.blockID & (par1World.getBlockLightOpacity(var7, var8 + 1, var9) > 2 | (par1World.getBlockLightValue(var7, var8 + 1, var9) > 9)))
                    {
                        par1World.setBlockWithNotify(var7, var8, var9, Block.dirt.blockID);
                    }
                }
            }
        }
    }
    
    /**
     * Returns true if the block is emitting indirect/weak redstone power on the specified side. If isBlockNormalCube
     * returns true, standard redstone propagation rules will apply instead and this will not be called. Args: World, X,
     * Y, Z, side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
     */
    public boolean isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return powered;
    }
    
    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return !powered;
    }    

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!par1World.isRemote)
        {
            if (par1World.getBlockLightValue(par2, par3 + 1, par4) < 9)
            {
                boolean k = par1World.difficultySetting > 0;

                if (k)
                {
                    k = par1World.getBlockId(par2, par3 + 1, par4) == 0 && par1World.getBlockId(par2, par3 + 2, par4) == 0;
                }

                if (k)
                {
                    for (int i = -1; i <= 1 & k; i += 1)
                    {
                        for (int j = -1; j <= 1 & k; j += 1)
                        {
                            if (par1World.getBlockId(par2 + i, par3 + 1, par4 + j) != 0)
                            {
                                k = false;
                            }
                        }
                    }
                }

                if (k)
                {
                    int var12 = par1World.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(par2, par3, par4, par2 + 1, par3 + 1, par4 + 1).expand(7, 2, 7)).size();
                    k = par5Random.nextInt(4) >= var12;
                }

                if (k)
                {
                    EntityLiving t;
                    List var5 = par1World.getChunkProvider().getPossibleCreatures(EnumCreatureType.monster, par2, par3, par4);
                    SpawnListEntry var7 = var5 != null && !var5.isEmpty() ? (SpawnListEntry)WeightedRandom.getRandomItem(par1World.rand, var5) : null;

                    if (var7 != null)
                    {
                        try
                        {
                            t = (EntityLiving)var7.entityClass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {par1World});
                        }
                        catch (Exception var31)
                        {
                            var31.printStackTrace();
                            return;
                        }

                        if (!(t instanceof EntityFlying))
                        {
                            t.setLocationAndAngles(par2 + 0.5, par3 + 1, par4 + 0.5, par5Random.nextFloat() * 360.0F, 0.0F);
                            par1World.spawnEntityInWorld(t);
                            t.initCreature();
                            t.playLivingSound();
                        }
                    }
                }
            }

            if (par1World.getBlockLightOpacity(par2, par3 + 1, par4) > 2 | par1World.getBlockLightValue(par2, par3 + 1, par4) > 9)
            {
                if (par1World.getBlockId(par2, par3 + 1, par4) == 0)
                {
                    par1World.setBlockWithNotify(par2, par3 + 1, par4, Block.fire.blockID);
                }

                par1World.setBlockWithNotify(par2, par3, par4, Block.dirt.blockID);

                for (int var6 = 0; var6 < 20; ++var6)
                {
                    int var7 = par2 + par5Random.nextInt(9) - 4;
                    int var8 = par3 + par5Random.nextInt(5) - 3;
                    int var9 = par4 + par5Random.nextInt(9) - 4;
                    int var10 = par1World.getBlockId(var7, var8 + 1, var9);

                    if (par1World.getBlockId(var7, var8, var9) == this.blockID & (par1World.getBlockLightOpacity(var7, var8 + 1, var9) > 2 | (par1World.getBlockLightValue(var7, var8 + 1, var9) > 9)))
                    {
                        par1World.setBlockWithNotify(var7, var8, var9, Block.dirt.blockID);
                    }
                }
            }
            else if (par1World.getBlockLightValue(par2, par3 + 1, par4) < 9)
            {
                for (int var6 = 0; var6 < 4; ++var6)
                {
                    int var7 = par2 + par5Random.nextInt(3) - 1;
                    int var8 = par3 + par5Random.nextInt(5) - 3;
                    int var9 = par4 + par5Random.nextInt(3) - 1;
                    int var10 = par1World.getBlockId(var7, var8 + 1, var9);

                    if (par1World.getBlockId(var7, var8, var9) == Block.dirt.blockID && par1World.getBlockLightOpacity(var7, var8 + 1, var9) <= 2 && (par1World.getBlockLightValue(var7, var8 + 1, var9) < 9))
                    {
                        par1World.setBlockWithNotify(var7, var8, var9, this.blockID);
                    }
                }
            }
        }
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

//    /**
//     * Returns the ID of the items to drop on destruction.
//     */
//    public int idDropped(int par1, Random par2Random, int par3)
//    {
//        return Block.dirt.idDropped(0, par2Random, par3);
//    }
}

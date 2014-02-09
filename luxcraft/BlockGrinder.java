package luxcraft;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGrinder extends BlockContainer
{
    public BlockGrinder(int id, int texture)
    {
        super(id, texture, Material.iron);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        this.setCreativeTab(CreativeTabs.tabBlock);
        //this.setLightOpacity(12);
    }

    /* public int getRenderType()
    {
     return 22;
    }*/

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return this.getBlockTextureFromSide(par1);
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int par1)
    {
        return par1 == 0 | par1 == 1 ? 213 : 54;
    }

    public TileEntity createNewTileEntity(World var1)
    {
        return new TileEntityGrinder();
    }

    public int getBlockColor()
    {
        return 0xffffff;
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
//        int var6 = determineOrientation(par1World, par2, par3, par4, (EntityPlayer)par5EntityLiving);
//        par1World.setBlockMetadataWithNotify(par2, par3, par4, var6);
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
        if (!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4))
        {
            if (par5Entity instanceof EntityLiving)
            {
                if (((EntityLiving) par5Entity).getHealth() > 0)
                {
                    if (((EntityLiving) par5Entity).getHealth() <= 1)
                    {
                        EntityPlayer attacker = par1World.getClosestPlayer(par2 + 0.5, par3 + 0.5, par4 + 0.5, 0);

                        if (attacker == null)
                            if (par1World.playerEntities.size() > 0)
                            {
                                attacker = (EntityPlayer) par1World.playerEntities.get(par1World.rand.nextInt(par1World.playerEntities.size()));
                            }

                        if (attacker != null)
                        {
                            par5Entity.attackEntityFrom(DamageSource.causePlayerDamage(attacker), 150);
                        }
                        else
                        {
                            par5Entity.attackEntityFrom(DamageSource.generic, 10);
                        }
                    }
                    else
                    {
                        par5Entity.attackEntityFrom(DamageSource.generic, 1);
                    }

 
                }
            }
            else if (par5Entity instanceof EntityItem)
            {
                par5Entity.setPosition(par5Entity.posX, par5Entity.posY - 0.5, par5Entity.posZ);
            }
            else if (par5Entity instanceof EntityXPOrb)
            {
                par5Entity.setDead();
            }
        }
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(par2, par3, par4, par2 + 1, par3 + 0.5, par4 + 1);
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        //     float var5 = 0.0625F;
        //     return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)par2 + var5), (double)par3, (double)((float)par4 + var5), (double)((float)(par2 + 1) - var5), (double)(par3 + 1), (double)((float)(par4 + 1) - var5));
        return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)par2), (double)par3, (double)((float)par4), (double)((float)(par2 + 1)), (double)(par3 + 0.6), (double)((float)(par4 + 1)));
    }
}

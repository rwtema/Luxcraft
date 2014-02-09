package luxcraft;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLinkingChest extends BlockContainer
{
    protected BlockLinkingChest(int par1)
    {
        super(par1, Material.rock);
        this.blockIndexInTexture = 1;
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
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

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return LuxcraftClient.linkingChestID;
    }

//	/**
//	 * Returns the ID of the items to drop on destruction.
//	 */
//	public int idDropped(int par1, Random par2Random, int par3)
//	{
//		return Block.obsidian.blockID;
//	}
//
//	/**
//	 * Returns the quantity of items to drop on block destruction.
//	 */
//	public int quantityDropped(Random par1Random)
//	{
//		return 8;
//	}

    /**
     * Return true if a player with Silk Touch can harvest this block directly, and not its normal drops.
     */
    protected boolean canSilkHarvest()
    {
        return true;
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        byte var6 = 0;
        int var7 = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (var7 == 0)
        {
            var6 = 2;
        }

        if (var7 == 1)
        {
            var6 = 5;
        }

        if (var7 == 2)
        {
            var6 = 3;
        }

        if (var7 == 3)
        {
            var6 = 4;
        }

        par1World.setBlockMetadataWithNotify(par2, par3, par4, var6);

        if (par1World.getBlockTileEntity(par2, par3, par4) != null)
        {
            if (par5EntityLiving instanceof EntityPlayer)
            {
                TileEntityLinkingChest chest = (TileEntityLinkingChest) par1World.getBlockTileEntity(par2, par3, par4);
                chest.owner = par5EntityLiving.getEntityName();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public int getBlockColor()
    {
        return 0xFF80FF;
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
    	return this.getBlockColor();
    }

    
    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        TileEntityLinkingChest var11 = (TileEntityLinkingChest)par1World.getBlockTileEntity(par2, par3, par4);

        if (var11 != null)
        {
            if (par1World.getBlockTileEntity(par2, par3 + 1, par4) != null)
            {
                if (par1World.getBlockTileEntity(par2, par3 + 1, par4) instanceof TileEntitySign)
                {
                    TileEntitySign sign = (TileEntitySign)par1World.getBlockTileEntity(par2, par3 + 1, par4);
                    var11.owner = sign.signText[0];
                    var11.onInventoryChanged();
                }
            }

            if (par1World.isBlockNormalCube(par2, par3 + 1, par4))
            {
                return true;
            }
            else if (par1World.isRemote)
            {
                return true;
            }
            else
            {
                //	if(var11.owner==par5EntityPlayer.username){
                par5EntityPlayer.openGui(Luxcraft.instance, 0, par1World, par2, par3, par4);
                //	}else{
                par5EntityPlayer.sendChatToPlayer("Chest belongs to " + var11.owner);
                //	}
                return true;
            }
        }
        else
        {
            return true;
        }
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World par1World)
    {
        return new TileEntityLinkingChest();
    }

    @SideOnly(Side.CLIENT)

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        for (int var6 = 0; var6 < 3; ++var6)
        {
            double var10000 = (double)((float)par2 + par5Random.nextFloat());
            double var9 = (double)((float)par3 + par5Random.nextFloat());
            var10000 = (double)((float)par4 + par5Random.nextFloat());
            double var13 = 0.0D;
            double var15 = 0.0D;
            double var17 = 0.0D;
            int var19 = par5Random.nextInt(2) * 2 - 1;
            int var20 = par5Random.nextInt(2) * 2 - 1;
            var13 = ((double)par5Random.nextFloat() - 0.5D) * 0.125D;
            var15 = ((double)par5Random.nextFloat() - 0.5D) * 0.125D;
            var17 = ((double)par5Random.nextFloat() - 0.5D) * 0.125D;
            double var11 = (double)par4 + 0.5D + 0.25D * (double)var20;
            var17 = (double)(par5Random.nextFloat() * 1.0F * (float)var20);
            double var7 = (double)par2 + 0.5D + 0.25D * (double)var19;
            var13 = (double)(par5Random.nextFloat() * 1.0F * (float)var19);
            par1World.spawnParticle("portal", var7, var9, var11, var13, var15, var17);
        }
    }
}

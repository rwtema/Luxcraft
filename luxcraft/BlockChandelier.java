package luxcraft;

import static net.minecraftforge.common.ForgeDirection.EAST;
import static net.minecraftforge.common.ForgeDirection.NORTH;
import static net.minecraftforge.common.ForgeDirection.SOUTH;
import static net.minecraftforge.common.ForgeDirection.WEST;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Facing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockChandelier extends Block {
	public static int maxRange = 72;
	public static int delay = 3;

	public static int[] dx = {-1,1,0,0,0,0};
	public static int[] dy = {0,0,0,0,-1,1};
	public static int[] dz = {0,0,-1,1,0,0};

	public BlockChandelier(int par1) {
		super(par1, Material.rock);
		this.setLightValue(1);
		this.setLightOpacity(0);
		//		this.setTickRandomly(true);
		this.setTextureFile("/luxcraft/terrain.png");		
		this.blockIndexInTexture = 60;
		this.setCreativeTab(CreativeTabs.tabBlock);

	}


	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	public void onBlockAdded(World world, int x, int y, int z) {
		for(int i = 0;i < 6;i++){
			if(world.getBlockId(x+dx[i], y+dy[i], z+dz[i])==0){
				world.setBlockWithNotify(x+dx[i], y+dy[i], z+dz[i], Luxcraft.intangibleLightId);
			}
		}
	}    

	/**
	 * ejects contained items into the world, and notifies neighbours of an update, as appropriate
	 */
	public void breakBlock(World world, int x, int y, int z, int par5, int par6)
	{
		for(int i = 0;i < 6;i++){
			if(world.getBlockId(x+dx[i], y+dy[i], z+dz[i])==Luxcraft.intangibleLightId){
				boolean destroy=true;
				for(int j = 0;j < 6;j++){
					if(world.getBlockId(x+dx[i]+dx[j], y+dy[i]+dy[j], z+dz[i]+dz[j]) == this.blockID){
						destroy=false;
						break;
					}
				}
				if(destroy)
					world.setBlockWithNotify(x+dx[i], y+dy[i], z+dz[i], 0);
			}
		}
		super.breakBlock(world, x, y, z, par5, par6);
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
		return 1;
	}

	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		if (par1World.isBlockSolidOnSide(par2, par3+1, par4, ForgeDirection.DOWN, true))
		{
			return true;
		}
		else
		{
			int id = par1World.getBlockId(par2, par3+1, par4);
			return id == Block.fence.blockID || id == Block.netherFence.blockID || id == Block.glass.blockID || id == Block.cobblestoneWall.blockID;
		}        
	}

	/**
	 * Tests if the block can remain at its current location and will drop as an item if it is unable to stay. Returns
	 * True if it can stay and False if it drops. Args: world, x, y, z
	 */
	private boolean dropTorchIfCantStay(World par1World, int par2, int par3, int par4)
	{
		if (!this.canPlaceBlockAt(par1World, par2, par3, par4))
		{
			if (par1World.getBlockId(par2, par3, par4) == this.blockID)
			{
				this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
				par1World.setBlockWithNotify(par2, par3, par4, 0);
			}

			return false;
		}
		else
		{
			return true;
		}
	}	


	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	public void onNeighborBlockChange(World world, int x, int y, int z, int par5)
	{
		if (!canPlaceBlockAt(world, x, y, z))
		{
			this.dropBlockAsItem(world, x, y, z, 0, 0);
			world.setBlockWithNotify(x, y, z, 0);
		}
		else{
			for(int i = 0;i < 6;i++){
				if(world.getBlockId(x+dx[i], y+dy[i], z+dz[i])==0){
					world.setBlockWithNotify(x+dx[i], y+dy[i], z+dz[i], Luxcraft.intangibleLightId);
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)

	/**
	 * A randomly called display update to be able to add particles or other items for display
	 */
	public void randomDisplayTick(World world, int x, int y, int z, Random rand)
	{

		double i = rand.nextInt(5);
		double t = (2+i*3)/16;
		if(rand.nextBoolean()){
			world.spawnParticle("smoke", x + t, y+Math.abs(i-2)*2/16, z+t, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", x + t, y+Math.abs(i-2)*2/16, z+t, 0.0D, 0.0D, 0.0D);

		}else{
			world.spawnParticle("smoke", x + t, y+Math.abs(i-2)*2/16, z+1-t, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", x + t, y+Math.abs(i-2)*2/16, z+1-t, 0.0D, 0.0D, 0.0D);
		}
	}	
}

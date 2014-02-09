package luxcraft;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.liquids.IBlockLiquid;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAnt extends Block
{
	//	private static DummyPlayer dummy = null;
	int id_a = Block.grass.blockID;
	int id_b = Block.stone.blockID;
	
	int[] dx = {0,1,0,-1};
	int[] dy = {1,0,-1,0};

	protected BlockAnt(int par1)
	{
		super(par1, Material.rock);
		this.blockIndexInTexture = 0;
		//this.setTickRandomly(true);
		this.setCreativeTab(CreativeTabs.tabBlock);
		//this.setTextureFile("/luxcraft/terrain.png");
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate());
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	public void updateTick(World world, int x, int y, int z, Random par5Random)
	{
		int metadata = world.getBlockMetadata(x, y, z);
		int dir = metadata % 4;
		int block = (metadata-dir)/4;
		
		if(block==0){
			world.setBlockWithNotify(x, y, z, id_b);
			dir=(dir+1)%4;
		}else{
			world.setBlockWithNotify(x, y, z, id_a);
			dir=(dir+3)%4;
		}
		
		int i = 0;
		for(;i < 4;i++){
			if(valid(world,x + dx[(dir+i)%4],y,z+dy[(dir+i)%4])){
				dir=(dir+i)%4;
				x+=dx[dir];
				z+=dy[dir];
				break;
			}
		}
		if(i == 4) return;
		
		block=world.getBlockId(x, y, z)==id_a ? 0 : 1;
		world.setBlockAndMetadataWithNotify(x, y, z, this.blockID, block*4+dir);
		world.scheduleBlockUpdate(x, y, z, this.blockID, this.tickRate());
	}
	
	public int tickRate(){
		return 2;
	}
	
	public boolean valid(World world, int x, int y, int z){
		if(world.getBlockId(x, y+1, z)!=0)
			return false;
		int id = world.getBlockId(x, y, z);
		return id == id_a | id == id_b;
	}
	
	
    @SideOnly(Side.CLIENT)
    public int getBlockColor()
    {
        return 0xff2020;
    }

	@SideOnly(Side.CLIENT)

	/**
	 * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
	 * when first determining what to render.
	 */
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{


		int var5 = 0;
		int var6 = 0;
		int var7 = 0;

		for (int var8 = -1; var8 <= 1; ++var8)
		{
			for (int var9 = -1; var9 <= 1; ++var9)
			{
				int var10 = par1IBlockAccess.getBiomeGenForCoords(par2 + var9, par4 + var8).getWaterColorMultiplier();
				var5 += (var10 & 16711680) >> 16;
			var6 += (var10 & 65280) >> 8;
			var7 += var10 & 255;
			}
		}

		return 0xff2020;
		//return (var5 / 13 & 255) << 16 | (var6 / 17 & 255) << 8 | var7 / 17 & 255;
	}

}

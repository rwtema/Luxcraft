package luxcraft;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.liquids.IBlockLiquid;
import cpw.mods.fml.common.FMLCommonHandler;

public class BlockTunnelingStone extends Block
{
	//	private static DummyPlayer dummy = null;

	protected BlockTunnelingStone(int par1)
	{
		super(par1, Material.rock);
		this.blockIndexInTexture = 43;
		this.setTickRandomly(true);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setTextureFile("/luxcraft/terrain.png");
	}

	public boolean canCreatureSpawn(EnumCreatureType type, World world, int x, int y, int z)
	{
		return false;
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		float var5 = 0.0625F;
		return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)par2), (double)par3, (double)((float)par4), (double)((float)(par2 + 1)), (double)((float)(par3 + 1) - var5), (double)((float)(par4 + 1)));
	}

	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
	{
		par5Entity.attackEntityFrom(DamageSource.generic, 3);
	}

	public boolean eatable(World par1World, int par2, int par3, int par4)
	{
		if(nearbySpawn(par1World, par2, par3, par4)) return false;
		int id = par1World.getBlockId(par2, par3, par4);
		return  id == Block.dirt.blockID ||
				id == Block.grass.blockID ||
				id == Block.stone.blockID ||
				//id == Block.cobblestone.blockID ||
				id == Block.sand.blockID ||
				id == Block.sandStone.blockID ||
				//id == 0 ||
				//				isLiquid(par1World,par2,par3,par4) ||
				id == Block.gravel.blockID;
	}

	public boolean isLiquid(World par1World, int par2, int par3, int par4)
	{
		int id = par1World.getBlockId(par2, par3, par4);

		if(id==0 || id == this.blockID) return false;
		if(Block.blocksList[id] == null) return false;
		if(Block.blocksList[id] instanceof IBlockLiquid) return true;
		if(Block.blocksList[id] instanceof BlockFluid) return true;

		return id == Block.waterStill.blockID
				|| id == Block.waterMoving.blockID
				|| id == Block.lavaStill.blockID
				|| id == Block.lavaMoving.blockID;
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if (par5Random.nextInt(1 + (int)Math.sqrt(Math.min(par3,64))) > 2)
		{
			return;
		}

		if (!par1World.isRemote)
		{
			int dx[] = { -1, 0, 1, 0};
			int dz[] = {0, -1, 0, 1};
			int myId = par1World.getBlockId(par2, par3, par4);
			myId = this.blockID;

			//			if(par3>0 & eatable(par1World,par2,par3-1,par4)){
			//				par1World.setBlockWithNotify(par2, par3-1, par4, myId);
			//				destroy=false;
			//				return;
			//			}

			//check for water
			if (par1World.getBlockMetadata(par2, par3, par4) != 0)
			{
				boolean destroy = true;
				boolean above = false;

				for (int ax = -1; ax <= 1 & destroy; ax += 1)
					for (int ay = -1; ay <= 1 & destroy; ay += 1)
						for (int az = -1; az <= 1 & destroy; az += 1)
							if (par1World.getBlockId(par2 + ax, par3 + ay, par4 + az) == myId & par1World.getBlockMetadata(par2 + ax, par3 + ay, par4 + az) == 0)
							{
								destroy = true;
							}
							else if (ay == 1 & !above & isLiquid(par1World, par2 + ax, par3 + ay, par4 + az))
							{
								above = true;
							}

				if (destroy)
				{
					par1World.setBlock(par2, par3, par4, Block.cobblestone.blockID);
				}
				else
				{
					par1World.setBlockMetadata(par2, par3, par4, 0);
				}

				if (above)
				{
					for (int ax = -1; ax <= 1 & destroy; ax += 1)
						for (int az = -1; az <= 1 & destroy; az += 1)
							if (par1World.getBlockId(par2 + ax, par3 + 1, par4 + az) == 0)
							{
								par1World.setBlock(par2 + ax, par3 + 1, par4 + az, Block.cobblestone.blockID);
							}
				}
			}

			if (par3 > 1)
			{
				for (int a = -1; a <= 1; a += 1)
					for (int b = -1; b <= 1; b += 1)
						for (int i = 3; i >= 0; i -= 1)
							if (par1World.getBlockId(par2 + a, par3 + i, par4 + b) == myId)
								if (eatable(par1World, par2 + a, par3 + i - 1, par4 + b))
								{
									par1World.setBlockWithNotify(par2 + a, par3 + i - 1, par4 + b, myId);
								}
			}

			boolean destroy = true;

			//			for (int a = -1; a <= 1; a += 1)
			//				for (int b = -1; b <= 1; b += 1)
			//					for (int i = 3; i >= -3; i -= 1)
			//						if (par1World.getBlockId(par2 + a, par3 - i, par4 + b) == myId)
			//							if (eatable(par1World, par2 + a, par3 - i + 1, par4 + b))
			//							{
			//								par1World.setBlockWithNotify(par2 + a, par3 - i + 1, par4 + b, myId);
			//								destroy=false;
			//							}

			if (par5Random.nextBoolean())
				while (par1World.getBlockId(par2, par3 + 1, par4) == myId & par1World.getBlockMetadata(par2, par3 + 1, par4) == 0)
				{
					if (par1World.getBlockId(par2, par3 + 10, par4) == myId  & par1World.getBlockMetadata(par2, par3 + 10, par4) == 0)
					{
						par3 += 10;
					}
					else
					{
						par3 += 1;
					}

					boolean con = true;

					while (con & par1World.getBlockId(par2, par3 + 1, par4) != myId)
					{
						con = false;

						for (int a = -1; a <= 1; a += 1)
							for (int b = -1; b <= 1; b += 1)
							{
								if (par1World.getBlockId(par2 + a, par3 + 1, par4 + b) == myId & par1World.getBlockMetadata(par2 + a, par3 + 1, par4 + b) == 0)
								{
									con = true;
									par2 += a;
									par3 += 1;
									par4 += b;
								}
							}
					}
				}

			/*	if(destroy){
            	int i=par5Random.nextInt(4);
            	if(eatable(par1World,par2+dx[i],par3,par4+dz[i])){
            		destroy=false;
            		int count = 0;
            		for(int j=0;j<4;j+=1){
            			if(eatable(par1World,par2+dx[i]+dz[j],par3,par4+dz[i]+dz[j]))
            				count+=1;
            			if(count>=2) break;
            		}
            		if(count>=2)
            			par1World.setBlockWithNotify(par2+dx[i], par3, par4+dz[i], myId);
            	}
            }*/

			//			if(eatable(par1World,par2,par3+1,par4))
			//				destroy=false;

			if(!destroy)
				return;

			if (destroy)
			{
				int sx = par5Random.nextInt(2) * 2 - 1;
				int sz = par5Random.nextInt(2) * 2 - 1;

				int h = 1;
				for (int ddx = -h; ddx <= h; ddx += 1)
					for (int ddz  = -h; ddz <= h; ddz += 1)
					{
						if (eatable(par1World, par2 + sx * ddx, par3, par4 + sz * ddz) & !(par1World.getBlockMetadata(par2, par3, par4) == 1 & par1World.getBlockId(par2 + sx * ddx, par3, par4 + sz * ddz) == Block.cobblestone.blockID))
						{
							int count = 0;

							for (int j = 0; j < 4; j += 1)
							{
								if (par1World.getBlockId(par2 + sx * ddx + dx[j], par3, par4 + sz * ddz + dz[j]) == myId)
								{
									count += 1;
								}

								if (count >= 2)
								{
									break;
								}
							}

							if (count >= 2)
							{
								par1World.setBlockWithNotify(par2 + sx * ddx, par3, par4 + sz * ddz, myId);

								for (int a = -1; a <= 1 & destroy; a += 1)
									for (int b = -1; b <= 1 & destroy; b += 1)
										if (isLiquid(par1World, par2 + sx * ddx + a, par3, par4 + sz * ddz + b) | isLiquid(par1World, par2 + sx * ddx + a, par3 + 1, par4 + sz * ddz + b))
										{
											par1World.setBlockMetadataWithNotify(par2 + sx * ddx, par3, par4 + sz * ddz, 1);
											destroy = false;
										}

								destroy = false;
							}
						}
					}
			}

			//			if(destroy)
			//				destroy=par5Random.nextInt(5)==0;

			if (destroy)
				for (int a = -1; a <= 1 & destroy; a += 1)
					for (int b = -1; b <= 1 & destroy; b += 1)
						if (isLiquid(par1World, par2 + a, par3, par4 + b) | isLiquid(par1World, par2 + a, par3 + 1, par4 + b))
						{
							par1World.setBlockMetadata(par2, par3, par4, 1);
							destroy = false;
						}

			if (par1World.getBlockId(par2, par3 + 1, par4) == Block.sand.blockID |
					par1World.getBlockId(par2, par3 + 1, par4) == Block.gravel.blockID)
			{
				par1World.setBlockMetadata(par2, par3, par4, 1);
				destroy = false;
			}

			if (destroy)
			{
				boolean eat = false;

				for (int a = -1; a <= 1; a += 1)
					for (int b = -1; b <= 1; b += 1)
						if (par1World.getBlockId(par2 + a, par3 + 1, par4 + b) == myId & par1World.getBlockMetadata(par2 + a, par3 + 1, par4 + b) == 0)
						{
							if (par5Random.nextInt(5) != 0)
							{
								destroy = false;
							}

							return;
						}
			}

			/*			if(destroy){
            	for(int a=-1;a<=1;a+=1)
            		for(int b=-1;b<=1;b+=1)
            			for(int i=1;i<2 & destroy;i+=1)
            				if(par1World.getBlockId(par2+a,par3+i,par4+b)==myId)
            					destroy=false;
            }*/

			if (destroy)
			{
				if (eatable(par1World, par2, par3 - 1, par4))
				{
					par1World.setBlockWithNotify(par2, par3 - 1, par4, myId);
					destroy = false;
				}
			}

			if (destroy)
			{
				if (par1World.getBlockId(par2, par3 + 1, par4) == Block.snow.blockID)
				{
					par1World.setBlockWithNotify(par2, par3 + 1, par4, 0);
				}

				par1World.setBlockWithNotify(par2, par3, par4, 0);
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

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}


	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		if(!super.canPlaceBlockAt(par1World, par2, par3, par4) )
			return false;

		return !nearbySpawn(par1World, par2, par3, par4);
	}



	public boolean nearbySpawn(World par1World, int par2, int par3, int par4){


		if(par1World.provider.dimensionId==0){
			if(par1World.getSpawnPoint().getDistanceSquared(par2,par1World.getSpawnPoint().posY, par4)<64*64)
				return true;

			//			MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
			//
			//			if (par1World.getSaveHandler() instanceof SaveHandler)
			//			{
			//				SaveHandler temp = (SaveHandler)par1World.getSaveHandler();
			//				String[] t = temp.getAvailablePlayerDat();
			//
			//				for(int i = 0;i < t.length; i++){
			//					NBTTagCompound data = temp.getPlayerData(t[i]);
			//
			//					int dim = data.getInteger("Dimension");
			//
			//					if (data.hasKey("SpawnX") && data.hasKey("SpawnY") && data.hasKey("SpawnZ"))
			//					{
			//						if((par2-data.getInteger("SpawnX"))*(par2-data.getInteger("SpawnX"))+(par4-data.getInteger("SpawnZ"))*(par4-data.getInteger("SpawnZ"))<64*64){
			//							return true;
			//						}
			//					}				
			//				}
			//			}			
//
//			MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
//
//			if (server != null)
//			{
//				List t = server.getConfigurationManager().playerEntityList;
//				for(int i = 0;i < t.size(); i++){
//					EntityPlayerMP player = (EntityPlayerMP)t.get(i);
//					if(player.worldObj.provider.getRespawnDimension(player) == par1World.provider.dimensionId){
//						if(player.getBedLocation()!=null){
//							if(player.getBedLocation().getDistanceSquared(par2, player.getBedLocation().posY, par4)<64*64){
//								return true;
//							}
//						}
//					}
//				}
//			}
		}
		return false;
	}



	//	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z)
	//	{
	//		int myId=world.getBlockId(x, y, z);
	//		for(int dx=-1;dx<=1;dx+=1){
	//			for(int dy=-1;dy<=1;dy+=1){
	//				for(int dz=-1;dz<=1;dz+=1){
	//					if(world.getBlockId(x+dx, y+dy, z+dz)==myId){
	//						world.setBlockWithNotify(x+dx, y+dy, z+dz, 0);
	//					}
	//				}
	//			}
	//		}
	//		return world.setBlockWithNotify(x, y, z, 0);
	//	}
}
